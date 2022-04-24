package utils;

import domain.*;
import service.ReportTasks;

import java.util.Collection;

public class Tests {

    /* ТЗ: Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.
    Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)
    Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи
    и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
    И, наконец, попробуйте удалить одну из задач и один из эпиков. */
    public static void testSprint3(TaskManager manager) {
        Collection<TaskBase> tasks;

        manager.removeAllTasks();
        System.out.println("Тестирование приложения по условиям, заданным в техническом задании:");

        System.out.println("1.\tСоздайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей\n"
                + "Распечатайте списки эпиков, задач и подзадач, через System.out.println(..).");

        Task shoppingTask = new Task("Купить батарейки!");
        shoppingTask.setDescription("Необходимо 4 шт.");
        manager.create(shoppingTask);
        Task lessonTask = new Task("Проверить уроки!");
        lessonTask.setDescription("В дневнике задание.");
        manager.create(lessonTask);

        Epic removalEpic = new Epic("Переезд");
        removalEpic.setDescription("Снять наличные деньги");
        manager.create(removalEpic);
        Subtask callSubtask = new Subtask(removalEpic, "Заказать машину");
        callSubtask.setDescription("Газели будет достаточно.");
        removalEpic.create(callSubtask);
        Subtask packSubtask = new Subtask(removalEpic, "Упаковать коробки");
        packSubtask.setDescription("5-6 коробок должно хватить.");
        removalEpic.create(packSubtask);

        Epic epicParty = new Epic("Вечеринка");
        epicParty.setDescription("Поздравление с праздником в коллективе");
        manager.create(epicParty);
        Subtask barSubtask = new Subtask(removalEpic, "Купить цветы и шампанское");
        barSubtask.setDescription("К 2-3 бутылкам шампанского нужно взять 2 коробки шоколадных конфет и фрукты.");
        epicParty.create(barSubtask);

        System.out.println("Результат:");
        tasks = manager.getAllTasks();
        ReportTasks.printTasksCollection(tasks);

        System.out.println();
        System.out.println("2.\tИзмените статусы созданных объектов, распечатайте. Проверьте, что статус задачи\n"
                + "и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.");
        Task updateShoppingTask = new Task("Купить батарейки!");
        updateShoppingTask.setDescription("Необходимо 4-6 шт.");
        updateShoppingTask.setStatus(TaskStatusEnum.DONE);
        manager.update(0, updateShoppingTask);

        Subtask updateCallSubtask = new Subtask(removalEpic, "Заказать машину");
        updateCallSubtask.setDescription("Газели будет достаточно");
        updateCallSubtask.setStatus(TaskStatusEnum.NEW);
        removalEpic.update(0, updateCallSubtask);
        Subtask updatePackSubtask = new Subtask(removalEpic, "Упаковать коробки");
        updatePackSubtask.setDescription("5-6 коробок должно хватить");
        updatePackSubtask.setStatus(TaskStatusEnum.DONE);
        removalEpic.update(1, updatePackSubtask);

        System.out.println("Результат:");
        tasks = manager.getAllTasks();
        ReportTasks.printTasksCollection(tasks);

        System.out.println();
        System.out.println("3.\tПопробуйте удалить одну из задач и один из эпиков.");
        manager.removeTask(0);
        manager.removeTask(epicParty.getTaskId());

        System.out.println("Результат:");
        tasks = manager.getAllTasks();
        ReportTasks.printTasksCollection(tasks);

        System.out.println("4.\t Печать по отдельным категориям. Только задания:");
        tasks = manager.getTasks();
        ReportTasks.printTasksCollection(tasks);

        System.out.println("5.\t Печать по отдельным категориям. Только эпики:");
        tasks = manager.getEpics();
        ReportTasks.printTasksCollection(tasks);

        System.out.println("6.\t Печать по отдельным категориям. Только подзадания:");
        tasks = manager.getSubtasks();
        ReportTasks.printTasksCollection(tasks);
    }
}
