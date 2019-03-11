package tools;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

public class Tools {
    
    private String url;
    private String browser;
    private WebDriver driver;
    
    private WebDriverWait wait;
    private String root;
    private int count_screenshot;
    
    public Tools(){
        this.url = "https://www.google.cl/";
        this.browser = "chrome";
        this.root = "";
        this.count_screenshot = 0;
    }
    
    /* Get and Setter */
    public WebDriver getDriver() {
        return this.driver;
    }
    
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getBrowser() {
        return this.browser;
    }
    
    public void setBrowser(String browser) {
        browser = browser.toLowerCase();
        if(browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("ie") || browser.equalsIgnoreCase("firefox")) {
            this.browser = browser;
        }
    }

    
    /* Initializer */
    public WebDriver init() throws MalformedURLException {
        switch(this.browser) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("headless");
                options.addArguments("no-sandbox");
                DesiredCapabilities capability = DesiredCapabilities.chrome();
                capability.setCapability(ChromeOptions.CAPABILITY, options);
                this.driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
                break;
            case "ie":
                break;
            case "firefox":
                break;
            default:
                System.out.println("Ese navegador no existe.");
        }
        this.driver.manage().window().maximize();
        this.driver.get(this.url);
        this.wait = new WebDriverWait(this.driver, 10);
        return this.driver;
    }
    
    public void stop() {
        if(this.driver != null) {
            driver.close();
            driver.quit();
            driver = null;
        }
    }

    
    /* Evidence */
    public void screenshot(String _directory, String _class, String _method) throws IOException{
        String s_route = "";
        if(_directory == null || _directory == "") {
            s_route = _class;
        }else {
            _directory = _directory.replace("/",".");
            s_route = _directory + "." + _class;
        }
        if(this.root.isEmpty()) {
            this.root = (new File(".")).getCanonicalPath();
        }
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(root + "/screenshot/"+s_route+"/"+_method+"/screenshot_"+this.count_screenshot+".png"));
        this.count_screenshot++;
    }
    
    
    /* WebElement tools*/
    public void click(WebElement element){
        this.wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }
    
    public WebElement findElementByContent(List<WebElement> list, String content) {
        WebElement element = null;
        content = content.toLowerCase();
        System.out.println("La cantidad de estados es: " + list.size());
        for(int i=0; i<list.size();i++) {
            int index = list.get(i).getText().toLowerCase().indexOf(content);
            if(index != -1) {
                element = list.get(i);
            }
        }
        if(element != null)
            System.out.println("Contenido: "+ element.getText());
        return element;
    }
    
    
    /* Result skip */
    public void skipPreviousMethod() {
        throw new SkipException("\n" +
                " =============================================================== \n" +
                " TQC MESSAGE: The previous method can not continue with the flow." +
                " ===============================================================");
    }
    
    public void skipScript(Exception e) {
        this.stop();
        throw new SkipException("\n" + 
                " =============================================================== \n" +
                " TQC MESSAGE: contact the automation. \n" +
                "    * Exception: " + e.toString() + "\n" +
                "    * Message: " + e.getMessage() + "\n" +
                "    * Localized Message: " + e.getLocalizedMessage() + "\n" +
                "    * Cause: " + e.getCause() + "\n" +
                "    * fillInStackTrace: " + e.fillInStackTrace() + "\n" +
                "    * Clase: " + e.getClass().getName() +  "\n" +
                "    * Suppressed: " + e.getSuppressed().toString() + "\n" +
                " ==============================================================="
            );
    }
    
}