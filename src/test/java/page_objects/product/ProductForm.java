package page_objects.product;

import framework.elements.Button;
import framework.elements.Label;
import framework.utils.BaseForm;
import interfaces.Product;
import org.openqa.selenium.By;

public class ProductForm extends BaseForm implements Product {
    public final Label productPrice;
    public final Label productTitle;
    private final Label productPriceOpened;
    private final Button favoriteButton;

    public ProductForm(int index){
        super(By.xpath(String.format("(//div[@id='card_grid']//div[@class='card-v2'])[%s]", index)), "Product Form Locator");
        final var formLocator = String.format("(//div[@id='card_grid']//div[@class='card-v2'])[%s]", index);
        this.productPrice = new Label(By.xpath(String.format("%s//p[@class='product-new-price'][1]", formLocator)), "Product Price");
        this.productTitle = new Label(By.xpath(String.format("%s//h2[@class='card-v2-title-wrapper']/a", formLocator)), "Product Title");
        this.productPriceOpened = new Label(By.xpath(String.format("%s//p[@class='pricing rrp']", formLocator)), "Opened Product Price");
        this.favoriteButton = new Button(By.xpath(String.format("%s//button[contains(@class, 'add-to-favorites btn')]", formLocator)), "Product Favorite Button");
    }

    @Override
    public String getPriceText(){
        return this.productPrice.getText();
    }

    @Override
    public String getTitleText(){
        return this.productTitle.getText();
    }

    public String getOpenedPrice(){
        return this.productPriceOpened.getText();
    }

    public void clickOnProduct(){
        this.formElement.click();
    }

    public void clickOnFavoriteButton(){
        this.favoriteButton.click();
    }

    public String getFavoriteButtonState(){
        return this.favoriteButton.getAttribute("class");
    }
}
