package dev.domain;


public class Task extends AbstractTask {
    protected TaskStatusEnum status;

    public Task(int taskId, String name, String description, TaskStatusEnum status) {
        super(taskId, name, description);
        this.status = status;
    }

    public Task(String name, String description, TaskStatusEnum status) {
        super(name, description);
        this.status = status;
    }

    public Task(int taskId, String name, String description) {
        this(taskId, name, description, TaskStatusEnum.NEW);
    }

    public Task(int taskId, String name) {
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

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId + '\'' +
                ", status=" + status.getTitle() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (getTaskId() != task.getTaskId()) return false;
        if (!getName().equals(task.getName())) return false;
        if (!getDescription().equals(task.getDescription())) return false;
        return getStatus().equals(task.getStatus());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getTaskId();
        result = 31 * result + getStatus().hashCode();
        return result;
    }
}