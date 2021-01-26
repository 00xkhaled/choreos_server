package hsos.cho.srv.entity;

public class Scene {

    private int id;
    private String name;
    private String description;
    private boolean isActiv;

    public Scene(){
        this.isActiv = false;
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
