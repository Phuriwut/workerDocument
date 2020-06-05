package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONArray;
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
        PreparedStatement ppsm = this.database.getCon().prepareStatement("INSERT INTO `sheet` (year, year_num, user_id, date, day, salesman, note, condi) " +
                "SELECT YEAR(CURRENT_DATE)+543 AS year,( CASE WHEN MAX(year_num) IS NULL" +
                "    THEN 1" +
                "        ELSE MAX(year_num) + 1" +
                "    END )" +
                "    AS year_num , ? AS user_id, ? AS date, ? AS day, ? AS salesman, ? AS note, ? AS condi FROM sheet WHERE year = YEAR(CURRENT_DATE)+543", Statement.RETURN_GENERATED_KEYS);
//        PreparedStatement ppsm = database.preparedQuery("INSERT INTO `sheet` (year, year_num, user_id, date, enddate, day, salesman) " +
//                "SELECT YEAR(CURRENT_DATE)+543 AS year, COUNT(*) + 1 AS year_num, ? AS user_id, ? AS date, ? AS enddate, ? AS day, ? AS salesman FROM sheet WHERE year = YEAR(CURRENT_DATE)+543");
        ppsm.setInt(1,this.data.getInt("user_id"));
        ppsm.setString(2,this.data.getString("date"));
        ppsm.setInt(3,this.data.getInt("day"));
        ppsm.setString(4,this.data.getString("salesman"));
        ppsm.setString(5,this.data.getString("note"));
        ppsm.setString(6,this.data.getString("condi"));
        ppsm.executeUpdate();
        ResultSet rs = ppsm.getGeneratedKeys();
        rs.next();
        int sheetID = rs.getInt(1);
//        System.out.println(sheetID);

        sendQNum(sheetID);
        sendList();
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

    public void sendList() throws SQLException{
        PreparedStatement ppsm = this.database.preparedQuery("SELECT `sheet_id`, `year`, `year_num`, `date`" +
                "FROM `sheet` ORDER BY `sheet_id` DESC ");
        ppsm.execute();

        ResultSet rs = ppsm.getResultSet();

        JSONArray array = new JSONArray();

        while (rs.next()) {
            JSONObject userEventData = new JSONObject();
            userEventData.put("sheet_id", rs.getInt("sheet_id"));
            userEventData.put("year", rs.getInt("year"));
            userEventData.put("year_num", rs.getInt("year_num"));
            userEventData.put("date", rs.getString("date"));
            array.put(userEventData);
        }

        JSONObject sendObj = new JSONObject();
        sendObj.put("list",array);

        JSONObject sendClient = new JSONObject();
        sendClient.put("type", ClientEvents.RECEIVE_SHEET_LIST.toString());
        sendClient.put("data",sendObj);

        try {
            System.out.println("SHEET_LIST_WORKER ::: " + sendClient.toString());
            this.messager.send(sendClient.toString(),this.sessionID);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
