<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="localization/${localization_file}${language}"/>
<html>
<head>
    <title><<fmt:message key="label.admincontrolpanel"/>></title>
    <c:import url="../../imports/admin_imports.html" />
</head>
<body>
    <!-- header -->
    <c:import url="../../imports/header.jsp" />
    <div class="content-container">
        <div class="body-info">
            <div class="page_title"><fmt:message key="label.admincontrolpanel"/></div>
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
            <div class="account_services_table">
                <c:if test="${accServDataActive}">

                    <table>
                        <thead>
                        <tr>
                            <td>
                                First Name
                            </td>
                            <td>
                                Last Name
                            </td>
                            <td>
                                Second Name
                            </td>
                            <td>
                                Service count
                            </td>
                        </tr>
                        </thead>
                        <c:forEach var="accServData" items="${accountServices}" >
                            <tr>
                                <td>
                                        ${accServData.getKey().getfName()}
                                </td>
                                <td>
                                        ${accServData.getKey().getlName()}
                                </td>
                                <td>
                                        ${accServData.getKey().getsName()}
                                </td>
                                <td>
                                        ${accServData.getValue()}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                </c:if>
                <a class="edit_button show_account_services_table" href="admin_cabinet?showAccServData=${!accServDataActive}">Show Account Services</a>
            </div>
        </div>
    </div>
    <!-- footer -->
    <c:import url="/imports/footer.jsp" />
</body>
</html>
