package domain;

import utils.CollectionUtils;

/* ТЗ: должны выполняться следующие условия:
• Для каждой подзадачи известно, в рамках какого эпика она выполняется.
• Каждый эпик знает, какие подзадачи в него входят.
• Завершение всех подзадач эпика считается завершением эпика. */
public class Subtask extends Task {
    Epic parentEpic;

    public Subtask(Epic parent, String name, String description) {
        super(CollectionUtils.getNextTaskId(parent.subtasks.keySet()), name, description);
        parentEpic = parent;
    }

    public Subtask(Epic parent, String name) {
        this(parent, name, "");
    }

    // ТЗ: Для каждой подзадачи известно, в рамках какого эпика она выполняется.
    public Epic getEpic() {
        return parentEpic;
    }
}
