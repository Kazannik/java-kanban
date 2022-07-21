package dev.service;

import dev.utils.KVServer;

import java.io.File;
import java.io.IOException;

/* ТЗ №4: У Managers будет единственные метод getDefault().
 При этом вызывающему неизвестен конкретный класс, только то,
 что объект, который возвращает getDefault(), реализует интерфейс TaskManager. */
public class Managers {
    static TasksManager tasksManager;
    public static KVServer kvServer;

    private Managers(){}

    public static TasksManager getDefault() throws IOException {
        if (tasksManager == null) {
            tasksManager = setHttpTaskManager("http://localhost", KVServer.PORT);
        }
        return tasksManager;
    }

    public static FileBackedTasksManager setFileTasksManager(File file) {
        tasksManager = FileBackedTasksManager.loadFromFile(file);
        return (FileBackedTasksManager) tasksManager;
    }

    public static InMemoryTasksManager setMemoryTasksManager() {
        tasksManager = new InMemoryTasksManager();
        return (InMemoryTasksManager) tasksManager;
    }

    public static HttpTaskManager setHttpTaskManager(String host, int kvPort) throws IOException {
        if (kvServer == null) {
            kvServer = new KVServer();
            kvServer.start();
        }

        tasksManager = new HttpTaskManager(host, kvPort);
        return (HttpTaskManager) tasksManager;
    }

    /*ТЗ №4: Добавьте в служебный класс Managers статический метод HistoryManager getDefaultHistory().
    Он должен возвращать объект InMemoryHistoryManager — историю просмотров. */
    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
