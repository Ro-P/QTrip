
package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import java.util.concurrent.TimeUnit;

public class AdventureDetailsPage {
    WebDriver driver;
    public static int count =0;
    @FindBy(xpath="//input[@name='name']")
    //@CacheLookup
    WebElement name_textbox;

    @FindBy(xpath="//input[@name='date']")
   // @CacheLookup
    WebElement date_textbox;

    @FindBy(xpath="//input[@name='person']")
   // @CacheLookup
    WebElement person_textbox;
    
    @FindBy(xpath="//button[@class='reserve-button']")
    //@CacheLookup
    WebElement reserve_button;
    

    @FindBy(id="reserved-banner")
   // @CacheLookup
    WebElement booking_successful;
    

    public AdventureDetailsPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

   

    public void bookAdventure(String userName,String date,int noOfPerson)throws InterruptedException{
      
        name_textbox.sendKeys(userName);
        date_textbox.sendKeys(date);
    //   Thread.sleep(2000);
    //   JavascriptExecutor jse= (JavascriptExecutor)driver;
	// 	String script= "arguments[0].setAttribute('value','"+date+"');";
	// 	jse.executeScript(script, driver.findElement(By.xpath("//input[@name='date']")));
    //     Thread.sleep(2000);
        person_textbox.clear();
        person_textbox.sendKeys(noOfPerson+"");
        reserve_button.click();
        count++;
        //return count;

    }

    public boolean isBookingSuccessful(){
        return booking_successful.getText().contains("Greetings! Reservation for this adventure is successful.");
    }

}