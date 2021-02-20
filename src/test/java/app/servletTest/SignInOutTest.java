package app.servletTest;

import app.servlet.SignInOutServlet;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class SignInOutTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    HttpSession session;
    @Mock
    SignInOutServlet servlet;


    @Test
    public void signOutDoGetTest() {

    }

    @Test
    public void signInDoGetTest() {

    }

    @Test
    public void logInDoPostValidTest() {
        when(req.getParameter("login")).thenReturn("854652030");
        when(req.getParameter("pass")).thenReturn("testAdmin1!");

        //servlet.doGet(req, resp);



    }

    @Test
    public void logInDoPostInvalidTest() {

    }
}
