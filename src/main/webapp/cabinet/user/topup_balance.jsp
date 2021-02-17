<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Top Up Balance</title>
    <c:import url="../../imports/user_imports.html" />
</head>
<body>
    <!-- header -->
    <c:import url="../../imports/cabinet_header.jsp" />
    <div class="content-container">
        <div class="body-info">
            <div class="page_title">Top-Up Balance</div>
            <div class="any-form">
                <div id="topup_balance_result">
                    <c:if test="${topup_error}">
                        Wrong value of amount!
                    </c:if>
                </div>
                <form method="post" onsubmit="chectTopUpValue()">
                    <label>
                        <p>Top-up amount:</p>
                        <input data-rule="number" type="text" name="topup_amount" value="150" placeholder="Top-up amount">
                    </label>
                    <br>
                    <button class="save_button" id="topup_form" type="submit">Top Up!</button>
                </form>
            </div>
        </div>
    </div>
    <!-- footer -->
    <c:import url="/imports/footer.jsp" />
</body>
</html>
