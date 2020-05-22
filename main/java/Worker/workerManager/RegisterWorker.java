package Worker.workerManager;

import Server.constance.events.ClientEvents;
import Worker.database.Register;
import Worker.message.Messager;
import org.json.JSONObject;
import Worker.message.Messager.*;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterWorker extends Worker<Register> implements Runnable{
    public RegisterWorker(String message, Messager messager){
        super(Register.class,message,messager );}

    @Override
    public void run() {
        sendDB();
        System.out.println("message ::::: " + this.data.getUserID());
    }

    public void sendDB() {
        try {
            PreparedStatement ppsm = database.preparedQuery("SELECT * FROM `user` WHERE userID = ? AND taxID = ? LIMIT 1");
            ppsm.setString(1,data.getUserID());
            ppsm.setString(2,data.getTaxID());
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
        PreparedStatement ppsm = database.preparedQuery("INSERT INTO `user`(`number`, `userID`, `taxID`, `FirstnameContract`, `LastnameContract`, `numberPhone`, `nameConsumer`, `address`, `is_accept`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5],[value-6],[value-7],[value-8],[value-9])");
        ppsm.setString(1,this.data.getNumber());
        ppsm.setString(2,this.data.getUserID());
        ppsm.setString(3,this.data.getTaxID());
        ppsm.setString(4,this.data.getFirstnameContract());
        ppsm.setString(5,this.data.getLastnameContract());
        ppsm.setString(6,this.data.getNumberPhone());
        ppsm.setString(7,this.data.getNameConsumer());
        ppsm.setString(8,this.data.getAddress());
        ppsm.setBoolean(9,true);
        ppsm.execute();

        JSONObject obj = new JSONObject();
        obj.put("status",0);
        obj.put("title","Success");
        obj.put("detail","สามารถตรวจสอบข้อมูลได้ใน database/isc");

        String objJSON = obj.toString();
        
        JSONObject noti = new JSONObject();
        noti.put("type", ClientEvents.NOTIFICATE.getString());
        noti.put("session_id",this.data.getSession_id());
        noti.put("data",objJSON);
        
        this.messager.send(noti.toString());
    }

    public void failRegister() throws JMSException {
        JSONObject obj = new JSONObject();
        obj.put("status",2);
        obj.put("title","Fail");
        obj.put("detail","เช็ค รหัสลูกค้า และ เลขประจำตัวผู้เสียภาษี อีกครั้ง");

        String objJSON = obj.toString();

        JSONObject noti = new JSONObject();
        noti.put("type", ClientEvents.NOTIFICATE.getString());
        noti.put("session_id",this.data.getSession_id());
        noti.put("data",objJSON);

        this.messager.send(noti.toString());
    }
}
