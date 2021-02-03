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

/**
 * @author Lukas Grewe
 * ScenePublisher is used to hold the Connections from the Choreos AR-Sessions
 * It publish new Scene States with the Methods publishScene()/publishStop()
 * The other methods are default implementations from the Library
 */
@ApplicationScoped
@ServerEndpoint("/subscribe/{userId}")
public class ScenePublisher {
    //used to get the new scenestate
    @Inject private SceneManager sceneManager;
    //store all active AR-Sessions
    private Map<String, Session> sessions = new ConcurrentHashMap<>();
    //used to calculate the delta time from starting a scene to open a session (used in @onOpen onOpen()-method)
    private long sceneStartedAt = 0;
    //Logger for this class
    private static final Logger log = Logger.getLogger(ScenePublisher.class.getSimpleName());

    /**
     * @author Lukas Grewe
     * initialized a session and open a Websocket
     * write the current State to the new Session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String id) {
        //save Session in Map
        sessions.put(id, session);
        log.debug("USER: " + id + " SUBSCRIBED TO SOCKET");
        //Get the current SceneId to publish the current state
        int sceneId = sceneManager.getCurrentSceneId();
        //calculate the deltatime (= time.now - time.scenestarted)
        long millisSceneRunning = System.currentTimeMillis() - this.sceneStartedAt;
        //build the message
        String message = "start" + " " + sceneId + " " + millisSceneRunning;
        //inform all active sessions
        session.getAsyncRemote().sendObject(message, result ->  {
            System.out.println("sending Message " + message);
            if (result.getException() != null) {
                System.out.println("Unable to send new State: " + result.getException());
            }
        });
    }

    /**
     * @author Lukas Grewe
     * Close an active Session
     */
    @OnClose
    public void onClose(Session session, @PathParam("userId") String id){
        sessions.remove(id);
        log.debug("USER: " + id + " UNSUBSCRIBED FROM SOCKET");
    }

    /**
     * @author Lukas Grewe
     * ErrorAbort on an active Session
     */
    @OnError
    public void onError(Session session, @PathParam("userId") String id, Throwable throwable) {
        sessions.remove(id);
        log.debug("USER: " + id + " UNSUBSCRIBED FROM SOCKET ON ERROR");
    }

    /**
     * @author Lukas Grewe
     * newScene arrived -> inform all Session that a new Scene is active
     */
    public void publishScene(Scene currentScene) {
        log.info("PUBLISH SCENE " + currentScene.getName() + " WITH ID " + currentScene.getId() + " TO " + sessions.size() + " SESSIONS");
        //new set to calculate the delta time
        this.sceneStartedAt = System.currentTimeMillis();
        //building message
        String message = "start" + " " + sceneManager.getCurrentSceneId() + " " + 0;
        //inform all sessions that a new scene is active
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send new State: " + result.getException());
                }
            });
        });
    }

    /**
     * @author Lukas Grewe
     * stopScene arrived -> inform all Session that a the stopScene arrived
     */
    public void publishStop() {
        log.info("PUBLISH SCENE \"stop\" WITH ID -1 TO" + sessions.size() + " SESSIONS!");
        //new set to calculate the delta time
        this.sceneStartedAt = System.currentTimeMillis();
        //build message for the sessions
        String message = "stop";
        //inform all sessions that a new scene is active
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send new State: " + result.getException());
                }
            });
        });
    }
}
