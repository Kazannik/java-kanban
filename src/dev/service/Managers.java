package dev.service;

import java.io.File;

/* ТЗ №4: У Managers будет единственные метод getDefault().
 При этом вызывающему неизвестен конкретный класс, только то,
 что объект, который возвращает getDefault(), реализует интерфейс TaskManager. */
public class Managers {
    static TasksManager tasksManager;
    static HistoryManager historyManager;

    public static TasksManager getDefault() {
        if (tasksManager == null) {
            tasksManager = new InMemoryTasksManager();
        }
        return tasksManager;
    }

    public static FileBackedTasksManager SetFileTasksManager(File file) {
        tasksManager = FileBackedTasksManager.loadFromFile(file);
        return (FileBackedTasksManager) tasksManager;
    }

    public static InMemoryTasksManager SetMemoryTasksManager() {
        tasksManager = new InMemoryTasksManager();
        return (InMemoryTasksManager) tasksManager;
    }

    /*ТЗ №4: Добавьте в служебный класс Managers статический метод HistoryManager getDefaultHistory().
    Он должен возвращать объект InMemoryHistoryManager — историю просмотров. */
    public static HistoryManager getDefaultHistory() {
        if (historyManager == null) {
            historyManager = new InMemoryHistoryManager();
        }
        return historyManager;
    }
}
