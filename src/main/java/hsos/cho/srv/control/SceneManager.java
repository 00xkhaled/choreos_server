package hsos.cho.srv.control;

import hsos.cho.srv.boundary.sockets.ScenePublisher;
import hsos.cho.srv.entity.Scene;
import hsos.cho.srv.properties.SceneConfiguration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SceneManager {

    @Inject
    private ScenePublisher publisher;

    @Inject
    SceneConfiguration initiator;

    private Map<Integer, Scene> scenes;

    private Scene stop;

    private int currentSceneId;

    private SceneManager() {
        scenes = new HashMap<>();
        currentSceneId = -1;
    }

    public void changeState(int id) {
        if(currentSceneId != -1)
            scenes.get(currentSceneId).switchIsActiveTo(false);

        currentSceneId = id;
        scenes.get(currentSceneId).switchIsActiveTo(true);

        stop.switchIsActiveTo(true);

        publishState();
    }

    private void publishState() {
        if(currentSceneId != -1)
            publisher.publishScene(scenes.get(currentSceneId));
    }

    public void stopAllScenes(){
        scenes.forEach((id, scene)-> {
            scene.switchIsActiveTo(false);
        });
        currentSceneId = -1;

        stop.switchIsActiveTo(false);

        publisher.publishStop();
    }

    public List<Scene> getScenesAsList(){
        return new ArrayList<Scene>(scenes.values());
    }

    public Scene getCurrentScene() {
            return scenes.get(currentSceneId);
    }

    public void setcurrentSceneId(int id){
        this.currentSceneId = id;
    }

    public int getCurrentSceneId() {
        return currentSceneId;
    }

    public Scene getStop(){
        return this.stop;
    }

    public ScenePublisher getPublisher(){
        return publisher;
    }

    @PostConstruct
    public void initSceneManagerScenes() {

        stop = new Scene(-1, "STOP", "Stoppt die derzeit laufende Szene!");

        Scene a = new Scene(0, initiator.saname, initiator.sadescription);
        scenes.put(a.getId(), a);

        Scene b = new Scene(1, initiator.sbname, initiator.sbdescription);
        scenes.put(b.getId(), b);

        Scene c = new Scene(2, initiator.scname, initiator.scdescription);
        scenes.put(c.getId(), c);

        Scene d = new Scene(3, initiator.sdname, initiator.sddescription);
        scenes.put(d.getId(), d);

        Scene e = new Scene(4, initiator.sename, initiator.sedescription);
        scenes.put(e.getId(), e);

        Scene f = new Scene(5, initiator.sfname, initiator.sfdescription);
        scenes.put(f.getId(), f);

        Scene g = new Scene(6, initiator.sgname, initiator.sgdescription);
        scenes.put(g.getId(), g);

        Scene h = new Scene(7, initiator.shname, initiator.shdescription);
        scenes.put(h.getId(), h);
    }
}

