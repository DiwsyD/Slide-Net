<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="own" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/${language}"/>
<!DOCTYPE>
<html lang="en">
<html>
<head>
    <title><<fmt:message key="label.chooseatariff"/>></title>
    <c:import url="../../../imports/user_imports.html" />
</head>
<body>
    <c:import url="../../../imports/header.jsp" />

    <div class="content_table">
        <div  class="body-info">
            <div class="page_title"><fmt:message key="label.chooseatariff"/></div>
            <!-- Service Tabs -->
            <div class="service_tabs">
                <c:forEach var="service" items="${serviceList}">
                    <a class='tablink _active'><fmt:message key="label.${service.getName()}"/></a>
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
                            <td class="button-column">
                                <form method="post">
                                    <button id="save_changes" name="selectedTariff" class="save_button" value="${tariff.getId()}">
                                        <fmt:message key="label.select"/>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <own:pagination path="${uri}" activeService="${activeService}" maxPage="${maxPage}" page="${page}"/>

                <div class="save-cancel-editing">
                    <a class="cancel_button" href="/cabinet/user/user_cabinet"><fmt:message key="label.cancel"/></a>
                </div>

            </div>
        </div>
    </div>


    <c:import url="/imports/footer.jsp" />
</body>
</html>
