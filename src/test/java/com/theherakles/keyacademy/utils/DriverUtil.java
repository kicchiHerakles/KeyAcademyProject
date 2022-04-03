package com.theherakles.keyacademy.utils;

import com.theherakles.keyacademy.enums.BrowserType;
import com.theherakles.keyacademy.exception.AutomationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
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

    private static final String USERNAME = "hseyinkeeci_OFjRua";
    private static final String AUTOMATE_KEY = System.getenv("QepXX2yWg9cVpc27dGhy");
    private static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
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
                else if (isPropertyProvided("isRemote")){
                    JSONParser parser = new JSONParser();
                    JSONObject testConfig = null;
                    WebDriver driver = null;
                    DesiredCapabilities capabilities;
                    HashMap<String, Object> browserstackOptions;
                    switch (browserType){
                        case CHROME:
                            testConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/bs_chrome_win.json"));
                            capabilities = prepareDesiredCapabilities(testConfig);

//                            browserstackOptions = new HashMap<String, Object>();
//                            browserstackOptions.put("os", "Windows");
//                            browserstackOptions.put("osVersion", "10");
//                            capabilities.setCapability("bstack:options", browserstackOptions);

                            driver = new RemoteWebDriver(new URL(URL), capabilities);
                            driverPool.set(driver);
                            break;
                        case FIREFOX:
                            testConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/bs_firefox_win.json"));
                            capabilities = prepareDesiredCapabilities(testConfig);

//                            browserstackOptions = new HashMap<String, Object>();
//                            browserstackOptions.put("os", "Windows");
//                            browserstackOptions.put("osVersion", "10");
//                            capabilities.setCapability("bstack:options", browserstackOptions);

                            driver = new RemoteWebDriver(new URL(URL), capabilities);
                            driverPool.set(driver);
                            break;
                        case SAFARI:
                            testConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/bs_safari_macos.json"));
                            capabilities = prepareDesiredCapabilities(testConfig);
                            driver = new RemoteWebDriver(new URL(URL), capabilities);
                            driverPool.set(driver);
                            break;
                        case IOS_PHONE:
                            testConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/bs_ios_phone.json"));
                            capabilities = prepareDesiredCapabilities(testConfig);
                            driver = new RemoteWebDriver(new URL(URL), capabilities);
                            driverPool.set(driver);
                            break;
                        case IOS_TABLET:
                            testConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/bs_ios_tablet.json"));
                            capabilities = prepareDesiredCapabilities(testConfig);
                            driver = new RemoteWebDriver(new URL(URL), capabilities);
                            driverPool.set(driver);
                            break;
                        case ANDROID_PHONE:
                            testConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/bs_android_phone.json"));
                            capabilities = prepareDesiredCapabilities(testConfig);
                            driver = new RemoteWebDriver(new URL(URL), capabilities);
                            driverPool.set(driver);
                            break;
                        case ANDROID_TABLET:
                            testConfig = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/bs_android_tablet.json"));
                            capabilities = prepareDesiredCapabilities(testConfig);
                            driver = new RemoteWebDriver(new URL(URL), capabilities);
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

    private static boolean isPropertyProvided(String target){
        return System.getProperty(target) != null && System.getProperty(target).equals("true");
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
