<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>User Cabinet</title>
    <c:import url="../user_imports.html" />
</head>
<body>
    <!-- header -->
    <c:import url="../header_user.html" />
    <!-- Account Information Block -->
    <div class="content-container">
        <div class="body-info">
            <div class="page_title">Personal Info</div>
            <div class="member_info">
                <div class="static_info">
                    <ul>
                        <li>Account ID:</li>
                        <li>Full Name:</li>
                        <li>Address:</li>
                        <li>Phone:</li>
                    </ul>
                </div>
                <div class="dynamic_info">
                    <ul>
                        <li>${account_data.getLogin()}</li>
                        <li>${account_data.getlName()}
                            ${account_data.getfName()}
                            ${account_data.getsName()}</li>
                        <li>${account_data.getAddress()}</li>
                        <li>${account_data.getPhoneNumber()}</li>
                    </ul>
                </div>
            </div>
            <div class="page_title">Account Info</div>
            <div class="tech_info">
                <div class="static_info">
                    <ul>
                        <li>Money Balance:</li>
                        <li>IP Address:</li>
                        <li>Account Status:</li>
                    </ul>
                </div>
                <div class="dynamic_info">
                    <ul>
                        <li>${account_data.getMoneyBalance()}</li>
                        <li>${account_data.getIpAddress()}</li>
                        <c:if test="${account_data.isAccountStatus() == true}">
                            <li style="color: limegreen">Active</li>
                        </c:if>
                        <c:if test="${account_data.isAccountStatus() == false}">
                            <li style="color: orangered">Inactive</li>
                        </c:if>
                    </ul>
                </div>
            </div>
            <div class="page_title">Services</div>
            <div class="services">
                <c:forEach var="service" items="${serviceList}">
                    <c:set var="activeServiceData" scope="session" value="${account_data.isServiceLinked(service)}" />
                    <div id="${service.getId()}" class="service">
                        <div class="page_subtitle">${service.getName()}</div>
                        <c:choose>
                            <c:when test="${activeServiceData != null}">
                                <ul>
                                    <li>Service Status:
                                        <a class="<c:out value="${activeServiceData.isStatus() ? 'active_value' : 'inactive_value'}"/>">
                                            <c:out value="${activeServiceData.isStatus() ? 'Active' : 'Inactive'}"/>
                                        </a>
<%--                                        <a class="remove_button" href="account_mng/user_cabinet?disable=${service.getId()}">Disable</a>--%>
                                        <button class="remove_button" name="disable" value="disable">
                                            Disable
                                        </button>
                                    </li>
                                    <li>
                                        Tariff:
                                        <c:if test="${service.getTariffById(activeServiceData.getTariffId()) != null}">
                                            <c:set var="activeTariffId" scope="session" value="${service.getTariffById(activeServiceData.getTariffId()).getId()}" />
                                        </c:if>
                                        <a>
                                            <select id="service_selection-" disabled>
                                                <c:forEach var="tariff" items="${service.getTariffList()}">
                                                    <option <c:out value="${tariff.getId() == activeTariffId ? 'selected' : ''}"/>
                                                            value="${tariff.getName()}">
                                                            ${tariff.getName()}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </a>
                                        <button class="edit_button" value="Activate">
                                            Edit
                                        </button>
                                    </li>
                                    <li>
                                        <c:choose>
                                            <c:when test="${activeServiceData.isStatus()}">
                                                Next Payment: <a>${activeServiceData.getNexPaymentDay()}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <button class="edit_button" value="Activate" onclick="activateService('${service.getId()}')">
                                                    Top-up
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <div>Service Status:
                                    <a class="inactive_value">Inactive</a>
                                    <button class="add_button" name="disable" value="disable">
                                        Activate
                                    </button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
<!-- footer -->
<c:import url="/cabinet/footer.html" />
</body>
</html>
