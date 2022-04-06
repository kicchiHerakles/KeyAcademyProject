package com.theherakles.keyacademy.step_definitions.ui;

import com.theherakles.keyacademy.pom.HomePage;
import com.theherakles.keyacademy.utils.ConfigurationReaderUtil;
import com.theherakles.keyacademy.utils.DriverUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;

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
      log.info("VERIFY - " + menuName + " is visible at navigation bar");
      Assert.assertTrue(menuName + " is not visible!", homePage.getNavMenuByName(menuName).isDisplayed());


    }
  }

  @Then("page name should be {string}")
  public void pageNameShouldBe(String titleExpected) {
    log.info("STEP - Verify title is '" + titleExpected + "'");
    Assert.assertEquals(titleExpected, homePage.getPageName().getText());
  }

  @Then("user should see following buttons")
  public void userShouldSeeFollowingButtons(List<String> buttonNames) {
    for (String buttonName:buttonNames) {
      log.info("VERIFY - '" + buttonName + "' button is visible");
      Assert.assertTrue(buttonName + " button is not visible", homePage.getNavButtonByName(buttonName).isDisplayed());
    }
  }
}