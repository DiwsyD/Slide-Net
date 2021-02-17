<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE>
<html lang="en">
<html>
<head>
    <title>Choose a tariff</title>
    <c:import url="../../user_imports.html" />
</head>
<body>
    <c:import url="../../cabinet_header.jsp" />

    <div class="content_table">
        <div  class="body-info">
            <div class="page_title">Choose a Tariff to Service</div>
            <!-- Service Tabs -->
            <div class="service_tabs">
                <c:forEach var="service" items="${serviceList}">
                    <a class='tablink _active'>${service.getName()}</a>
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
                                <form method="post">
                                    <button id="save_changes" name="selectedTariff" class="save_button" value="${tariff.getId()}">
                                        Select
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <c:if test="${maxPage > 1}">
                    <div class="pagination-block">
                        <div class="pagination-control">
                            <hr>
                            <c:choose>
                                <c:when test="${page == 1}">
                                    <a class="inactive-link">|<</a>
                                    <a class="inactive-link"><<</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="active-link" href="${uri}?serviceId=${activeService}&page=1">|<</a>
                                    <a class="active-link-2" href="${uri}?serviceId=${activeService}&page=${page-1}"><<</a>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${page == maxPage}">
                                    <a class="inactive-link">>></a>
                                    <a class="inactive-link">>|</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="active-link-2" href="${uri}?serviceId=${activeService}&page=${page+1}">>></a>
                                    <a class="active-link" href="${uri}?serviceId=${activeService}&page=${maxPage}">>|</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:if>

                <div class="save-cancel-editing">
                    <a class="cancel_button" href="/cabinet/user/user_cabinet">Cancel</a>
                </div>

            </div>
        </div>
    </div>


    <c:import url="/cabinet/footer.html" />
</body>
</html>
