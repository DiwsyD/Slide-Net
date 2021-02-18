<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/localization_${language}"/>
<html>
<head>
    <title><<fmt:message key="label.acceditor"/>></title>
    <c:import url="../../../imports/admin_imports.html" />
</head>
<body onload="init()">
<c:import url="../../../imports/header.jsp" />

<!-- Account Information Block -->
    <div class="content-container">
        <div class="body-info">
            <div class="page_title"><fmt:message key="label.editaccount"/></div>

            <form method="post">
                <div class="member_info">
                    <div class="static_info">
                        <ul>
                            <li>ID:</li>
                            <li><fmt:message key="label.role"/>:</li>
                            <li><fmt:message key="label.login"/>:</li>
                            <li><fmt:message key="label.password"/>:</li>

                            <li><fmt:message key="label.name"/>:</li>
                            <li><fmt:message key="label.lastname"/>:</li>
                            <li><fmt:message key="label.secondname"/>:</li>

                            <li><fmt:message key="label.phone"/>:</li>
                            <li><fmt:message key="label.Address"/>:</li>
                            <li><fmt:message key="label.ipaddress"/>:</li>
                            <li><fmt:message key="label.moneybalance"/>:</li>

                            <li><fmt:message key="label.accountstatus"/>:</li>
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
                                <button class="add_button" onclick="generatePasswordRequest()"><fmt:message key="label.generate"/></button>
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
                                        <option class="active_value" value="true" selected><fmt:message key="label.active"/></option>
                                        <option class="inactive_value" value="false"><fmt:message key="label.inactive"/></option>
                                    </c:if>
                                    <c:if test="${!account.isAccountStatus()}">
                                        <option class="active_value" value="true" ><fmt:message key="label.active"/></option>
                                        <option class="inactive_value" value="false" selected><fmt:message key="label.inactive"/></option>
                                    </c:if>
                                </select>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="save-cancel-editing">
                    <button type="submit" class="cancel_button" name="account_result" value="cancel">
                        <fmt:message key="label.cancel"/>
                    </button>
                    <button id="submit_button" type="submit" class="save_button" name="account_result" value="save">
                        <fmt:message key="label.save"/>
                    </button>
                </div>
            </form>

        </div>
    </div>
<!-- footer -->
<c:import url="/imports/footer.jsp" />
</body>
</html>
