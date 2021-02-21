package app.serviceTest;

import app.service.language;
import org.junit.Before;
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

    @Before
    public void setup() {
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        cookie = new Cookie("language", "ru");

        when(req.getSession()).thenReturn(session);
    }

    @Test
    public void checkNonExistentLanguageTest() {
        when(req.getParameter(anyString())).thenReturn("fr");
        when(session.getAttribute(anyString())).thenReturn(null);
        when(req.getCookies()).thenReturn(null);

        language.checkLanguage(req, resp);
    }

    @Test
    public void checkExistentLanguageTest() {
        when(req.getParameter(anyString())).thenReturn("en");

        language.checkLanguage(req, resp);
    }

    @Test
    public void checkNotSetLangueageTest() {
        when(req.getParameter(anyString())).thenReturn(null);
        when(session.getAttribute(anyString())).thenReturn("ua");

        language.checkLanguage(req, resp);
    }

    @Test
    public void checkSetLangueageFromCookieTest() {

        Cookie[] cookies = new Cookie[]{cookie};

        when(req.getParameter(anyString())).thenReturn(null);
        when(session.getAttribute(anyString())).thenReturn(null);
        when(req.getCookies()).thenReturn(cookies);


        language.checkLanguage(req, resp);
    }
}

















