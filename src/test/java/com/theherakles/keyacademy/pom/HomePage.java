package com.theherakles.keyacademy.pom;

import com.theherakles.keyacademy.pom.components.NavHeaderComponent;
import com.theherakles.keyacademy.utils.DriverUtil;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HomePage extends NavHeaderComponent {
  public HomePage(){
    super();
    PageFactory.initElements(DriverUtil.getDriver(), this);
  }

  @FindBy(xpath = "//h1/b")
  private WebElement pageName;

  @FindBy(xpath="//div[@class='w3-center']//span")
  private List<WebElement> homePageSliders;

  @FindBy(xpath="//span[@class='w3-white w3-padding-large w3-animate-bottom']")
  private List<WebElement> sliderTitles;

  //collects each HomePage slide's title text in a list
  public List<String> getActualSliderTitles(){
    List<String> actualSliderTitles = new ArrayList<>();
    int sliderNumber = homePageSliders.size();

    for (int i = 0; i < sliderNumber; i++) {
      homePageSliders.get(i).click();
      String slideTitle = sliderTitles.get(i).getText();
      actualSliderTitles.add(slideTitle);
    }
    return actualSliderTitles;
  }


}