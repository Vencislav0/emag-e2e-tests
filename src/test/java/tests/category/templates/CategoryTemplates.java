package tests.category.templates;

import com.codeborne.selenide.WebDriverRunner;
import constants.Brands;
import constants.Categories;
import io.qameta.allure.Allure;
import page_objects.category.CategoryPage;
import page_objects.steps.Steps;

public class CategoryTemplates {
    private static final Steps steps = new Steps();
    private CategoryTemplates(){

    }

    public static void testProductsSortingAndFiltering(Categories category, Brands brand){
        CategoryPage categoryPage = new CategoryPage(Categories.PHONES);
        steps.filterProductsByBrand(brand);
        steps.assertProductTitlesIncludeBrand(brand);

        Allure.step("Navigating to the second page, verifying titles and verifying sorting in descending order sorts prices correctly", () -> {
            categoryPage.clickSecondPageButton();
            steps.waitForUrlToChange(WebDriverRunner.url());
            steps.assertProductTitlesIncludeBrand(brand);
            steps.verifyProductPricesInDescendingOrder();

        });
    }
}
