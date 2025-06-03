package page_objects.cart;

import framework.elements.Button;
import framework.elements.Label;
import framework.utils.BaseForm;
import interfaces.Product;
import org.openqa.selenium.By;

public class CartProductForm extends BaseForm implements Product {
    private final Button QntyIncreaseButton;
    private final Button deleteProductButton;
    private final Label productPrice;
    private final Label productTitle;

    public CartProductForm(int index){
        super(By.xpath(String.format("(//div[@class='cart-widget cart-line '])[%s]", index)), "Product Form Locator");
        final var formLocator = String.format("(//div[@class='cart-widget cart-line '])[%s]", index);
        this.QntyIncreaseButton = new Button(By.xpath(String.format("%s//div[contains(@class, 'd-md-block')]//button[@data-test='increaseQtyBtn']", formLocator)), "Increase Qnty Button");
        this.deleteProductButton = new Button(By.xpath(String.format("%s//div[contains(@class, 'd-md-block')]//button[contains(@class, 'remove-product')]", formLocator)), "Delete Product Button");
        this.productPrice = new Label(By.xpath(String.format("%s//div[contains(@class, 'd-md-block')]//p[@class='product-new-price']", formLocator)), "Cart Product Price");
        this.productTitle = new Label(By.xpath(String.format("%s//a[@class='line-item-title main-product-title']", formLocator)), "Cart Product Title");
    }

    public void clickIncreaseQntyButton(){
        this.QntyIncreaseButton.click();
    }

    public void clickDeleteProductButton(){
        this.deleteProductButton.click();
    }

@Override
    public String getPriceText(){
        return this.productPrice.getText();
    }
@Override
    public String getTitleText(){
        return this.productTitle.getText();
    }

    public String getQntyIncreaseButtonState(){
        return this.QntyIncreaseButton.getAttribute("class");
    }
}
