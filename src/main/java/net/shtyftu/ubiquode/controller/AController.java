package net.shtyftu.ubiquode.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author shtyftu
 */
public abstract class AController {

    public static final String QUEST_CONTROLLER_PATH = "/quest";
    public static final String PROTO_CONTROLLER_PATH = "/proto";
    public static final String PACK_CONTROLLER_PATH = "/pack";

    public static final String LIST_PATH = "/list";
    public static final String EDIT_PATH = "/edit";

    private final String controllerPath;

    public AController() {
        controllerPath = Arrays.stream(getClass().getDeclaredAnnotationsByType(RequestMapping.class))
                .map(RequestMapping::value)
                .filter(Objects::nonNull)
                .flatMap(Arrays::stream)
                .filter(StringUtils::isNotBlank)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("RequestMapping controller annotation is not found"));
    }

    protected String getUserId() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof String ? (String) principal : null;
    }

    protected abstract Map<String, Object> getDefaultViewModel();

    protected ModelAndView getDefaulView(Map<String, Object> viewModel) {
        final Map<String, Object> fullViewModel = new HashMap<>();
        fullViewModel.putAll(getDefaultViewModel());
        fullViewModel.putAll(viewModel);
        return new ModelAndView(getDefaultViewPath(), fullViewModel);
    }

    private String getDefaultViewPath() {
        return controllerPath + LIST_PATH;
    }

}
