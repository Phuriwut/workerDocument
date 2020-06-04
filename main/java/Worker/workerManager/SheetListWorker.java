package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SheetListWorker extends Worker implements Runnable{
    SheetListWorker(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        try {
            sendList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void sendList() throws SQLException{
        PreparedStatement ppsm = this.database.preparedQuery("SELECT `sheet_id`, `year`, `year_num`, `date`" +
                "FROM `sheet`");
        ppsm.execute();

        ResultSet rs = ppsm.getResultSet();

        JSONArray array = new JSONArray();

        while (rs.next()) {
            JSONObject userEventData = new JSONObject();
            userEventData.put("sheet_id", rs.getInt("sheet_id"));
            userEventData.put("year", rs.getInt("year"));
            userEventData.put("year_num", rs.getInt("year_num"));
            userEventData.put("date", rs.getString("date"));
            array.put(userEventData);
        }

        JSONObject sendObj = new JSONObject();
        sendObj.put("list",array);

        JSONObject sendClient = new JSONObject();
        sendClient.put("type", ClientEvents.RECEIVE_SHEET_LIST.toString());
        sendClient.put("data",sendObj);

        try {
            System.out.println("SHEET_LIST_WORKER ::: " + sendClient.toString());
            this.messager.send(sendClient.toString(),this.sessionID);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
