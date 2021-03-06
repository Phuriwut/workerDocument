package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SheetUpdateWorker extends Worker implements Runnable{
    SheetUpdateWorker(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        try {
            updateSheet();
        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateSheet() throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.preparedQuery("UPDATE `sheet` SET `user_id`= ? ,`date`= ? ,`day`= ? ,`salesman`= ? ,`note` = ? , `condi` = ? WHERE `sheet_id`= ?");
        ppsm.setInt(7,this.data.getInt("sheet_id"));
        ppsm.setInt(1,this.data.getInt("user_id"));
        ppsm.setString(2,this.data.getString("date"));
        ppsm.setInt(3,this.data.getInt("day"));
        ppsm.setString(4,this.data.getString("salesman"));
        ppsm.setString(5,this.data.getString("note"));
        ppsm.setString(6,this.data.getString("condi"));
        ppsm.execute();

        JSONObject object = new JSONObject();
        object.put("status",0);
        object.put("title","SUCCESS");
        object.put("detail","อัพเดทชีทเรียบร้อย");

        JSONObject sendNoti = new JSONObject();
        sendNoti.put("type", ClientEvents.NOTIFICATE.toString());
        sendNoti.put("data",object);

        this.messager.send(sendNoti.toString(),this.sessionID);
    }
}
