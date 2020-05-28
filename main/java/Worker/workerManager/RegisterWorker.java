package Worker.workerManager;

import Server.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterWorker extends Worker implements Runnable{
    public RegisterWorker(JSONObject data, Messager messager, String sessionID){
        super(data, messager, sessionID);
    }

    @Override
    public void run() {
        sendDB();
        System.out.println(data);
    }

    public void sendDB() {
        try {
            PreparedStatement ppsm = database.preparedQuery("SELECT * FROM `user` WHERE userID = ? AND taxID = ? LIMIT 1");
            ppsm.setString(1,data.getString("userID"));
            ppsm.setString(2,data.getString("taxID"));
            ppsm.execute();
            ResultSet rs = ppsm.getResultSet();

            if(rs.next()){
                failRegister();
                return;
            }
            successRegister();

        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
        }
    }

    public void successRegister() throws SQLException, JMSException {
        PreparedStatement ppsm = database.preparedQuery("INSERT INTO `user`(`userID`, `taxID`, `FirstnameContract`, `LastnameContract`, `numberPhone`, `nameConsumer`, `address`, `is_accept`) VALUES (?,?,?,?,?,?,?,?)");
        ppsm.setString(1,this.data.getString("userID"));
        ppsm.setString(2,this.data.getString("taxID"));
        ppsm.setString(3,this.data.getString("contactName"));
        ppsm.setString(4,this.data.getString("contactSurname"));
        ppsm.setString(5,this.data.getString("phone"));
        ppsm.setString(6,this.data.getString("customerName"));
        ppsm.setString(7,this.data.getString("address"));
        ppsm.setBoolean(8,true);
        ppsm.execute();

        JSONObject obj = new JSONObject();
        obj.put("status",0);
        obj.put("title","Success");
        obj.put("detail","สามารถตรวจสอบข้อมูลได้ใน database/isc");

        String objJSON = obj.toString();
        
//        JSONObject noti = new JSONObject();
//        noti.put("type", ClientEvents.NOTIFICATE.getString());
//        noti.put("data",objJSON);
        
        this.messager.send(objJSON,this.sessionID);
    }

    public void failRegister() throws JMSException {
        JSONObject obj = new JSONObject();
        obj.put("status",2);
        obj.put("title","Fail");
        obj.put("detail","เช็ค รหัสลูกค้า และ เลขประจำตัวผู้เสียภาษี อีกครั้ง");

        String objJSON = obj.toString();

//        JSONObject noti = new JSONObject();
//        noti.put("type", ClientEvents.NOTIFICATE.getString());
//        noti.put("session_id",this.sessionID);
//        noti.put("data",objJSON);

        this.messager.send(objJSON,this.sessionID);
    }
}
