package constants;

import java.util.Optional;

public enum Categories {
    PHONES("Мобилни телефони", "1", "3861", null),
    AIR_CONDITIONERS("Климатици", "418", "3187", null),
    ELECTRICAL_RAZERS("Електрически самобръсначки", "549", "3906", "Ел. самобръсначки"),
    DRONES("Drones", null, null, null),
    GAMES_FOR_CONSOLES_AND_PC("Игри за конзола & Компютър", "3096", "3725", null),
    VR_HEADSETS("VR Gaming Очила", "3096", "3805", null),
    DUMMY_CATEGORY("Dummy CategoryPage", null, null, null);

    private final String categoryName;
    private final String categoryID;
    private final String itemID;
    private final String categoryShortName;

    Categories(String categoryName, String categoryID, String itemID, String categoryShortName) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.itemID = itemID;
        this.categoryShortName = categoryShortName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getItemID() {
        return itemID;
    }

    public Optional<String> getCategoryShortName() {
        return Optional.ofNullable(categoryShortName);
    }

}
