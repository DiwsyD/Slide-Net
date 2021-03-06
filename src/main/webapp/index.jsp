<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="own" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/customtf" prefix="ctf" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/${localization_file}${language}"/>
<!DOCTYPE>
<html lang="en">
<head>
    <title><<ctf:SlideNet/>></title>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/fonts.css">
    <link rel="shortcut icon" href="media/images/icon.png" type="image/png">
</head>
<body>
<c:import url="imports/header.jsp" />
    <!-- body -->
    <div class="welcome_screen">
        <div class="welcome_info">
            <div class="intro_text">
                <h1>
                    <fmt:message key="label.Internet"/>     <fmt:message key="label.provider"/><br>
                    <ctf:SlideNet/>
                </h1>
            </div>
            <div class="tagline">
                <fmt:message key="label.slogan"/>
            </div>

            <div>
                <a class="index-private-cabinet" href="/sign?signAction=in"><fmt:message key="label.Private"/> <fmt:message key="label.Cabinet"/></a>
            </div>
        </div>
    </div>

    <div id="about">
        <div class="content-container">
            <div class="about_info">
                <div class="page_title"><fmt:message key="label.AboutUs"/></div>
                <div class="about_text">
                    <strong><ctf:SlideNet/></strong> - <fmt:message key="label.aboutText"/>
                </div>
            </div>

            <img class="about_image" src="media/images/about_us_image.png" alt="about us image" />

        </div>
    </div>
    <div id="services">
        <div class="content-container">
            <div class="services_info">
                <div class="body-info">
                    <div class="service_table">
                        <div class="page_title"><fmt:message key="label.Services"/></div>
                        <!-- Service Tabs -->
                        <div class="service_tabs">
                            <c:forEach var="service" items="${serviceList}">
                                <a class='tablink <c:out value="${activeService == service.getId() ? \'_active\' : \'none\'}"/>' href="?serviceId=${service.getId()}#services"><fmt:message key="label.${service.getName()}"/></a>
                            </c:forEach>
                        </div>
                        <!-- Service Tables -->
                        <div class="service_table">
                            <table>
                                <thead>
                                <tr>
                                    <c:choose>
                                        <c:when test="${desc != ''}">
                                            <th><a class="sort-link" href="<own:modifyURL name='orderBy' value='name'/>#services"><fmt:message key="label.TariffName"/> ^</a></th>
                                        </c:when>
                                        <c:otherwise>
                                            <th><a class="sort-link" href="<own:modifyURL name='orderBy' value='name_desc'/>#services"><fmt:message key="label.TariffName"/> v</a></th>
                                        </c:otherwise>
                                    </c:choose>
                                    <th><fmt:message key="label.Description"/></th>
                                    <c:choose>
                                        <c:when test="${desc != ''}">
                                            <th><a class="sort-link" href="<own:modifyURL name='orderBy' value='price'/>#services"><fmt:message key="label.Price"/> ^</a></th>
                                        </c:when>
                                        <c:otherwise>
                                            <th><a class="sort-link" href="<own:modifyURL name='orderBy' value='price_desc'/>#services"><fmt:message key="label.Price"/> v</a></th>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                                </thead>
                                <tbody id="tariff-table" class="service_content_table">
                                <c:forEach var="tariff" items="${tariffList}">
                                    <tr id="${tariff.getName()}">
                                        <td>${tariff.getName()}</td>
                                        <td>${tariff.getDescription()}</td>
                                        <td>${tariff.getPrice()} <fmt:message key="label.currency"/></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="download-services">
                                <a href="/?download=true#services"><fmt:message key="label.Download"/> <fmt:message key="label.Services"/></a>
                            </div>

                            <own:pagination path="${uri}" activeService="${activeService}" maxPage="${maxPage}" page="${page}"/>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="contacts">
        <div class="content-container">
            <div class="page_title"><fmt:message key="label.Contacts"/></div>
            <div class="over-map-contacts">
                <div>
                    <strong><fmt:message key="label.ContactPhone"/>:</strong>
                    <p>+380123456789</p>
                </div>
                <div>
                    <strong>E-mail:</strong>
                    <p>someemail@some.com</p>
                </div>
                <div>
                    <strong><fmt:message key="label.Address"/>:</strong>
                    <p>Kharkiv city, Gosprom str.</p>
                </div>
            </div>
            <div class="map">
                <iframe title="google maps" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d164153.5222915211!2d36.14574339243615!3d49.99450702735827!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4127a09f63ab0f8b%3A0x2d4c18681aa4be0a!2z0KXQsNGA0YzQutC-0LIsINCl0LDRgNGM0LrQvtCy0YHQutCw0Y8g0L7QsdC70LDRgdGC0Yw!5e0!3m2!1sru!2sua!4v1612658499623!5m2!1sru!2sua"
                        frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
            </div>
<%--            <div class="feedback">--%>
<%--                <div class="feedback_title">Have a question?<br>Fill form below!</div>--%>
<%--                <div class="feedback_form">--%>
<%--                    <form method="post">--%>
<%--                        <label>--%>
<%--                            <input type="text" name="phone_number" placeholder="Your Mobile Number" />--%>
<%--                        </label>--%>
<%--                        <br>--%>
<%--                        <label>--%>
<%--                            <textarea rows="10" cols="20" wrap="soft" class="feedback-message" name="request" placeholder="Your Message"></textarea>--%>
<%--                        </label>--%>
<%--                        <br>--%>
<%--                        <button type="submit">Send</button>--%>
<%--                    </form>--%>
<%--                </div>--%>
<%--            </div>--%>
        </div>
    </div>
    <!-- footer -->
    <c:import url="/imports/footer.jsp" />
</body>
</html>
