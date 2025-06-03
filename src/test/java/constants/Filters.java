package constants;

public enum Filters {
    MANUFACTURER("6416"),
    PRICE("6412");

    private final String filterID;

    Filters(String filterID) {
        this.filterID = filterID;
    }

    public String getFilterID() {
        return filterID;
    }
}
