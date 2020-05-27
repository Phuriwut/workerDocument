package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.database.OrderList;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

public class OrderListWorker extends Worker<OrderList> implements Runnable{

    OrderListWorker( String message, Messager messanger) {
        super(OrderList.class, message, messanger);
    }

    @Override
    public void run() {
        checkDB();
    }

    public void checkDB(){
        try {
            PreparedStatement ppsm = database.preparedQuery("SELECT * FROM `orderlist` WHERE QNum = ? LIMIT 1");
            ppsm.setString(1,data.getQNum());
            ppsm.execute();

            ResultSet rs = ppsm.getResultSet();

            if (!rs.next()){
                notFoundHandler();
                return;
            }
            FoundHandler();

        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
        }
    }

    public void notFoundHandler() throws JMSException {
        JSONObject userEventData = new JSONObject();
        userEventData.put("status",2);
        userEventData.put("detail","ตรวจสอบรหัสลูกค้าอีกครั้ง หรือ\nสามารถหาข้อมูลเพิ่มเติมจาก database/isc ");
        userEventData.put("title","รหัสลูกค้าไม่ถูกต้อง");

        String userEventDataJSON = userEventData.toString();

        JSONObject workerToSocketData = new JSONObject();
        workerToSocketData.put("type", ClientEvents.NOTIFICATE.getString());
        workerToSocketData.put("session_id",this.data.getSession_id());
        workerToSocketData.put("data",userEventDataJSON);

        this.messager.send(workerToSocketData.toString());

    }

    public void FoundHandler(){

    }
}
