package app.constants;

public class language {

    /**
     * I tried to use enum, but cause of it i get recursion :\
     *
     * I didn't find the reason, so, that's why i use this switch :(
     *
     * */
    public static final String LANGUAGE = "language";
    public static final String LOCALIZATION_FILE = "localization";
    private static final String[] supportedLanguages = {"en", "ru", "ua"};


    public static boolean checkLanguageParam(String lang) {
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
