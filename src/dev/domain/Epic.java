package dev.domain;

import dev.service.TaskManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* ТЗ №3: должны выполняться следующие условия:
• Для каждой подзадачи известно, в рамках какого эпика она выполняется.
• Каждый эпик знает, какие подзадачи в него входят.
• Завершение всех подзадач эпика считается завершением эпика. */
public class Epic extends Task {
    private final TaskManager manager;
    private final List<Integer> subtasks;

    public Epic(TaskManager manager, int taskId, String name, String description) {
        super(taskId, name, description);
        this.manager = manager;
        this.subtasks = new ArrayList<>();
        this.status = TaskStatusEnum.NEW;
    }

    public Epic(TaskManager manager, int taskId, String name) {
        this(manager, taskId, name, "");
    }

    // ТЗ №3: Создание. Сам объект должен передаваться в качестве параметра.
    public void create(Subtask subtask) {
        if (!subtasks.contains(subtask.getTaskId())) {
            if (!manager.containsSubtaskId(subtask.getTaskId())) {
                manager.create(subtask);
            }
            subtasks.add(subtask.getTaskId());
            updateStatus();
        } else {
            throw new IndexOutOfBoundsException("Подзадача с идентификационным номером " +
                    subtask.getTaskId() + " уже присутствует в коллекции.");
        }
    }

    // ТЗ №3: Обновление. Новая версия объекта с верным идентификатором передаются в виде параметра.
    public void update(Subtask subtask) {
        if (subtasks.contains(subtask.getTaskId())) {
            manager.update(subtask);
            updateStatus();
        } else {
            throw new IndexOutOfBoundsException("Подзадача с идентификационным номером " +
                    subtask.getTaskId() + " отсутствует в коллекции.");
        }
    }

    public Subtask create(int taskId, String name, String description) {
        Subtask addingSubtask = new Subtask(this.getTaskId(), taskId, name, description);
        manager.create(addingSubtask);
        subtasks.add(taskId);
        updateStatus();
        return addingSubtask;
    }

    public Subtask create(int taskId, String name) {
        return create(taskId, name, "");
    }

    public Subtask getSubtask(Integer taskId) {
        if (subtasks.contains(taskId)) {
            return manager.getSubtask(taskId);
        } else {
            throw new IndexOutOfBoundsException("Идентификационный номер задачи " +
                    taskId + " отсутствует в коллекции.");
        }
    }

    private void updateStatus() {
        if (subtasks.size() == 0) {
            status = TaskStatusEnum.NEW;
        } else {
            status = manager.getSubtask(subtasks.get(0)).status;
            for (int i = 1; i < subtasks.size(); i++) {
                status = TaskStatusEnum.compareEnum(status, manager.getSubtask(subtasks.get(i)).status);
            }
        }
    }

    public int size() {
        return subtasks.size();
    }

    public boolean containsSubtaskId(Integer taskId) {
        return subtasks.contains(taskId);
    }

    public List<Integer> subtaskIdList() {
        return subtasks;
    }

    // ТЗ №3: Получение списка всех подзадач определённого эпика.
    public List<Subtask> getAllSubtasks() {
        return manager.getSubtasks().stream()
                .filter(subtask -> subtask.getEpicId().equals(this.getTaskId()))
                .collect(Collectors.toList());
    }

    public void removeSubtask(Integer taskId) {
        if (subtasks.contains(taskId)) {
            subtasks.remove(taskId);
            updateStatus();
            if (manager.containsSubtaskId(taskId)) {
                manager.removeTask(taskId);
            }
        } else {
            throw new IndexOutOfBoundsException("Идентификационный номер задачи " +
                    taskId + " отсутствует в коллекции.");
        }
    }

    public void removeAllTasks() {
        subtasks.clear();
        updateStatus();
    }

    @Override
    public Object clone() {
        super.clone();
        Epic cloneableEpic = new Epic(this.manager, this.getTaskId(), this.getName(), this.getDescription());
        cloneableEpic.subtasks.addAll(subtasks);
        return cloneableEpic;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", taskId=" + this.getTaskId() + '\'' +
                ", status=" + status.title + '\'' +
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
        if (!status.equals(epic.status)) return false;
        return subtasks.equals(epic.subtasks);
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getTaskId();
        result = 31 * result + status.hashCode();
        result = 31 * result + subtasks.hashCode();
        return result;
    }
}
