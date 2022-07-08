import dev.service.Managers;
import dev.service.TasksManager;
import dev.utils.menu.MainMenu;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        System.out.println("Трекер задач");
        // Для выбора менеджера работы с памятью
        // следует выполнить команду: Managers.SetMemoryTasksManager();
        Path path = FileSystems.getDefault().getPath("java-kanban.csv");
        Managers.SetFileTasksManager(path.toFile());
        TasksManager manager = Managers.getDefault();
        MainMenu.Menu(manager);
    }
}