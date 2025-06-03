package framework.elements;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import framework.utils.LoggerUtil;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class BaseElement {
    protected final By locator;
    protected final String name;

    public BaseElement(By locator, String name){
        this.locator = locator;
        this.name = name;
    }

    public SelenideElement _GetElement(){
        return $(locator);
    }

    public void waitForVisible(){
        LoggerUtil.debug("Waiting for {} element to be visible", name);
        _GetElement().shouldBe(Condition.visible);
    }

    public void waitForVisible(int timeout){
        LoggerUtil.debug("Waiting for {} element to be visible with duration: {} seconds", name, timeout);
        _GetElement().shouldBe(Condition.visible, Duration.ofSeconds(timeout));
    }

    public Dimension getSize(){
        LoggerUtil.debug("Getting size width/height of {}", name);
        var size = _GetElement().getSize();
        LoggerUtil.debug("Element size: {}/{}", size.getWidth(), size.getHeight());
        return size;

    }

    public void dragAndDropBy(int x, int y){
        LoggerUtil.debug("Performing drag and drop on {} by offset: {}/{}", name, x, y);
        new Actions(WebDriverRunner.getWebDriver())
                .clickAndHold(_GetElement())
                .moveByOffset(x, y)
                .release()
                .perform();
    }

    public void waitForEnabled(){
        LoggerUtil.debug("Waiting for {} element to be visible", name);
        _GetElement().shouldBe(Condition.enabled);
    }

    public void waitForEnabled(int timeout){
        LoggerUtil.debug("Waiting for {} element to be enabled with duration: {} seconds", name, timeout);
        _GetElement().shouldBe(Condition.enabled, Duration.ofSeconds(timeout));
    }

    public void waitForText(String text){
        LoggerUtil.debug("Waiting for {} element to have text: {}", name, text);
        _GetElement().shouldHave(Condition.text(text));
    }

    public void click(){
        LoggerUtil.debug("Clicking on element: {}", name);
        _GetElement().click();
    }
    public void doubleClick(){
        LoggerUtil.debug("Double clicking on element: {}", name);
        _GetElement().doubleClick();
    }

    public void hover(){
        LoggerUtil.debug("Hovering on element: {}", name);
        _GetElement().hover();
    }

    public String getText(){
        LoggerUtil.debug("Getting Text from element: {}", name);
        var text = _GetElement().getText();
        LoggerUtil.debug("{} Text: {}", name, text);
        return text;
    }

    public Boolean isVisible(){
        LoggerUtil.debug("Checking visability for element: {}", name);
        var isDisplayed = _GetElement().isDisplayed();
        LoggerUtil.debug("{} Visability: {}", name, isDisplayed);
        return isDisplayed;
    }

    public String getAttribute(String attribute){
        LoggerUtil.debug("Getting {} attribute from element: {}", attribute, name);
        return _GetElement().getAttribute(attribute);
    }

    public String getCSSValue(String value){
        LoggerUtil.debug("Getting {} CSS value from element: {}", value, name);
        return _GetElement().getCssValue(value);
    }
}