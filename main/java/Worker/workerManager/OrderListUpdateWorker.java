package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderListUpdateWorker extends Worker implements Runnable{
    OrderListUpdateWorker(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        try {
            updateOrderList();
        } catch (SQLException | JMSException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderList() throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.preparedQuery("UPDATE `orderlist` SET `list`= ? ,`amount`= ? ,`unitPrice`= ? ,`cost`= ? ,`type`= ? WHERE `order_id`= ?");
        ppsm.setInt(6,this.data.getInt("order_id"));
        ppsm.setString(1,this.data.getString("list"));
        ppsm.setInt(2,this.data.getInt("amount"));
        ppsm.setInt(3,this.data.getInt("pricePerUnit"));
        ppsm.setInt(4,this.data.getInt("cost"));
        ppsm.setInt(5,this.data.getInt("type"));
        ppsm.execute();

        JSONObject obj = new JSONObject();
        obj.put("status",0);
        obj.put("title","SUCCESS");
        obj.put("detail","อัพเดทข้อมูลเรียบร้อย");

        JSONObject sendClient = new JSONObject();
        sendClient.put("type", ClientEvents.NOTIFICATE.toString());
        sendClient.put("data",obj);

        this.messager.send(sendClient.toString(),this.sessionID);
    }

}
