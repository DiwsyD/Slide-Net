<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/${localization_file}${language}"/>
<html>
<head>
    <title><<fmt:message key="label.changepassword"/>></title>
    <c:import url="../../imports/user_imports.html" />
</head>
<body>
    <!-- header -->
    <c:import url="../../imports/header.jsp" />
    <div class="content-container">
        <div class="body-info">
            <div class="page_title"><fmt:message key="label.changepassword"/></div>
            <div class="any-form">
                <div id="invalid-data-error">
                    <c:if test="${changePasswordError}">
                        <fmt:message key="label.errorchangepassword"/>
                    </c:if>
                </div>
                <form method="post" onsubmit="checkPasswordChange()">
                    <label>
                        <p><fmt:message key="label.currpass"/>:</p>
                        <input data-rule="text" type="password" name="oldPass" value="" placeholder='<fmt:message key="label.currpass"/>'>
                    </label>
                    <br>
                    <label>
                        <p><fmt:message key="label.newpass"/>:</p>
                        <input data-rule="text" type="password" name="newPass" value="" placeholder='<fmt:message key="label.newpass"/>'>
                    </label>
                    <br>
                    <label>
                        <p><fmt:message key="label.repnewpass"/>:</p>
                        <input data-rule="text" type="password" name="newPassRepeat" value="" placeholder='<fmt:message key="label.repnewpass"/>'>
                    </label>
                    <br>
                    <button class="save_button" id="change_password_form" type="submit"><fmt:message key="label.save"/></button>
                </form>
            </div>
        </div>
    </div>
    <!-- footer -->
    <c:import url="/imports/footer.jsp" />
</body>
</html>
