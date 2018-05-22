package net.shtyftu.ubiquode.controller;

import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author shtyftu
 */
public abstract class AController {

    public static final String QUEST_CONTROLLER_PATH = "/quest";
    public static final String PACK_CONTROLLER_PATH = "/pack";
    public static final String STATS_CONTROLLER_PATH = "/stats";

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
        if (principal instanceof String) {
            return (String) principal;
        }
        if (principal instanceof UserDetails) {
            final UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername();
        }
        return null;
    }

    //TODO попробовать переписать на @ModelAttribute и контекст
    protected abstract Map<String, Object> getDefaultViewModel();

    protected ModelAndView getMessageView(List<Object> messages) {
        return getDefaultPathView(ImmutableMap.of("messages", messages));
    }

    protected ModelAndView getErrorView(List<Object> errors) {
        return getDefaultPathView(getErrorsViewMap(errors));
    }

    protected Map<String, Object> getErrorsViewMap(List<Object> errors1) {
        return ImmutableMap.of("errors", errors1);
    }

    protected ModelAndView getDefaultPathView(Map<String, Object> viewModel) {
        final Map<String, Object> fullViewModel = new HashMap<>();
        fullViewModel.putAll(getDefaultViewModel());
        fullViewModel.putAll(viewModel);
        return new ModelAndView(getDefaultViewPath(), fullViewModel);
    }

    private String getDefaultViewPath() {
        return controllerPath + LIST_PATH;
    }

}
