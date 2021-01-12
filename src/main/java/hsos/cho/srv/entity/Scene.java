package hsos.cho.srv.entity;

public class Scene {

    private int id;
    private String name;
    private String description;
    private boolean isActive;

    public Scene(){
        this.isActive = false;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) { this.id = id; }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public boolean isActiv(){ return isActive; };

    public void setDescription(String d){
        this.description = d;
    }

    public String getDescription(){
        return this.description;
    }

}
