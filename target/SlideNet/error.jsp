<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/customtf" prefix="ctf" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/${localization_file}${language}"/>
<html>
<head>
    <title><<fmt:message key="label.notfound"/>></title>
    <link rel="stylesheet" href="css/fonts.css">
    <link rel="stylesheet" href="css/error.css">
    <link rel="shortcut icon" href="media/images/error_icon.png" type="image/png">
</head>
<body>

    <div class="ErrorMassage">
        <h1><fmt:message key="label.sorry"/>!</h1>
        <h2><fmt:message key="label.errorsmth"/> :(</h2>

        <c:if test="${ErrorCause != null}">
            <h2><c:out value="${ErrorCause}"/></h2>
        </c:if>
        <a href="/"><fmt:message key="label.back"/></a>
    </div>
</body>
</html>
