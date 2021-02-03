package hsos.cho.srv.scenes.adapter;

import hsos.cho.srv.scenes.control.SceneManager;
import hsos.cho.srv.settings.entity.Properties;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Lukas Grewe
 * Adapter is used to render the templates/control.html page
 */
@ApplicationScoped
public class ControlHtmlAdapter {
    //import template/controller.html file to render the page
    @ResourcePath("controller.html") Template controllerPage;
    //for using some properties
    @Inject Properties properties;
    //to get all the Scenes as a List
    @Inject SceneManager sceneManager;

    /**
     * @author Lukas Grewe
     * Method renders the control.html page
     */
    public String generateControlHTML() {
        return controllerPage.data("serverurl", properties.serverurl)
                .data("scenes", sceneManager.getScenesAsList())
                .data("stop", sceneManager.getStop())
                .render();
    }
}
