<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE>
<html lang="en">
<head>
    <title><Slide Net></title>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/fonts.css">
    <link rel="shortcut icon" href="media/images/icon.png" type="image/png">
    <script src="js/service_mng.js"></script>
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
                        <li><a href="/sign?signAction=in"><c:out value="${role == null ? 'Log In' : ''}"/> </a></li>
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

    <!-- body -->
    <div class="welcome_screen">
        <div class="welcome_info">
            <div class="intro_text">
                <h1>
                    Internet provider<br>
                    Slide-NET
                </h1>
            </div>
            <div class="tagline">
                Slide on the Net with Slide-NET
            </div>

            <div>
                <a class="index-private-cabinet" href="/sign?signAction=in">Private Cabinet</a>
            </div>
        </div>
    </div>

    <div id="about">
        <div class="content-container">
            <div class="about_info">
                <div class="page_title">About Us</div>
                <div class="about_text">
                    <strong>Slide-NET</strong> - is a high-speed Internet provider, we provide access to the network
                    wherever there is a need and where it benefits. We also provide cable, Analog, Digital and IP TV.
                    We deliver tv signal in a most comfortable way for the customer.
                    We use the most up-to-date digital technologies, high-quality network equipment and software.
                    Also we conduct upgrading works constantly to keep the high performance of our optical network.
                    We believe that it is the most important to provide a stable and reliable service to customers.
                </div>
            </div>

            <img class="about_image" src="media/images/about_us_image.png" alt="about us image" />

        </div>
    </div>
    <div id="services">
        <div class="content-container">
            <div class="services_info">
                <div class="page_title">Services & Tariffs</div>
                <!-- Services INFO -->
                <div class="content_table">
                    <!-- Service Tabs -->
                    <div class="service_tabs">
                        <c:forEach var="service" items="${serviceList}">
                            <a class='tablink <c:out value="${activeService == service.getId() ? \'_active\' : \'none\'}"/>' href="?service=${service.getId()}">${service.getName()}</a>
                        </c:forEach>
                    </div>
                    <!-- Service Tables -->
                    <div class="service_table">
                        <table>
                            <thead>
                            <tr>
                                <th>Tariff Name</th>
                                <th>Description</th>
                                <th>Price</th>
                            </tr>
                            </thead>
                            <tbody  class="service_content_table">
                            <c:forEach var="tariff" items="${tariffList}">
                                <tr id="${tariff.getName()}">
                                    <td>${tariff.getName()}</td>
                                    <td>${tariff.getDescription()}</td>
                                    <td>${tariff.getPrice()}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="pagination-control">
                            <hr>
                            <c:if test="${maxPage > 1}">
                                <c:choose>
                                    <c:when test="${page == 1}">
                                        <a class="inactive-link">|<</a>
                                        <a class="inactive-link"><<</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="active-link" href="?service=${activeService}&page=1">|<</a>
                                        <a class="active-link" href="?service=${activeService}&page=${page-1}"><<</a>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${page == maxPage}">
                                        <a class="inactive-link">>></a>
                                        <a class="inactive-link">>|</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="active-link" href="?service=${activeService}&page=${page+1}">>></a>
                                        <a class="active-link" href="?service=${activeService}&page=${maxPage}">>|</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="contacts">
        <div class="content-container">
            <div class="page_title">Contacts</div>
            <div class="over-map-contacts">
                <div>
                    <strong>Contact Phone:</strong>
                    <p>+380123456789</p>
                </div>
                <div>
                    <strong>E-mail:</strong>
                    <p>someemail@some.com</p>
                </div>
                <div>
                    <strong>Address:</strong>
                    <p>Kharkiv city, Gosprom str.</p>
                </div>
            </div>
            <div class="map">
                <iframe title="google maps" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d164153.5222915211!2d36.14574339243615!3d49.99450702735827!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4127a09f63ab0f8b%3A0x2d4c18681aa4be0a!2z0KXQsNGA0YzQutC-0LIsINCl0LDRgNGM0LrQvtCy0YHQutCw0Y8g0L7QsdC70LDRgdGC0Yw!5e0!3m2!1sru!2sua!4v1612658499623!5m2!1sru!2sua"
                        frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
            </div>
            <div class="feedback">
                <div class="feedback_title">Have a question?<br>Fill form below!</div>
                <div class="feedback_form">
                    <form method="post">
                        <label>
                            <input type="text" name="phone_number" placeholder="Your Mobile Number" />
                        </label>
                        <br>
                        <label>
                            <textarea rows="10" cols="20" wrap="soft" class="feedback-message" name="request" placeholder="Your Message"></textarea>
                        </label>
                        <br>
                        <button type="submit">Send</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- footer -->
    <c:import url="/cabinet/footer.html" />
</body>
<script>
    let tabLinks = document.getElementsByClassName('tablink');
    tabLinks[0].click();
</script>
</html>
