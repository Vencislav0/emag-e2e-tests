package page_objects.category;

import constants.Timeouts;
import framework.elements.Button;
import framework.elements.Label;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

import static framework.utils.Utils.waitUntil;

public class SortDropdownForm extends BaseForm {
    private final Button sortButton;
    private final Label descendingOption;
    private final Label newestOption;
    private final Label mostPopularOption;

    public SortDropdownForm(){
        super(By.xpath("(//span[contains(@class, 'sort-control-btn-option')])[1]"), "Sort Dropdown Locator");
        this.sortButton = new Button(By.xpath("(//button[contains(@class, 'sort-control-btn')]//span[text()='Подреди по'])[1]/.."), "Sort Expand Button");
        this.descendingOption = new Label(By.xpath("//a[text()='Цена низх.']"), "Descending Option");
        this.newestOption = new Label(By.xpath("//a[text()='Най-нови']"), "Newest Option");
        this.mostPopularOption = new Label(By.xpath("//a[text()='Най-популярни']"), "Most Popular Option");
    }

    public void sortPriceInDescendingOrder(){
        this.sortButton.click();
        this.descendingOption.click();

        waitUntil(() -> this.formElement.getText().equals("Цена низх."), Timeouts.SHORT_TIMEOUT.getTimeout(), "Didn't sort in time");

    }

    public void sortItemsByPopularity(){
        this.sortButton.click();
        this.mostPopularOption.click();

        waitUntil(() -> this.formElement.getText().equals("Най-популярни"), Timeouts.SHORT_TIMEOUT.getTimeout(), "Didn't sort in time");
    }
}
