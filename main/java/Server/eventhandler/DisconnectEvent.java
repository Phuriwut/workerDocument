package Server.eventhandler;

import Server.session.SessionStore;
import Server.session.SessionStoreInstance;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class DisconnectEvent implements DisconnectListener {
    SessionStoreInstance session = SessionStore.getInstance();
    @Override
    public void onDisconnect(SocketIOClient client) {
        System.out.println("disconnected " + client.getSessionId().toString());
    }
}