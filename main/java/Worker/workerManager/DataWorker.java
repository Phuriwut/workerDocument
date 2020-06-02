package Worker.workerManager;

import Worker.message.Messager;
import org.json.JSONObject;

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void sendCliend() throws SQLException {
        PreparedStatement ppsm = this.database.preparedQuery("SELECT * FROM `sheet` " +
                "INNER JOIN `user` ON sheet.user_id = user.user_id" +
                "INNER JOIN `orderlist` ON sheet.sheet_id = orderlist.sheet_id" +
                "LIMIT 1");
        ppsm.execute();

        ResultSet rs = ppsm.getResultSet();

        if (!rs.next()){
            return;
        }
    }

    public void sendDataAll(ResultSet rs) throws SQLException {
        JSONObject userEventData = new JSONObject();
        userEventData.put("user_id",rs.getInt("user_id"));
        userEventData.put("sheet_id",rs.getInt("sheet_id"));
        userEventData.put("year",rs.getInt("year"));
        userEventData.put("year_num",rs.getInt("year_num"));
        userEventData.put("date",rs.getString("date"));
        userEventData.put("enddate",rs.getString("enddate"));
        userEventData.put("day",rs.getInt("day"));
        userEventData.put("salesman",rs.getString("salesman"));
        userEventData.put("userID",rs.getString("userID"));
        userEventData.put("taxID",rs.getString("taxID"));
        userEventData.put("branch",rs.getString("branch"));
        userEventData.put("FirstnameContract",rs.getString("FirstnameContract"));
        userEventData.put("LastnameContract",rs.getString("LastnameContract"));
        userEventData.put("numberPhone",rs.getString("numberPhone"));
        userEventData.put("nameConsumer",rs.getString("nameConsumer"));
        userEventData.put("address",rs.getString("address"));
        userEventData.put("seq",rs.getInt("seq"));
        userEventData.put("list",rs.getString("list"));
        userEventData.put("numList",rs.getInt("numList"));
        userEventData.put("unitPrice",rs.getInt("unitPrice"));
        userEventData.put("price",rs.getInt("price"));
        userEventData.put("License",rs.getString("License"));
        userEventData.put("Customization",rs.getString("Customization"));
        userEventData.put("Maintenance",rs.getString("Maintenance"));
        userEventData.put("Miscellneous",rs.getString("Miscellneous"));
        userEventData.put("note",rs.getString("note"));
        userEventData.put("condi",rs.getString("condi"));
    }
}