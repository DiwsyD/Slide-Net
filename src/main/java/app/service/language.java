package app.service;

import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class language {
    private static final Logger LOG = Logger.getLogger(language.class);

    /**
     * I tried to use enum, but cause of it i get recursion :\
     *
     * I didn't find the reason, so, that's why i use this switch :(
     *
     * */
    public static final String LANGUAGE = "language";
    public static final String LOCALIZATION_FILE = "localization";
    public static final String[] supportedLanguages = {"en", "ru", "ua"};

    public static void checkLanguage(HttpServletRequest req, HttpServletResponse resp) {
        //if we want to change lang -> change and return
        String lang = req.getParameter(LANGUAGE);
        if (language.checkLanguageParam(lang)) {
            LOG.debug("checked: " + lang);
            setLangueage(req, resp, lang);
            return;
        }
        LOG.debug("param is null");

        //Here we don't want to change lang, so, we check if session is not new
        if (req.getSession().getAttribute(LANGUAGE) != null) {
            return;
        }

        //now, we know, session was destroyed or never exist, so get lang from cookie
        LOG.debug("new session: " + lang);
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(language.LANGUAGE)) {
                    lang = c.getValue();
                    break;
                }
            }
        }
        //if lang value is not supporting by site set to default (en)
        if (!language.checkLanguageParam(lang)) {
            lang = supportedLanguages[0];
        }
        setLangueage(req, resp, lang);
    }


    public static void checkLanguage(HttpServletRequest req, HttpServletResponse resp, String oo) {
        LOG.debug("enter to change Language");
        //if we want to change lang -> change and return
        String lang = req.getParameter(LANGUAGE);
        if (language.checkLanguageParam(lang)) {
            setLangueage(req, resp, lang);
            return;
        }
        LOG.debug("param is null");

        LOG.debug("getLocale");
        lang = req.getLocale().getLanguage();

        LOG.debug("Locale is " + lang);


        LOG.debug("getCookie");
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(language.LANGUAGE)) {
                    lang = c.getValue();
                    break;
                }
            }
        }
        LOG.debug("Cookie is " + lang);
        if (!language.checkLanguageParam(lang)) {
            lang = "en";
        }

        setLangueage(req, resp, lang);
    }

    private static void setLangueage(HttpServletRequest req, HttpServletResponse resp, String lang) {
        LOG.debug("Setting Language");
        req.getSession().setAttribute("language", language.LOCALIZATION_FILE + "_" + lang);
        LOG.debug("Setting cookie");
        resp.addCookie(new Cookie(language.LANGUAGE, lang));
        resp.addCookie(new Cookie(language.LANGUAGE, lang));

        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals(language.LANGUAGE)) {
                LOG.debug("COOKIE: " + c.getValue());
                break;
            }
        }
        LOG.info("Language Changed to: [" + lang + "]");
    }

    private static boolean checkLanguageParam(String lang) {
        LOG.debug("Checking Language: " + lang );
        if (lang == null) {
            return false;
        }
        for (String slangs : supportedLanguages) {
            if (lang.equals(slangs)) {
                return true;
            }
        }
        return false;
    }

}
