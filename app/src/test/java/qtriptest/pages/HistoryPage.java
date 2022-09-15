
package qtriptest.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HistoryPage {
   WebDriver driver;
   private String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html";

   @FindBy(xpath = "//tbody[@id='reservation-table']/tr//th")
   List<WebElement> list_transaction_id;

   public HistoryPage(WebDriver driver){
    this.driver = driver;
    PageFactory.initElements(new AjaxElementLocatorFactory(driver,20), this);
    // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
   }

   public void refreshPage(){
      driver.get(driver.getCurrentUrl());
   }

   public void navigateToHistoryPage(){

     try{ 
      driver.get(url);
      Thread.sleep(5000);
      driver.switchTo().alert().accept();
      }catch(Exception e){
        System.out.println("alert not present");
      }

   }

   public List<WebElement> getReservations(){
      WebDriverWait wait = new WebDriverWait(driver,30);
      wait.until(ExpectedConditions.visibilityOfAllElements(list_transaction_id));
      return list_transaction_id;
   }

   public void cancelReservations(String transactionId){
       for(int i=0;i<list_transaction_id.size();i++){
         if(list_transaction_id.get(i).getText().equals(transactionId)){
            WebElement trans_id=driver.findElement(By.xpath("//tbody[@id='reservation-table']/tr["+(i+1)+"]//td[8]"));
            trans_id.click();
            return ;
         }
       }
      }  

   public boolean isAdventureCancelled(String transactionId){
      // WebDriverWait wait = new WebDriverWait(driver,30);
      // wait.until(ExpectedConditions.visibilityOfAllElements(list_transaction_id));
      for(int i=0;i<list_transaction_id.size();i++){
         if(list_transaction_id.get(i).getText().equals(transactionId)){
           return false;
         }
       }
       return true;
   }
}