<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Subscriber Management</title>
    <c:import url="../admin_imports.html" />
</head>
<body>
<!-- header -->
<c:import url="../cabinet_header.jsp" />
<c:if test="${account != null}">
    <c:remove var="account" />
</c:if>
<c:if test="${account_id != null}">
    <c:remove var="account_id" />
</c:if>
<!-- User Table -->
<div class="content_table">
    <div  class="body-info">
        <div class="page_title">Slide-Net Subscribers</div>
        <div class="table">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Role</th>
                    <th>Login</th>
                    <th>Full Name</th>
                    <th>Mobile Number</th>
                    <th>Address</th>
                    <th>IP Address</th>
                    <th>Money Balance</th>
                    <th>Account Status</th>
                    <th>Control</th>
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
                                <p class="active_value">Active</p>
                            </c:if>
                            <c:if test="${account.isAccountStatus() == false}">
                                <p class="inactive_value">Inactive</p>
                            </c:if>

                        </td>
                        <td class="button-column">
                            <a class="edit_button" href="account_mng/account_editor?account_id=${account.getId()}">Edit</a>
                        </td>
                    </tr>
                </c:forEach>


                <tr class="add_new_account" id="account_adder">
                    <td colspan="12">
                        <a class="add_button" href="account_mng/account_editor?account_id=-1">Add</a>
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
                        <a class="active-link" href="account_mng?page=1">|<</a>
                        <a class="active-link-2" href="account_mng?page=${page-1}"><<</a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${page == maxPage}">
                        <a class="inactive-link">>></a>
                        <a class="inactive-link">>|</a>
                    </c:when>
                    <c:otherwise>
                        <a class="active-link-2" href="account_mng?page=${page+1}">>></a>
                        <a class="active-link" href="account_mng?page=${maxPage}">>|</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </div>
</div>
<!-- footer -->
<c:import url="/cabinet/footer.html" />
</body>
</html>