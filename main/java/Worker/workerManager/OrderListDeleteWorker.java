package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONArray;
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
        PreparedStatement ppsm = this.database.preparedQuery("DELETE FROM `orderlist` WHERE order_id = ? ");
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

        sendOrderList();
    }

    public void sendOrderList() throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.preparedQuery("SELECT * FROM `orderlist` WHERE `sheet_id` = ? ");
        ppsm.setInt(1,data.getInt("sheet_id"));
        ppsm.execute();

        ResultSet rs = ppsm.getResultSet();

        JSONArray array = new JSONArray();

        while (rs.next()) {
            JSONObject userEventData = new JSONObject();
            userEventData.put("sheet_id", rs.getInt("sheet_id"));
            userEventData.put("list", rs.getString("list"));
            userEventData.put("amount", rs.getInt("amount"));
            userEventData.put("pricePerUnit", rs.getInt("unitPrice"));
            userEventData.put("type", rs.getInt("type"));
            userEventData.put("order_id", rs.getInt("order_id"));
//            userEventData.put("junior",rs.getString(database));
            array.put(userEventData);
        }

        JSONObject sendData = new JSONObject();
        sendData.put("list",array);

        JSONObject sendClient = new JSONObject();
        sendClient.put("type",ClientEvents.RECEIVE_ORDER_LIST.toString());
        sendClient.put("data",sendData);

//        System.out.println("Order_List_Worker ::: " + sendClient.toString());
        this.messager.send(sendClient.toString(),this.sessionID);
    }
}
