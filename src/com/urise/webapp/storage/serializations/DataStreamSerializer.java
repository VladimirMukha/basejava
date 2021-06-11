package com.urise.webapp.storage.serializations;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StrategyInterface {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            Map<ContactType, String> mapContacts = resume.getMapContacts();
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeCollections(dos, mapContacts.entrySet(), contacts -> {
                dos.writeUTF(contacts.getKey().name());
                dos.writeUTF(contacts.getValue());
            });

//            for (Map.Entry<ContactType, String> contact : mapContacts.entrySet()) {
//                dos.writeUTF(contact.getKey().name());
//                dos.writeUTF(contact.getValue());
//
//            }

            //    Map<SectionType, AbstractSection> mapSections = resume.getMapSections();


//            for (Map.Entry<SectionType, AbstractSection> sectionEntry : mapSections.entrySet()) {
//                dos.writeUTF(sectionEntry.getKey().name());
//                SectionType sectionType = sectionEntry.getKey();
//                AbstractSection section = sectionEntry.getValue();

            writeCollections(dos, resume.getMapSections().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case QUALIFICATION:
                    case ACHIEVEMENT:

                        writeCollections(dos, ((ListSection) section).getTextContent(), dos::writeUTF);

//                        dos.writeInt(((ListSection) section).getTextContent().size());
//                        for (String list : ((ListSection) section).getTextContent()) {
//                            dos.writeUTF(list);
//                        }
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeCollections(dos, ((ListOrganizations) section).getOrganizationList(), s -> {
                            dos.writeUTF(s.getHomePage().getName());
                            dos.writeUTF(s.getHomePage().getUrl());
                            writeCollections(dos, s.getList(), experience -> {
                                writeLocalDate(dos, experience.getStartDate());
                                writeLocalDate(dos, experience.getEndDate());
                                dos.writeUTF(experience.getTitle());
                                dos.writeUTF(experience.getDescription());
                            });
                        });
                        break;


//                        dos.writeInt(((ListOrganizations) section).getOrganizationList().size());
//                        for (Organization organization : ((ListOrganizations) section).getOrganizationList()) {
//                            dos.writeUTF(organization.getHomePage().getName());
//                            dos.writeUTF(organization.getHomePage().getUrl());
//
//                            for (Organization.Experience exception : organization.getList()) {
//                                writeLocalDate(dos, exception.getStartDate());
//                                writeLocalDate(dos, exception.getEndDate());
//                                dos.writeUTF(exception.getTitle());
//                                dos.writeUTF(exception.getDescription());
//                            }
//                        }


                }
            });
        }
    }

    interface WriteElement<T> {
        void write(T element) throws IOException;

    }

    public <T> void writeCollections(DataOutputStream dos, Collection<T> collection, WriteElement<T> element) throws IOException {
        dos.writeInt(collection.size());
        for (T elem : collection) {
            element.write(elem);
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate startDate) throws IOException {
        dos.writeInt(startDate.getYear());
        dos.writeInt(startDate.getMonth().getValue());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    public Resume doRead(InputStream is) throws IOException {

        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readItems(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, getSectionItem(dis, sectionType));
            });
            return resume;
        }
    }

    public AbstractSection getSectionItem(DataInputStream dis, SectionType sectionType) throws IOException {

        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATION:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new ListOrganizations(
                        readList(dis, () -> new Organization(new Link(dis.readUTF(), dis.readUTF()),
                                readList(dis, () -> new Organization.Experience(
                                        readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()))
                        )));
            default:
                throw new IllegalStateException();
        }
    }

    interface Element {
        void doItems() throws IOException;
    }

    public void readItems(DataInputStream dis, Element element) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            element.doItems();
        }
    }

    interface ConsumerList<T> {
        T getList() throws IOException;
    }

    public <T> List<T> readList(DataInputStream dio, ConsumerList<T> consumerList) throws IOException {
        int size = dio.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(consumerList.getList());
        }
        return list;
    }
}