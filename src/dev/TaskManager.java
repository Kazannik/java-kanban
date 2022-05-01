package dev;

import dev.domain.AbstractTask;
import dev.domain.Epic;
import dev.domain.Subtask;
import dev.domain.Task;
import dev.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {
    private final HashMap<Integer, AbstractTask> tasks = new HashMap<>();

    public boolean containsTaskId(int taskId) {
        return tasks.containsKey(taskId);
    }

    public AbstractTask getTask(int taskId) {
        if (tasks.containsKey(taskId)) {
            return tasks.get(taskId);
        } else {
            throw new IndexOutOfBoundsException("Идентификационный номер задачи отсутствует в коллекции.");
        }
    }

    public int size() {
        return tasks.size();
    }

    public int epicsSize() {
        int count = 0;
        for (AbstractTask task : tasks.values()) {
            if (task instanceof Epic) {
                count++;
            }
        }
        return count;
    }

    // ТЗ: Создание. Сам объект должен передаваться в качестве параметра.
    public int create(Epic epic) {
        int newTaskId = CollectionUtils.getNextTaskId(tasks.keySet());
        epic.setTaskId(newTaskId);
        tasks.put(epic.getTaskId(), epic);
        return newTaskId;
    }

    // ТЗ: Создание. Сам объект должен передаваться в качестве параметра.
    public int create(Task task) {
        int newTaskId = CollectionUtils.getNextTaskId(tasks.keySet());
        task.setTaskId(newTaskId);
        tasks.put(task.getTaskId(), task);
        return newTaskId;
    }

    public int create(AbstractTask task) {
        if (task instanceof Epic) {
            return create((Epic) task);
        } else {
            return create((Task) task);
        }
    }

    // ТЗ: Обновление. Новая версия объекта с верным идентификатором передаются в виде параметра.
    public void update(int taskId, Epic epic) {
        if (tasks.containsKey(taskId)) {
            epic.setTaskId(taskId);
            tasks.put(taskId, epic);
        } else {
            throw new IndexOutOfBoundsException("Эпик-задача с заданным идентификационным номером отсутствует" +
                    " в коллекции.");
        }
    }

    // ТЗ: Обновление. Новая версия объекта с верным идентификатором передаются в виде параметра.
    public void update(int taskId, Task task) {
        if (tasks.containsKey(taskId)) {
            task.setTaskId(taskId);
            tasks.put(taskId, task);
        } else {
            throw new IndexOutOfBoundsException("Задача с заданным идентификационным номером отсутствует" +
                    " в коллекции.");
        }
    }

    public Task createTask(String name) {
        int newTaskId = CollectionUtils.getNextTaskId(tasks.keySet());
        Task addingTask = new Task(newTaskId, name);
        tasks.put(newTaskId, addingTask);
        return addingTask;
    }

    public Epic createEpic(String name) {
        int newTaskId = CollectionUtils.getNextTaskId(tasks.keySet());
        Epic addingEpic = new Epic(newTaskId, name);
        addingEpic.setName(name);
        tasks.put(newTaskId, addingEpic);
        return addingEpic;
    }

    public Subtask createSubtask(int epicId, String name) {
        if (tasks.containsKey(epicId)) {
            Epic epic = (Epic) tasks.get(epicId);
            int newTaskId = CollectionUtils.getNextTaskId(epic.keySet());
            return epic.create(newTaskId, name);
        } else {
            throw new IndexOutOfBoundsException("Идентификационный номер эпик-задачи отсутствует в коллекции.");
        }
    }

    public Collection<AbstractTask> getAllTasks() {
        return tasks.values();
    }

    public List<AbstractTask> getEpics() {
        return tasks.values().stream()
                .filter(Epic.class::isInstance)
                .collect(Collectors.toList());
    }

    public List<AbstractTask> getTasks() {
        return tasks.values().stream()
                .filter(Task.class::isInstance)
                .collect(Collectors.toList());
    }

    public List<AbstractTask> getSubtasks() {
        return tasks.values().stream().filter(Epic.class::isInstance)
                .map(e -> ((Epic) e).getAllTasks())
                .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeTask(int taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
        } else {
            throw new IndexOutOfBoundsException("Идентификационный номер задачи отсутствует в коллекции.");
        }
    }
}