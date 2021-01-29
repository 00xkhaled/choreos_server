package hsos.cho.srv.scenes.adapter;

import hsos.cho.srv.scenes.control.SceneManager;
import hsos.cho.srv.settings.entity.Properties;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ControlHtmlAdapter {

    @ResourcePath("controller.html")
    Template controllerPage;

    @Inject
    Properties properties;

    @Inject
    SceneManager sceneManager;

    public String generateControlHTML() {
        return controllerPage.data("serverurl", properties.serverurl)
                .data("scenes", sceneManager.getScenesAsList())
                .data("stop", sceneManager.getStop())
                .render();
    }
}
