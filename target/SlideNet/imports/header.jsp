<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/localization_${language}"/>
<!-- header -->
<div class="header">
    <div class="content-container">
        <div class="head_holder">
            <div class="header_logo">
                <a href="/"><img src="/media/images/logo.png" alt="logo-SlideNet" /></a>
            </div>
            <div class="header_menu">
                <ul>
                    <c:if test="${role == 'admin'}">
                        <li><a href="/cabinet/admin/admin_cabinet"><fmt:message key="label.admincontrolpanel"/></a></li>
                        <li><a href="/cabinet/admin/account_mng"><fmt:message key="label.accountmng"/></a></li>
                        <li><a href="/cabinet/admin/service_mng"><fmt:message key="label.servicemng"/></a></li>
                    </c:if>
                    <c:if test="${role == 'user'}">
                        <li><a href="/cabinet/user/user_cabinet"><fmt:message key="label.accountinformation"/></a></li>
                        <li><a href="/cabinet/user/change_password"><fmt:message key="label.changepassword"/></a></li>
                        <li><a href="/cabinet/user/topup_balance"><fmt:message key="label.topupbalance"/></a></li>
                    </c:if>
                    <c:if test="${role == null}">
                        <li><a href="/#about"><fmt:message key="label.AboutUs"/></a></li>
                        <li><a href="/#services"><fmt:message key="label.Services"/></a></li>
                        <li><a href="/#contacts"><fmt:message key="label.Contacts"/></a></li>
                    </c:if>
                </ul>
            </div>
            <div class="log_button">
                <ul>
                    <li>
                        <a href="/sign?signAction=out">
                            <c:choose>
                                <c:when test="${role == null}">
                                    <fmt:message key="label.logIn"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="label.logOut"/>
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="header_languages">
                <select onchange="window.location.href=this.value;">
                    <c:forEach var="lang" items="${languages}">
                        <option <c:out value="${lang == language ? 'selected' : ''}" /> value="?language=${lang}"><a href="?language=${lang}"><fmt:message key="label.${lang}"/></a></option>
                    </c:forEach>
                </select>

<%--                <ul>--%>
<%--                    <li><a href="?language=ru"><fmt:message key="label.ru"/></a></li>--%>
<%--                    <li class="active"><a href="?language=en"><fmt:message key="label.en"/></a></li>--%>
<%--                </ul>--%>
            </div>
        </div>
    </div>
</div>