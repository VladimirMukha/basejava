<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title> ${resume.fullName}</title>
</head>

<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value= ${resume.uuid}>
        <dl>
            <dt>Имя</dt>
            <dd><input type="text" name="fullName" size="50" value=${resume.fullName}></dd>

        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%= ContactType.values()%>">
            <dl>
                <dt> ${type.title}</dt>
                <dd><input type="text" name=${type.name()} size="50" value=${resume.getContact(type)}></dd>
            </dl>
        </c:forEach>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Rollback</button>
        <h3>Секции</h3>
        <c:forEach var="section" items="<%=SectionType.values() %>">
            <d>
                <td>${section.title}</td>
                <dd><input type="text" name=${section.name()} size="50"></dd>
            </d>
        </c:forEach>
        </p>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
