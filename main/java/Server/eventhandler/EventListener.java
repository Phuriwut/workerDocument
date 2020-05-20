package Server.eventhandler;

import Server.constance.events.ClientEvents;
import Server.message.Messager;
import Server.message.MessagerInstance;
import Server.session.SessionData;
import Server.session.SessionStore;
import Server.session.SessionStoreInstance;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;

import javax.jms.JMSException;

public abstract class EventListener<T> implements DataListener<T> {
    MessagerInstance messager;
    SessionStoreInstance sessionstore;

    public EventListener(){
        this.sessionstore = SessionStore.getInstance();
        try {
            this.messager = Messager.getInstance();
        }catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onData(SocketIOClient client, T registerExtracter, AckRequest ackRequest) throws Exception{
        SessionData userSession= this.sessionstore.getSessionData(client.getSessionId().toString());
        if(userSession != null){
            userSession.setClient(client);
            System.out.println(userSession.isLogin());
            if(userSession.isLogin()) client.sendEvent(ClientEvents.RECEIVE_PROFILE.getString(), userSession.toString());
        }else{
            this.sessionstore.addSessionData(client.getSessionId().toString(),new SessionData(client));
        }
        handler(client, registerExtracter, ackRequest);
    }

    public abstract void handler(SocketIOClient socketIOClient, T registerExtracter, AckRequest ackRequest) throws JMSException;
}
