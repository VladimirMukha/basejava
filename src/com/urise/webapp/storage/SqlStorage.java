package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecute(con -> {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO resume (uuid, full_name)" + " VALUES (?,?)");) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            initContact(con, resume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionExecute(con -> {
            PreparedStatement ps = con.prepareStatement("UPDATE resume SET full_name = ?  WHERE uuid = ?");
            String uuid = resume.getUuid();
            ps.setString(1, resume.getFullName());
            ps.setString(2, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            deleteContact(con, resume);
            initContact(con, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.getConnection(
                "SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "ON r.uuid= c.resume_uuid " +
                        "WHERE uuid=?", ps -> {
                    ps.setString(1, uuid);
                    ResultSet resultSet = ps.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        addContact(resultSet, resume);
                    } while (resultSet.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.getConnection("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.getConnection("SELECT * FROM  resume r " +
                "LEFT JOIN contact c on r.uuid = c.resume_uuid" +
                " ORDER BY full_name,uuid", ps -> {

            ResultSet resultSet = ps.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();

            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                Resume resume = map.get(uuid);
                if (resume == null) {
                    resume = new Resume(uuid, resultSet.getString("full_name"));
                    map.put(uuid, resume);
                }
                addContact(resultSet, resume);
            }
            return new CopyOnWriteArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.getConnection("SELECT count(*) FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    public void initContact(Connection con, Resume resume) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO " +
                "contact (resume_uuid, type, value) " + "VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getMapContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contact.getKey().name());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void addContact(ResultSet res, Resume resume) throws SQLException {
        String value = res.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(res.getString("type")), value);
        }
    }

    public void deleteContact(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE  FROM contact where resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}