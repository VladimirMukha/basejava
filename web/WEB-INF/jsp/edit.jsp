<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <link rel="short icon" href="img/3.jpg">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title> ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value=${resume.uuid}>
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
        <button class="new" type="submit">save</button>
        <button class="new" type="reset" onclick="window.history.back()">rollback</button>
        <h3>Секции</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <h2>${type.title}</h2>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <input type="text" name="${type.name()}" size=75 value="<%=section%>"/>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <input type="text" name="${type.name()}" size="75" value="<%=section%>"/>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT'|| type=='QUALIFICATION'}">
                    <textarea name='${type.name()}' rows="5"
                              cols=75><%=String.join("\n", ((ListSection) section).getTextContent()) %></textarea>
                </c:when>
                <c:when test="${type=='EDUCATION'|| type=='EXPERIENCE'}">
                    <c:forEach var="exp" items="<%=((ListOrganizations)section).getOrganizationList()%>"
                               varStatus="counter">
                        <dl>
                            <dt>Имя организации</dt>
                            <dd><input type="text" size="75" name="${type}" value="${exp.homePage.name}"></dd>
                            <dt>Сайт организации</dt>
                            <dd><input type="text" size="75" name="${type}url" value="${exp.homePage.url}"></dd>
                        </dl>
                        <c:forEach var="period" items="${exp.list}">
                            <jsp:useBean id="period" type="com.urise.webapp.model.Organization.Experience"/>
                            <dl>
                                <dt>Начало</dt>
                                <dd><input type="text" name="${type}${counter.index}startDate" size="10"
                                           value="<%=DateUtil.format(period.getStartDate())%>"
                                           placeholder="MM.yyyy"></dd>
                                <dt>Окончание</dt>
                                <dd><input type="text" name="${type}${counter.index}endDate" size="10"
                                           value="<%=DateUtil.format(period.getEndDate())%>"
                                           placeholder="MM/yyyy"></dd>
                                <dt>Должность</dt>
                                <dd><input type="text" name="${type}${counter.index}title" size="75"
                                           value="${period.title}"></dd>
                                <dt>Описание</dt>
                                <dd><input type="text" name="${type}${counter.index}description" size="75"
                                           value="${period.description}"></dd>
                            </dl>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button class="new" type="submit">save</button>
        <button class="new" type="reset" onclick="window.history.back()">rollback</button>
        <jsp:include page="fragments/footer.jsp"/>
        <p>&nbsp</p>
    </form>
</section>
</body>
</html>