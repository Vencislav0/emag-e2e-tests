package tests;

import constants.Categories;
import constants.Filters;
import io.qameta.allure.Allure;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import page_objects.HomePage;
import page_objects.cart.CartPage;
import page_objects.cart.CartProductForm;
import page_objects.category.CategoryPage;
import page_objects.category.filters.PriceFilterForm;
import page_objects.category.filters.SidebarFilterForm;
import page_objects.product.ProductForm;
import page_objects.product.ProductModalForm;
import page_objects.product.ProductPage;
import page_objects.steps.Steps;

public class PriceSortingAndCartTest extends BaseTest{
    HomePage homePage = new HomePage();
    ProductPage productPage = new ProductPage();
    CategoryPage categoryPage = new CategoryPage(Categories.GAMES_FOR_CONSOLES_AND_PC);
    Steps steps = new Steps();
    SidebarFilterForm sidebarFilterForm = new SidebarFilterForm();
    PriceFilterForm priceFilterForm = new PriceFilterForm(Filters.PRICE);
    ProductModalForm modalForm = new ProductModalForm();
    CartPage cartPage = new CartPage();

    @Test
    public void shouldSortByPriceCheckoutAndDeleteFromCart() {
        Allure.step("Accepting cookies if needed", () -> {
            homePage.acceptCookiesIfNeeded();
            homePage.dismissAccountLogin();
        });

        steps.navigateToCategory(Categories.GAMES_FOR_CONSOLES_AND_PC);

        Allure.step("Navigating to VR headsets using the sidebar filter and verifying section title is VR headsets", () -> {
            //initial section title before navigation needed to handle dynamic content
            final var sectionTitle = categoryPage.getSectionTitle();
            sidebarFilterForm.clickItemByText("VR Gaming Очила ");
            steps.waitForValueToChange(sectionTitle, () -> categoryPage.getSectionTitle());

            assertEquals(categoryPage.getSectionTitle(), "VR Gaming Очила", "Section title after navigation to VR headsets should be VR Gaming Очила");
        });

        Allure.step("Dragging price knob to the middle and verifying the minimum price has changed and Ценова рамка checkbox is checked", () -> {
            final var firstPriceBeforeFilter = steps.getNumericValue(new ProductForm(1).getPriceText());
            final var initialProductAmount = categoryPage.getSectionProductsAmount();
            priceFilterForm.moveLeftKnob(115);
            steps.waitForValueToChange(initialProductAmount, () -> categoryPage.getSectionProductsAmount());
            final var firstPriceAfterFilter = steps.getNumericValue(new ProductForm(1).getPriceText());

            assertNotEquals(firstPriceBeforeFilter, firstPriceAfterFilter, String.format("Prices on the first products should differ after price filter, price before: %f price after: %f", firstPriceBeforeFilter, firstPriceAfterFilter));
            assertTrue(priceFilterForm.isPriceFrameChecked(), "Price frame checkbox should be checked after filtering");

        });

        Allure.step("Clicking on the first product on the page verifying navigation to product page as well as price and product title", () -> {
            final var firstProduct = new ProductForm(1);
            final var categoryPagePrice = steps.getNumericValue(firstProduct.getPriceText());
            final var categoryPageTitle = firstProduct.getTitleText();

            firstProduct.clickOnProduct();
            final var productPagePrice = steps.getNumericValue(productPage.getPrice());
            final var productPageTitle = productPage.getTitle();

            assertEquals(categoryPagePrice, productPagePrice, String.format("Product page price should match the price listed on the category page. Category page price: %f, Product page price: %f", categoryPagePrice, productPagePrice));
            assertEquals(categoryPageTitle, productPageTitle, String.format("Product page title should match the title listed on the category page. Category page title: %s, Product page title: %s", categoryPageTitle, productPageTitle));
        });

        Allure.step("Clicking Add To Cart button and verifying that modal form is open as well as price and product title", () -> {
            final var productTitle = productPage.getTitle();
            final var productPrice = steps.getNumericValue(productPage.getPrice());
            productPage.clickAddToCartButton();

            final var modalProductTitle = modalForm.getTitle();
            final var modalProductPrice = steps.getNumericValue(modalForm.getPrice());

            assertEquals(modalForm.getFormTitleText(), "Продуктът е добавен в количката", "Modal Form should have correct title");
            assertEquals(productTitle, modalProductTitle, String.format("Product title should match the title listed on the modal form. Product title: %s, Modal form title: %s", productTitle, modalProductTitle));
            assertEquals(productPrice, modalProductPrice, String.format("Product price should match the price listed on the modal form. Product price: %f, Modal form price: %f", productPrice, modalProductPrice));
        });

        Allure.step("Clicking See Cart button and verifying page header is Количка за пазаруване and the product present has correct details", () -> {
            final var modalProductTitle = modalForm.getTitle();
            final var modalProductPrice = steps.getNumericValue(modalForm.getPrice());
           modalForm.clickGoToCartButton();

           final var cartProduct = new CartProductForm(1);
           final var cartProductTitle = cartProduct.getTitleText();
           final var cartProductPrice = steps.getNumericValue(cartProduct.getPriceText());

           assertEquals(cartPage.getHeaderText(), "Количка за пазаруване", "Количка за пазаруване should be displayed after navigation");
           assertEquals(modalProductTitle, cartProductTitle, "cart product title should be the same as modal form title");
           assertEquals(modalProductPrice, cartProductPrice, "cart product price should be the same as modal form price");
        });

        Allure.step("Increasing product quantity and verifying correct details after", () -> {
            final var cartProduct = new CartProductForm(1);
            final var priceBeforeIncrease = steps.getNumericValue(cartProduct.getPriceText());
            double dynamicPrice;
            int increaseCount = 2;

            while(!(cartProduct.getQntyIncreaseButtonState()).contains("pe-none") && increaseCount <= 7){
                dynamicPrice = steps.getNumericValue(cartProduct.getPriceText());

                cartProduct.clickIncreaseQntyButton();
                steps.waitForValueToChange(dynamicPrice, () -> steps.getNumericValue(cartProduct.getPriceText()));

                assertEquals(priceBeforeIncrease * increaseCount, steps.getNumericValue(cartProduct.getPriceText()));

                increaseCount++;
            }
        });

        Allure.step("Deleting product and verifying empty cart and empty cart message", () -> {
            final var cartProduct = new CartProductForm(1);

            cartProduct.clickDeleteProductButton();

            assertEquals(cartPage.getEmptyCartMessage(), "Количката за пазаруване е празна. За да добавиш продукти в количката, моля да се върнеш в началото.", "Should display correct message on empty cart");
            assertFalse(cartProduct.IsDisplayed());
        });
    }
}
