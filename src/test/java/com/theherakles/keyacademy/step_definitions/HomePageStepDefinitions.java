package com.theherakles.keyacademy.step_definitions;

import com.theherakles.keyacademy.config.ApplicationConfig;
import com.theherakles.keyacademy.pom.HomePage;
import com.theherakles.keyacademy.utils.ConfigurationReaderUtil;
import com.theherakles.keyacademy.utils.DriverUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;

@Log4j2
public class HomePageStepDefinitions {

  private HomePage homePage;

  @Given("Home page is loaded")
  public void home_page_is_loaded() {
    log.info("STEP - Navigate to home page");
    DriverUtil.getDriver().get(ConfigurationReaderUtil.getConfiguration().getMainPageUrl());
    homePage = new HomePage();
    log.info("STEP - Home Page loaded");
  }

  @Then("user should see the following menus in the navigation bar")
  public void user_should_see_the_following_menus_in_the_navigation_bar(List<String> menuNames) {
    for (String menuName:menuNames) {
      log.info("STEP - Verify " + menuName + " is visible at navigation bar");
      Assert.assertTrue(menuName + " is not visible!", homePage.getNavMenuByName(menuName).isDisplayed());


    }
  }

  @Then("page title should be {string}")
  public void pageTitleShouldBe(String titleExpected) {
    log.info("STEP - Verify title is '" + titleExpected + "'");
      Assert.assertEquals(titleExpected, homePage.getTitle().getText());
  }
}
