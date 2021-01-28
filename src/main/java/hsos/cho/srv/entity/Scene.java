package hsos.cho.srv.entity;

public class Scene {

    private static int idCounter = -1;
    private int id;
    private String name;
    private String description;
    private boolean isActiv;

    public Scene(){
        this.isActiv = false;
    }

    public Scene(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActiv = false;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public boolean isActiv(){ return isActiv; };

    public void switchIsActiveTo(boolean b){
        this.isActiv = b;
    }

    public void setDescription(String d){
        this.description = d;
    }

    public String getDescription(){
        return this.description;
    }

}
