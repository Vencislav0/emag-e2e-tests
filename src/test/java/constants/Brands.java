package constants;

import java.util.Optional;

public enum Brands {
    BRAUN("Braun", "Браун"),
    SAMSUNG("Samsung", null),
    DAIKIN("Daikin", "Дайкин");

    final String brandName;
    final String brandNameLocalized;

    Brands(String brandName, String brandNameLocalized){
        this.brandName = brandName;
        this.brandNameLocalized = brandNameLocalized;
    }

    public String getBrandName(){
        return this.brandName;
    }

    public Optional<String> getLocalizedBrandName(){
        return Optional.ofNullable(this.brandNameLocalized);
    }
}
