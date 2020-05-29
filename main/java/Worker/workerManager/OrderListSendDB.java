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
        PreparedStatement ppsm = database.preparedQuery(
                "INSERT INTO `orderlist`(`QNum`, `seq`, `list`, `numList`, `unitPrice`, `price`, `License`, `Customization`, `Maintenance`, `Miscellneous`, `note`, `condi`) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        ppsm.setString(1,this.data.getString("QNum"));
        ppsm.setInt(2,this.data.getInt("Seq"));
        ppsm.setString(3,this.data.getString("list"));
        ppsm.setInt(4,this.data.getInt(""));
        ppsm.setInt(5,this.data.getInt(""));
        ppsm.setInt(6,this.data.getInt(""));
        ppsm.setInt(7,this.data.getInt(""));
        ppsm.setInt(8,this.data.getInt(""));
        ppsm.setInt(9,this.data.getInt(""));
        ppsm.setInt(10,this.data.getInt(""));
        ppsm.setString(11,this.data.getString(""));
        ppsm.setString(12,this.data.getString(""));
        ppsm.execute();
    }
}