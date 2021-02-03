package hsos.cho.srv.scenes.control;

import hsos.cho.srv.scenes.boundary.ScenePublisher;
import hsos.cho.srv.scenes.entity.Scene;
import hsos.cho.srv.settings.entity.SceneConfiguration;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @author Lukas Grewe
 * SceneManagar act sceneoperations and communicate with the publisherWebsocket
 */
@ApplicationScoped
public class SceneManager {
    //Websocket to publish a new scene if a scene is set as active
    @Inject private ScenePublisher publisher;
    //Used to initiate the Scenes
    @Inject private SceneConfiguration initiator;
    //Holding scenes
    private Map<Integer, Scene> scenes;
    //Stopscene: used to say stop to the Websocketsession, if no Scene is active
    private Scene stop;
    //Id of the current active scene
    private int currentSceneId;

    /**
     * @author Lukas Grewe
     * Default Constructor
     */
    private SceneManager() {
        //initiate a new HashMap for the scenes
        scenes = new ConcurrentHashMap<>();
        //set currentSceneId to default value
        currentSceneId = -1;
    }

    /**
     * @author Lukas Grewe
     * changing the scene state
     * switching activ/inactiv scenes
     * @param
     */
    public void changeState(int id) {

        if(currentSceneId != -1)
            //currentSceneId isn`t the default value (0-7)
            scenes.get(currentSceneId).switchIsActiveTo(false);
        //set new id
        currentSceneId = id;
        //switch isActive in SceneEntity
        scenes.get(currentSceneId).switchIsActiveTo(true);
        //stop button is now active - can be pushed to stop all scenes
        stop.switchIsActiveTo(true);
        //publish new State
        publishState();
    }

    /**
     * @author Lukas Grewe
     * Method is setting every scene to isActive=false to stop every Scene
     */
    public void stopAllScenes(){
        //switch every scene isActive:false
        scenes.forEach((id, scene)-> {
            scene.switchIsActiveTo(false);
        });
        //set default Scene id
        currentSceneId = -1;
        //set stop scene to false
        stop.switchIsActiveTo(false);
        //publish new State
        publisher.publishStop();
    }

    /**
     * @author Lukas Grewe
     * Method pushes new Message to Websocket
     */
    private void publishState() {
        if(currentSceneId != -1)
            publisher.publishScene(scenes.get(currentSceneId));
    }

    /**
     * @author Lukas Grewe
     * Method is used to initialise the Scenes by using settings/entity/SceneConfigurations values
     * to this.scenes HashMap
     */
    @PostConstruct
    public void initSceneManagerScenes() {
        //init Stop scene
        stop = new Scene(-1, "STOP", "Stoppt die derzeit laufende Szene!");
        //init Scene 0
        Scene a = new Scene(0, initiator.saname, initiator.sadescription);
        scenes.put(a.getId(), a);
        //init Scene 1
        Scene b = new Scene(1, initiator.sbname, initiator.sbdescription);
        scenes.put(b.getId(), b);
        //init Scene 2
        Scene c = new Scene(2, initiator.scname, initiator.scdescription);
        scenes.put(c.getId(), c);
        //init Scene 3
        Scene d = new Scene(3, initiator.sdname, initiator.sddescription);
        scenes.put(d.getId(), d);
        //init Scene 4
        Scene e = new Scene(4, initiator.sename, initiator.sedescription);
        scenes.put(e.getId(), e);
        //init Scene 5
        Scene f = new Scene(5, initiator.sfname, initiator.sfdescription);
        scenes.put(f.getId(), f);
        //init Scene 6
        Scene g = new Scene(6, initiator.sgname, initiator.sgdescription);
        scenes.put(g.getId(), g);
        //init Scene 7
        Scene h = new Scene(7, initiator.shname, initiator.shdescription);
        scenes.put(h.getId(), h);
    }

    /**
     * GETTERS AND SETTERS BELOW
     */
    public List<Scene> getScenesAsList(){ return new ArrayList<Scene>(scenes.values());}

    public Scene getCurrentScene() { return scenes.get(currentSceneId); }

    public int getCurrentSceneId() { return currentSceneId; }

    public Scene getStop(){ return this.stop; }

    public ScenePublisher getPublisher(){ return publisher; }
}

