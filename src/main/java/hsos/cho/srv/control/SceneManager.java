package hsos.cho.srv.control;

import hsos.cho.srv.boundary.sockets.ScenePublisher;
import hsos.cho.srv.entity.Scene;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SceneManager {

    @Inject
    ScenePublisher publisher;

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

    public int getCurrentSceneId() {
        return currentSceneId;
    }

    public Scene getStop(){
        return this.stop;
    }

    @PostConstruct
    public void initSceneManagerScenes() {

        stop = new Scene();
        stop.setId(-1);
        stop.setName("STOP");
        stop.setDescription("Stoppt die derzeit laufende Szene!");

        for (int i = 0; i < 7; ++i) {

            Scene s = new Scene();
            s.setId(i);
            s.setName("Scene" + i);
            s.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque nisl eros");

            scenes.put(s.getId(), s);
        }
    }
}

