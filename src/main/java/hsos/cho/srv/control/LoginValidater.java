package hsos.cho.srv.control;

import hsos.cho.srv.properties.Properties;
import io.quarkus.arc.config.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

public class LoginValidater {

    @ConfigProperty(name = "loginusername")
    private String username = "choreos";

    @ConfigProperty(name = "loginpassword")
    private String password = "choreos";

    //Status Validation
    private boolean validated = false;
    //Count of Tries
    private short loginTry;

    /*
     * default Constructor
     * */
    public LoginValidater (){
        loginTry = 0;
    }

    /*
     * validate incoming login data from user
     * */
    public void validate(String uname, String pword)
    {
        System.out.println("un: " + username + " pw: " + password);

        if( uname.contentEquals(username) && pword.contentEquals(password) )
        {
            System.out.println("Validater: Password Correct");
            loginTry = 0;
            validated = true;
            return;
        }

        System.out.println("Validater: username or password incorrect! Try again! Try: " + loginTry );
        ++loginTry;
        validated = false;
    }

    /*
     * Get validated
     * */
    public boolean isValidated()
    {
        return validated;
    }


}
