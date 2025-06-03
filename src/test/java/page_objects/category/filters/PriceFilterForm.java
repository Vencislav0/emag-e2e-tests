package page_objects.category.filters;

import constants.Filters;
import framework.elements.Checkbox;
import framework.elements.Label;
import org.openqa.selenium.By;

public class PriceFilterForm extends FilterForm{

    private final Checkbox priceFrameCheckbox;
    private final Label leftKnob;
    private final Label slider;

    public PriceFilterForm(Filters filter){
        super(filter);
        var filterLocator = String.format("//div[@data-filter-id='%s']", filter.getFilterID());
        this.priceFrameCheckbox = new Checkbox(By.xpath(String.format("//a[@data-filter-id='%s' and text()='Ценова рамка']", filter.getFilterID())), "Price Frame Checkbox");
        this.leftKnob = new Label(By.xpath(String.format("%s//a[@class='knob left']", filterLocator)), "Left Knob");
        this.slider = new Label(By.xpath(String.format("%s//div[@class='range-bar']", filterLocator)), "Prices Slider");
    }

    public void moveLeftKnob(int moveAmount){
        //Check this variable to determine move amount for your case
        var sliderSize = this.slider.getSize();

        this.leftKnob.dragAndDropBy(moveAmount, 0);
    }

    public boolean isPriceFrameChecked(){
        var state = this.priceFrameCheckbox.getAttribute("class");

        return state.contains("active");
    }
}
