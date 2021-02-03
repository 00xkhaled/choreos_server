package hsos.cho.srv.settings.entity;

import io.quarkus.arc.config.ConfigProperties;;

/**
 * @author Lukas Grewe
 * This class reads the scene name and scene description from application.properties
 */
@ConfigProperties(prefix = "scene")
public class SceneConfiguration {

    /**
     * Inject Scene Names and Description from application.properties with prefix "scene."
     * sa = scene0
     * sb = scene1
     * sc = scene2
     * sd = scene3
     * se = scene4
     * sf = scene5
     * sg = scene6
     * sh = scene7
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
