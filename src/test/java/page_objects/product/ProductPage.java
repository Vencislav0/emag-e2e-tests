package page_objects.product;

import framework.elements.Label;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

public class ProductPage extends BaseForm {

    private final Label productPrice;
    private final Label productTitle;
    private final Label addToCartButton;

    public ProductPage(){
        super(By.xpath("//div[@class='row product-main-area mb-2']"), "Product Container Locator");
        this.productPrice = new Label(By.xpath("//p[@data-test='main-price']"), "Product Price (Product Page)");
        this.productTitle = new Label(By.xpath("//h1[@data-test='page-title']"), "Product Title (Product Page)");
        this.addToCartButton = new Label(By.xpath("//button[@data-test='main-add-to-cart-button']"), "Add To Cart Button");
    }

    public String getPrice(){
        return this.productPrice.getText();
    }

    public String getTitle(){
        return this.productTitle.getText();
    }

    public void clickAddToCartButton(){
        this.addToCartButton.click();
    }
}
