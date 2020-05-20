package Server.user.sender.data;

import com.corundumstudio.socketio.SocketIOServer;
import org.json.JSONObject;

import java.util.Scanner;

public class NotificateAlert {
    Scanner input_string = new Scanner(System.in);
    SocketIOServer server ;

    public NotificateAlert(SocketIOServer server){
        this.server = server;
    }

    public void notificateStart(){
        while(true) {
            System.out.print("status: ");
            String status = input_string.nextLine();
            System.out.print("title: ");
            String title = input_string.nextLine();
            System.out.print("detail: ");
            String detail = input_string.nextLine();
//            System.out.println("Finish");

            JSONObject obj = new JSONObject();
            obj.put("status", status);
            obj.put("title", title);
            obj.put("detail", detail);
            System.out.println(obj.toString());
            server.getBroadcastOperations().sendEvent("NOTIFICATE", obj.toString());
        }
    }


}
