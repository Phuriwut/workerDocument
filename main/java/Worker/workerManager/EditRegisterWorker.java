package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditRegisterWorker extends Worker implements Runnable{
    EditRegisterWorker(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        try {
            success();
        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
        }
    }

    public void success() throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.preparedQuery("SELECT * FROM `user` WHERE user_id = ? LIMIT 1");
        ppsm.setInt(1,this.data.getInt("user_id"));
        ppsm.execute();

        ResultSet rs = ppsm.getResultSet();

        rs.next();

        JSONObject object = new JSONObject();

        object.put("user_id",rs.getInt("user_id"));
        object.put("userID",rs.getString("userID"));
        object.put("taxID",rs.getString("taxID"));
        object.put("branch",rs.getString("branch"));
        object.put("customerName",rs.getString("nameConsumer"));
        object.put("contactName",rs.getString("FirstnameContract"));
        object.put("contactSurname",rs.getString("LastnameContract"));
        object.put("phone",rs.getString("numberPhone"));
        object.put("address",rs.getString("address"));

        JSONObject sendData = new JSONObject();
        sendData.put("type", ClientEvents.RECEIVE_EDIT_REGISTER.toString());
        sendData.put("data",object);

        this.messager.send(sendData.toString(),this.sessionID);
    }

}
