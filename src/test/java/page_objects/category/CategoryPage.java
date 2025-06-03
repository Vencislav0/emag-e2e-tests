package page_objects.category;

import constants.Categories;
import framework.elements.Button;
import framework.elements.ElementCollection;
import framework.elements.Label;
import framework.utils.BaseForm;
import org.openqa.selenium.By;
import page_objects.steps.Steps;

public class CategoryPage extends BaseForm {


    private final Label sectionTitle;
    private final Label sectionProductsAmount;
    private final Label categoryLabel;
    private final Button itemButton;
    private final Label pageHeader;
    private final Button nextPageButton;

    public CategoryPage(Categories category){
        super(By.xpath("//div[@class='page-container']"), "Category Container Locator");
        this.sectionTitle = new Label(By.xpath("//h1"), "Section Title");
        this.sectionProductsAmount = new Label(By.xpath("//div[contains(@class, 'listing-page-title')]//span"), "Products amount label next to section title");
        this.categoryLabel = new Label(By.xpath(String.format("//li[@data-id='%s']", category.getCategoryID())), String.format("%s Navigation Locator", category.getCategoryName()));
        this.itemButton = new Button(By.xpath(String.format("//a[@data-id='%s']", category.getItemID())), String.format("%s Item Button", category.getCategoryName()));
        this.pageHeader = new Label(By.xpath("(//span[contains(@class, 'title-phrasing')])[1]"), "Page Header");
        this.nextPageButton = new Button(By.xpath("//span[text()='Напред']"), "Navigation Button To Next Page");

    }

    public int getFormsCount(){
        final var productForms = new ElementCollection(By.xpath("//div[@id='card_grid']//div[@class='card-v2']"), "Locator For Forms Inside Categories");

        return productForms.size();
    }

    public void hoverOnCategoryLabel(){
        this.categoryLabel.hover();
    }

    public void clickItemButton(){
        this.itemButton.click();
    }

    public String getSectionTitle(){
        return this.sectionTitle.getText();
    }

    public void clickSecondPageButton(){
        this.nextPageButton.click();

    }

    public String getSectionProductsAmount() {
        return sectionProductsAmount.getText();
    }

    public String getPageHeader(){
        return this.pageHeader.getText();
    }
}
