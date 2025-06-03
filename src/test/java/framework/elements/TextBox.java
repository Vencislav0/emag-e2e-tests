package framework.elements;

import framework.utils.LoggerUtil;
import org.openqa.selenium.*;

public class TextBox extends BaseElement {
    public TextBox(By locator, String name){
        super(locator, name);
    }

    public void sendText(String text){
        LoggerUtil.debug("Sending text \"{}\" to element: {}", text, name);
        _GetElement().setValue(text);
    }

    public void clear(){
        LoggerUtil.debug("Clearing text field: {}", name);
        _GetElement().clear();
    }

    public String getPlaceholder(){
        var placeholder =  getAttribute("placeholder");
        return placeholder;
    }
}
