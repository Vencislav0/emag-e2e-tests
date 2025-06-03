package page_objects.category.filters;

import constants.Filters;
import framework.elements.Button;
import framework.elements.Checkbox;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

public class FilterForm extends BaseForm {

    private final Button seeMoreButton;

    public FilterForm(Filters filter){
        super(By.xpath(String.format("//div[@data-filter-id='%s']", filter.getFilterID())), "Filter Form Locator");
        var filterLocator = String.format("//div[@data-filter-id='%s']", filter.getFilterID());
        this.seeMoreButton = new Button(By.xpath(String.format("%s//div[@class='filter-body-separator']/a/..", filterLocator)), "See More Button");
    }

    public void clickSeeMoreButton(){
        this.seeMoreButton.click();
    }

    public void checkCheckbox(String name){
        final var checkbox = new Checkbox(By.xpath(String.format("//a[@data-name='%s']", name)), String.format("%s Checkbox"));
        checkbox.click();
    }
}
