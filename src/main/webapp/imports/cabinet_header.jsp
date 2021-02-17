<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!-- header -->
<div class="header">
    <div class="content-container">
        <div class="head_holder">
            <div class="header_logo">
                <a href="/"><img src="/media/images/logo.png" alt="logo-SlideNet" /></a>
            </div>
            <div class="header_menu">
                <ul>
                    <c:choose>
                        <c:when test="${role == 'admin'}">
                            <li><a href="/cabinet/admin/admin_cabinet">Admin Control Panel</a></li>
                            <li><a href="/cabinet/admin/account_mng">Subscriber Management</a></li>
                            <li><a href="/cabinet/admin/service_mng">Service Management</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="/cabinet/user/user_cabinet">Account Information</a></li>
                            <li><a href="/cabinet/user/change_password">Change Password</a></li>
                            <li><a href="/cabinet/user/topup_balance">Top Up Balance</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
            <div class="log_button">
                <ul>
                    <li><a href="/sign?signAction=out">Log Out</a></li>
                </ul>
            </div>
            <div class="header_languages">
                <ul>
                    <li><a href="/">Rus</a></li>
                    <li class="active"><a href="/">Eng</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>