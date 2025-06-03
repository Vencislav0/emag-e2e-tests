package page_objects.category.filters;

import constants.Filters;
import framework.elements.Button;
import framework.elements.Checkbox;
import framework.elements.TextBox;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

public class PopupFilterForm extends BaseForm {

    private final Button filterButton;
    private final TextBox searchBox;

    public PopupFilterForm(Filters filter){
        super(By.xpath(String.format("//div[@data-filter-id='%s']", filter.getFilterID())), "Popup Filter Form Locator");
        var filterLocator = String.format("//div[@data-filter-id='%s']", filter.getFilterID());
        this.filterButton = new Button(By.xpath(String.format("%s//button[text()='Филтрирай']", filterLocator)), "Popup Filter Button");
        this.searchBox = new TextBox(By.xpath(String.format("%s//input[@type='search']", filterLocator)), "Popup Filter Searchbox");

    }

    public void sendTextToSearchbox(String text){
        this.searchBox.sendText(text);
    }

    public void clickFilterButton(){
        this.filterButton.click();
    }

    public void checkCheckbox(String name){
        final var checkbox = new Checkbox(By.xpath(String.format("//a[@data-name='%s' and @data-position='popup']", name)), String.format("%s Checkbox", name));
        checkbox.click();

    }
}
