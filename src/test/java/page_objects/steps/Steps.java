package page_objects.steps;

import static framework.utils.Utils.waitUntil;
import com.codeborne.selenide.WebDriverRunner;
import constants.Brands;
import constants.Categories;
import constants.Filters;
import constants.Timeouts;
import framework.utils.LoggerUtil;
import interfaces.Product;
import io.qameta.allure.Allure;
import static org.testng.Assert.*;

import page_objects.HomePage;
import page_objects.cart.CartProductForm;
import page_objects.category.CategoryPage;
import page_objects.category.SortDropdownForm;
import page_objects.category.filters.FilterForm;
import page_objects.category.filters.PopupFilterForm;
import page_objects.product.ProductForm;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class Steps {
    private HomePage homePage;
    private CategoryPage categoryPage;
    private SortDropdownForm sortForm;
    private FilterForm filterForm;
    private PopupFilterForm popUpFilterForm;

    public Steps(){
        this.homePage = new HomePage();
        this.categoryPage = new CategoryPage(Categories.DUMMY_CATEGORY);
        this.sortForm = new SortDropdownForm();
        this.filterForm = new FilterForm(Filters.MANUFACTURER);
        this.popUpFilterForm = new PopupFilterForm(Filters.MANUFACTURER);


    }

    public <T extends Product> boolean isValidProduct(T form){
        try{
            try {
                String title = form.getTitleText();
                String price = form.getPriceText();

                return !title.isEmpty() && !price.isEmpty();
            } catch (Exception e) {
                return false;
            }
        }
        catch(Exception er){
            return false;
        }
    }

    public double getNumericValue(String priceText){
        String numericValue = priceText.replaceAll("[^\\d,]", "");
        numericValue = numericValue.replace(",", ".");

        try{
            return Double.parseDouble(numericValue);
        }
        catch(NumberFormatException ex){
            return 0.0;
        }
    }

    public <T> void waitForValueToChange(T initialValue, Supplier<T> getNewValue){
        waitUntil(() -> {
            final var currentValue = getNewValue.get();
            return !Objects.equals(initialValue, currentValue);
        }, Timeouts.SHORT_TIMEOUT.getTimeout(), 200, "Value didn't change it time");
    }

    public void navigateToCategory(Categories category){
        var categoryPage = new CategoryPage(category);
        Allure.step("Verifying page title is as expected", () -> {
            assertTrue(WebDriverRunner.getWebDriver().getTitle().contains("eMAG.bg - Широка гама продукти"), "page title should be eMAG.bg - Широка гама продукти");
        });

        Allure.step(String.format("Navigating to %s category", category.getCategoryName()), () -> {
            var url = WebDriverRunner.url();
            homePage.hoverOnCategoriesMenu();
            categoryPage.hoverOnCategoryLabel();
            categoryPage.clickItemButton();
            waitForUrlToChange(url);
        });

        Allure.step(String.format("Verify that the section title contains %s aswell as browser tab", category.getCategoryName()), () -> {
            var browserTitle = WebDriverRunner.getWebDriver().getTitle();

            assertTrue(browserTitle.contains(category.getCategoryName()), String.format("Browser tab should contain %s instead it was %s", category.getCategoryName(), browserTitle));
            if(!category.getCategoryShortName().isEmpty())
            {
                assertEquals(categoryPage.getSectionTitle(), category.getCategoryShortName().get(), String.format("Category section title should be %s", category.getCategoryShortName()));
            }
            else{
                assertEquals(categoryPage.getSectionTitle(), category.getCategoryName(), String.format("Category section title should be %s", category.getCategoryName()));
            }
        });

    }

    public void filterProductsByBrand(Brands brand){
        Allure.step(String.format("Navigating to search filter section and typing in %s", brand.getBrandName()), () -> {
            filterForm.clickSeeMoreButton();
            popUpFilterForm.sendTextToSearchbox(brand.getBrandName());
        });

        Allure.step(String.format("Checking %s checkbox and clicking filter button", brand.getBrandName()), () -> {
            popUpFilterForm.checkCheckbox(brand.getBrandName());
            popUpFilterForm.clickFilterButton();

            waitUntilUrlContains(brand.getBrandName().toLowerCase());
        });
    }

    public void assertProductTitlesIncludeBrand(Brands brand){
        Allure.step(String.format("Verifying that each title on the page contains %s", brand.getBrandName()), () -> {
            var formsCount = categoryPage.getFormsCount();
            LoggerUtil.info(String.format("Forms count: %d", formsCount));

            for(int i = 1; i <= formsCount; i++){
                var productForm = new ProductForm(i);
                var isValidProduct = isValidProduct(productForm);

                if(!isValidProduct){
                    fail("Product should have visible title and price");
                }

                var productTitle = productForm.getTitleText().toLowerCase();

                if(brand.getLocalizedBrandName().isPresent()){
                    assertTrue(productTitle.contains(brand.getBrandName().toLowerCase()) || productTitle.contains(brand.getLocalizedBrandName().get().toLowerCase()), String.format("Product %d title %s should include %s or %s", i, productTitle, brand.getBrandName(), brand.getLocalizedBrandName().get()));
                }
                else{
                    assertTrue(productTitle.contains(brand.getBrandName().toLowerCase()), String.format("Product %d title should include %s", i, brand.getBrandName()));
                }
            }
        });
    }

    public void verifyProductPricesInDescendingOrder(){
        Allure.step("Sorting prices in descending order", () -> {
            final var currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
            sortForm.sortPriceInDescendingOrder();
            waitForUrlToChange(currentUrl);
        });

        Allure.step("Verifying each product's price is higher or equal to the next", () -> {
            final var formsCount = categoryPage.getFormsCount();
            LoggerUtil.info(String.format("Forms count: %d", formsCount));
            for(int i = 1; i < formsCount; i++){
                var product = new ProductForm(i);
                var nextProduct = new ProductForm(i + 1);
                final var isValidProduct = isValidProduct(product);
                final var isNextValidProduct = isValidProduct(nextProduct);

                if(!isValidProduct || !isNextValidProduct){
                    fail(String.format("Each product should have a visible title and price. First product valid: %b, Second product valid: %b", isValidProduct, isNextValidProduct));
                }
                var productPrice = getNumericValue(product.getPriceText());
                final var nextProductPrice = getNumericValue(nextProduct.getPriceText());

                if(product.getTitleText().contains("Разопакован:")){
                    productPrice = getNumericValue(product.getOpenedPrice());
                }
                assertTrue(productPrice >= nextProductPrice, String.format("Product price: %f should be higher or equal to the next product price: %f", productPrice, nextProductPrice));
            }
        });
    }

    public void waitUntilUrlContains(String keyword){
        waitUntil(() -> WebDriverRunner.url().contains(keyword), Timeouts.SHORT_TIMEOUT.getTimeout(), "Url didn't change in time");
    }

    public void waitUntilHeartIconIs2(){
        waitUntil(() -> homePage.getNumberOnHeartIcon().equals("2"), Timeouts.SHORT_TIMEOUT.getTimeout(), "Heart icon didnt become 2");
    }

    public void waitForUrlToChange(String url){
        waitUntil(() -> !WebDriverRunner.url().equals(url), Timeouts.SHORT_TIMEOUT.getTimeout(), 200, "Url didn't change in time");
    }
}
