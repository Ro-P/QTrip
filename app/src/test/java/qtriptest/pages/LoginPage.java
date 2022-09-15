package qtriptest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    WebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";

    @FindBy(id="floatingInput")
    WebElement email_address_textbox;
    @FindBy(name="password")
    WebElement password_textbox;
    @FindBy(className="btn-login")
    WebElement register_button;

    

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,20), this);
    }

    public void navigateToRegisterPage(){
        if(!driver.getCurrentUrl().equals(url))
            driver.get(url);
        
    }

    public void performLogin(String userName,String password){
       
        email_address_textbox.sendKeys(userName);
        password_textbox.sendKeys(password);
        register_button.click();

        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/"));

      //  return logout_button.getText().equals("Logout");
        
    } 
}
