package com.theherakles.keyacademy.pom;

import com.theherakles.keyacademy.annotations.Desktop;
import com.theherakles.keyacademy.annotations.Phone;
import com.theherakles.keyacademy.pom.components.NavHeaderComponent;
import com.theherakles.keyacademy.utils.AbstractPage;
import com.theherakles.keyacademy.utils.DriverUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
@Desktop(HomePage.class)
@Phone(HomePagePhone.class)
public class HomePage extends AbstractPage {
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

  @FindBy(xpath = "(//footer)[2]/a")
  private WebElement toTheTopButton;

  @FindBy(xpath = "(//footer)[2]/a/i")
  private WebElement arrowOfTopButton;

}

class HomePagePhone extends HomePage {


}