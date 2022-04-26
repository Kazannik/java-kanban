package dev.domain;

/* ТЗ: должны выполняться следующие условия:
• Для каждой подзадачи известно, в рамках какого эпика она выполняется.
• Каждый эпик знает, какие подзадачи в него входят.
• Завершение всех подзадач эпика считается завершением эпика. */
public class Subtask extends Task {
    private final Epic parentEpic;

    public Subtask(Epic parent, int taskId, String name, String description) {
        super(taskId, name, description);
        parentEpic = parent;
    }

    public Subtask(Epic parent, int taskId, String name) {
        this(parent, taskId, name, "");
    }

    // ТЗ: Для каждой подзадачи известно, в рамках какого эпика она выполняется.
    public Epic getEpic() {
        return parentEpic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId + '\'' +
                ", status=" + status.getTitle() + '\'' +
                ", epicTaskId=" + parentEpic.getTaskId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;

        Subtask subtask = (Subtask) o;

        if (getTaskId() != subtask.getTaskId()) return false;
        if (!getName().equals(subtask.getName())) return false;
        if (!getDescription().equals(subtask.getDescription())) return false;
        if (!getStatus().equals(subtask.getStatus())) return false;
        return parentEpic.taskId == subtask.parentEpic.taskId;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getTaskId();
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + parentEpic.taskId;
        return result;
    }
}
