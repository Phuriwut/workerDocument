package Worker.workerManager;

import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
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
            successRegister();

        } catch (SQLException e) {
            if(e.getErrorCode() == 1062){
                failRegister();
                return;
            }
            e.printStackTrace();
        } catch (JMSException e){
            e.printStackTrace();
        }
    }

    public void successRegister() throws SQLException, JMSException {
        PreparedStatement ppsm = database.preparedQuery(
                "INSERT INTO `user`(`userID`, `taxID`,`branch`, `FirstnameContract`, `LastnameContract`, `numberPhone`, `nameConsumer`, `address`, `is_accept`) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)");
        ppsm.setString(1,this.data.getString("userID"));
        ppsm.setString(2,this.data.getString("taxID"));
        ppsm.setString(3,this.data.getString("branch"));
        ppsm.setString(4,this.data.getString("contactName"));
        ppsm.setString(5,this.data.getString("contactSurname"));
        ppsm.setString(6,this.data.getString("phone"));
        ppsm.setString(7,this.data.getString("customerName"));
        ppsm.setString(8,this.data.getString("address"));
        ppsm.setBoolean(9,true);
        ppsm.execute();

        JSONObject obj = new JSONObject();
        obj.put("status",0);
        obj.put("title","Success");
        obj.put("detail","สามารถตรวจสอบข้อมูลได้ใน database/isc");

        String objJSON = obj.toString();
        
        this.messager.send(objJSON,this.sessionID);
    }

    public void failRegister() {
        JSONObject obj = new JSONObject();
        obj.put("status",2);
        obj.put("title","Fail");
        obj.put("detail","เช็ค รหัสลูกค้า และ เลขประจำตัวผู้เสียภาษี อีกครั้ง");

        String objJSON = obj.toString();

        try {
            this.messager.send(objJSON,this.sessionID);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
