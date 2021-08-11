<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.urise.webapp.model.ContactType" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body vlink="#87cefa" link="#e6e6fa" alink="#006400">
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<img>
<table class="table" width="60%" style="color:orangered" border="12" cellpadding="10" cellspacing="12">
    <p>
        <button class="new"><a style="color:orangered "
                               href="resume?uuid=${resume.uuid}&action=add">Add new Resume</a></button>
    </p>
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Delete</th>
        <th>Edit</th>
    </tr>
    <c:forEach items="${resumes}" var="resume">
        <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
        <tr>
            <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
            <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
            </td>
            <td><a href="resume?uuid=${resume.uuid}&action=delete">Delete</a></td>
            <td><a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></td>
        </tr>
    </c:forEach>
</table>
<div><img aria-orientation="vertical" height="250" width="350" src="img/3.jpg"></div>

</section>

<p>


</p>
<p>

    <jsp:include page="fragments/footer.jsp"/>
</p>
</body>
</html>