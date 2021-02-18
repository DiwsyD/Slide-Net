<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/customtf" prefix="ctf" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/interface"/>
<!DOCTYPE>
<html lang="en">
<head>
    <title><<fmt:message key="label.signIn"/>></title>
    <link rel="stylesheet" href="css/signin.css">
    <link rel="stylesheet" href="css/fonts.css">
    <link rel="stylesheet" href="css/forms.css">
    <link rel="shortcut icon" href="media/images/icon.png" type="image/png">
</head>
<body>
<c:import url="imports/header.jsp"/>
    <!-- bodyBlock -->
    <div class="content-container">
        <div class="body-info">
            <div class="body_block">
                <!-- input block -->
                <div class="signin_block">
                    <div class="page_title"><fmt:message key="label.enter"/></div>
                    <div class="auth_result">
                        <c:if test="${AuthorizationResultError}">
                            <fmt:message key="label.wrongLP"/>
                        </c:if>
                    </div>
                    <div class="signin_form">
                        <form method="post">
                            <label>
                                <input type="text" name="login" value="" placeholder="Slidenet ID">
                            </label>
                            <br>
                            <label>
                                <input type="password" name="pass" value="" placeholder="Password">
                            </label>
                            <br>
                            <button type="submit">
                                <fmt:message key="label.signIn"/>
                            </button>
                        </form>
                        <div class="disclaimer">
                            <fmt:message key="label.disclaimer"/>
                        </div>
                    </div>
                </div>
                <!-- Information block -->
                <div class="info_block">
                    <div class="page_title"><fmt:message key="label.haveaquestion"/></div>
                    <div class="info_text">
                        <ul>
                            <li>-<fmt:message key="label.infocenter"/>: 0800-31-0800 (24/7)</li>
                            <li>-<fmt:message key="label.infocunsult"/>: abonontdel@slidenet.ua</li>
                            <li>-<fmt:message key="label.techconsult"/>: support@slidenet.ua</li>
                            <li>-<fmt:message key="label.customercenter"/>: Ukraine, Kharkiv, Nauki Ave 43B</li>
                        </ul>
                        <div class="disclaimer"><fmt:message key="label.workinghours"/></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
