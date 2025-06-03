package page_objects;

import framework.elements.Button;
import framework.elements.Label;
import framework.elements.TextBox;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

public class SearchForm extends BaseForm {
    private final TextBox searchTextBox;
    private final Button magnifierButton;
    private final Button XButton;
    private final Label paragraph;

    public SearchForm(){
        super(By.xpath("//form[@action='/search']"), "Search Form Locator");
            this.searchTextBox = new TextBox(By.xpath("//input[@id='searchboxTrigger']"), "Search Text Box");
            this.magnifierButton = new Button(By.xpath("//button[contains(@class, 'searchbox-submit-button')]"), "Magnifier Button");
            this.XButton = new Button(By.xpath("//button[contains(@class, 'searchbox-close')]"), "X Button");
            this.paragraph = new Label(By.xpath("//form[@action='/search']//p"), "Search Placeholder");
    }

    public boolean isSearchParagraphDisplayed(){
        return this.paragraph.isVisible();
    }

    public String getSearchParagraphText(){
        return this.paragraph.getText();
    }

    public boolean isMagnifierButtonDisplayed(){
        return this.magnifierButton.isVisible();
    }

    public boolean isXButtonDisplayed(){
        return this.XButton.isVisible();
    }

    public String getSearchTextboxPlaceholder(){
        return this.searchTextBox.getAttribute("placeholder");
    }

    public void sendTextToSearch(String text){
        this.searchTextBox.sendText(text);
    }

    public String getSearchboxText(){
        return this.searchTextBox.getAttribute("value");
    }

    public void clickMagnifierButton(){
        this.magnifierButton.click();
    }

    public void clickXButton(){
        this.XButton.click();
    }

    public void clickOnSearchbox(){
        this.searchTextBox.click();
    }
}
