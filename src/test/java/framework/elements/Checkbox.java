package framework.elements;

import framework.utils.LoggerUtil;
import org.openqa.selenium.By;

public class Checkbox extends BaseElement{

    public Checkbox(By locator, String name){
        super(locator, name);
    }

    public void check(){
        LoggerUtil.debug("Checking: {}", name);
        _GetElement().setSelected(true);
    }

    public void uncheck(){
        LoggerUtil.debug("Unchecking: {}", name);
        _GetElement().setSelected(false);
    }

    public boolean isChecked(){
        LoggerUtil.debug("Checking if {} is selected", name);
        var isSelected = _GetElement().isSelected();
        LoggerUtil.debug("{} checked status: {}", name, isSelected);
        return isSelected;
    }
}
