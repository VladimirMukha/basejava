package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("not driver found for postgresql.Driver", e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecute(con -> {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO resume (uuid, full_name)" + " VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            initContact(con, resume);
            initSections(con, resume);
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
            deleteSections(con, resume);
            initSections(con, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionExecute(con -> {
            Resume resume;
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM resume WHERE uuid=?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM contact WHERE resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    addContact(resultSet, resume);
                }
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM section WHERE resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    addSection(resultSet, resume);
                }
            }
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
        return sqlHelper.transactionExecute(con -> {
            Map<String, Resume> mapResumes = new LinkedHashMap<>();
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM  resume")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    mapResumes.put(uuid, new Resume(uuid, resultSet.getString("full_name")));
                }
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM contact")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    Resume resume = mapResumes.get(resultSet.getString("resume_uuid"));
                    addContact(resultSet, resume);
                }
            }

            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM section")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    Resume resume = mapResumes.get(resultSet.getString("resume_uuid"));
                    addSection(resultSet, resume);
                }
            }
            return new ArrayList<>(mapResumes.values());
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
        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value)VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getMapContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contact.getKey().name());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void initSections(Connection con, Resume resume) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO section (resume_uuid, type, value)VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> sections : resume.getMapSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, sections.getKey().name());
                AbstractSection value = sections.getValue();
                ps.setString(3, JsonParser.write(value, AbstractSection.class));
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

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String content = rs.getString("value");
        if (content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            resume.addSection(type, JsonParser.reade(content, AbstractSection.class));
        }

    }

    public void deleteContact(Connection connection, Resume resume) throws SQLException {
        deleteItems(connection, resume, "DELETE  FROM contact WHERE resume_uuid=?");
    }

    public void deleteSections(Connection connection, Resume resume) throws SQLException {
        deleteItems(connection, resume, "DELETE FROM section WHERE resume_uuid= ?");
    }

    public void deleteItems(Connection connection, Resume resume, String sql) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}