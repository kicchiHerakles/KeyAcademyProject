package com.theherakles.keyacademy.step_definitions;

import com.theherakles.keyacademy.utils.BrowserUtil;
import com.theherakles.keyacademy.utils.DriverUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

  @Before
  public void setUp(){
    BrowserUtil.turnOnImplicitWaits();
    DriverUtil.getDriver().manage().window().maximize();
  }

  @After
  public void tearDown(Scenario scenario){
    if(scenario.isFailed()){
      final byte[] screenshot = ((TakesScreenshot) DriverUtil.getDriver())
          .getScreenshotAs(OutputType.BYTES);
      scenario.attach(screenshot,"image/png","screenshot");
    }

    DriverUtil.closeDriver();
  }
}
