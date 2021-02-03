package hsos.cho.srv.scenes.entity;

/**
 * @author Lukas Grewe
 * Scene Entity represents a Scene
 * implements Data that describe an Ar-Scene that is used in Choreos dream(e)scapes App
 */
public class Scene {
    //Scene ID
    private int id;
    //Name der Scene
    private String name;
    //Description of the scene
    private String description;
    //true: scene is active, false: scene is inactive
    private boolean isActiv;

    /**
     * Default Constructor
     */
    public Scene(){
        this.isActiv = false;
    }

    /**
     * @author Lukas Grewe
     * Normal used Constructor in class SceneManager
     * @param id set the SceneID
     * @param name sets the Scene Name
     * @param description sets the Scene description
     */
    public Scene(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActiv = false;
    }

    /**
     * GETTERS AND SETTER BELOW
     */
    public int getId(){ return id; }

    public String getName(){ return name; }

    public boolean isActiv(){ return isActiv; };

    public void switchIsActiveTo(boolean b){ this.isActiv = b; }

    public void setDescription(String d){ this.description = d; }

    public String getDescription(){ return this.description; }

}
