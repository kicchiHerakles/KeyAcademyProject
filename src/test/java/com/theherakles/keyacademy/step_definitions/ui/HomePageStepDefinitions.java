package com.theherakles.keyacademy.step_definitions.ui;

import com.theherakles.keyacademy.pom.HomePage;
import com.theherakles.keyacademy.utils.ConfigurationReaderUtil;
import com.theherakles.keyacademy.utils.DriverUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

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

  @Then("user should see the following titles in order through the Home Page slider")
  public void userShouldSeeTheFollowingTitlesInOrderThroughTheHomePageSlider(List<String> expectedSliderTitles){
    log.info("VERIFY - HomePage slider titles are seen as expected");
    Assert.assertEquals(expectedSliderTitles,homePage.getActualSliderTitles());
  }

  @When("user scrolls down to the end of the page")
  public void userScrollsDownToTheEndOfThePage() {
    log.info("STEP - User scrolls down to the end of the page'");
    JavascriptExecutor js = (JavascriptExecutor) DriverUtil.getDriver();
    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
  }

  @Then("user should see -To the top- button with an arrow upwards")
  public void userShouldSeeToTheTopButtonWithAnArrowUpwards() {
    log.info("VERIFY - -To the top- button is visible with an arrow-up icon");
    Assert.assertTrue(homePage.getToTheTopButton().isDisplayed() && homePage.getToTheTopButton().getText().equals("To the top") && homePage.getArrowOfTopButton().getAttribute("class").contains("fa-arrow-up"));

  }
}