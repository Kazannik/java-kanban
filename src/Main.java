import domain.TaskBase;
import domain.TaskManager;
import service.ReportTasks;
import utils.Tests;

import java.util.Collection;
import java.util.Scanner;

import static service.ConsoleSubmenu.*;
import static service.ReportTasks.LINE_LENGTH;
import static service.ReportTasks.printTasksCollection;

public class Main {
    private static final String EXIT_KEYS = "e";
    private static final String WARNING_MESSAGE = "Внимание! Команды задаются числовыми значениями от 1 до 7,\n"
            + "либо последовательностью символов \"" + EXIT_KEYS + "\". Попробуйте еще раз!";
    private static TaskManager manager;

    public static void main(String[] args) {
        manager = new TaskManager();
        Scanner scanner = getScanner();

        System.out.println("Трекер задач");
        while (true) {
            printMainMenu();
            String command = scanner.nextLine().trim();
            switch (command) {
                case "1":
                    getAllTasks();
                    break;
                case "2":
                    removeAllTasks();
                    break;
                case "3":
                    getTask();
                    break;
                case "4":
                    createTask();
                    break;
                case "5":
                    updateTask();
                    break;
                case "6":
                    removeTask();
                    break;
                case "7":
                    Tests.testSprint3(manager);
                    break;
                case EXIT_KEYS:
                    System.out.println("Завершение работы приложения");
                    scanner.close();
                    return;
                default:
                    System.out.println(WARNING_MESSAGE);
                    System.out.println();
                    break;
            }
        }
    }

    static void printMainMenu() {
        System.out.println();
        System.out.println("Основное меню:");
        System.out.println("1\tПолучение списка всех задач;");
        System.out.println("2\tУдаление всех задач;");
        System.out.println("3\tПолучение задачи по идентификатору;");
        System.out.println("4\tСоздание задачи;");
        System.out.println("5\tОбновление задачи по идентификатору;");
        System.out.println("6\tУдаление задачи по идентификатору;");
        System.out.println("7\tТестирование приложения по ТЗ;");
        System.out.println(EXIT_KEYS + "\tЗавершение работы приложения.");
    }

    static void getAllTasks() {
        if (isNotNullTasks()) {
            System.out.println("Список всех задач:");
            Collection<TaskBase> tasks = manager.getAllTasks();
            printTasksCollection(tasks);
        }
    }

    static void removeAllTasks() {
        if (isNotNullTasks()) {
            manager.removeAllTasks();
            System.out.println("Все задачи удалены!");
        }
    }

    static void getTask() {
        if (isNotNullTasks()) {
            System.out.println("Получение задачи по ее идентификатору");
            TaskBase task = inputTask(manager);
            if (task != null) {
                ReportTasks.printTask(task);
                System.out.println("=".repeat(LINE_LENGTH));
            }
        }
    }

    static void createTask() {
        System.out.println("Создание задачи");
        inputCreateTask(manager);
    }

    static void updateTask() {
        if (isNotNullTasks()) {
            System.out.println("Обновление задачи");
            inputUpdateTask(manager);
        }
    }

    static void removeTask() {
        if (isNotNullTasks()) {
            System.out.println("Удаление задачи по идентификатору");
            inputRemoveTask(manager);
        }
    }

    static boolean isNotNullTasks() {
        if (manager.size() == 0) {
            System.out.println("Внимание! Задачи в трекере отсутствуют.");
            return false;
        } else {
            return true;
        }
    }
}
