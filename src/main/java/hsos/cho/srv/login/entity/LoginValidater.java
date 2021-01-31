package hsos.cho.srv.login.entity;

import org.apache.commons.codec.binary.Hex;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginValidater {

    @ConfigProperty(name ="login.username")
    private String username = "choreos";

    @ConfigProperty(name ="login.password")
    private String password = "choreos";

    private String md5password;

    //Status Validation
    private boolean validated = false;
    //Count of Tries
    private short loginTry;

    private static final Logger log = Logger.getLogger(LoginValidater.class.getSimpleName());

    /*
     * default Constructor
     * */
    public LoginValidater () {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();

            byte[] bytesOfMd5Pw = md.digest(password.getBytes(StandardCharsets.UTF_8));
            this.md5password = new String(Hex.encodeHex(bytesOfMd5Pw));

        } catch (NoSuchAlgorithmException e){

        }
        loginTry = 0;
    }

    /*
     * validate incoming login data from user
     * */
    public void validate(String uname, String pword)
    {
        if( uname.contentEquals(username) && pword.contentEquals(md5password))
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
