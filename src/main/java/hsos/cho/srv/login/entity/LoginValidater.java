package hsos.cho.srv.login.entity;

import hsos.cho.srv.settings.entity.Properties;
import org.jboss.logging.Logger;
import javax.inject.Inject;

/**
 * @author Lukas Grewe
 * LoginValidater is an Entity, that exist in the Session.
 * This entity validates the session
 */
public class LoginValidater {
    //for using some properties
    @Inject private Properties properties;
    //get username from properties
    private String username = properties.username;
    //get password from properties
    private String password = properties.password;
    //status of Validation
    private boolean validated = false;
    //count of logintries
    private short loginTry;
    //Logger for this class
    private static final Logger log = Logger.getLogger(LoginValidater.class.getSimpleName());

    /**
     * default Constructor
     **/
    public LoginValidater () { loginTry = 0; }

    /**
     * @author Lukas Grewe
     * validate incoming login data from a Post-Request
     * @param uname username extracted from Post-Request
     * @param pword password extracted from PostRequest
     * */
    public void validate(String uname, String pword)
    {
        if( uname.contentEquals(username) && pword.contentEquals(password)) {
            //username/password correct -> session is validated
            log.info("Password Correct");
            validated = true;
            return;
        }
        //else
        //username/password incorrect -> session not validated
        log.info("username or password incorrect! Try again! Try: " + loginTry );
        ++loginTry;
    }

    /**
     * GETTER AND SETTER METHODS BELOW
     */
    public boolean isValidated() { return validated; }

    public long getLoginTry(){ return loginTry; }
}
