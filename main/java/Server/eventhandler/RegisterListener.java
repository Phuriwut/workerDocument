package Server.eventhandler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import Server.constance.events.ClientEvents;
import Server.constance.events.ServerEvents;
import Server.dataextracter.NotificationExtracter;
import Server.dataextracter.RegisterExtracter;
import org.json.JSONObject;

import javax.jms.JMSException;

public class RegisterListener extends  EventListener<RegisterExtracter> {
    public RegisterListener(){
        super();
    }
    @Override
    public void handler(SocketIOClient client, RegisterExtracter registerExtracter, AckRequest ackRequest) throws JMSException {
        registerExtracter.setSessionID(client.getSessionId());
        System.out.println("Firstname: " + registerExtracter.getFirstname());
        System.out.println("SessionID : " + client.getSessionId());


        // We will send a small text message saying 'Hello World!!!'
        if(registerExtracter.getCareer() < 5 &&
                registerExtracter.getBank_id().length() == 12 &&
                registerExtracter.getBank_name() < 6 &&
                registerExtracter.getPassword().length() >= 6) {

            JSONObject obj = new JSONObject();
            obj.put("type", ServerEvents.REGISTER.getString());
            obj.put("data", registerExtracter.toString());
            this.messager.send(obj.toString());

            System.out.println("------------------------------------------\nMessage Register::: '" + registerExtracter.toString()+ "'\n------------------------------------------");
        }else {
            statusWarming(client);
        }
    }

    public void statusSuccess(SocketIOClient client){
        NotificationExtracter noti = new NotificationExtracter();
        noti.setStatus(0);
        noti.setTitle("Success");
        noti.setDetail("Waiting for Server accept ☺");
        client.sendEvent(ClientEvents.NOTIFICATE.getString(),noti.toString());
    }

    public void statusWarming(SocketIOClient client){
        JSONObject noti = new JSONObject();
        noti.put("status", 1);
        noti.put("title", "Warming");
        noti.put("detail","Character is wrong\nCheck character again ");
//        System.out.println(noti.toString());
        client.sendEvent(ClientEvents.NOTIFICATE.getString(),noti.toString());
    }
}
