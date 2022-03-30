package com.theherakles.keyacademy.pom;

import com.theherakles.keyacademy.pom.components.NavHeaderComponent;
import com.theherakles.keyacademy.utils.DriverUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends NavHeaderComponent {
  public HomePage(){
    super();
    PageFactory.initElements(DriverUtil.getDriver(), this);
  }


}
