package qtriptest;

import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;


public class DriverSingleton {
    private static RemoteWebDriver driver;
    private static DriverSingleton singleton = null;

    private DriverSingleton() throws MalformedURLException{
        final DesiredCapabilities capabilities= new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"),capabilities);
        driver.manage().window().maximize();
    }

    public static RemoteWebDriver getSingletonDriver() throws MalformedURLException{
       if(singleton==null){
        singleton = new DriverSingleton();
       }
       return driver;
    }
}
