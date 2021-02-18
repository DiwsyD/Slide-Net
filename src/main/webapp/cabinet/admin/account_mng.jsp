<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="own" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/customtf" prefix="ctf" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/${localization_file}${language}"/>
<html>
<head>
    <title><<fmt:message key="label.accountmng"/>></title>
    <c:import url="../../imports/admin_imports.html" />
</head>
<body>
<!-- header -->
<c:import url="../../imports/header.jsp" />
<c:if test="${account != null}">
    <c:remove var="account" />
</c:if>
<c:if test="${account_id != null}">
    <c:remove var="account_id" />
</c:if>
<!-- User Table -->
<div class="content_table">
    <div  class="body-info">
        <div class="page_title"><fmt:message key="label.slidenetsubscribers"/></div>
        <div class="table">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th><fmt:message key="label.role"/></th>
                    <th><fmt:message key="label.login"/></th>
                    <th><fmt:message key="label.fullname"/></th>
                    <th><fmt:message key="label.phone"/></th>
                    <th><fmt:message key="label.Address"/></th>
                    <th><fmt:message key="label.ipaddress"/></th>
                    <th><fmt:message key="label.moneybalance"/></th>
                    <th><fmt:message key="label.accountstatus"/></th>
                    <th><fmt:message key="label.control"/></th>
                </tr>
                </thead>
                <tbody>


                <c:forEach items="${accountList}" var="account">
                    <tr>
                        <td>${account.getId()}</td>
                        <td>${account.getRoleName()}</td>
                        <td>${account.getLogin()}</td>
                        <td>
                                ${account.getlName()}
                                ${account.getfName()}
                                ${account.getsName()}
                        </td>
                        <td>${account.getPhoneNumber()}</td>
                        <td>${account.getAddress()}</td>
                        <td>${account.getIpAddress()}</td>
                        <td>${account.getMoneyBalance()}</td>
                        <td>
                            <c:if test="${account.isAccountStatus() == true}">
                                <p class="active_value"><fmt:message key="label.active"/></p>
                            </c:if>
                            <c:if test="${account.isAccountStatus() == false}">
                                <p class="inactive_value"><fmt:message key="label.inactive"/></p>
                            </c:if>

                        </td>
                        <td class="button-column">
                            <a class="edit_button" href="account_mng/account_editor?account_id=${account.getId()}"><fmt:message key="label.edit"/></a>
                        </td>
                    </tr>
                </c:forEach>


                <tr class="add_new_account" id="account_adder">
                    <td colspan="12">
                        <a class="add_button" href="account_mng/account_editor?account_id=-1"><fmt:message key="label.add"/></a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="pagination-block">
            <div class="pagination-control">
                <hr>
                <c:choose>
                    <c:when test="${page == 1}">
                        <a class="inactive-link">|<</a>
                        <a class="inactive-link"><<</a>
                    </c:when>
                    <c:otherwise>
                        <a class="active-link" href="${uri}?page=1">|<</a>
                        <a class="active-link-2" href="${uri}?page=${page-1}"><<</a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${page == maxPage}">
                        <a class="inactive-link">>></a>
                        <a class="inactive-link">>|</a>
                    </c:when>
                    <c:otherwise>
                        <a class="active-link-2" href="${uri}?page=${page+1}">>></a>
                        <a class="active-link" href="${uri}?page=${maxPage}">>|</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </div>
</div>
<!-- footer -->
<c:import url="/imports/footer.jsp" />
</body>
</html>