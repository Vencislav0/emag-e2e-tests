package page_objects.socials_links_media;

import framework.elements.Link;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

public class SocialLinksForm extends BaseForm {
    private final Link facebookLink;
    private final Link youtubeLink;
    private final Link instagramLink;

    public SocialLinksForm(){
        super(By.xpath("//div[@class='col-md-3 text-center']"), "Social Media Links Container");
        this.facebookLink = new Link(By.xpath("//a/i[@class='em em-facebook']"), "Facebook Page Link");
        this.youtubeLink = new Link(By.xpath("//a/i[@class='em em-youtube']"), "Youtube Page Link");
        this.instagramLink = new Link(By.xpath("//a/i[@class='em em-instagram']"), "Instagram Page Link");
    }

    public void clickFacebookLink(){
        this.facebookLink.click();
    }

    public void clickYoutubeLink(){
        this.youtubeLink.click();
    }

    public void clickInstagramLink(){
        this.instagramLink.click();
    }
}
