package page_objects;

import com.codeborne.selenide.ex.UIAssertionError;
import framework.elements.Button;
import framework.elements.Label;
import framework.utils.BaseForm;
import framework.utils.LoggerUtil;
import org.openqa.selenium.By;

import java.time.Duration;

public class HomePage extends BaseForm {
    private final Label categoriesMenu;
    private final Button acceptCookiesButton;
    private final Button accountLoginDismissButton;
    private final Button favoritesMenuItem;
    private final Label heartIconNumber;

    public HomePage(){
        super(By.xpath("div[class='main-container']"), "Home Page Main Container");
        this.categoriesMenu = new Label(By.xpath("//div[contains(text(), 'Kатегории')]"), "Categories Menu");
        this.acceptCookiesButton = new Button(By.xpath("//button[contains(@class, 'js-accept') and text()='Приеми всички']"), "Accept Cookies Button");
        this.accountLoginDismissButton = new Button(By.xpath("//button[contains(@class, 'js-dismiss-login')]"), "Account Login Dismiss Button");
        this.favoritesMenuItem = new Button(By.xpath("//i[contains(@class, 'heart navbar-icon')]"), "Favorites Menu Item");
        this.heartIconNumber = new Label(By.xpath("//a[@id='my_wishlist']//span[@class='jewel jewel-danger']"), "Number Label On Favorites Menu Item");
    }

    public void hoverOnCategoriesMenu(){
        this.categoriesMenu.hover();
    }

    public void acceptCookiesIfNeeded(){
        try {
            this.acceptCookiesButton.waitForVisible(5);
            this.acceptCookiesButton.click();
        }
        catch(UIAssertionError er){
            LoggerUtil.warn("Failed to find or accept cookies form within the timeout period, proceding without accepting cookies", er);

        }
    }
    public void dismissAccountLogin(){
        try{
            this.accountLoginDismissButton.waitForVisible(5);
            this.accountLoginDismissButton.click();

        }
        catch(UIAssertionError er){
            LoggerUtil.warn("Failed to find or dismiss account login form within the timeout period, proceding without dismissing login pop up", er);
        }
    }
    public void clickFavoritesMenuItem(){
        this.favoritesMenuItem.click();
    }

    public String getNumberOnHeartIcon(){
        return this.heartIconNumber.getText();
    }

}
