package hsos.cho.srv.scenes.boundary;

import hsos.cho.srv.scenes.control.SceneManager;
import hsos.cho.srv.scenes.entity.Scene;
import org.jboss.logging.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

@ApplicationScoped
@ServerEndpoint("/subscribe/{userId}")
public class ScenePublisher {

    @Inject
    SceneManager sceneManager;

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    long sceneStartedAt = 0;

    private static final Logger log = Logger.getLogger(ScenePublisher.class.getSimpleName());

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String id) {
        sessions.put(id, session);
        log.debug("USER: " + id + " SUBSCRIBED TO SOCKET");

        int sceneId = sceneManager.getCurrentSceneId();

        long millisSceneRunning = System.currentTimeMillis() - this.sceneStartedAt;

        String message = "start" + " " + sceneId + " " + millisSceneRunning;

        session.getAsyncRemote().sendObject(message, result ->  {
            System.out.println("sending Message " + message);
            if (result.getException() != null) {
                System.out.println("Unable to send new State: " + result.getException());
            }
        });
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String id){
        sessions.remove(id);
        log.debug("USER: " + id + " UNSUBSCRIBED FROM SOCKET");
    }

    @OnError
    public void onError(Session session, @PathParam("userId") String id, Throwable throwable) {
        sessions.remove(id);
        log.debug("USER: " + id + " UNSUBSCRIBED FROM SOCKET ON ERROR");
    }

    public void publishScene(Scene currentScene) {
        log.info("Publish Scene " + currentScene.getName() + " with ID " + currentScene.getId() + " to " + sessions.size() + " Sessions");

        this.sceneStartedAt = System.currentTimeMillis();

        String message = "start" + " " + sceneManager.getCurrentSceneId() + " " + 0;

        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send new State: " + result.getException());
                }
            });
        });
    }

    public void publishStop() {
        log.info("Publish Scene \"stop\" with ID -1 to " + sessions.size() + " Sessions!");

        this.sceneStartedAt = System.currentTimeMillis();

        String message = "stop";

        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send new State: " + result.getException());
                }
            });
        });
    }
}
