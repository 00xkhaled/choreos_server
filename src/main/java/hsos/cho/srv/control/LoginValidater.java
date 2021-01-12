package hsos.cho.srv.control;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LoginValidater {
    //Constant login variables
    private final String username = "admin";
    private final String password = "password";

    //Status Validation
    private boolean validated = false;
    //Count of Tries
    private short loginTry;

    /*
     * default Constructor
     * */
    public LoginValidater ()
    {
        super();
        loginTry = 0;
    }

    /*
     * validate incoming login data from user
     * */
    public void validate(String uname, String pword)
    {

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
