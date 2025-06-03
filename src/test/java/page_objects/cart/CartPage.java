package page_objects.cart;

import framework.elements.Label;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

public class CartPage extends BaseForm {
    private final Label header;
    private final Label emptyCartMessage;

    public CartPage(){
        super(By.xpath("//div[@id='cart-products']"), "Cart Page Container Locator");
        this.header = new Label(By.xpath("//h1"), "Cart Page Header");
        this.emptyCartMessage = new Label(By.xpath("//p[@class='mb-0']"), "Empty Cart Message");
    }

    public String getHeaderText(){
        return this.header.getText();
    }

    public String getEmptyCartMessage(){
        return this.emptyCartMessage.getText();
    }
}
