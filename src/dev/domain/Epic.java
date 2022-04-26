package dev.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/* ТЗ: должны выполняться следующие условия:
• Для каждой подзадачи известно, в рамках какого эпика она выполняется.
• Каждый эпик знает, какие подзадачи в него входят.
• Завершение всех подзадач эпика считается завершением эпика. */
public class Epic extends AbstractTask {
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public Epic(int taskId, String name, String description) {
        super(taskId, name, description);
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name) {
        this(name, "");
    }

    public Epic(int taskId, String name) {
        this(taskId, name, "");
    }

    public TaskStatusEnum getStatus() {
        if (subtasks.size() == 0) {
            return TaskStatusEnum.NEW;
        } else {
            TaskStatusEnum result = null;
            for (Subtask subtask : subtasks.values()) {
                result = TaskStatusEnum.compareEnum(result, subtask.getStatus());
            }
            return result;
        }
    }

    // ТЗ: Создание. Сам объект должен передаваться в качестве параметра.
    public void create(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getTaskId())) {
            subtasks.put(subtask.getTaskId(), subtask);
        } else {
            throw new IndexOutOfBoundsException("Подзадача с таким идентификационным номером уже присутствует" +
                    " в коллекции.");
        }
    }

    // ТЗ: Обновление. Новая версия объекта с верным идентификатором передаются в виде параметра.
    public void update(int taskId, Subtask subtask) {
        if (subtasks.containsKey(taskId)) {
            subtask.setTaskId(taskId);
            subtasks.put(taskId, subtask);
        } else {
            throw new IndexOutOfBoundsException("Подзадача с заданным идентификационным номером отсутствует" +
                    " в коллекции.");
        }
    }

    public Subtask create(int taskId, String name, String description) {
        Subtask addingSubtask = new Subtask(this, taskId, name, description);
        subtasks.put(taskId, addingSubtask);
        return addingSubtask;
    }

    public Subtask create(int taskId, String name) {
        return create(taskId, name, "");
    }

    public Subtask getTask(int taskId) {
        if (subtasks.containsKey(taskId)) {
            return subtasks.get(taskId);
        } else {
            throw new IndexOutOfBoundsException("Идентификационный номер задачи отсутствует в коллекции.");
        }
    }

    public int size() {
        return subtasks.size();
    }

    public boolean containsTaskId(int taskId) {
        return subtasks.containsKey(taskId);
    }

    public Set<Integer> keySet(){
        return subtasks.keySet();
    }

    // ТЗ: Получение списка всех подзадач определённого эпика.
    public Collection<Subtask> getAllTasks() {
        return subtasks.values();
    }

    public void removeTask(int taskId) {
        if (subtasks.containsKey(taskId)) {
            subtasks.remove(taskId);
        } else {
            throw new IndexOutOfBoundsException("Идентификационный номер задачи отсутствует в коллекции.");
        }
    }

    public void removeAllTasks() {
        subtasks.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId + '\'' +
                ", status=" + getStatus().getTitle()  + '\'' +
                ", size=" + size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;

        Epic epic = (Epic) o;

        if (getTaskId() != epic.getTaskId()) return false;
        if (!getName().equals(epic.getName())) return false;
        if (!getDescription().equals(epic.getDescription())) return false;
        if (!getStatus().equals(epic.getStatus())) return false;
        return getAllTasks().equals(epic.getAllTasks());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getTaskId();
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + getAllTasks().hashCode();
        return result;
    }
}
