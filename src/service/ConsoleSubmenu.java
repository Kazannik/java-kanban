package service;

import domain.*;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class ConsoleSubmenu {
    public static final String INPUT_NAME_CAPTION = "Название: ";
    public static final String INPUT_DESCRIPTION_CAPTION = "Описание: ";
    public static final String INPUT_STATUS_CAPTION = "Статус: ";
    public static final String BACK_KEYS = "b";
    private static final String SUBMENU_THREE_WARNING_MESSAGE
            = "Внимание! Команды задаются числовыми значениями от 1 до 3,\n"
            + "либо последовательностью символов \"" + BACK_KEYS + "\". Попробуйте еще раз!";
    private static final String SUBMENU_THREE_ENTER_WARNING_MESSAGE
            = "Внимание! Команды задаются числовыми значениями от 1 до 3,\n"
            + "либо последовательностью символов \"" + BACK_KEYS + "\", либо командой `Ввод`. Попробуйте еще раз!";
    private static final String SUBMENU_FIVE_WARNING_MESSAGE
            = "Внимание! Команды задаются числовыми значениями от 1 до 5,\n"
            + "либо последовательностью символов \"" + BACK_KEYS + "\". Попробуйте еще раз!";

    static Scanner scanner;

    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(in);
        }
        return scanner;
    }

    public static TaskBase inputTask(TaskManager manager) {
        Integer taskId = ConsoleSubmenu.inputTaskId();
        if (taskId != null) {
            if (manager.containsTaskId(taskId)) {
                return manager.getTask(taskId);
            } else {
                ConsoleSubmenu.printIndexOutWarningMessage(taskId);
            }
        }
        return null;
    }

    public static void inputUpdateTask(TaskManager manager) {
        Integer taskId = ConsoleSubmenu.inputTaskId();
        if (taskId != null) {
            if (manager.containsTaskId(taskId)) {
                if (manager.getTask(taskId) instanceof Epic) {
                    Epic epic = (Epic) manager.getTask(taskId);
                    ConsoleSubmenu.inputEditedTask(epic);
                } else if (manager.getTask(taskId) instanceof Task) {
                    Task task = (Task) manager.getTask(taskId);
                    String name = ConsoleSubmenu.inputText("Название (предыдущее значение): ", task.getName());
                    String description = ConsoleSubmenu.inputText("Описание (предыдущее значение): ", task.getDescription());
                    TaskStatusEnum status = ConsoleSubmenu.inputTaskStatus(task.getStatus());
                    task.setName(name);
                    task.setDescription(description);
                    task.setStatus(status);
                    System.out.println("Задача № " + taskId + " успешно отредактирована.");
                }
            } else {
                ConsoleSubmenu.printIndexOutWarningMessage(taskId);
            }
        }
    }

    public static void inputCreateTask(TaskManager manager) {
        Scanner scanner = getScanner();
        while (true) {
            printTypeMenu();
            String command = scanner.nextLine().trim();
            switch (command) {
                case "1": {
                    String name = inputText("Название задачи: ");
                    String description = inputText(INPUT_DESCRIPTION_CAPTION);
                    int taskId = manager.create(new Task(name, description));
                    System.out.println(INPUT_STATUS_CAPTION + manager.getTask(taskId).getStatus().getTitle() + ".");
                    out.println("Созданной задаче присвоен идентификационный номер: " + taskId);
                    return;
                }
                case "2": {
                    String name = inputText("Название эпик-задачи: ");
                    String description = inputText(INPUT_DESCRIPTION_CAPTION);
                    int epicId = manager.create(new Epic(name, description));
                    System.out.println(INPUT_STATUS_CAPTION + manager.getTask(epicId).getStatus().getTitle() + ".");
                    out.println("Созданной эпик-задаче присвоен идентификационный номер: " + epicId);
                    return;
                }
                case "3":
                    if (manager.epicsSize() == 0) {
                        out.println("Внимание! Подзадача является частью эпик-задачи," +
                                " вместе с тем эпик-задачи в трекере отсутствуют.");
                        break;
                    }
                    out.println("Выбор эпик-задачи:");
                    int taskId = inputTaskId();
                    if (manager.containsTaskId(taskId)) {
                        if (manager.getTask(taskId) instanceof Epic) {
                            Epic epic = (Epic) manager.getTask(taskId);
                            String name = inputText("Название подзадачи: ");
                            String description = inputText(INPUT_DESCRIPTION_CAPTION);
                            int subtaskId = epic.create(name, description);
                            System.out.println(INPUT_STATUS_CAPTION + epic.getTask(subtaskId).getStatus().getTitle() + ".");
                            out.println("Созданной подзадаче присвоен идентификационный номер: " + subtaskId);
                            return;
                        } else {
                            printInvalidTypeWarningMessage(taskId);
                        }
                    } else {
                        printIndexOutWarningMessage(taskId);
                    }
                    break;
                case BACK_KEYS:
                    return;
                default:
                    out.println(SUBMENU_THREE_WARNING_MESSAGE);
                    out.println();
                    break;
            }
        }
    }

    public static void inputEditedTask(Epic epic) {
        Scanner scanner = getScanner();
        while (true) {
            printEditedTaskMenu();
            String command = scanner.nextLine().trim();
            switch (command) {
                case "1": {
                    String name = inputText("Название эпик-задачи (предыдущее значение): ", epic.getName());
                    String description = inputText("Описание (предыдущее значение): ", epic.getDescription());
                    epic.setName(name);
                    epic.setDescription(description);
                    out.println("Эпик-задача № " + epic.getTaskId() + " успешно отредактирована.");
                    return;
                }
                case "2": {
                    if (epic.size() == 0) {
                        out.println("Внимание! Подзадачи в эпик-задаче отсутствуют.");
                        break;
                    }
                    out.println("Выбор подзадачи:");
                    Integer taskId = inputTaskId();
                    if (taskId !=null && epic.containsTaskId(taskId)) {
                        Subtask subtask = epic.getTask(taskId);
                        String name = inputText("Название подзадачи (предыдущее значение): ", subtask.getName());
                        String description = inputText("Описание (предыдущее значение): ", subtask.getDescription());
                        TaskStatusEnum status = inputTaskStatus(subtask.getStatus());
                        subtask.setName(name);
                        subtask.setDescription(description);
                        subtask.setStatus(status);
                        System.out.println("Подзадача номер: " + subtask.getTaskId() + " успешно отредактирована.");
                        return;
                    } else {
                        printIndexOutWarningMessage(taskId);
                    }
                    break;
                }
                case "3":
                    String name = inputText("Название подзадачи: ");
                    String description = inputText(INPUT_DESCRIPTION_CAPTION);
                    int subtaskId = epic.create(name, description);
                    System.out.println(INPUT_STATUS_CAPTION + epic.getTask(subtaskId).getStatus().getTitle() + ".");
                    out.println("Созданной подзадаче присвоен идентификационный номер: " + subtaskId);
                    return;
                case "4":
                    if (epic.size() == 0) {
                        out.println("Внимание! Подзадачи в эпик-задаче отсутствуют.");
                        break;
                    }
                    Integer taskId = inputTaskId();
                    if (taskId != null) {
                        if (epic.containsTaskId(taskId)) {
                            epic.removeTask(taskId);
                            System.out.println("Подзадача № " + taskId + " успешно удалена!");
                            return;
                        } else {
                            ConsoleSubmenu.printIndexOutWarningMessage(taskId);
                        }
                    }
                    break;
                case "5":
                    if (epic.size() == 0) {
                        out.println("Внимание! Подзадачи в эпик-задаче отсутствуют.");
                        break;
                    }
                    epic.removeAllTasks();
                    System.out.println("Все подзадачи удалены!");
                    return;
                case BACK_KEYS:
                    return;
                default:
                    out.println(SUBMENU_FIVE_WARNING_MESSAGE);
                    out.println();
                    break;
            }
        }
    }

    public static TaskStatusEnum inputTaskStatus(TaskStatusEnum defaultStatus) {
        Scanner scanner = getScanner();
        while (true) {
            printStatusMenu(defaultStatus);
            String command = scanner.nextLine().trim();
            switch (command) {
                case "1":
                    return TaskStatusEnum.NEW;
                case "2":
                    return TaskStatusEnum.IN_PROGRESS;
                case "3":
                    return TaskStatusEnum.DONE;
                case BACK_KEYS:
                case "":
                    return defaultStatus;
                default:
                    out.println(SUBMENU_THREE_ENTER_WARNING_MESSAGE);
                    out.println();
                    break;
            }
        }
    }

    public static Integer inputTaskId() {
        Scanner scanner = getScanner();
        out.print("Укажите идентификатор: ");
        if (scanner.hasNextInt()) {
            return Integer.parseInt(scanner.nextLine());
        } else {
            scanner.nextLine();
            out.println("Внимание! Идентификаторы задач задаются только числовыми значениями!");
            return null;
        }
    }

    public static String inputText(String caption) {
        Scanner scanner = getScanner();
        out.print(caption);
        return scanner.nextLine().trim();
    }

    public static String inputText(String caption, String defaultText) {
        Scanner scanner = getScanner();
        out.println(caption + defaultText);
        out.println("Укажите новое значение или нажмите `Ввод` для сохранения прежнего:");
        String resultText = scanner.nextLine().trim();
        if (resultText.length() == 0) {
            return defaultText;
        } else {
            return resultText;
        }
    }

    public static void inputRemoveTask(TaskManager manager) {
        Integer taskId = ConsoleSubmenu.inputTaskId();
        if (taskId != null) {
            if (manager.containsTaskId(taskId)) {
                manager.removeTask(taskId);
                System.out.println("Задача № " + taskId + " успешно удалена!");
            } else {
                ConsoleSubmenu.printIndexOutWarningMessage(taskId);
            }
        }
    }

    public static void printIndexOutWarningMessage(int taskId) {
        out.println("Внимание! Заданный идентификационный номер задачи ("
                + taskId + ") отсутствует в трекере.");
    }

    public static void printInvalidTypeWarningMessage(int taskId) {
        out.println("Внимание! Задача с идентификационным номером "
                + taskId + " не является эпик-задачей.");
    }

    static void printTypeMenu() {
        out.println();
        out.println("Укажите тип создаваемой задачи:");
        out.println("1\tОбычная задача;");
        out.println("2\tЭпик-задача;");
        out.println("3\tПодзадача;");
        out.println(BACK_KEYS + "\tВозврат в основное меню.");
    }

    static void printEditedTaskMenu() {
        out.println();
        out.println("Выбор объекта для редактирования:");
        out.println("1\tРедактирование эпик-задачи;");
        out.println("2\tРедактирование подзадачи;");
        out.println("3\tСоздание подзадачи;");
        out.println("4\tУдаление подзадачи;");
        out.println("5\tУдаление всех подзадач;");
        out.println(BACK_KEYS + "\tВозврат в основное меню.");
    }

    static void printStatusMenu() {
        out.println();
        out.println("Укажите статус задачи:");
        out.println("1\t" + TaskStatusEnum.NEW.getTitle() + ";");
        out.println("2\t" + TaskStatusEnum.IN_PROGRESS.getTitle() + ";");
        out.println("3\t" + TaskStatusEnum.DONE.getTitle() + ";");
        out.println(BACK_KEYS + "\tВозврат в основное меню.");
    }

    static void printStatusMenu(TaskStatusEnum defaultStatus) {
        out.println();
        out.println("Укажите статус задачи или нажмите `Ввод` для сохранения предыдущего:");
        out.println("1\t" + TaskStatusEnum.NEW.getTitle() + ";");
        out.println("2\t" + TaskStatusEnum.IN_PROGRESS.getTitle() + ";");
        out.println("3\t" + TaskStatusEnum.DONE.getTitle() + ";");
        out.println(BACK_KEYS + " или `Ввод`\tПредыдущий статус: " + defaultStatus.getTitle() + ".");
    }
}
