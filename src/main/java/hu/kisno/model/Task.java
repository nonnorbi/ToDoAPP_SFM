package hu.kisno.model;

public class Task {
    private long dtaecreated;
    private String description;
    private String task;

    public Task() {
    }

    public Task(long dtaecreated, String description, String task) {
        this.dtaecreated = dtaecreated;
        this.description = description;
        this.task = task;
    }

    public long getDtaecreated() {
        return dtaecreated;
    }

    public void setDtaecreated(long dtaecreated) {
        this.dtaecreated = dtaecreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
