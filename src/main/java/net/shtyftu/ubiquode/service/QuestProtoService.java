package net.shtyftu.ubiquode.service;

import net.shtyftu.ubiquode.model.persist.simple.QuestProto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class QuestProtoService {

    public void validate(QuestProto proto) {
        if (StringUtils.isBlank(proto.getId())) {
            throw new IllegalArgumentException("id (required field) is empty");
        }
        final Long cooldown = proto.getCooldown();
        if (cooldown == null || cooldown <= 0) {
            throw new IllegalArgumentException("cooldown must be positive: [" + cooldown + "]");
        }
        final Long deadline = proto.getDeadline();
        if (deadline == null || deadline <= 0) {
            throw new IllegalArgumentException("deadline must be positive: [" + deadline + "]");
        }
        if (StringUtils.isBlank(proto.getName())) {
            throw new IllegalArgumentException("name must be not blank");
        }
    }
}
