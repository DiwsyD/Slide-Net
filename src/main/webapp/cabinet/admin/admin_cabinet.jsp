<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/interface"/>
<html>
<head>
    <title><<fmt:message key="label.admincabinet"/>></title>
    <c:import url="../../imports/admin_imports.html" />
</head>
<body>
    <!-- header -->
    <c:import url="../../imports/cabinet_header.jsp" />
    <div class="content-container">
        <div class="body-info">
            <div class="page_title"><fmt:message key="label.admincontrpanel"/></div>
            <div class="member_info">
                <div class="static_info">
                    <ul>
                        <li><fmt:message key="label.acccount"/>:</li>
                        <li><fmt:message key="label.servicecount"/>:</li>
                        <li><fmt:message key="label.tariffcount"/>:</li>
                    </ul>
                </div>
                <div class="dynamic_info">
                    <ul>
                        <li>${serviceCount}</li>
                        <li>${tariffCount}</li>
                        <li>${accountCount}</li>
                    </ul>
                </div>
            </div>
            <div>
                <div class="page_title"><fmt:message key="label.timeaccelerator"/>:</div>
                <form method="post">
                    <input name="month" type="number" value="1" min="1" max="12">
                    <button id="remove_button" class="save_button">
                        <fmt:message key="label.accelerate"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
    <!-- footer -->
    <c:import url="/imports/footer.jsp" />
</body>
</html>
