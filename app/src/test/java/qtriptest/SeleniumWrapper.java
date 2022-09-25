package qtriptest;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumWrapper {

  public static boolean click(WebElement e,WebDriver driver){
    // Check if element exists before click
    // Scroll into view for a web element before trying to click it
    // Return true if the action was successful , else return false
   // boolean status = false;
   
    try{
    if(e.isDisplayed()){
       e.click();
    }else{
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView();", e);
        e.click();
    }
    return true;
    }catch(Exception ex){
        return false;
    }
  }

  public static boolean sendKeys(WebElement e,String keyToSend){
    //Clear existing elements of an input box before typing in new text
    //boolean status = false;
    try{
       e.clear();
       e.sendKeys(keyToSend);
       return true;
    }catch(Exception ex){
       return false;
    }
  }

  public static boolean navigate(WebDriver driver,String url){
    //Navigate to the new url only if the given url is different from the existing url
    try{
    if(!driver.getCurrentUrl().equals(url)){
        driver.get(url);
    }
    return true;
    }catch(Exception ex){
        return false;
    }
  }

  public static WebElement findElementWithRetry(WebDriver driver,By by,int retryCount){
    // Try to find an element
    // If the element is not available , retry upto 3 times before failing
        WebElement element = driver.findElement(by);
        if(!element.isDisplayed()){
          for(int i=0;i<retryCount;i++){
            if(element.isDisplayed()){
              return element;
            }
          }
        }
      return element;
  }

  public static String capture(WebDriver driver) throws IOException {
    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    File Dest = new File("src/../QTrip/" + System.currentTimeMillis()
    + ".png");
    String errflpath = Dest.getAbsolutePath();
    FileUtils.copyFile(scrFile, Dest);
    return errflpath;
    }
}

