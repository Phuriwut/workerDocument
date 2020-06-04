package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import akka.stream.Client;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderListDeleteWorker extends Worker implements Runnable{
    OrderListDeleteWorker(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        try {
            listDeleted();
        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
        }
    }

    public void listDeleted() throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.preparedQuery("DELETE FROM `orderlist` WHERE order_id = ? ;");
        ppsm.setInt(1,this.data.getInt("order_id"));
        ppsm.execute();

        JSONObject sendNoti = new JSONObject();
        sendNoti.put("status",0);
        sendNoti.put("title","SUCCESS");
        sendNoti.put("detail","ลบข้อมูลเรียบร้อย");

        JSONObject sendClient = new JSONObject();
        sendClient.put("type", ClientEvents.NOTIFICATE.toString());
        sendClient.put("data",sendNoti);

        this.messager.send(sendClient.toString(),this.sessionID);
    }
}
