package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.testng.annotations.Test;
import page_objects.HomePage;
import page_objects.socials_links_media.SocialLinksForm;
import page_objects.socials_links_media.YoutubeChannelPage;

import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;

public class SocialLinksTest extends BaseTest {
    HomePage homePage = new HomePage();
    SocialLinksForm socialLinksForm = new SocialLinksForm();
    YoutubeChannelPage youtubeChannelPage = new YoutubeChannelPage();

    @Test
    public void shouldNavigateToExpectedSocialMediaUrls() {
        Allure.step("Verifying page title is as expected and accepting cookies", () -> {
            assertTrue(WebDriverRunner.getWebDriver().getTitle().contains("eMAG.bg - Широка гама продукти"), "page title should be eMAG.bg - Широка гама продукти");
            homePage.acceptCookiesIfNeeded();
            homePage.dismissAccountLogin();
        });

        Allure.step("Clicking Facebook link and verifying correct url on the page, closing it and then verifying browser tabs are 1", () -> {
            socialLinksForm.clickFacebookLink();
            switchTo().window(1);

            assertEquals(WebDriverRunner.getWebDriver().getCurrentUrl(), "https://www.facebook.com/eMAGbg", "Url should be correct after redirection to facebook page");
            closeWindow();
            switchTo().window(0);

            assertEquals(WebDriverRunner.getWebDriver().getWindowHandles().size(), 1, "only 1 window should be opened after closing facebook page");
        });

        Allure.step("Clicking Youtube link and verifying correct url on the page, closing it and then verifying browser tabs are 1", () -> {
            socialLinksForm.clickYoutubeLink();
            switchTo().window(1);
            youtubeChannelPage.acceptCookiesIfNeeded();

            assertTrue(WebDriverRunner.getWebDriver().getCurrentUrl().contains("https://www.youtube.com/channel/UC5y5r9BY5IiT4MkBrMtZRnA"), "Url should be correct after redirection to youtube page");
            closeWindow();
            switchTo().window(0);

            assertEquals(WebDriverRunner.getWebDriver().getWindowHandles().size(), 1, "only 1 window should be opened after closing youtube page");
        });

        Allure.step("Clicking Instagram link and verifying correct url on the page, closing it and then verifying browser tabs are 1", () -> {
            socialLinksForm.clickInstagramLink();
            switchTo().window(1);

            assertEquals(WebDriverRunner.getWebDriver().getCurrentUrl(), "https://www.instagram.com/emag.bg_official/", "Url should be correct after redirection to instagram page");
            closeWindow();
            switchTo().window(0);

            assertEquals(WebDriverRunner.getWebDriver().getWindowHandles().size(), 1, "only 1 window should be opened after closing instagram page");
        });
    }
}
