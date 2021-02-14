<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE>
<html lang="en">
<head>
    <title>Sign In</title>
    <link rel="stylesheet" href="css/signin.css">
    <link rel="stylesheet" href="css/fonts.css">
    <link rel="shortcut icon" href="media/images/icon.png" type="image/png">
</head>
<body>
    <!-- header -->
    <div class="header">
        <div class="content-container">
            <div class="head_holder">
                <div class="header_logo">
                    <a href="/"><img src="media/images/logo.png" alt="logo-SlideNet" /></a>
                </div>
                <div class="header_menu">
                    <ul>
                        <li><a href="#about">About</a></li>
                        <li><a href="#services">Services</a></li>
                        <li><a href="#contacts">Contacts</a></li>
                    </ul>
                </div>
                <div class="exit_button">
                    <ul>
                        <li><a href="/sign?signAction=in">Log In</a></li>
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
    <!-- bodyBlock -->
    <div class="content-container">
        <div class="body-info">
            <div class="body_block">
                <!-- input block -->
                <div class="signin_block">
                    <div class="page_title">Enter to private cabinet</div>
                    <div class="auth_result">
                        <c:if test="${AuthorizationResultError}">
                            Wrong Login or Password!
                        </c:if>
                    </div>
                    <div class="signin_form">
                        <form method="post">
                            <label>
                                <input type="text" name="login" value="254658" placeholder="Slidenet ID">
                            </label>
                            <br>
                            <label>
                                <input type="password" name="pass" value="word" placeholder="Password">
                            </label>
                            <br>
                            <button type="submit">Sign In</button>
                        </form>
                        <div class="disclaimer">
                            To enter your personal account,<br> you should be a Slide-NET subscriber
                        </div>
                    </div>
                </div>
                <!-- Information block -->
                <div class="info_block">
                    <div class="page_title">Have a questions?</div>
                    <div class="info_text">
                        <ul>
                            <li>-Service-information center: 0800-31-0800 (24/7)</li>
                            <li>-Information-Сonsultation: abonontdel@slidenet.ua</li>
                            <li>-Technical consultation: support@slidenet.ua</li>
                            <li>-Customer service center: Ukraine, Kharkiv, Nauki Ave 43B</li>
                        </ul>
                        <div class="disclaimer">
                            Working hours:
                            Mon-Fri from 9 am to 6 pm
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
