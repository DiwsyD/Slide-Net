<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/${language}"/>
<html>
<head>
    <title><<fmt:message key="label.topupbalance"/>></title>
    <c:import url="../../imports/user_imports.html" />
</head>
<body>
    <!-- header -->
    <c:import url="../../imports/header.jsp" />
    <div class="content-container">
        <div class="body-info">
            <div class="page_title"><fmt:message key="label.topupbalance"/></div>
            <div class="any-form">
                <div id="topup_balance_result">
                    <c:if test="${topup_error}">
                        <fmt:message key="label.errortopup"/>
                    </c:if>
                </div>
                <form method="post" onsubmit="chectTopUpValue()">
                    <label>
                        <p><fmt:message key="label.topupamount"/>:</p>
                        <input data-rule="number" type="text" name="topup_amount" value="150" placeholder="Top-up amount">
                    </label>
                    <br>
                    <button class="save_button" id="topup_form" type="submit"><fmt:message key="label.topup"/>!</button>
                </form>
            </div>
        </div>
    </div>
    <!-- footer -->
    <c:import url="/imports/footer.jsp" />
</body>
</html>
