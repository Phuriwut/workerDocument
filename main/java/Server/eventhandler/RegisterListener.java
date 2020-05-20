package Server.eventhandler;

import Server.constance.events.ServerEvents;
import Server.dataextracter.RegisterExtracter;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import org.json.JSONObject;

import javax.jms.JMSException;

public class RegisterListener extends EventListener<RegisterExtracter>{
    public RegisterListener(){ super(); }
    @Override
    public void handler(SocketIOClient client, RegisterExtracter registerExtracter, AckRequest ackRequest) throws JMSException {
        registerExtracter.setSessionID(client.getSessionId());
        System.out.println("userID : " + registerExtracter.getUserID());

        if (true){
            JSONObject obj = new JSONObject();
            obj.put("type", ServerEvents.REGISTER.getString());
            obj.put("data",registerExtracter.toString());
            this.messager.send(obj.toString());
            System.out.println("------------------------------------------\nMessage REGISTER::: '" + registerExtracter.toString()+ "'\n------------------------------------------");
        }
    }
}
