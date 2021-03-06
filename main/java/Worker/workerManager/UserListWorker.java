package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserListWorker extends Worker implements Runnable{

    UserListWorker(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        try {
            sendClient();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void sendClient() throws SQLException {
        PreparedStatement ppsm = this.database.preparedQuery("SELECT * " +
                "FROM `user`");
        ppsm.execute();
        ResultSet rs = ppsm.getResultSet();
        JSONArray array = new JSONArray();

        while (rs.next()){
            JSONObject obj = new JSONObject();
            obj.put("user_id",rs.getInt("user_id"));
            obj.put("userID",rs.getString("userID"));
            obj.put("branch",rs.getString("branch"));
            obj.put("customerName",rs.getString("nameConsumer"));
            obj.put("contactName",rs.getString("FirstnameContract"));
            obj.put("contactSurname",rs.getString("LastnameContract"));
            array.put(obj);
        }
        JSONObject sendObj = new JSONObject();
        sendObj.put("userList",array);


        JSONObject sendClient = new JSONObject();
        sendClient.put("type", ClientEvents.RECEIVE_USER_LIST.toString());
        sendClient.put("data",sendObj);

        try {
            this.messager.send(sendClient.toString(),this.sessionID);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
