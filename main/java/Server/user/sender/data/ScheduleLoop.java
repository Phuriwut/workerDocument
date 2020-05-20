package Server.user.sender.data;

import Server.session.SessionStore;
import java.util.TimerTask;

public class ScheduleLoop extends TimerTask {

    @Override
    public void run() {
        SessionStore.getInstance().syncSession();
    }
}
