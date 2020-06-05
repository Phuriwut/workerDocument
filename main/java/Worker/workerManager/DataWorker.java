package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataWorker extends Worker implements Runnable{
    DataWorker(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        try {
            sendCliend();
        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
        }
    }

    public void sendCliend() throws SQLException, JMSException {
        sendDataUser();
    }

    public void sendDataUser() throws SQLException, JMSException {
//        userData and sheetData
        PreparedStatement ppsm = this.database.preparedQuery("SELECT * FROM `sheet` " +
                "INNER JOIN `user` ON sheet.user_id = user.user_id " +
                "WHERE sheet_id = ? " +
                "LIMIT 1");
        ppsm.setInt(1,this.data.getInt("sheet_id"));
        ppsm.execute();

        ResultSet rs = ppsm.getResultSet();

        JSONObject userEventData = new JSONObject();

        if (rs.next()) {
            userEventData.put("user_id", rs.getInt("user_id"));
            userEventData.put("sheet_id", rs.getInt("sheet_id"));
            userEventData.put("year", rs.getInt("year"));
            userEventData.put("year_num", rs.getInt("year_num"));
            userEventData.put("date", rs.getString("date"));
            userEventData.put("day", rs.getInt("day"));
            userEventData.put("salesman", rs.getString("salesman"));
            userEventData.put("userID", rs.getString("userID"));
            userEventData.put("taxID", rs.getString("taxID"));
            userEventData.put("branch", rs.getString("branch"));
            userEventData.put("FirstnameContract", rs.getString("FirstnameContract"));
            userEventData.put("LastnameContract", rs.getString("LastnameContract"));
            userEventData.put("numberPhone", rs.getString("numberPhone"));
            userEventData.put("nameConsumer", rs.getString("nameConsumer"));
            userEventData.put("address", rs.getString("address"));
            userEventData.put("note",rs.getString("note"));
            userEventData.put("condi",rs.getString("condi"));
        }else{
            failUserEventData();
            return;
        }

//        OrderData
        PreparedStatement ppsm2 = this.database.preparedQuery("SELECT " +
                "sheet_id, " +
                "list, " +
                "amount, " +
                "unitPrice, " +
                "cost, " +
                "type, " +
                "order_id FROM `orderlist` WHERE sheet_id = ? ");
        ppsm2.setInt(1,this.data.getInt("sheet_id"));
        ppsm2.execute();

        ResultSet rs2 = ppsm2.getResultSet();

        JSONArray array = new JSONArray();

        while (rs2.next()) {
            JSONObject userOrderData = new JSONObject();
            userOrderData.put("list", rs2.getString("list"));
            userOrderData.put("amount", rs2.getInt("amount"));
            userOrderData.put("pricePerUnit", rs2.getInt("unitPrice"));
            userOrderData.put("cost", rs2.getInt("cost"));
            userOrderData.put("type", rs2.getInt("type"));
            userOrderData.put("order_id", rs2.getInt("order_id"));
            array.put(userOrderData);
        }

        JSONObject sendAllData = new JSONObject();
        sendAllData.put("sheetdata",userEventData);
        sendAllData.put("orderlist",array);

        JSONObject sendData = new JSONObject();
        sendData.put("type",ClientEvents.RECEIVE_SHEET_ALL_DATA.toString());
        sendData.put("data",sendAllData);

        this.messager.send(sendData.toString(),this.sessionID);
    }

    public void failUserEventData() throws JMSException {
        JSONObject object = new JSONObject();
        object.put("status",2);
        object.put("title","ERROR");
        object.put("detail","ไม่สามารถดึงข้อมุลจาก Database ได้");

        this.messager.send(object.toString(),this.sessionID);
    }
}
