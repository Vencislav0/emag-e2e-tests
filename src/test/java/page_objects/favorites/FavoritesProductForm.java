package page_objects.favorites;

import framework.elements.Button;
import framework.elements.Label;
import framework.utils.BaseForm;
import interfaces.Product;
import org.openqa.selenium.By;

public class FavoritesProductForm extends BaseForm implements Product {
    private final Label productTitle;
    private final Label productPrice;
    private final Button productDeleteButton;

    public FavoritesProductForm(int index){
        super(By.xpath(String.format("(//div[contains(@class, 'card-favorites-wrapper')])[%d]", index)), "Favorites Product Form Locator");
        final var formLocator = String.format("(//div[contains(@class, 'card-favorites-wrapper')])[%d]", index);
        this.productTitle = new Label(By.xpath(String.format("%s//a[@data-zone='title']", formLocator)), "Favorites Product Title");
        this.productPrice = new Label(By.xpath(String.format("%s//p[@class='product-new-price'][1]", formLocator)), "Favorites Product Price");
        this.productDeleteButton = new Button(By.xpath(String.format("%s//span[text()='Изтрий']", formLocator)), "Favorites Product Delete Button");
    }

    @Override
    public String getTitleText(){
        return this.productTitle.getText();
    }

    @Override
    public String getPriceText(){
        return this.productPrice.getText();
    }

    public void clickDeleteButton(){
        this.productDeleteButton.click();
    }
}
