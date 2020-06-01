package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SheetSendDB extends Worker implements Runnable{
    public SheetSendDB(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        sendDB();
    }

    public void sendDB(){
        try {
            successSheet();
        } catch (SQLException | JMSException throwables) {
            throwables.printStackTrace();
        }
    }

    public void successSheet() throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.getCon().prepareStatement("INSERT INTO `sheet` (year, year_num, user_id, date, enddate, day, salesman) " +
                "SELECT YEAR(CURRENT_DATE)+543 AS year, COUNT(*) + 1 AS year_num, ? AS user_id, ? AS date, ? AS enddate, ? AS day, ? AS salesman FROM sheet WHERE year = YEAR(CURRENT_DATE)+543", Statement.RETURN_GENERATED_KEYS);
//        PreparedStatement ppsm = database.preparedQuery("INSERT INTO `sheet` (year, year_num, user_id, date, enddate, day, salesman) " +
//                "SELECT YEAR(CURRENT_DATE)+543 AS year, COUNT(*) + 1 AS year_num, ? AS user_id, ? AS date, ? AS enddate, ? AS day, ? AS salesman FROM sheet WHERE year = YEAR(CURRENT_DATE)+543");
        ppsm.setInt(1,this.data.getInt("user_id"));
        ppsm.setString(2,this.data.getString("date"));
        ppsm.setString(3,this.data.getString("enddate"));
        ppsm.setInt(4,this.data.getInt("day"));
        ppsm.setString(5,this.data.getString("salesman"));
        ppsm.executeUpdate();
        ResultSet rs = ppsm.getGeneratedKeys();
        rs.next();
        int year = rs.getInt("year");
        int year_num = rs.getInt("year_num");
        int sheet_id = rs.getInt("sheet_id");

        JSONObject userGetQNum = new JSONObject();
        userGetQNum.put("year",year);
        userGetQNum.put("year_num",year_num);
        userGetQNum.put("sheet_id",sheet_id);
        userGetQNum.put("type", ClientEvents.SHEET_RECEIVE.getString());

        this.messager.send(userGetQNum.toString(),sessionID);
    }
}
