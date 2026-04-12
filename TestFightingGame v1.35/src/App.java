import haoran.domain.User;
import haoran.ui.FightGame;
import haoran.ui.Login;

public class App {
    public static void main(String[] args) throws InterruptedException {
//        Login log = new Login();
//        log.Start();
        User u = new User("浩然","1");
        FightGame f = new FightGame();
        f.gameStart(u);
    }
}
