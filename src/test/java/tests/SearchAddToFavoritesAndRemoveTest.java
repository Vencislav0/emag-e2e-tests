package tests;

import com.codeborne.selenide.WebDriverRunner;
import constants.Categories;
import io.qameta.allure.Allure;
import org.testng.annotations.Test;
import page_objects.HomePage;
import page_objects.SearchForm;
import page_objects.category.CategoryPage;
import page_objects.category.SortDropdownForm;
import page_objects.favorites.FavoritesPage;
import page_objects.favorites.FavoritesProductForm;
import page_objects.product.ProductForm;
import page_objects.steps.Steps;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

public class SearchAddToFavoritesAndRemoveTest extends BaseTest{
    SearchForm searchForm = new SearchForm();
    CategoryPage dronesPage = new CategoryPage(Categories.DRONES);
    SortDropdownForm sortForm = new SortDropdownForm();
    HomePage homePage = new HomePage();
    FavoritesPage favoritesPage = new FavoritesPage();
    Steps steps = new Steps();

    @Test
    public void shouldSearchFavoriteAndDeleteProductSuccessfully() {
        Allure.step("Accepting cookies if needed", () -> {
            homePage.acceptCookiesIfNeeded();
            homePage.dismissAccountLogin();
        });

        Allure.step("Clicking on search box and verifying expected elements and placeholder are displayed", () -> {
            searchForm.clickOnSearchbox();
            assertTrue(searchForm.isMagnifierButtonDisplayed(), "Magnifier button should be displayed");
            assertTrue(searchForm.isXButtonDisplayed(), "(X) Button should be displayed after clicking on search box");
            assertTrue(searchForm.isSearchParagraphDisplayed(), "Search paragraph should be displayed");
            assertEquals(searchForm.getSearchTextboxPlaceholder(), "Какво търсиш днес?", "Search box should have placeholder Какво търсиш днес?");
            assertEquals(searchForm.getSearchParagraphText(), "Популярни търсения в eMAG", "Search paragraph should have text Популярни търсения в eMAG");
        });

        Allure.step("Typing in dji mini 4 pro searching then verifying the section title structure", () -> {
            final var initialUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
            searchForm.sendTextToSearch("dji mini 4 pro");
            searchForm.clickMagnifierButton();
            steps.waitForUrlToChange(initialUrl);

            final var header = dronesPage.getPageHeader();
            final var patternToFollow = "^\\d+\\s+резултата\\s+.+\\s+за\\s+\"[^\"]+\"$";

            final var matches = Pattern.matches(patternToFollow, header);

            assertTrue(matches, String.format("Header structure should follow this pattern \"some_numbers резултата some_text за “search_string”\" instead it was %s", header));
        });

        Allure.step("Sorting by popularity, favouriting the first two products, verifying favorite buttons states and menu favorites item has the number 2", () -> {
            sortForm.sortItemsByPopularity();
            final var firstProduct = new ProductForm(1);
            final var secondProduct = new ProductForm(2);

            firstProduct.clickOnFavoriteButton();
            assertTrue(firstProduct.getFavoriteButtonState().contains("active"), "first product should include active after adding to favorites");

            secondProduct.clickOnFavoriteButton();
            assertTrue(secondProduct.getFavoriteButtonState().contains("active"), "second product should include active after adding to favorites");

            steps.waitUntilHeartIconIs2();
            assertEquals(homePage.getNumberOnHeartIcon(), "2", "Heart Icon number should be 2 after adding two products");
        });

        Allure.step("Clicking on Favorites Menu Item, verifying the 2 previous products are added with correct details", () -> {
            final var firstProductTitle = new ProductForm(1).getTitleText();
            final var firstProductPrice = steps.getNumericValue(new ProductForm(1).getPriceText());

            final var secondProductTitle = new ProductForm(2).getTitleText();
            final var secondProductPrice = steps.getNumericValue(new ProductForm(2).getPriceText());

            final var initialUrl = WebDriverRunner.getWebDriver().getCurrentUrl();

            Map<String, Double> productData = new HashMap<>();
            productData.put(firstProductTitle, firstProductPrice);
            productData.put(secondProductTitle, secondProductPrice);

            homePage.clickFavoritesMenuItem();
            steps.waitForUrlToChange(initialUrl);

            final var firstFavoriteProduct = new FavoritesProductForm(1);
            final var secondFavoriteProduct = new FavoritesProductForm(2);

            final var firstFavoriteProductTitle = firstFavoriteProduct.getTitleText();
            final var firstFavoriteProductPrice = steps.getNumericValue(firstFavoriteProduct.getPriceText());

            final var secondFavoriteProductTitle = secondFavoriteProduct.getTitleText();
            final var secondFavoriteProductPrice = steps.getNumericValue(secondFavoriteProduct.getPriceText());


            assertTrue(productData.containsKey(firstFavoriteProductTitle), "first product title should be the same as the drones page product title");
            assertTrue(productData.containsKey(secondFavoriteProductTitle), "second product title should be the same as the drones page product title");

            assertTrue(productData.containsValue(firstFavoriteProductPrice), "first product price should be the same as the drones page product price");
            assertTrue(productData.containsValue(secondFavoriteProductPrice), "second product price should be the same as the drones page product price");
        });

        Allure.step("Deleting products 1 by 1 and verifying deletion each time", () -> {
            var pageHeaderText = favoritesPage.getHeaderText();
            final var firstProduct = new FavoritesProductForm(1);
            final var secondProduct = new FavoritesProductForm(2);

            assertEquals(pageHeaderText.trim(), "Любими\n2 Продукта", "Should have 2 products before deletion");

            secondProduct.clickDeleteButton();
            steps.waitForValueToChange(pageHeaderText, () -> favoritesPage.getHeaderText());

            pageHeaderText = favoritesPage.getHeaderText();
            assertEquals(pageHeaderText.trim(), "Любими\n1 продукт", "Should have 1 product before deletion of the second one");

            firstProduct.clickDeleteButton();
            steps.waitForValueToChange(pageHeaderText, () -> favoritesPage.getHeaderText());
            pageHeaderText = favoritesPage.getHeaderText();
            assertEquals(pageHeaderText.trim(), "Любими\n0 Продукта", "Should have 0 products after deleting the last one");
        });


    }
}
