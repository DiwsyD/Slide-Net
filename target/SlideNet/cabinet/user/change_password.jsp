<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Change Password</title>
    <c:import url="../../imports/user_imports.html" />
</head>
<body>
    <!-- header -->
    <c:import url="../../imports/cabinet_header.jsp" />
    <div class="content-container">
        <div class="body-info">
            <div class="page_title">Change Password</div>
            <div class="any-form">
                <div id="invalid-data-error">
                    <c:if test="${changePasswordError}">
                        Wrong old password or new password repeats the old password!
                    </c:if>
                </div>
                <form method="post" onsubmit="checkPasswordChange()">
                    <label>
                        <p>Old Password:</p>
                        <input data-rule="text" type="password" name="oldPass" value="" placeholder="Current Password">
                    </label>
                    <br>
                    <label>
                        <p>New Password:</p>
                        <input data-rule="text" type="password" name="newPass" value="" placeholder="New Password">
                    </label>
                    <br>
                    <label>
                        <p>Repeat New Password:</p>
                        <input data-rule="text" type="password" name="newPassRepeat" value="" placeholder="Repeat New Password">
                    </label>
                    <br>
                    <button class="save_button" id="change_password_form" type="submit">Save</button>
                </form>
            </div>
        </div>
    </div>
    <!-- footer -->
    <c:import url="/imports/footer.jsp" />
</body>
</html>
