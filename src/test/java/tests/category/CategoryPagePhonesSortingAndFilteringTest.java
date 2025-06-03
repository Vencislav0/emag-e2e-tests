package tests.category;

import constants.Brands;
import constants.Categories;
import io.qameta.allure.Allure;
import org.testng.annotations.Test;
import page_objects.HomePage;
import page_objects.steps.Steps;
import tests.BaseTest;

import static tests.category.templates.CategoryTemplates.testProductsSortingAndFiltering;

public class CategoryPagePhonesSortingAndFilteringTest extends BaseTest {
    HomePage homePage = new HomePage();
    Steps steps = new Steps();
    @Test
    public void shouldSortInDescendingOrderAndFilterByName() {
        Allure.step("Accepting cookies if needed", () -> {
            homePage.acceptCookiesIfNeeded();
            homePage.dismissAccountLogin();
        });
        steps.navigateToCategory(Categories.PHONES);
        testProductsSortingAndFiltering(Categories.PHONES, Brands.SAMSUNG);

    }
}
