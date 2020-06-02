package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.database.Sheet;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SheetWorker extends Worker implements Runnable{
    SheetWorker(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        checkDB();
    }

    public void checkDB(){
        try {
            PreparedStatement ppsm = database.preparedQuery("SELECT * FROM `sheet` WHERE sheet_id = ? LIMIT 1");
            ppsm.setInt(1,data.getInt("sheet_id"));

            ResultSet rs = ppsm.getResultSet();

            if (!rs.next()){
                notFoundHandler();
                return;
            }
            FoundHandler(rs);

        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
        }
    }

    public void notFoundHandler() throws JMSException {
        JSONObject userEventData = new JSONObject();
        userEventData.put("status", 2);
        userEventData.put("detail", "ตรวจสอบรหัสลูกค้าอีกครั้ง หรือ\nสามารถหาข้อมูลเพิ่มเติมจาก database/isc ");
        userEventData.put("title","รหัสลูกค้าไม่ถูกต้อง");
        userEventData.put("type",ClientEvents.NOTIFICATE.toString());

        String userEventDataJSON = userEventData.toString();

        this.messager.send(userEventDataJSON,this.sessionID);
    }

    public void FoundHandler(ResultSet rs) throws SQLException, JMSException {
        JSONObject userEventData = new JSONObject();
        userEventData.put("sheet_id",rs.getInt("sheet_id"));
        userEventData.put("year",rs.getInt("year"));
        userEventData.put("year_num",rs.getInt("year_num"));
        userEventData.put("userID",rs.getString("userID"));
        userEventData.put("date",rs.getString("date"));
        userEventData.put("enddate",rs.getString("enddate"));
        userEventData.put("day",rs.getInt("day"));
        userEventData.put("salesman",rs.getString("salesman"));
        userEventData.put("type",ClientEvents.SHEET_RECEIVE.getString());

        String userEventDataJSON = userEventData.toString();

        this.messager.send(userEventDataJSON,this.sessionID);
    }
}
