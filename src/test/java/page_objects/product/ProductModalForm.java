package page_objects.product;

import framework.elements.Button;
import framework.elements.Label;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

public class ProductModalForm extends BaseForm {
    private final Button goToCartButton;
    private final Label productTitle;
    private final Label productPrice;
    private final Label formTitle;

    public ProductModalForm(){
        super(By.xpath("//div[@class='modal-dialog modal-lg']/div[@class='modal-content']"), "Modal Form Container Locator");
        this.goToCartButton = new Button(By.xpath("//div[contains(@class, 'table')]//a[@data-test='atc-modal-cart-details']"), "Go To Cart Button");
        this.productTitle = new Label(By.xpath("//span[@class='small']"), "Modal Product Title");
        this.formTitle = new Label(By.xpath("//div[@class='modal-dialog modal-lg']/div[@class='modal-content']//h4/span"), "Modal Form Title");
        this.productPrice = new Label(By.xpath("(//div[contains(@class, 'table')]//p[@class='product-new-price'])[3]"), "Modal Product Title");
    }

    public void clickGoToCartButton(){
        this.goToCartButton.click();
    }

    public String getFormTitleText(){
        return this.formTitle.getText();
    }

    public String getTitle(){
        return this.productTitle.getText();
    }

    public String getPrice(){
        return this.productPrice.getText();
    }
}
