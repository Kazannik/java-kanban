package dev.service;

import dev.domain.*;
import dev.utils.CollectionUtils;
import dev.utils.ReportUtils;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTasksManager {

    private static final String PARAM_SEPARATOR = "|";
    private static final String COLUMN_HEADER =
            "id" + PARAM_SEPARATOR +
                    "type" + PARAM_SEPARATOR +
                    "name" + PARAM_SEPARATOR +
                    "status" + PARAM_SEPARATOR +
                    "description" + PARAM_SEPARATOR +
                    "epic";
    private final File file;

    private FileBackedTasksManager(File file) {
        super();
        this.file = file;
    }

    /* ТЗ №6: Для этого создайте метод static void main(String[] args)
    в классе FileBackedTasksManager и реализуйте небольшой сценарий:
   1.	Заведите несколько разных задач, эпиков и подзадач.
   2.	Запросите некоторые из них, чтобы заполнилась история просмотра.
   3.	Создайте новый FileBackedTasksManager менеджер из этого же файла.
   4.	Проверьте, что история просмотра восстановилась верно и все задачи,
    эпики, подзадачи, которые были в старом, есть в новом менеджере. */
    public static void main(String[] args) {
        System.out.println("Тестирование приложения по условиям, заданным в техническом задании Спринта №6:");

        Path path = FileSystems.getDefault().getPath("java-kanban.csv");
        Managers.SetFileTasksManager(path.toFile());

        TasksManager manager = Managers.getDefault();
        manager.removeAllTasks();

        System.out.println("\n1.\tЗаведите несколько разных задач, эпиков и подзадач;");
        int nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Task task = new Task(nextTaskId, "Задача 1", "Создаю обычную задачу с индексом 0.");
        manager.create(task);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        task = new Task(nextTaskId, "Задача 2", "Создаю обычную задачу с индексом 1.");
        manager.create(task);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Epic epic = new Epic(nextTaskId,
                "Эпик-задача 1", "Создаю эпик-задачу с индексом 2, в которой будет создано три подзадачи.");
        manager.create(epic);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Subtask subtask = new Subtask(epic.getTaskId(), nextTaskId,
                "Подзадача 1", "Создаю подзадачу с индексом 3.");
        epic.create(subtask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        subtask = new Subtask(epic.getTaskId(), nextTaskId,
                "Подзадача 2", "Создаю подзадачу с индексом 4.");
        epic.create(subtask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        subtask = new Subtask(epic.getTaskId(), nextTaskId,
                "Подзадача 3", "Создаю подзадачу с индексом 5.");
        epic.create(subtask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        epic = new Epic(nextTaskId,
                "Эпик-задача 2", "Создаю эпик-задачу с индексом 6 без подзадач.");
        manager.create(epic);

        System.out.println("Результат:");
        ReportUtils.printTasksCollection(manager.getHighLevelTasks(), false);

        System.out.println("\n2.\tЗапросите некоторые из них, чтобы заполнилась история просмотра;");
        System.out.println("\nВызываю задачи 20 раз в случайном порядке.");
        for (int i = 0; i < 20; i++) {
            int randomId = (int) (Math.random() * 7);
            TaskBase randomTask = manager.getTaskBase(randomId);
            System.out.print((i + 1) + ") ");
            ReportUtils.printTask(randomTask, false);
        }
        System.out.println("\nПечатаем историю просмотра.");
        ReportUtils.printTasksCollection(Managers.getDefaultHistory().getHistory(), false);

        System.out.println("\n3.\tСоздаем новый FileBackedTasksManager менеджер из этого же файла.;");
        manager = FileBackedTasksManager.loadFromFile(path.toFile());

        System.out.println("\nПечатаем историю просмотра.");
        ReportUtils.printTasksCollection(Managers.getDefaultHistory().getHistory(), false);
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        Managers.getDefaultHistory().clear();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.readLine();
                while (reader.ready()) {
                    String line = reader.readLine();
                    if (!line.isBlank()) {
                        manager.fromString(line);
                    } else {
                        break;
                    }
                }
                String history = reader.readLine();
                manager.createHistory(history);
            } catch (IOException e) {
                System.out.println("Произошла ошибка во время чтения файла.");
            }
        }
        return manager;
    }

    private static String toString(HistoryManager manager) {
        return manager.getHistoryId().stream().map(String::valueOf)
                .collect(Collectors.joining(PARAM_SEPARATOR));
    }

    // ТЗ №6: Напишите метод создания задачи из строки Task fromString(String value).
    private TaskBase fromString(String value) {
        String[] param = value.split("[" + PARAM_SEPARATOR + "]");
        int taskId = Integer.parseInt(param[0]);
        TaskTypeEnum type = TaskTypeEnum.fromKey(param[1]);
        TaskStatusEnum status = TaskStatusEnum.fromKey(param[3]);
        int epicId = Integer.parseInt(param[5]);
        switch (type) {
            case EPIC: {
                Epic epic = new Epic(taskId, param[2], param[4]);
                epics.put(taskId, epic);
                return epic;
            }
            case SUBTASK: {
                Subtask subtask = new Subtask(epicId, taskId, param[2], param[4], status);
                subtasks.put(taskId, subtask);
                Epic epic = getEpic(epicId);
                epic.updateStatus();
                return subtask;
            }
            default: {
                Task task = new Task(taskId, param[2], param[4], status);
                tasks.put(taskId, task);
                return task;
            }
        }
    }

    private void createHistory(String history) {
        if (history != null) {
            String[] tasks = history.split("[" + PARAM_SEPARATOR + "]");
            for (String id : tasks) {
                int taskId = Integer.parseInt(id);
                Managers.getDefaultHistory().add(getTaskBase(taskId));
            }
        }
    }

    private void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(COLUMN_HEADER + "\n");
            List<TaskBase> tasks = getAllTasks();
            for (TaskBase task : tasks) {
                try {
                    writer.write(task.toString(PARAM_SEPARATOR));
                } catch (ManagerSaveException e) {
                    throw new ManagerSaveException("Ошибка записи задания в файл.", task);
                }
            }
            try {
                writer.write("\n");
                String history = toString(Managers.getDefaultHistory());
                writer.write(history);
            } catch (ManagerSaveException e) {
                throw new ManagerSaveException("Ошибка записи истории просмотров в файл.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Task createTask(String name) {
        Task task = super.createTask(name);
        save();
        return task;
    }

    @Override
    public Epic createEpic(String name) {
        Epic epic = super.createEpic(name);
        save();
        return epic;
    }

    @Override
    public Subtask createSubtask(int epicId, String name) {
        Subtask subtask = super.createSubtask(epicId, name);
        save();
        return subtask;
    }

    @Override
    public int create(Task task) {
        int id = super.create(task);
        save();
        return id;
    }

    @Override
    public int create(Epic epic) {
        int id = super.create(epic);
        save();
        return id;
    }

    @Override
    public int create(Subtask subtask) {
        int id = super.create(subtask);
        save();
        return id;
    }

    @Override
    public int create(TaskBase task) {
        if (task instanceof Epic) {
            return create((Epic) task);
        } else if (task instanceof Subtask) {
            return create((Subtask) task);
        } else {
            return create((Task) task);
        }
    }

    @Override
    public void update(Task task) {
        super.update(task);
        save();
    }

    @Override
    public void update(Epic epic) {
        super.update(epic);
        save();
    }

    @Override
    public void update(Subtask subtask) {
        super.update(subtask);
        save();
    }

    @Override
    public void update(TaskBase task) {
        if (task instanceof Task) {
            update((Task) task);
        } else if (task instanceof Epic) {
            update((Epic) task);
        } else {
            update((Subtask) task);
        }
    }

    @Override
    public Task getTask(int taskId) {
        Task task = super.getTask(taskId);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int taskId) {
        Epic epic = super.getEpic(taskId);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int taskId) {
        Subtask subtask = super.getSubtask(taskId);
        save();
        return subtask;
    }

    @Override
    public TaskBase getTaskBase(int taskId) {
        TaskBase task = super.getTaskBase(taskId);
        save();
        return task;
    }

    @Override
    public void removeTask(int taskId) {
        super.removeTask(taskId);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }
}