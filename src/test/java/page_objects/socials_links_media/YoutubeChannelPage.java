package page_objects.socials_links_media;

import framework.elements.Button;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

public class YoutubeChannelPage extends BaseForm {

    private final Button acceptCookiesButton;

    public YoutubeChannelPage(){
        super(By.xpath("//ytd-page-manager[@id='page-manager']"), "Youtube Channel Container");
        this.acceptCookiesButton = new Button(By.xpath("(//span[text()='Accept all'])[1]"), "Youtube Accept Cookies Button");
    }

    public void acceptCookiesIfNeeded(){
        if(this.acceptCookiesButton.isVisible()){
            this.acceptCookiesButton.click();
        }
    }
}
