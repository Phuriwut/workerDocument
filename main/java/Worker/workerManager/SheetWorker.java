package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.database.Sheet;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SheetWorker extends Worker<Sheet> implements Runnable{
    SheetWorker(String message, Messager messanger) {
        super(Sheet.class, message, messanger);
    }

    @Override
    public void run() {
        checkDB();
    }

    public void checkDB(){
        try {
            PreparedStatement ppsm = database.preparedQuery("SELECT * FROM `sheet` WHERE QNum = ? LIMIT 1");
            ppsm.setString(1,data.getQNum());
            ppsm.execute();

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

        String userEventDataJSON = userEventData.toString();

        JSONObject workerToSocketData = new JSONObject();
        workerToSocketData.put("type", ClientEvents.NOTIFICATE.getString());
        workerToSocketData.put("session_id",this.data.getSession_id());
        workerToSocketData.put("data",userEventDataJSON);

        System.out.println(workerToSocketData.toString());

        this.messager.send(workerToSocketData.toString());
    }

    public void FoundHandler(ResultSet rs) throws SQLException, JMSException {
        JSONObject userEventData = new JSONObject();
        userEventData.put("QNum",rs.getString("QNum"));
        userEventData.put("userID",rs.getString("userID"));
        userEventData.put("date",rs.getString("date"));
        userEventData.put("enddate",rs.getString("enddate"));
        userEventData.put("day",rs.getInt("day"));
        userEventData.put("salesman",rs.getString("salesman"));

        String userEventDataJSON = userEventData.toString();

        JSONObject workerToSocketData = new JSONObject();
        workerToSocketData.put("type", ClientEvents.SHEET_RECEIVE.getString());
        workerToSocketData.put("session_id",this.data.getSession_id());
        workerToSocketData.put("data",userEventDataJSON);

        System.out.println(workerToSocketData);
        this.messager.send(workerToSocketData.toString());
    }
}
