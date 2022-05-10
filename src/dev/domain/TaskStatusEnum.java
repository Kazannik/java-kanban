package dev.domain;

public enum TaskStatusEnum {
    NEW("Задача только создана", "1"),
    IN_PROGRESS("Над задачей ведётся работа", "2"),
    DONE("Задача выполнена","3");

    public final String title;
    private final String key;

    TaskStatusEnum(String title, String key) {
        this.title = title;
        this.key = key;
    }

    private String toCommandItem() {
        return key + "\t" + title + ";";
    }

    public static void printCommands() {
        for (int i = 0; i < TaskStatusEnum.values().length; i++) {
            System.out.println(TaskStatusEnum.values()[i].toCommandItem());
        }
    }

   /* ТЗ №3: - если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
             - если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
             - во всех остальных случаях статус должен быть IN_PROGRESS. */
    public static TaskStatusEnum compareEnum(TaskStatusEnum first, TaskStatusEnum second) {
        if (first == null && second == null) {
            return null;
        } else if (first != null && second == null) {
            return first;
        } else if (first == null) {
            return second;
        } else if (first == TaskStatusEnum.NEW && second == TaskStatusEnum.NEW) {
            return TaskStatusEnum.NEW;
        } else if (first == TaskStatusEnum.DONE && second == TaskStatusEnum.DONE) {
            return TaskStatusEnum.DONE;
        } else {
            return TaskStatusEnum.IN_PROGRESS;
        }
    }
}
