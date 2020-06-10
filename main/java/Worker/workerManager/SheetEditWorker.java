package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SheetEditWorker extends Worker implements Runnable{
    SheetEditWorker(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        try {
            sendSheet();
        } catch (SQLException | JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendSheet() throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.preparedQuery("SELECT * FROM `sheet` WHERE sheet_id = ? LIMIT 1");
        ppsm.setInt(1,this.data.getInt("sheet_id"));
        ppsm.execute();

        ResultSet rs = ppsm.getResultSet();

        rs.next();

        JSONObject object = new JSONObject();
        object.put("user_id",rs.getInt("user_id"));
        object.put("sheet_id",rs.getInt("sheet_id"));
        object.put("date",rs.getString("date"));
        object.put("day",rs.getInt("day"));
        object.put("salesman",rs.getString("salesman"));
        object.put("note",rs.getString("note"));
        object.put("condi",rs.getString("condi"));

        JSONObject sendData = new JSONObject();
        sendData.put("type", ClientEvents.RECEIVE_EDIT_SHEET.toString());
        sendData.put("data",object);

        this.messager.send(sendData.toString(),this.sessionID);
    }
}
