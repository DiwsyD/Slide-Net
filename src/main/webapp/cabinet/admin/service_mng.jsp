<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="own" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/interface"/>
<!DOCTYPE>
<html lang="en">
<head>
    <title><<fmt:message key="label.servicemng"/>></title>
    <c:import url="../../imports/admin_imports.html" />
</head>
<body>
<c:import url="../../imports/cabinet_header.jsp" />

<div class="content_table">
    <div  class="body-info">
        <div class="page_title"><fmt:message key="label.servicemng"/></div>
        <!-- Service Tabs -->
        <div class="service_tabs">
            <c:forEach var="service" items="${serviceList}">
                <a class='tablink <c:out value="${activeService == service.getId() ? \'_active\' : \'none\'}"/>' href="service_mng?serviceId=${service.getId()}"><fmt:message key="label.${service.getName()}"/></a>
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
                            <td><fmt:message key="label.${tariff.getName()}"/></td>
                            <td><fmt:message key="label.${tariff.getDescription()}"/></td>
                            <td><fmt:message key="label.${tariff.getPrice()}"/></td>
                            <td class="button-column">
                                <button class="cancel_button" value="cancel" style="display: none;" onclick="cancelTariff('${tariff.getName()}')">
                                    <fmt:message key="label.cancel"/>
                                </button>
                            </td>
                            <td class="button-column">
                                <button class="edit_button" value="edit" onclick="editTariffRow('${tariff.getName()}', '${tariff.getDescription()}','${tariff.getPrice()}')">
                                    <fmt:message key="label.edit"/>
                                </button>
                            </td>
                            <td class="button-column">
                                <button class="remove_button" value="remove" onclick="removeTariffRow('${tariff.getName()}')">
                                    <fmt:message key="label.remove"/>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    <!-- HERE -->
                    <tr class="add_new_account">
                        <td colspan="10">
                            <button id="account_adder" class="add_button" value="new" onclick="createFormsToAddNewTariffRow('${activeService}')">
                                <fmt:message key="label.remove"/>
                            </button>
                            <button id="save_changes" style="display: none;" class="save_button" value="save" onclick="saveChanges('${activeService}')">
                                <fmt:message key="label.add"/>
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <own:pagination path="${uri}" activeService="${activeService}" maxPage="${maxPage}" page="${page}"/>

            </div>
    </div>
</div>
<!-- footer -->
<c:import url="/imports/footer.jsp" />

</body>
</html>
