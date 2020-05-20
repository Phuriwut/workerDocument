package Server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.MemoryStoreFactory;
import javax.jms.*;
import Server.constance.events.ServerEvents;
import Server.dataextracter.*;
import Server.user.sender.data.NotificateAlert;
import Server.eventhandler.*;

public class main {
    public static void main(String args[])  throws JMSException{

        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(3700);
        MemoryStoreFactory msf = new MemoryStoreFactory();
        config.setStoreFactory(msf);
        //config.setStoreFactory(); how to use this

        SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(new ConnectEvent());
        server.addDisconnectListener(new DisconnectEvent());

//        server.addEventListener(ServerEvents.SIGNUP.getString(), SignUpExtracter.class, new SignUpListener());
        server.addEventListener(ServerEvents.REGISTER.getString(),RegisterExtracter.class, new RegisterListener());

        server.start();

        Thread th = new Thread(new UserSender());
        th.start();

        NotificateAlert nta = new NotificateAlert(server);
        nta.notificateStart();
    }
}
