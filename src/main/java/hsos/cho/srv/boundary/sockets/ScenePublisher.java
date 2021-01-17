package hsos.cho.srv.boundary.sockets;

import hsos.cho.srv.control.SceneManager;
import hsos.cho.srv.entity.Scene;
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

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    long sceneStartedAt = 0;

    @Inject
    SceneManager sceneManager;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String id) {
        sessions.put(id, session);
        System.out.println(id  + " subscribed to ChoreosServer");

        Scene scene = sceneManager.getCurrentScene();

        long millisSceneRunning = System.currentTimeMillis() - this.sceneStartedAt;
        String message = scene.getName() + " " + millisSceneRunning;

        session.getAsyncRemote().sendObject(message, result ->  {
            System.out.println("sending Message " + message);
            if (result.getException() != null) {
                System.out.println("Unable to send new State: " + result.getException());
            }
        });
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String id) {
        sessions.remove(id);
        System.out.println(id + " unsubscribed from ChoreosServer");
    }

    @OnError
    public void onError(Session session, @PathParam("userId") String id, Throwable throwable) {
        sessions.remove(id);
        System.out.println(id + " unsubscribed from ChoreosServer onError");
    }

    public void publish(Scene scene) {
        System.out.println("-----\nWebSocket - publish new scene:" + scene.getName());
        System.out.println("Anzahl Sessions " + sessions.size());

        this.sceneStartedAt = System.currentTimeMillis();

        String message = scene.getName() + " " + 0;

        sessions.values().forEach(s -> {
            System.out.println("Session MSG " +  message);

            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send new State: " + result.getException());
                }
            });
        });

        System.out.println("-----");
    }
}
