package dev.domain;

/* ТЗ: У задачи есть следующие свойства:
1.	Название, кратко описывающее суть задачи (например, «Переезд»).
2.	Описание, в котором раскрываются детали.
3.	Уникальный идентификационный номер задачи, по которому её можно будет найти.
4.	Статус, отображающий её прогресс. */
public abstract class AbstractTask {
    protected String name;
    protected String description;
    protected int taskId;

    protected AbstractTask(int taskId, String name, String description) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
    }

    protected AbstractTask(String name, String description) {
        this(-1, name, description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public abstract TaskStatusEnum getStatus();

    @Override
    public String toString() {
        return "TaskBase{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId + '\'' +
                ", status=" + getStatus().getTitle() +
                '}';
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

}
