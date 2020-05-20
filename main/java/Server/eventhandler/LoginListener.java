package Server.eventhandler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import Server.dataextracter.LoginExtracter;
import Server.dataextracter.NotificationExtracter;
import org.json.JSONObject;

import javax.jms.JMSException;

public class LoginListener extends EventListener<LoginExtracter> {
    public LoginListener(){
        super();
    }

    @Override
    public void handler(SocketIOClient client, LoginExtracter loginExtracter, AckRequest ackRequest) throws JMSException {
        loginExtracter.setSessionID(client.getSessionId());

        // We will send a small text message saying 'Hello World!!!'
        if(true) {
            JSONObject obj = new JSONObject();
            obj.put("type", "LOGIN");
            obj.put("data",loginExtracter.toString());
            this.messager.send(obj.toString());
            System.out.println("------------------------------------------\nMessage LOGIN::: '" + loginExtracter.toString()+ "'\n------------------------------------------");
        }else {
            statusWarming(client);
        }
    }

    public void statusWarming(SocketIOClient client){
        NotificationExtracter noti = new NotificationExtracter();
        noti.setStatus(1);
        noti.setTitle("Check Please ");
        noti.setDetail("email or password is wrong –");
    }

}

