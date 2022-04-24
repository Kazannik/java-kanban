package service;

import domain.Epic;
import domain.Subtask;
import domain.TaskBase;

import java.util.Collection;

public class ReportTasks {
    public static final int LINE_LENGTH = 60;

    public static void printTask(TaskBase task) {
        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            System.out.println("=".repeat(LINE_LENGTH));
            printTask(epic, 0);
            Collection<Subtask> subtasks = epic.getAllTasks();
            if (subtasks.size() > 0) {
                System.out.println("-".repeat(LINE_LENGTH));
                System.out.println("Список подзадач:");
                for (TaskBase subtask : subtasks) {
                    System.out.println("-".repeat(LINE_LENGTH));
                    printTask(subtask, 4);
                }
            } else {
                System.out.println("Подзадачи отсутствуют.");
            }
        } else {
            System.out.println("=".repeat(LINE_LENGTH));
            printTask(task, 0);
        }
    }

    public static void printTask(TaskBase task, int margin) {
        System.out.println(" ".repeat(margin) + "Название: " + task.getName() + ";");
        System.out.println(" ".repeat(margin) + "Описание: " + task.getDescription() + ";");
        System.out.println(" ".repeat(margin) + "Идентификатор: " + task.getTaskId() + ";");
        System.out.println(" ".repeat(margin) + "Статус: " + task.getStatus().getTitle() + ".");
    }

    public static void printTasksCollection(Collection<TaskBase> tasks) {
        if (tasks.size() > 0) {
            for (TaskBase task : tasks) {
                printTask(task);
            }
            System.out.println("=".repeat(LINE_LENGTH));
        } else {
            System.out.println("Задачи в трекере отсутствуют.");
        }
    }
}
