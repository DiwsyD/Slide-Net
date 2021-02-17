package app.servlet;

import app.constants.PageEnum;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/error", "/400"})
public class ErrorHandlerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer statusCode = (Integer)
                req.getAttribute("javax.servlet.error.status_code");

        String errorMessage = "Error:  <" + statusCode + ">";

        session.setAttribute("ErrorCause", errorMessage);

        resp.sendRedirect(PageEnum.ERROR_PAGE.getValue());

    }
}
