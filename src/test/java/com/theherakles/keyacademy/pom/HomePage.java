package com.theherakles.keyacademy.pom;

import com.theherakles.keyacademy.pom.components.NavHeaderComponent;
import com.theherakles.keyacademy.utils.DriverUtil;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class HomePage extends NavHeaderComponent {
  public HomePage(){
    super();
    PageFactory.initElements(DriverUtil.getDriver(), this);
  }

  @FindBy(xpath = "//h1/b")
  private WebElement pageName;


}