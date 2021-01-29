package hsos.cho.srv.login.entity;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

public class LoginValidater {

    @ConfigProperty(name = "loginusername")
    private String username = "choreos";

    @ConfigProperty(name = "loginpassword")
    private String password = "choreos";

    //Status Validation
    private boolean validated = false;
    //Count of Tries
    private short loginTry;

    private static final Logger log = Logger.getLogger(LoginValidater.class.getSimpleName());

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

        if( uname.contentEquals(username) && pword.contentEquals(password) )
        {
            log.info("Password Correct");
            loginTry = 0;
            validated = true;
            return;
        }

        log.info("username or password incorrect! Try again! Try: " + loginTry );
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
