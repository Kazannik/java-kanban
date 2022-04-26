import dev.TaskManager;
import dev.domain.AbstractTask;
import dev.service.ConsoleSubmenu;
import dev.service.ReportTasks;
import dev.utils.Tests;

import java.util.Collection;
import java.util.Scanner;

public class Main {
    private static final String EXIT_KEYS = "e";
    private static final String WARNING_MESSAGE = "Внимание! Команды задаются числовыми значениями от 1 до 7,\n"
            + "либо последовательностью символов \"" + EXIT_KEYS + "\". Попробуйте еще раз!";
    private static TaskManager manager;

    public static void main(String[] args) {
        manager = new TaskManager();
        Scanner scanner = ConsoleSubmenu.getScanner();

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
            Collection<AbstractTask> tasks = manager.getAllTasks();
            ReportTasks.printTasksCollection(tasks);
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
            AbstractTask task = ConsoleSubmenu.inputTask(manager);
            if (task != null) {
                ReportTasks.printTask(task);
                System.out.println("=".repeat(ReportTasks.LINE_LENGTH));
            }
        }
    }

    static void createTask() {
        System.out.println("Создание задачи");
        ConsoleSubmenu.inputCreateTask(manager);
    }

    static void updateTask() {
        if (isNotNullTasks()) {
            System.out.println("Обновление задачи");
            ConsoleSubmenu.inputUpdateTask(manager);
        }
    }

    static void removeTask() {
        if (isNotNullTasks()) {
            System.out.println("Удаление задачи по идентификатору");
            ConsoleSubmenu.inputRemoveTask(manager);
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
