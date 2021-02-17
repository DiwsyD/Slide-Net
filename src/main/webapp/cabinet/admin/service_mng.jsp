<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pgn" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<jsp:useBean id="accounts" class="app.entity.Account" scope="page" />
<!DOCTYPE>
<html lang="en">
<head>
    <title>Service Management</title>
    <c:import url="../../imports/admin_imports.html" />
</head>
<body>
<c:import url="../../imports/cabinet_header.jsp" />

<div class="content_table">
    <div  class="body-info">
        <div class="page_title">Service Management</div>
        <!-- Service Tabs -->
        <div class="service_tabs">
            <c:forEach var="service" items="${serviceList}">
                <a class='tablink <c:out value="${activeService == service.getId() ? \'_active\' : \'none\'}"/>' href="service_mng?serviceId=${service.getId()}">${service.getName()}</a>
            </c:forEach>
        </div>
        <!-- Service Tables -->
            <div class="service_table">
                <table>
                    <thead>
                    <tr>
                        <c:choose>
                            <c:when test="${desc != ''}">
                                <th><a class="sort-link" href="<my:modifyURL name="orderBy" value="name"/>">Tariff Name ^</a></th>
                            </c:when>
                            <c:otherwise>
                                <th><a class="sort-link" href="<my:modifyURL name="orderBy" value="name_desc"/>">Tariff Name v</a></th>
                            </c:otherwise>
                        </c:choose>
                        <th>Description</th>
                        <c:choose>
                            <c:when test="${desc != ''}">
                                <th><a class="sort-link" href="<my:modifyURL name="orderBy" value="price"/>">Price ^</a></th>
                            </c:when>
                            <c:otherwise>
                                <th><a class="sort-link" href="<my:modifyURL name="orderBy" value="price_desc"/>">Price v</a></th>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    </thead>
                    <tbody id="tariff-table" class="service_content_table">
                    <c:forEach var="tariff" items="${tariffList}">
                        <tr id="${tariff.getName()}">
                            <td>${tariff.getName()}</td>
                            <td>${tariff.getDescription()}</td>
                            <td>${tariff.getPrice()}</td>
                            <td class="button-column">
                                <button class="cancel_button" value="cancel" style="display: none;" onclick="cancelTariff('${tariff.getName()}')">
                                    Cancel
                                </button>
                            </td>
                            <td class="button-column">
                                <button class="edit_button" value="edit" onclick="editTariffRow('${tariff.getName()}', '${tariff.getDescription()}','${tariff.getPrice()}')">
                                    Edit
                                </button>
                            </td>
                            <td class="button-column">
                                <button class="remove_button" value="remove" onclick="removeTariffRow('${tariff.getName()}')">
                                    Remove
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    <!-- HERE -->
                    <tr class="add_new_account">
                        <td colspan="10">
                            <button id="account_adder" class="add_button" value="new" onclick="createFormsToAddNewTariffRow('${activeService}')">
                                Add
                            </button>
                            <button id="save_changes" style="display: none;" class="save_button" value="save" onclick="saveChanges('${activeService}')">
                                Save
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <pgn:pagination path="${uri}" activeService="${activeService}" maxPage="${maxPage}" page="${page}"/>

            </div>
    </div>
</div>
<!-- footer -->
<c:import url="/imports/footer.jsp" />

</body>
</html>
