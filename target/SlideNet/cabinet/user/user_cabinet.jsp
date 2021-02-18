<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/customtf" prefix="ctf" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/interface"/>
<html>
<head>
    <title><<fmt:message key="label.usercabinet"/>></title>
    <c:import url="../../imports/user_imports.html" />
</head>
<body>
    <!-- header -->
    <c:import url="../../imports/header.jsp" />
    <!-- Account Information Block -->
    <div class="content-container">
        <div class="body-info">
            <div class="page_title"><fmt:message key="label.perosnalinfo"/></div>
            <div class="member_info">
                <div class="static_info">
                    <ul>
                        <li><fmt:message key="label.accountid"/>:</li>
                        <li><fmt:message key="label.fullname"/>:</li>
                        <li><fmt:message key="label.Address"/>:</li>
                        <li><fmt:message key="label.phone"/>:</li>
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
            <div class="page_title"><fmt:message key="label.accountinfo"/></div>
            <div class="tech_info">
                <div class="static_info">
                    <ul>
                        <li><fmt:message key="label.moneybalance"/>:</li>
                        <li><fmt:message key="label.ipaddress"/>:</li>
                        <li><fmt:message key="label.accountstatus"/>:</li>
                    </ul>
                </div>
                <div class="dynamic_info">
                    <ul>
                        <li>${account_data.getMoneyBalance()}</li>
                        <li>${account_data.getIpAddress()}</li>
                        <c:if test="${account_data.isAccountStatus() == true}">
                            <li style="color: limegreen"><fmt:message key="label.active"/></li>
                        </c:if>
                        <c:if test="${account_data.isAccountStatus() == false}">
                            <li style="color: orangered"><fmt:message key="label.inactive"/></li>
                        </c:if>
                    </ul>
                </div>
            </div>
            <div class="page_title"><fmt:message key="label.services"/></div>
            <div class="services">
                <c:forEach var="service" items="${serviceList}">

                    <c:set var="activeServiceData" scope="session" value="${account_data.isServiceLinked(service)}" />
                    <div id="${service.getId()}" class="service">
                        <div class="page_subtitle"><fmt:message key="label.${service.getName()}"/></div>
                        <c:choose>
<%--                            If this service is activated on account --%>
                            <c:when test="${activeServiceData != null}">

                                <ul>
                                    <%-- Show service status and Disable Button --%>
                                    <li>
                                        <a><fmt:message key="label.servicestatus"/>:</a>
                                        <a class="<c:out value="${activeServiceData.isStatus() ? 'active_value' : 'inactive_value'}"/>">
                                            <c:choose>
                                                <c:when test="${activeServiceData.isStatus()}">
                                                    <fmt:message key='label.active'/>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key='label.inactive'/>
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                        <a class="remove_button" href="user_cabinet?action=disable&serviceId=${service.getId()}"><fmt:message key='label.disable'/></a>
                                    </li>

                                    <%-- Check all Tariffs and find activated one --%>
                                    <li>    <%-- If found active tariff: show it's name, description and Edit button to able to change Tariff ---%>
                                        <c:forEach var="tariff" items="${service.getTariffList()}">
                                            <c:if test="${tariff.getId() == activeServiceData.getTariffId()}">
                                                <p>
                                                    <a><fmt:message key='label.tariff'/>:</a> <a style="color: #646cf1">${tariff.getName()}</a>
                                                    <a class="edit_button" href="user_cabinet?action=edit&serviceId=${service.getId()}&tariffId=${tariff.getId()}"><fmt:message key='label.edit'/></a>
                                                </p>
                                                <p>
                                                    <a><fmt:message key="label.Description"/>:</a> <a style="color: #646cf1">${tariff.getDescription()}</a>
                                                </p>
                                                <p>
                                                    <a><fmt:message key="label.nextpayment"/>:</a> <a style="color: #f1a164">${activeServiceData.getNexPaymentDay()}</a>
                                                </p>
                                                <p>
                                                    <c:choose>
                                                        <c:when test="${activeServiceData.isStatus()}">
                                                            <a class="remove_button" href="user_cabinet?action=pause&serviceId=${service.getId()}"><fmt:message key="label.pause"/></a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:choose>
                                                                <%-- enough money--%>
                                                                <c:when test="${(account_data.getMoneyBalance() >= activeServiceData.getPaymentAmount()) || activeServiceData.isPayed()}">
                                                                    <a class="add_button" href="user_cabinet?action=start&serviceId=${service.getId()}"><fmt:message key="label.start"/></a
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a class="edit_button" href="/cabinet/user/topup_balance"><fmt:message key="label.topup"/></a
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
                                <div>
                                    <a><fmt:message key="label.servicestatus"/></a>:
                                    <a class="inactive_value"><fmt:message key="label.inactive"/></a>
                                    <a class="add_button" href="user_cabinet?action=activate&serviceId=${service.getId()}"><fmt:message key="label.activate"/></a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>


                </c:forEach>
            </div>
        </div>
    </div>
    <!-- footer -->
    <c:import url="/imports/footer.jsp" />
</body>
</html>
