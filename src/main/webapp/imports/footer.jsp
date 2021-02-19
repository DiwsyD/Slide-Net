<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/${localization_file}${language}"/>
<!-- footer -->
<div class="footer">
    <div class="content-container">
        <div class="footer_menu">
            <ul>
                <li><a href="/#about"><fmt:message key="label.AboutUs"/></a></li>
                <li><a href="/#services"><fmt:message key="label.Services"/></a></li>
                <li><a href="/#contacts"><fmt:message key="label.Contacts"/></a></li>
            </ul>
        </div>
        <div class="footer_languages">
            <ul>

                <c:forEach var="lang" items="${languages}">
                    <li><a href="?language=${lang}"><fmt:message key="label.${lang}"/></a></li>
                </c:forEach>
            </ul>
        </div>
        <hr>
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
    </div>
</div>