package net.shtyftu.ubiquode.model.persist.simple;

/**
 * @author shtyftu
 */
public class QuestProto extends ModelWithMetaData {

    private String name;
    private Long cooldown;
    private Long deadline;
    private int scores;
    private boolean activatedByTrigger;

    @SuppressWarnings("unused")
    public QuestProto() {
    }

    public QuestProto(String id, String name, Long cooldown, Long deadline, int scores, boolean activatedByTrigger) {
        super(id);
        this.name = name;
        this.cooldown = cooldown;
        this.deadline = deadline;
        this.scores = scores;
        this.activatedByTrigger = activatedByTrigger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCooldown() {
        return cooldown;
    }

    public void setCooldown(Long cooldown) {
        this.cooldown = cooldown;
    }

    public Long getCooldownInHours() {
        return cooldown / 1000 / 60 / 60;
    }

    public void setCooldownInHours(long cooldownInHours) {
        cooldown = cooldownInHours * 1000 * 60 * 60;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public boolean isActivatedByTrigger() {
        return activatedByTrigger;
    }

    public void setActivatedByTrigger(boolean activatedByTrigger) {
        this.activatedByTrigger = activatedByTrigger;
    }
}
