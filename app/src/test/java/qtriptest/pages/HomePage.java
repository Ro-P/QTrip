package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage {
    WebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/";

    @FindBy(className="register")
    WebElement register_button;

    @FindBy(className = "login")
    WebElement logout_button;

    @FindBy(className = "hero-input")
    WebElement search_textbox;

    @FindBy(xpath="//ul[@id='results']/a//li")
    WebElement auto_complete_text;

    @FindBy(xpath="//ul[@id='results']//h5")
    WebElement auto_complete_text_no_match_found;

    @FindBy(xpath="//div[@class='col-6 col-lg-3 mb-4']")
    List<WebElement> city_list;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void navigateToHomePage(){
        // if(!driver.getCurrentUrl().equals(url))
        // driver.get(url);
        SeleniumWrapper.navigate(driver, url);
    }
    
    //Click on register button
    public void clickOnRegister(){
        SeleniumWrapper.click(register_button, driver);
      //  register_button.click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/register/"));
    }

    //Check for loggedin user
    public boolean isUserLoggedIn(){
     try{
     return  logout_button.getText().equals("Logout");
    }catch(Exception e){
        return false;
    }
    }

    //Logout user
    public void logoutUser(){
        SeleniumWrapper.click(logout_button, driver);
        //logout_button.click();
    }
    
    //Search city
    public void searchCity(String cityName){
         WebDriverWait wait = new WebDriverWait(driver,30);
         wait.until(ExpectedConditions.visibilityOfAllElements(city_list));
         SeleniumWrapper.sendKeys(search_textbox,cityName);
        //  search_textbox.clear();
        //  search_textbox.sendKeys(cityName);
    }

    //verify autocomplete 
    public boolean assertAutoCompleteText(String cityName){
        try{
        return auto_complete_text.getText().equalsIgnoreCase(cityName);
        }catch(Exception e){
            return false;
        }
    }

    //Select a city from autocomplete
    public void selectCity(String cityName){
        // WebDriverWait wait = new WebDriverWait(driver,30);
        // wait.until(ExpectedConditions.visibilityOf(auto_complete_text));
        SeleniumWrapper.click(auto_complete_text, driver);
        //auto_complete_text.click();
    }

    //No city found
    public boolean verifyNoCityFound(String message){
    try{
        return auto_complete_text_no_match_found.getText().equalsIgnoreCase(message);
    }catch(Exception e){
        return false;
    }
    }

    
}
