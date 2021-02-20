package app.serviceTest;

import app.service.language;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class languageTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    HttpSession session;
    @Mock
    Cookie cookie;


    @Test
    public void checkNonExistentLanguageTest() {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        cookie = mock(Cookie.class);

        Cookie[] cookies = new Cookie[]{cookie};

        when(req.getParameter(anyString())).thenReturn("fr");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(null);
        when(req.getCookies()).thenReturn(null);

        language.checkLanguage(req, resp);
    }

    @Test
    public void checkExistentLanguageTest() {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        cookie = mock(Cookie.class);

        when(req.getParameter(anyString())).thenReturn("en");
        when(req.getSession()).thenReturn(session);

        language.checkLanguage(req, resp);
    }

    @Test
    public void checkNotSetLangueageTest() {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        when(req.getParameter(anyString())).thenReturn(null);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn("ua");

        language.checkLanguage(req, resp);
    }

    @Test
    public void checkSetLangueageFromCookieTest() {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        cookie = new Cookie("language", "ru");

        Cookie[] cookies = new Cookie[]{cookie};

        when(req.getParameter(anyString())).thenReturn(null);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(null);
        when(req.getCookies()).thenReturn(cookies);


        language.checkLanguage(req, resp);
    }
}

















