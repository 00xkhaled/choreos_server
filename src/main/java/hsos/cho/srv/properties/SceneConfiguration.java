package hsos.cho.srv.properties;

import io.quarkus.arc.config.ConfigProperties;;

@ConfigProperties(prefix = "scene")
public class SceneConfiguration {

    /*
    Inject Scene Names and Description from application.properties with prefix "scene."
    */

    public String saname;
    public String sadescription;

    public String sbname;
    public String sbdescription;

    public String scname;
    public String scdescription;

    public String sdname;
    public String sddescription;

    public String sename;
    public String sedescription;

    public String sfname;
    public String sfdescription;

    public String sgname;
    public String sgdescription;

    public String shname;
    public String shdescription;

    public SceneConfiguration(){}
}
