import org.friends.app.DeployMode;
import org.friends.app.view.Application;

public class HerokuMain {

	public static void main(String[] args) {
		new Application().start(DeployMode.HEROKU);
	}
}
