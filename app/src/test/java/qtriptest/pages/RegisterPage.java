package qtriptest.pages;

import java.util.UUID;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
    WebDriver driver;
    public String last_generated_user_name;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    @FindBy(id="floatingInput")
    WebElement email_address_textbox;
    @FindBy(name="password")
    WebElement password_textbox;
    @FindBy(xpath="//input[@name='confirmpassword']")
    WebElement confirm_password_textbox;
    @FindBy(className="btn-login")
    WebElement register_button;
    

    public RegisterPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,20), this);
    }

    public void navigateToRegisterPage(){
        if(!driver.getCurrentUrl().equals(url))
            driver.get(url);
        
    }

    public void registerNewUser(String userName,String password,String confirmPassword,boolean generateDynamicUserName){
        if(generateDynamicUserName)
            userName = userName + UUID.randomUUID().toString();

        last_generated_user_name = userName;

        email_address_textbox.sendKeys(userName);
        password_textbox.sendKeys(password);
        confirm_password_textbox.sendKeys(confirmPassword);
        register_button.click();

        // WebDriverWait wait = new WebDriverWait(driver,30);
        // wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/login/"));

       // return driver.getCurrentUrl().endsWith("/login");
        
    } 
}
