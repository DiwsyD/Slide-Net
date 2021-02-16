<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!-- header -->
<div class="header">
    <div class="content-container">
        <div class="head_holder">
            <div class="header_logo">
                <a href="/"><img src="media/images/logo.png" alt="logo-SlideNet" /></a>
            </div>
            <div class="header_menu">
                <ul>
                    <li><a href="/#about">About</a></li>
                    <li><a href="/#services">Services</a></li>
                    <li><a href="/#contacts">Contacts</a></li>
                </ul>
            </div>
            <div class="exit_button">
                <ul>
                    <li><a href="/sign?signAction=in"><c:out value="${role == null ? 'Log In' : 'OO'}"/> </a></li>
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