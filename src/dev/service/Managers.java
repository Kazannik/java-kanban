package dev.service;

/* ТЗ №4: У Managers будет единственные метод getDefault().
 При этом вызывающему неизвестен конкретный класс, только то,
 что объект, который возвращает getDefault(), реализует интерфейс TaskManager. */
public class Managers {
    static TaskManager taskManager;
    static HistoryManager historyManager;

    public static TaskManager getDefault() {
        if (taskManager == null) {
            taskManager = new InMemoryTaskManager();
        }
        return taskManager;
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
