package hsos.cho.srv.control;

import hsos.cho.srv.boundary.sockets.ScenePublisher;
import hsos.cho.srv.entity.Scene;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class SceneManager {

    @Inject
    ScenePublisher publisher;

    private Map<Integer, Scene> scenes;
    private int actSceneId;

    private SceneManager() {
        scenes = new HashMap<>();
        actSceneId = 0;
    }

    public void changeState(int id) {
        actSceneId = id;
        publishState();
    }

    public Scene getCurrentScene() {
        return scenes.get(actSceneId);
    }

    private void publishState() {
        publisher.publish(scenes.get(actSceneId));
    }

    @PostConstruct
    public void initSceneManagerScenes() {
       /*
        try {
            FileWriter writer = new FileWriter("\\resources\\META-INF\\resources\\scenes\\scenes.json");
            System.out.println("File geoeffnet!");
        } catch (IOException e){
            e.printStackTrace();
        }
        */

        for (int i = 0; i <= 5; ++i) {
            Scene s = new Scene();
            s.setId(i);
            s.setName("Scene" + i);
            s.setDescription("Description Scene" + i);
            scenes.put(s.getId(), s);
        }
    }
}

