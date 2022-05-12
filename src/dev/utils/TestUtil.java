package dev.utils;

import dev.domain.*;
import dev.service.TaskManager;

import java.util.List;
import java.util.stream.Collectors;

public final class TestUtil {

    /* ТЗ №3: Создайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей.
    Распечатайте списки эпиков, задач и подзадач, через System.out.println(..)
    Измените статусы созданных объектов, распечатайте. Проверьте, что статус задачи
    и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
    И, наконец, попробуйте удалить одну из задач и один из эпиков. */
    public static void testSprint3(TaskManager manager) {
        manager.removeAllTasks();
        System.out.println("Тестирование приложения по условиям, заданным в техническом задании Спринта №3:");

        System.out.println("\n1.\tСоздайте 2 задачи, один эпик с 2 подзадачами, а другой эпик с 1 подзадачей\n"
                + "Распечатайте списки эпиков, задач и подзадач, через System.out.println(..).");

        int nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Task shoppingTask = new Task(nextTaskId, "Купить батарейки!", "Необходимо 4 шт.");
        manager.create(shoppingTask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Task lessonTask = new Task(nextTaskId, "Проверить уроки!");
        lessonTask.setDescription("В дневнике задание.");
        manager.create(lessonTask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Epic removalEpic = new Epic(nextTaskId,
                "Переезд", "Снять наличные деньги");
        manager.create(removalEpic);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Subtask callSubtask = new Subtask(removalEpic.getTaskId(), nextTaskId,
                "Заказать машину", "Газели будет достаточно.");
        removalEpic.create(callSubtask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Subtask packSubtask = new Subtask(removalEpic.getTaskId(), nextTaskId, "Упаковать коробки");
        packSubtask.setDescription("5-6 коробок должно хватить.");
        removalEpic.create(packSubtask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Subtask tapeSubtask = new Subtask(removalEpic.getTaskId(), nextTaskId, "Купить скотч");
        tapeSubtask.setDescription("20 метров.");
        removalEpic.create(tapeSubtask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Epic epicParty = new Epic(nextTaskId, "Вечеринка");
        epicParty.setDescription("Поздравление с праздником в коллективе");
        manager.create(epicParty);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Subtask barSubtask = new Subtask(epicParty.getTaskId(), nextTaskId, "Купить цветы и шампанское");
        barSubtask.setDescription("К 2-3 бутылкам шампанского нужно взять 2 коробки шоколадных конфет и фрукты.");
        epicParty.create(barSubtask);

        System.out.println("Результат:");
        ReportUtils.printTasksCollection(manager.getHighLevelTasks());

        System.out.println("\n2.\tИзмените статусы созданных объектов, распечатайте. Проверьте, что статус задачи\n"
                + "и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.");

        Task updateShoppingTask = (Task) manager.getTask(0).clone();
        updateShoppingTask.setStatus(TaskStatusEnum.DONE);
        manager.update(updateShoppingTask);

        Subtask updateCallSubtask = (Subtask) manager.getSubtask(3).clone();
        updateCallSubtask.setStatus(TaskStatusEnum.IN_PROGRESS);
        removalEpic.update(updateCallSubtask);

        Subtask updatePackSubtask = (Subtask) manager.getSubtask(4).clone();
        updatePackSubtask.setDescription("В одну коробку войдет.");
        updatePackSubtask.setStatus(TaskStatusEnum.DONE);
        removalEpic.update(updatePackSubtask);

        Subtask updateBarSubtask = (Subtask) manager.getSubtask(5).clone();
        updateBarSubtask.setStatus(TaskStatusEnum.DONE);
        removalEpic.update(updateBarSubtask);

        System.out.println("Результат:");
        ReportUtils.printTasksCollection(manager.getHighLevelTasks());

        System.out.println("\n3.\tПопробуйте удалить одну из задач и один из эпиков.");
        manager.removeTask(0);
        manager.removeTask(epicParty.getTaskId());

        System.out.println("Результат:");
        ReportUtils.printTasksCollection(manager.getHighLevelTasks());

        System.out.println("\n4.\t Печать по отдельным категориям. Только задачи:");
        ReportUtils.printTasksCollection(manager.getTasks().stream()
                .map(t -> (TaskBase) t)
                .collect(Collectors.toList()));

        System.out.println("\n5.\t Печать по отдельным категориям. Только эпики:");
        ReportUtils.printTasksCollection(manager.getEpics().stream()
                .map(t -> (TaskBase) t)
                .collect(Collectors.toList()));

        System.out.println("\n6.\t Печать по отдельным категориям. Только подзадачи:");
        ReportUtils.printTasksCollection(manager.getSubtasks().stream()
                .map(t -> (TaskBase) t)
                .collect(Collectors.toList()));
    }

    /* ТЗ №4: В главном классе воспроизведите несложный пользовательский сценарий:
    -	создайте несколько задач разного типа.
    -	вызовите разные методы интерфейса TaskManager и напечатайте историю просмотров
     после каждого вызова. */
    public static void testSprint4(TaskManager manager) {
        manager.removeAllTasks();
        System.out.println("Тестирование приложения по условиям, заданным в техническом задании Спринта №4:");

        System.out.println("\n1.\tСоздайте несколько задач разного типа.");

        int nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Task shoppingTask = new Task(nextTaskId, "Задача 1", "Создаю обычную задачу с индексом 0.");
        manager.create(shoppingTask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Epic repairEpic = new Epic(nextTaskId,
                "Эпик-задача 1", "Создаю пустую эпик-задачу с индексом 1.");
        manager.create(repairEpic);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        Subtask subtask = new Subtask(repairEpic.getTaskId(), nextTaskId,
                "Подзадача 1", "Создаю подзадачу с индексом 2. Метод: epic.create(subtask)");
        repairEpic.create(subtask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        subtask = new Subtask(repairEpic.getTaskId(), nextTaskId,
                "Подзадача 2",
                "Создаю подзадачу с индексом 3. Метод: epic.create(subtask)",
                TaskStatusEnum.DONE);
        repairEpic.create(subtask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        subtask = new Subtask(repairEpic.getTaskId(), nextTaskId, "Подзадача 3",
                "Создаю подзадачу с индексом 4. Метод: manager.create(subtask)");
        manager.create(subtask);

        nextTaskId = CollectionUtils.getNextTaskId(manager.getAllTaskId());
        subtask = new Subtask(repairEpic.getTaskId(), nextTaskId,
                "Подзадача 4", "Создаю подзадачу с индексом 5. Метод: manager.create(subtask)");
        manager.create(subtask);

        System.out.println("Результат:");
        ReportUtils.printTasksCollection(manager.getHighLevelTasks());

        System.out.println("\n2.\tВызываем разные методы интерфейса TaskManager\n" +
                "и напечатаем историю просмотров после каждого вызова");

        System.out.println("\nВызываем задание № 0 и меняем его статус.");
        Task updateShoppingTask = (Task) manager.getTask(0).clone();
        updateShoppingTask.setStatus(TaskStatusEnum.DONE);
        manager.update(updateShoppingTask);

        System.out.println("Результат:");
        ReportUtils.printTasksCollection(manager.getHighLevelTasks());

        System.out.println("\nПечатаем историю просмотра.");
        ReportUtils.printTasksCollection(manager.getHistory());

        System.out.println("\nВызываем задания 12 раз в цикле по одному.");
        List<Integer> taskIsCollection = manager.getAllTaskId();
        int index = 0;
        for (int i = 0; i < 12; i++) {
            if (index >= taskIsCollection.size()) {
                index = 0;
            }
            TaskBase task = manager.getTaskBase(taskIsCollection.get(index));
            System.out.print((i + 1) + ") ");
            ReportUtils.printTask(task);
            index++;
        }

        System.out.println("\nПечатаем историю просмотра.");
        ReportUtils.printTasksCollection(manager.getHistory());
    }
}
