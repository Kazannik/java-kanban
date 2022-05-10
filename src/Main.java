import dev.service.InMemoryTaskManager;
import dev.service.TaskManager;
import dev.utils.menu.MainMenu;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager();

        System.out.println("Трекер задач");
        MainMenu.Menu(manager);
    }
}