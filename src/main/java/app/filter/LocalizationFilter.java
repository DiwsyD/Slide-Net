package app.filter;

import app.constants.language;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

@WebFilter("/*")
public class LocalizationFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(LocalizationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        checkLanguage(req, resp);
        chain.doFilter(request, response);
        return;
    }

    private void checkLanguage(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("enter to change Language");

        String lang = req.getParameter(language.LANGUAGE);
        if (language.checkLanguageParam(lang)) {
            setLangueage(req, resp, lang);
            return;
        }

        lang = req.getLocale().getLanguage();

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(language.LANGUAGE)) {
                    lang = c.getValue();
                    break;
                }
            }
        }

        if (!language.checkLanguageParam(lang)) {
            lang = "en";
        }

        setLangueage(req, resp, lang);
    }

    private void setLangueage(HttpServletRequest req, HttpServletResponse resp, String lang) {
        req.getSession().setAttribute("language", language.LOCALIZATION_FILE + "_" + lang);
        resp.addCookie(new Cookie(language.LANGUAGE, lang));
        LOG.info("Language Changed to: [" + lang + "]");
    }
}
