<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Admin Cabinet</title>
    <c:import url="../../imports/admin_imports.html" />
</head>
<body>
    <!-- header -->
    <c:import url="../../imports/cabinet_header.jsp" />
    <div class="content-container">
        <div class="body-info">
            <div class="page_title">Admin Control Panel</div>
            <div class="member_info">
                <div class="static_info">
                    <ul>
                        <li>Account Count:</li>
                        <li>Service Count:</li>
                        <li>Tariff Count:</li>
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
                <div class="page_title">Time Accelerator:</div>
                <form method="post">
                    <input name="month" type="number" value="1" min="1" max="12">
                    <button id="remove_button" class="save_button">
                        Accelerate
                    </button>
                </form>
            </div>
        </div>
    </div>
    <!-- footer -->
    <c:import url="/imports/footer.jsp" />
</body>
</html>
