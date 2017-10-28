package net.shtyftu.ubiquode.controller;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author shtyftu
 */
public class AController {

    public static final String QUEST_CONTROLLER_PATH = "/quest";
    public static final String PROTO_CONTROLLER_PATH = "/proto";
    public static final String PACK_CONTROLLER_PATH = "/pack";

    public static final String LIST_PATH = "/list";
    public static final String EDIT_PATH = "/edit";

    protected String getUserId() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof String ? (String) principal : null;
    }




}
