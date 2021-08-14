package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (fullName != null && fullName.trim().length() != 0) {

            boolean isCreate = (uuid == null || uuid.trim().length() == 0);
            Resume resume;
            if (isCreate) {
                resume = new Resume(fullName);
            } else {
                resume = storage.get(uuid);
                resume.setFullName(fullName);
            }
            for (ContactType type : ContactType.values()) {
                String content = request.getParameter(type.name());
                if (content != null && content.trim().length() != 0) {
                    resume.addContact(type, content);
                } else
                    resume.getMapContacts().remove(type);
            }
            for (SectionType sectionType : SectionType.values()) {
                String value = request.getParameter(sectionType.name());
                String[] values = request.getParameterValues(sectionType.name());
                if (value != null && value.trim().length() != 0) {
                    switch (sectionType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            resume.addSection(sectionType, new TextSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATION:
                            String[] list = value.split("\n");
                            resume.addSection(sectionType, new ListSection(Arrays.stream(list).
                                    filter(s -> s.trim().length() != 0).collect(Collectors.toList())));
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            List<Organization> listOrganizations = new ArrayList<>();
                            String[] url = request.getParameterValues(sectionType.name() + "url");
                            for (int i = 0; i < values.length; i++) {
                                String name = values[i];
                                if (name != null) {
                                    String pref = sectionType.name() + i;
                                    List<Organization.Experience> experiences = new ArrayList<>();
                                    String[] startDate = request.getParameterValues(pref + "startDate");
                                    String[] endDate = request.getParameterValues(pref + "endDate");
                                    String[] title = request.getParameterValues(pref + "title");
                                    String[] description = request.getParameterValues(pref + "description");
                                    for (int j = 0; j < title.length; j++) {
                                        if (title[j] != null) {
                                            experiences.add(new Organization.Experience(DateUtil.parser(startDate[j]),
                                                    DateUtil.parser(endDate[j]), title[j], description[j]));
                                        }
                                    }
                                    listOrganizations.add(new Organization(new Link(name, url[i]), experiences));
                                }
                            }
                            resume.addSection(sectionType, new ListOrganizations(listOrganizations));
                            break;
                    }
                } else
                    resume.getMapSections().remove(sectionType);
            }
            if (isCreate) {
                storage.save(resume);
            } else {
                storage.update(resume);
            }
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "add":
                r = Resume.EMPTY;
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATION:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            ListOrganizations listOrg = (ListOrganizations) section;
                            List<Organization> organizations = new ArrayList<>();
                            organizations.add(Organization.EMPTY);
                            if (listOrg != null) {
                                for (Organization org : listOrg.getOrganizationList()) {
                                    List<Organization.Experience> experiences = new ArrayList<>();
                                    experiences.add(Organization.Experience.EMPTY);
                                    experiences.addAll(org.getList());
                                    organizations.add(new Organization(org.getHomePage(), experiences));
                                }
                            }
                            section = new ListOrganizations(organizations);
                            break;
                    }
                    r.addSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action" + action + "is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(("view".equals(action) ?
                "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")).forward(request, response);
    }
}