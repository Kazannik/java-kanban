package domain;


public class Task extends TaskBase {
    protected TaskStatusEnum status;

    protected Task(int taskId, String name, String description, TaskStatusEnum status) {
        super(taskId, name, description);
        this.status = status;
    }

    protected Task(String name, String description, TaskStatusEnum status) {
        super(name, description);
        this.status = status;
    }

    protected Task(int taskId, String name, String description) {
        this(taskId, name, description, TaskStatusEnum.NEW);
    }

    protected Task(int taskId, String name) {
        this(taskId, name, "", TaskStatusEnum.NEW);
    }

    public Task(String name, String description) {
        this(name, description, TaskStatusEnum.NEW);
    }

    public Task(String name) {
        this(name, "");
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }
}
