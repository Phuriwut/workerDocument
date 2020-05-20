package Server.eventhandler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import Server.dataextracter.Extracter;

public class LogoutListen extends EventListener<Extracter>{

    @Override
    public void handler(SocketIOClient socketIOClient, Extracter registerExtracter, AckRequest ackRequest){
        this.sessionstore.getSessionData(socketIOClient.getSessionId().toString()).setLogin(false);
    }
}
