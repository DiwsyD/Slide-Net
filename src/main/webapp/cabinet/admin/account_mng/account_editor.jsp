<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<jsp:useBean id="accounts" class="app.entity.Account" scope="page" />
<html>
<head>
    <title>Account Editor</title>
    <c:import url="../../../imports/admin_imports.html" />
</head>
<body onload="init()">
<c:import url="../../../imports/cabinet_header.jsp" />

<!-- Account Information Block -->
    <div class="content-container">
        <div class="body-info">
            <div class="page_title">Edit Account</div>

            <form method="post">
                <div class="member_info">
                    <div class="static_info">
                        <ul>
                            <li>ID:</li>
                            <li>Role</li>
                            <li>Login:</li>
                            <li>Password:</li>

                            <li>First Name:</li>
                            <li>Last Name:</li>
                            <li>Second Name:</li>

                            <li>Mobile Number:</li>
                            <li>Address:</li>
                            <li>IP Address:</li>
                            <li>Money Balance:</li>

                            <li>Account Status:</li>
                        </ul>
                    </div>
                    <div class="dynamic_info">
                        <ul class="input_info_fields">
                            <li>${account.getId()}</li>
                            <li>${account.getRoleName()}</li>
                            <li>${account.getLogin()}</li>
                            <!--  -->
                            <li>
                                <input id="new_password" type="text" name="new_password" placeholder="New Password"readonly class="input-account-data" value="">
                                <button class="add_button" onclick="generatePasswordRequest()">Generate</button>
                            </li>
                            <!--  -->
                            <li><input data-rule="text" type="text" name="first_name" placeholder="Name" class="input-account-data" value="${account.getfName()}"></li>
                            <li><input data-rule="text" type="text" name="last_name" placeholder="Surname" class="input-account-data"value="${account.getlName()}"></li>
                            <li><input data-rule="text" type="text" name="second_name" placeholder="Second Name" class="input-account-data"value="${account.getsName()}"></li>
                            <!--  -->
                            <li><input id="phone" data-rule="phone" type="text" name="phone_number" placeholder="Phone Number" class="input-account-data"value="${account.getPhoneNumber()}"></li>
                            <li><input data-rule="text" type="text" name="address" placeholder="Address" class="input-account-data"value="${account.getAddress()}"></li>
                            <li><input id="ip" data-rule="ip" type="text" name="ip_address" placeholder="Ip Address" class="input-account-data"value="${account.getIpAddress()}"></li>
                            <li><input data-rule="number" type="text" name="money_balance" placeholder="Money Balance" class="input-account-data"value="${account.getMoneyBalance()}"></li>

                            <li>
                                <select name="account_status" class="select_account_status">
                                    <c:if test="${account.isAccountStatus()}">
                                        <option class="active_value" value="true" selected>Active</option>
                                        <option class="inactive_value" value="false">Inactive</option>
                                    </c:if>
                                    <c:if test="${!account.isAccountStatus()}">
                                        <option class="active_value" value="true">Active</option>
                                        <option class="inactive_value" value="false" selected>Inactive</option>
                                    </c:if>
                                </select>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="save-cancel-editing">
                    <button type="submit" class="cancel_button" name="account_result" value="cancel">
                        Cancel
                    </button>
                    <button id="submit_button" type="submit" class="save_button" name="account_result" value="save">
                        Save
                    </button>
                </div>
            </form>

        </div>
    </div>
<!-- footer -->
<c:import url="/imports/footer.jsp" />
</body>
</html>
