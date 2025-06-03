package framework.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class ElementCollection {
    private final String name;
    private final By locator;

    public ElementCollection(By locator, String name){
        this.name = name;
        this.locator = locator;
    }

    public ElementsCollection _getElements(){
        return $$(locator);
    }

    public void shouldAllBeVisible(){
        _getElements().forEach(el -> el.shouldBe(Condition.visible));
    }

    public int size(){
        return _getElements().size();
    }

    public SelenideElement getElement(int index){
        return _getElements().get(index);
    }

    public List<String> getTexts(){
        return _getElements().texts();
    }

}
