package hsos.cho.srv.control;

import hsos.cho.srv.boundary.sockets.ScenePublisher;
import hsos.cho.srv.entity.Scene;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class SceneManager {

    @Inject
    ScenePublisher publisher;

    private Map<Integer, Scene> scenes;
    private int previousSceneId;
    private int currentSceneId;

    private SceneManager() {
        scenes = new HashMap<>();
        previousSceneId = 0;
        currentSceneId = 0;
    }

    public void changeState(int id) {
        scenes.get(currentSceneId).switchIsActive(false);
        previousSceneId = currentSceneId;

        currentSceneId = id;
        scenes.get(currentSceneId).switchIsActive(true);

        publishState();
    }

    private void publishState() {
        publisher.publish(scenes.get(previousSceneId), scenes.get(currentSceneId));
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

    @PostConstruct
    public void initSceneManagerScenes() {
        for (int i = 0; i < 8; ++i) {

            Scene s = new Scene();
            s.setId(i);
            s.setName("Scene" + i);
            s.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque nisl eros");

            scenes.put(s.getId(), s);
        }

    }
}

