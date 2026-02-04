
import models.MissionSpecModel;
import models.SecurityLevel;
import models.User;

public class Main {
    public static void main(String[] args) {
        MissionSpecModel spec = new MissionSpecModel("Attack Plans", SecurityLevel.TOP_SECRET);

        User president = new User("Donald", "Trump", SecurityLevel.TOP_SECRET);

        User director = new User("Elon", "Musk", SecurityLevel.SECRET);

        try {
            System.out.println(spec.getTitle(director));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
