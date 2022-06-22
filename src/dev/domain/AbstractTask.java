package dev.domain;

/* ТЗ №3: У задачи есть следующие свойства:
1.	Название, кратко описывающее суть задачи (например, «Переезд»).
2.	Описание, в котором раскрываются детали.
3.	Уникальный идентификационный номер задачи, по которому её можно будет найти.
4.	Статус, отображающий её прогресс. */
abstract class AbstractTask implements TaskBase {
    private final int taskId;
    protected TaskStatusEnum status;
    private String name;
    private String description;

    protected AbstractTask(int taskId, String name, String description) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.status = TaskStatusEnum.NEW;
    }

    @Override
    public int getTaskId() {
        return taskId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public TaskStatusEnum getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AbstractTask {" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId + '\'' +
                ", status=" + getStatus().title +
                '}';
    }

    @Override
    public abstract String toString(String separator) ;

    @Override
    public int compareTo(TaskBase o) {
        return Integer.compare(taskId, o.getTaskId());
    }

    @Override
    public abstract Object clone();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();
}
