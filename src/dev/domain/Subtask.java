package dev.domain;

/* ТЗ №3: должны выполняться следующие условия:
• Для каждой подзадачи известно, в рамках какого эпика она выполняется.
• Каждый эпик знает, какие подзадачи в него входят.
• Завершение всех подзадач эпика считается завершением эпика. */
public class Subtask extends Task {
    private final Integer parentEpicId;

    public Subtask(Integer parentEpicId, int taskId, String name, String description, TaskStatusEnum status) {
        super(taskId, name, description, status);
        this.parentEpicId = parentEpicId;
    }

    public Subtask(Integer parentEpicId, int taskId, String name, String description) {
        super(taskId, name, description);
        this.parentEpicId = parentEpicId;
    }

    public Subtask(Integer parentEpicId, int taskId, String name) {
        super(taskId, name);
        this.parentEpicId = parentEpicId;
    }

    // ТЗ №3: Для каждой подзадачи известно, в рамках какого эпика она выполняется.
    public Integer getEpicId() {
        return parentEpicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", taskId=" + this.getTaskId() + '\'' +
                ", status=" + status.title + '\'' +
                ", epicTaskId=" + parentEpicId +
                '}';
    }

    @Override
    public Object clone() {
        super.clone();
        return new Subtask(this.parentEpicId, this.getTaskId(), this.getName(), this.getDescription(), this.status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;

        Subtask subtask = (Subtask) o;

        if (getTaskId() != subtask.getTaskId()) return false;
        if (!getName().equals(subtask.getName())) return false;
        if (!getDescription().equals(subtask.getDescription())) return false;
        if (!status.equals(subtask.status)) return false;
        return parentEpicId.equals(subtask.parentEpicId);
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getTaskId();
        result = 31 * result + status.hashCode();
        result = 31 * result + parentEpicId;
        return result;
    }
}
