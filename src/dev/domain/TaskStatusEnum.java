package dev.domain;

public enum TaskStatusEnum {
    NEW("Задача только создана"),
    IN_PROGRESS("Над задачей ведётся работа"),
    DONE("Задача выполнена");

    private final String title;

    public String getTitle() {
        return title;
    }

    TaskStatusEnum(String title) {
        this.title = title;
    }

   /* ТЗ: - если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
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
