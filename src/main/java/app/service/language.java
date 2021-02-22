package app.service;

import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class language {
    private static final Logger LOG = Logger.getLogger(language.class);

    public static final String LANGUAGE = "language";
    public static final String LOCALIZATION_FILE = "localization_";
    public static final String[] supportedLanguages = {"en", "ru", "ua"};

    /**.
     * 1. Check if param in url is exist - it means we want to change language to another.
     * 2. Check if have lang in session - if true - just return.
     * 3. Check if lang is exist in cookie
     * 4. If lang value still null, set it to default (eng)
     * Set language
     * */

    public static void checkLanguage(HttpServletRequest req, HttpServletResponse resp) {
        //1 if we want to change lang -> change and return
        String lang = req.getParameter(LANGUAGE);
        if (language.checkLanguageParam(lang)) {
            setLanguage(req, resp, lang);
            return;
        }
        HttpSession session = req.getSession();
        //2 Here we don't want to change lang, so, we check if session is not new
        if (session.getAttribute(LANGUAGE) != null) {
            return;
        }

        //3 now, we know, session was destroyed or never exist, so get lang from cookie
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(language.LANGUAGE)) {
                    lang = c.getValue();
                    break;
                }
            }
        }
        //4 if lang value is not supporting by site set to default (en)
        if (!language.checkLanguageParam(lang)) {
            lang = supportedLanguages[0];
        }
        setLanguage(req, resp, lang);
    }

    /**.
     * set language attributes to session
     * Create Cookie for future.
     * */
    private static void setLanguage(HttpServletRequest req, HttpServletResponse resp, String lang) {
        req.getSession().setAttribute(language.LANGUAGE, lang);
        req.getSession().setAttribute("localization_file", LOCALIZATION_FILE);
        resp.addCookie(new Cookie(language.LANGUAGE, lang));
        LOG.info("Language Changed to: [" + lang + "]");
    }

    //Check if app supports current language
    private static boolean checkLanguageParam(String lang) {
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
