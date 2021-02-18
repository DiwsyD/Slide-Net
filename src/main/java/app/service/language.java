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
        LOG.debug("enter to change Language");
        String lang = req.getParameter(language.LANGUAGE);
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
        resp.addCookie(new Cookie(language.LANGUAGE, lang));
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
