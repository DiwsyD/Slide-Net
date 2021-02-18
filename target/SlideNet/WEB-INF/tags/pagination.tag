<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag isELIgnored="false" %>

<%@ attribute name="path" type="java.lang.String" required="true"%>

<%@ attribute name="activeService" type="java.lang.Integer" required="true"%>

<%@ attribute name="maxPage" type="java.lang.Integer" required="true"%>
<%@ attribute name="page" type="java.lang.Integer" required="true"%>


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
                    <a class="active-link" href="${path}?serviceId=${activeService}&page=1#services">|<</a>
                    <a class="active-link-2" href="${path}?serviceId=${activeService}&page=${page-1}#services"><<</a>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${page == maxPage}">
                    <a class="inactive-link">>></a>
                    <a class="inactive-link">>|</a>
                </c:when>
                <c:otherwise>
                    <a class="active-link-2" href="${path}?serviceId=${activeService}&page=${page+1}#services">>></a>
                    <a class="active-link" href="${path}?serviceId=${activeService}&page=${maxPage}#services">>|</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</c:if>