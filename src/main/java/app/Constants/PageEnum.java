package app.Constants;

public enum PageEnum {
    INDEX_JSP("index.jsp"),
    ERROR_PAGE("error.jsp");


    private String value;

    private PageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
