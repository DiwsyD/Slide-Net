<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!-- footer -->
<div class="footer">
    <div class="content-container">
        <div class="footer_menu">
            <ul>
                <li><a href="/#about">About</a></li>
                <li><a href="/#services">Services</a></li>
                <li><a href="/#contacts">Contacts</a></li>
            </ul>
        </div>
        <div class="footer_languages">
            <ul>
                <li><a href="/">Rus</a></li>
                <li class="active"><a href="/">Eng</a></li>
            </ul>
        </div>
        <hr>
        <div class="log_button">
            <ul>
                <c:choose>
                    <c:when test="${role == null}">
                        <li><a href="/sign?signAction=in">Log In</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/sign?signAction=out">Log Out</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</div>