package hsos.cho.srv.login.entity;

import hsos.cho.srv.settings.entity.Properties;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginValidater {

    @Inject
    private Properties properties;

    private String username = properties.username;

    private String password = properties.password;

    //Status Validation
    private boolean validated = false;
    //Count of Tries
    private short loginTry;

    private static final Logger log = Logger.getLogger(LoginValidater.class.getSimpleName());

    /*
     * default Constructor
     * */
    public LoginValidater () {
        loginTry = 0;
    }

    /*
     * validate incoming login data from user
     * */
    public void validate(String uname, String pword)
    {
        if( uname.contentEquals(username) && pword.contentEquals(password))
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

    public long getLoginTry(){
        return loginTry;
    }
}
