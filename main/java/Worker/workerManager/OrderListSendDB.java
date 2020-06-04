package Worker.workerManager;

import Worker.database.OrderList;
import Worker.message.Messager;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class  OrderListSendDB extends Worker implements Runnable {

    public OrderListSendDB(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        sendDB();
    }

    public void  sendDB(){
        try {
            successOrder();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void successOrder() throws SQLException {
        PreparedStatement ppsm = this.database.preparedQuery(
                "INSERT INTO `orderlist`(`sheet_id`, `list`, `amount`, `unitPrice`, `cost`, `type`, `note`, `condi`) " +
                        "VALUES (?,?,?,?,?,?,?,?)");
        ppsm.setInt(1,this.data.getInt("sheet_id"));
        ppsm.setString(2,this.data.getString("list"));
        ppsm.setInt(3,this.data.getInt("amount"));
        ppsm.setInt(4,this.data.getInt("pricePerUnit"));
        ppsm.setInt(5,this.data.getInt("cost"));
        ppsm.setInt(6,this.data.getInt("type"));
        ppsm.setString(7,this.data.getString("note"));
        ppsm.setString(8,this.data.getString("condi"));
        ppsm.execute();
    }
}