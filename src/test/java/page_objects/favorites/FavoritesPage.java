package page_objects.favorites;

import framework.elements.Label;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

public class FavoritesPage extends BaseForm {
    private final Label pageHeader;

    public FavoritesPage(){
        super(By.xpath("//div[@class='main-container']"), "Favorites Page Container Locator");
        this.pageHeader = new Label(By.xpath("//div[contains(@class, 'text-nowrap')]"), "Favorites Page Header");
    }

    public String getHeaderText(){
        return this.pageHeader.getText();
    }
}
