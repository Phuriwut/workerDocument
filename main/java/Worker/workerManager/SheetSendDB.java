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
                "SELECT YEAR(CURRENT_DATE)+543 AS year,( CASE WHEN MAX(year_num) IS NULL" +
                "    THEN 1" +
                "        ELSE MAX(year_num) + 1" +
                "    END )" +
                "    AS year_num , ? AS user_id, ? AS date, ? AS enddate, ? AS day, ? AS salesman FROM sheet WHERE year = YEAR(CURRENT_DATE)+543", Statement.RETURN_GENERATED_KEYS);
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
        int sheetID = rs.getInt(1);
//        System.out.println(sheetID);

        sendQNum(sheetID);
    }

    public void sendQNum(int sheet_id) throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.preparedQuery("SELECT year, year_num FROM `sheet` WHERE sheet_id = ? LIMIT 1");
        ppsm.setInt(1,sheet_id);

        ResultSet rsData = ppsm.executeQuery();
        rsData.next();

        JSONObject userEventData = new JSONObject();
        userEventData.put("year",rsData.getInt("year"));
        userEventData.put("year_num",rsData.getInt("year_num"));
        userEventData.put("sheet_id",sheet_id);

        JSONObject sendData = new JSONObject();
        sendData.put("type",ClientEvents.SHEET_RECEIVE.getString());
        sendData.put("data",userEventData);

        this.messager.send(sendData.toString(),this.sessionID);
    }
}
