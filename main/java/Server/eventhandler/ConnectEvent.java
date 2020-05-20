package Server.eventhandler;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import Server.constance.events.ClientEvents;
import Server.session.SessionData;
import Server.session.SessionStore;
import Server.session.SessionStoreInstance;

public class ConnectEvent implements ConnectListener {
    SessionStoreInstance session = SessionStore.getInstance();

    @Override
    public void onConnect(SocketIOClient client) {
        System.out.println("------------------------------------------\nconnected : " + client.getSessionId().toString() + "\n------------------------------------------");
        SessionData userSession= this.session.getSessionData(client.getSessionId().toString());
        if(userSession != null){
            userSession.setClient(client);
            System.out.println(userSession.isLogin());
            if(userSession.isLogin()) client.sendEvent(ClientEvents.RECEIVE_PROFILE.getString(), userSession.toString());
        }else{
            this.session.addSessionData(client.getSessionId().toString(),new SessionData(client));
        }
    }
}
