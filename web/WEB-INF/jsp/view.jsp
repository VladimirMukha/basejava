<%@ page import="com.urise.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}:<a href="resume?uuid=${resume.uuid}&action=edit">Edit </a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.mapContacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType,java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue()) %> </br>
        </c:forEach>
    </p>

    <c:forEach var="sectionEntry" items="${resume.mapSections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType,com.urise.webapp.model.AbstractSection>"/>
        <c:set var="typeSection" value="${ sectionEntry.key }"/>
        <c:set var="contentSection" value="${sectionEntry.value}"/>
        <jsp:useBean id="contentSection" type="com.urise.webapp.model.AbstractSection"/>
        <tr>
            <td><h3>${typeSection.title}</h3></td>
        </tr>
        <c:choose>
            <c:when test="${typeSection=='OBJECTIVE'|| typeSection=='PERSONAL'}">
                <tr>
                    <td>
                        <%=((TextSection) contentSection).getText()%>
                    </td>
                </tr>
            </c:when>
            <c:when test="${ typeSection=='QUALIFICATION'||typeSection=='ACHIEVEMENT'}">
                <tr>
                    <td>
                        <ul>
                            <c:forEach var="list" items="<%= ((ListSection)contentSection).getTextContent()%>">
                                <li>${list}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:when>
            <c:when test="${typeSection=='EDUCATION'|| typeSection=='EXPERIENCE'}">
                <c:forEach var="organ" items="<%=((ListOrganizations)contentSection).getOrganizationList()%>">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${empty organ.homePage.url }">
                                    ${organ.homePage.name}
                                </c:when>
                                <c:otherwise>
                                    <a href="${organ.homePage.url}">${organ.homePage.name}</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <c:forEach var="exp" items="${organ.list}">
                        <tr>
                            <td>${exp.startDate}<%=" : " %>
                                    ${exp.endDate}</td>
                        </tr>
                        <tr>
                            <td>${exp.title}</td>
                            <td>${exp.description}<br></td
                        </tr>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
    <p>
        <button class="new" onclick="window.history.back()">back</button>
    </p>
    <p>&nbsp</p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>