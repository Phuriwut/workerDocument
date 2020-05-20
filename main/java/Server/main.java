package Server;

import Server.constance.events.ServerEvents;
import Server.dataextracter.RegisterExtracter;
import Server.eventhandler.ConnectEvent;
import Server.eventhandler.DisconnectEvent;
import Server.eventhandler.RegisterListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.MemoryStoreFactory;

import javax.jms.JMSException;

public class main {
    public static void main(String args[]) throws JMSException {

        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(3700);
        MemoryStoreFactory msf = new MemoryStoreFactory();
        config.setStoreFactory(msf);
        //config.setStoreFactory(); how to use this

        SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(new ConnectEvent());
        server.addDisconnectListener(new DisconnectEvent());

        server.addEventListener(ServerEvents.REGISTER.getString(), RegisterExtracter.class, new RegisterListener());

        server.start();
    }
}
