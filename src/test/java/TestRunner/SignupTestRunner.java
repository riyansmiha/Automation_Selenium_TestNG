package TestRunner;

import Base.Setup;
import Pages.LoginPage;
import Pages.SignupPage;
import Utils.Utils;
import io.qameta.allure.Allure;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class SignupTestRunner extends Setup {
    //positive test case
    Utils utils=new Utils();
    String email;
    String password;
    String mobile;
    public void requiredData(){
        String emailRandom="testman"+utils.generateRandomNumber(100,10000);
        email=emailRandom+"@test.com";
        password=utils.generateRandomPassword(8);
        mobile="175"+utils.generateRandomNumber(100000,9999999);
    }
    @Test(priority=1,description = "Cannot Create Account with already registered email")
    public void alreadyRegisteredEmail() throws IOException, ParseException, InterruptedException {
        driver.get("http://automationpractice.com/");
        SignupPage signupPage=new SignupPage(driver);
        utils.getUserCreds(0);
        String errorAlreadyregisteredMsg=signupPage.alreadyRegisteredEmail(utils.getEmail());
        Assert.assertTrue(errorAlreadyregisteredMsg.contains("An account using this email address has already been registered."));
        //Thread.sleep(1500);
//        Allure.description("Already been registered email cannot be used for creating new user and an " +
//                "error message will be given and user will not be taken to the next step and form fillup."
//        );
    }
    @Test(priority = 2,description = "After giving valid email,user will be redirecrted to registration form")
    public void validEmailRegisterForm() throws InterruptedException {
//        driver.get("http://automationpractice.com/");
        SignupPage signupPage=new SignupPage(driver);
        requiredData();
        signupPage.validEmailRegisterForm(email);
        //after logging with valid email
        Assert.assertTrue(signupPage.txtcreateAccount.getText().contains("CREATE AN ACCOUNT"));
        //Allure.description("After giving valid email, user should be redirected to the registration form containing Create Account and register button");
    }

    @Test(priority =3,description = "User press register button without inputting any data")
    public void EmptyInfoReg() throws InterruptedException {
        SignupPage signupPage=new SignupPage(driver);
        signupPage.EmptyInfoReg();
        Assert.assertTrue(signupPage.totalErrors.getText().equals("There are 8 errors"));
        //Allure.description("If the user does not input any data in the registration form ,total 8 errors will be given, that is," +
        //"the mandatory fields must be filled up for successful registration");
    }
    @Test(priority = 4,description = "Firstname and lastname cannot include any digit at last")
    public void invalidFirstandLastName() throws InterruptedException {
        SignupPage signupPage=new SignupPage(driver);
        List<String> invalidName=signupPage.invalidFirstandLastName();
        String invalidFirstName=invalidName.get(0);
        String invalidLastName=invalidName.get(1);
        Assert.assertTrue(invalidFirstName.contains("firstname is invalid"));
        Assert.assertTrue(invalidLastName.contains("lastname is invalid"));
        Allure.description("User cannot give firstname and last name containing digits, should contain only alphabets," +
                "numbers can be in between the alphabets.");
    }
    @Test(priority = 5,description = "Invalid Password Given,must be minimum 5 length")
    public void invalidPasswordCheck() throws InterruptedException {
        SignupPage signupPage=new SignupPage(driver);
//        System.out.println(signupPage.invalidPasswordCheck());
        Assert.assertTrue(signupPage.invalidPasswordCheck().contains("passwd is invalid."));
        //Allure.description("Password should be atleast 5 character length . Otherwise user won't be able to sign up.");
    }
    @Test(priority=6,description = "Zip/postal code must not be more or less than five digits")
    public void invalidZipCode() throws InterruptedException {
        SignupPage signupPage=new SignupPage(driver);
        String errorZipCode=signupPage.invalidZipCode();
        Assert.assertTrue(errorZipCode.contains("The Zip/Postal code you've entered is invalid. It must follow this format: 00000"));
        //Allure.description("Zip/postal code must not be greater or less than five digits and must follow this format:00000 " +
        //"else user won't be able to sign up");
    }
    @Test(priority = 7,description = "Mandatory Fields Must be filled up for successful sign up")
    public void OneMobileNum() throws InterruptedException, IOException, ParseException {
        SignupPage signupPage=new SignupPage(driver);
        String moberrormsg= signupPage.OneMobileNum();
        Assert.assertTrue(moberrormsg.contains("at least one phone number"));
        //Allure.description("All the mandatory fields in the form must be filled up,if one is missed atleast for example:mobile number is not given," +
        //"then an error message will be thrown to the user 'You must register at least one phone number' and the user will be unsuccessfull logging in. ");

    }

    @Test(priority = 8)
    public void finalSignUp(){
        SignupPage signupPage=new SignupPage(driver);
        String succssfulMsg = signupPage.finalSignUp(mobile);
        Assert.assertTrue(succssfulMsg.contains("MY ACCOUNT"));

    }


}