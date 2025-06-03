package page_objects.category.filters;

import framework.elements.Label;
import framework.utils.BaseForm;
import org.openqa.selenium.By;

public class SidebarFilterForm extends BaseForm {

    public SidebarFilterForm(){
        super(By.xpath("//div[@class='sidebar-tree-simple mb-2']"), "Sidebar Container");

    }

    public void clickItemByText(String text){
        final var item = new Label(By.xpath(String.format("//a[@data-ref='search_menu_category' and text()='%s']", text)), String.format("Sidebar item: %s", text));
        item.click();
    }
}
