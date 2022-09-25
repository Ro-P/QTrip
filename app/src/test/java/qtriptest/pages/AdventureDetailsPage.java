
package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import qtriptest.SeleniumWrapper;
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
        SeleniumWrapper.sendKeys(name_textbox, userName);
        SeleniumWrapper.sendKeys(date_textbox, date);
        SeleniumWrapper.sendKeys(person_textbox, noOfPerson+"");
        SeleniumWrapper.click(reserve_button, driver);
        // name_textbox.sendKeys(userName);
        // date_textbox.sendKeys(date);
        // person_textbox.clear();
        // person_textbox.sendKeys(noOfPerson+"");
        //reserve_button.click();
        count++;
        //return count;

    }

    public boolean isBookingSuccessful(){
        return booking_successful.getText().contains("Greetings! Reservation for this adventure is successful.");
    }

}