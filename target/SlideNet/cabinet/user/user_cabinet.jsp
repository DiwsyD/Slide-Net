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
    <c:import url="../cabinet_header.jsp" />
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
<%--                            If this service is activated on account --%>
                            <c:when test="${activeServiceData != null}">

                                <ul>
                                    <%-- Show service status and Disable Button --%>
                                    <li>
                                        <a>Service Status:</a>
                                        <a class="<c:out value="${activeServiceData.isStatus() ? 'active_value' : 'inactive_value'}"/>">
                                            <c:out value="${activeServiceData.isStatus() ? 'Active' : 'Inactive'}"/>
                                        </a>
                                        <a class="remove_button" href="user_cabinet?action=disable&serviceId=${service.getId()}">Disable</a>
                                    </li>

                                    <%-- Check all Tariffs and find activated one --%>
                                    <li>    <%-- If found active tariff: show it's name, description and Edit button to able to change Tariff ---%>
                                        <c:forEach var="tariff" items="${service.getTariffList()}">
                                            <c:if test="${tariff.getId() == activeServiceData.getTariffId()}">
                                                <p>
                                                    <a>Tariff:</a> <a style="color: #646cf1">${tariff.getName()}</a>
                                                    <a class="edit_button" href="user_cabinet?action=edit&serviceId=${service.getId()}&tariffId=${tariff.getId()}">Edit</a>
                                                </p>
                                                <p>
                                                    <a>Description:</a> <a style="color: #646cf1">${tariff.getDescription()}</a>
                                                </p>
                                                <p>
                                                    <a>Next Payment:</a> <a style="color: #f1a164">${activeServiceData.getNexPaymentDay()}</a>
                                                </p>
                                                <p>
                                                    <c:choose>
                                                        <c:when test="${activeServiceData.isStatus()}">
                                                            <a class="remove_button" href="user_cabinet?action=pause&serviceId=${service.getId()}">Pause</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:choose>
                                                                <%-- enough money--%>
                                                                <c:when test="${(account_data.getMoneyBalance() >= activeServiceData.getPaymentAmount()) || activeServiceData.isPayed()}">
                                                                    <a class="add_button" href="user_cabinet?action=start&serviceId=${service.getId()}">Start</a
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a class="edit_button" href="/cabinet/user/topup_balance">Top-up</a
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </c:if>
                                        </c:forEach>
                                    </li>

                                </ul>
                            </c:when>


                            <c:otherwise>
                                <div>Service Status:
                                    <a class="inactive_value">Inactive</a>
                                    <a class="add_button" href="user_cabinet?action=activate&serviceId=${service.getId()}">Activate</a>
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
