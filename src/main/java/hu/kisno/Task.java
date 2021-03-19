package hu.kisno;

public class Task {

     private int taskid;
     private int userid;
     private String description;
     private String task;
     private String deadline;

    public Task(int taskid, int userid, String description, String task, String deadline) {
        this.taskid = taskid;
        this.userid = userid;
        this.description = description;
        this.task = task;
        this.deadline = deadline;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
