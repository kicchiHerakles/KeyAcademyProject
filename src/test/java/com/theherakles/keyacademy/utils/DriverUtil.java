package com.theherakles.keyacademy.utils;

import com.theherakles.keyacademy.enums.BrowserType;
import com.theherakles.keyacademy.exception.AutomationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Capable of parallel browser execution by using the driverPool
 */
@NoArgsConstructor
public class DriverUtil {

    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    @NonNull
    public static WebDriver getDriver() {
        try {
            if (driverPool.get() == null) {
                BrowserType browserType = ConfigurationReaderUtil.getConfiguration().getBrowserType();
                if (System.getProperty("browser") != null)
                    browserType = BrowserType.getByName(System.getProperty("browser"));

                if (System.getProperty("isLocal") != null && System.getProperty("isLocal").equals("true"))
                {
                    switch (browserType) {
                        case CHROME:
                            WebDriverManager.chromedriver().setup();
                            driverPool.set(new ChromeDriver());
                            break;
                        case CHROME_HEADLESS:
                            WebDriverManager.chromedriver().setup();
                            driverPool.set(new ChromeDriver(new ChromeOptions().setHeadless(true)));
                            break;
                        case FIREFOX:
                            WebDriverManager.firefoxdriver().setup();
                            driverPool.set(new FirefoxDriver());
                            break;
                        case FIREFOX_HEADLESS:
                            WebDriverManager.firefoxdriver().setup();
                            driverPool.set(new FirefoxDriver(new FirefoxOptions().setHeadless(true)));
                            break;
                        case INTERNET_EXPLORER:
                            if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                                throw new AutomationException("Your OS doesn't support Internet Explorer");
                            WebDriverManager.iedriver().setup();
                            driverPool.set(new InternetExplorerDriver());
                            break;
                        case EDGE:
                            if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                                throw new AutomationException("Your OS doesn't support Edge");
                            WebDriverManager.edgedriver().setup();
                            driverPool.set(new EdgeDriver());
                            break;
                        case SAFARI:
                            if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                                throw new AutomationException("Your OS doesn't support Safari");
                            WebDriverManager.getInstance(SafariDriver.class).setup();
                            driverPool.set(new SafariDriver());
                            break;
                        case CHROME_REMOTE:
                            ChromeOptions chromeOptions = new ChromeOptions();
                            chromeOptions.setCapability("platform", Platform.ANY);
                            driverPool.set(new RemoteWebDriver(new URL(ConfigurationReaderUtil.getConfiguration().getRemoteGridUrl()), chromeOptions));
                            break;
                    }
                }
                else if (System.getProperty("isRemote") != null && System.getProperty("isRemote").equals("true")){
                    JSONParser parser = new JSONParser();
                    JSONObject testConfig = null;
                    WebDriver driver = null;
                    DesiredCapabilities capabilities;
                    HashMap<String, Object> browserstackOptions;
                    switch (browserType){
                        case CHROME:
                            testConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/bs_chrome_win10.json"));

                            capabilities = prepareDesiredCapabilities(testConfig);

                            browserstackOptions = new HashMap<String, Object>();
                            browserstackOptions.put("os", "Windows");
                            browserstackOptions.put("osVersion", "10");

                            capabilities.setCapability("bstack:options", browserstackOptions);
                            driver = new RemoteWebDriver(new URL("https://hseyinkeeci_OFjRua:QepXX2yWg9cVpc27dGhy@hub-cloud.browserstack.com/wd/hub"), capabilities);
                            driverPool.set(driver);
                            break;
                        case FIREFOX:
                            testConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/bs_firefox_win10.json"));

                            capabilities = prepareDesiredCapabilities(testConfig);

                            browserstackOptions = new HashMap<String, Object>();
                            browserstackOptions.put("os", "Windows");
                            browserstackOptions.put("osVersion", "10");

                            capabilities.setCapability("bstack:options", browserstackOptions);
                            driver = new RemoteWebDriver(new URL("https://hseyinkeeci_OFjRua:QepXX2yWg9cVpc27dGhy@hub-cloud.browserstack.com/wd/hub"), capabilities);
                            driverPool.set(driver);
                            break;

                    }
                }
                else
                    throw new AutomationException("Local or remote execution is not specified. Please provide either isLocal or isRemote properties with 'true' value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driverPool.get();
    }

    private static DesiredCapabilities prepareDesiredCapabilities(JSONObject testConfig){
        DesiredCapabilities capabilities = new DesiredCapabilities(testConfig);
        capabilities.setCapability("project", ConfigurationReaderUtil.getConfiguration().getProjectName());
        capabilities.setCapability("build", ConfigurationReaderUtil.getConfiguration().getBuild());
        capabilities.setCapability("name", "test name");
        return capabilities;
    }

    public static void closeDriver() {
        driverPool.get().quit();
        driverPool.remove();
    }
}

class TestClass1 implements Runnable {
    public void run() {
        Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
        capsHashtable.put("device", "iPhone 12 Pro");
        capsHashtable.put("real_mobile", "true");
        capsHashtable.put("os_version", "14");
        capsHashtable.put("build", "browserstack-build-1");
        capsHashtable.put("name", "Thread 1");
        mainTestClass r1 = new mainTestClass();
        r1.executeTest(capsHashtable);
    }
}
class TestClass2 implements Runnable {
    public void run() {
        Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
        capsHashtable.put("device", "Google Pixel 5");
        capsHashtable.put("real_mobile", "true");
        capsHashtable.put("os_version", "11.0");
        capsHashtable.put("build", "browserstack-build-1");
        capsHashtable.put("name", "Thread 2");
        mainTestClass r2 = new mainTestClass();
        r2.executeTest(capsHashtable);
    }
}

class mainTestClass {
    public static final String USERNAME = "hseyinkeeci_OFjRua";
    public static final String AUTOMATE_KEY = "QepXX2yWg9cVpc27dGhy";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public static void main(String[] args) throws Exception {
        Thread object1 = new Thread(new TestClass1());
        object1.start();
        Thread object2 = new Thread(new TestClass2());
        object2.start();
    }
    public void executeTest(Hashtable<String, String> capsHashtable) {
        String key;
        DesiredCapabilities caps = new DesiredCapabilities();
        // Iterate over the hashtable and set the capabilities
        Set<String> keys = capsHashtable.keySet();
        Iterator<String> itr = keys.iterator();
        while (itr.hasNext()) {
            key = itr.next();
            caps.setCapability(key, capsHashtable.get(key));
        }
        WebDriver driver;
        try {
            driver = new RemoteWebDriver(new URL(URL), caps);
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            // Searching for 'BrowserStack' on google.com
            driver.get("https://www.google.com");
            WebElement element = driver.findElement(By.name("q"));
            element.sendKeys("BrowserStack");
            element.submit();
            // Setting the status of test as 'passed' or 'failed' based on the condition; if title of the web page contains 'BrowserStack'
            WebDriverWait wait = new WebDriverWait(driver, 5);
            try {
                wait.until(ExpectedConditions.titleContains("BrowserStack"));
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Title matched!\"}}");
            }
            catch(Exception e) {
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Title not matched\"}}");
            }
            System.out.println(driver.getTitle());
            driver.quit();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}