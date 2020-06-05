package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SheetBillSendDB extends Worker implements Runnable{
    SheetBillSendDB(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        try {
            successSheet();
        } catch (JMSException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void successSheet() throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.getCon().prepareStatement("INSERT INTO `sheetbill` (`year`, `year_num`, `refer_year`, `refer_year_num`, `date`, `salesman`, `note`, `day`)" +
                "SELECT YEAR(CURRENT_DATE)+543 AS year,( CASE WHEN MAX(year_num) IS NULL " +
                "THEN 1 " +
                "ELSE MAX(year_num) + 1 " +
                "END " +
                "AS year_num , sheet.year AS refer_year, sheet.year_num AS refer_year_num, ? AS date, ? AS salesman, ? AS note, ? AS day FROM `sheet` " +
                "WHERE sheet_id = ? LIMIT 1", Statement.RETURN_GENERATED_KEYS);
        ppsm.setInt(5,this.data.getInt("sheet_id"));
        ppsm.setString(1,this.data.getString("date"));
        ppsm.setString(2,this.data.getString("salesman"));
        ppsm.setString(3,this.data.getString("note"));
        ppsm.setInt(4,this.data.getInt("day"));
        ppsm.executeUpdate();
        ResultSet rs = ppsm.getGeneratedKeys();
        rs.next();
        int sheetID = rs.getInt(1);
        sendIVNum(sheetID);
    }

    public void sendIVNum(int sheetID) throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.preparedQuery("SELECT year, year_num FROM `sheet` WHERE sheet_id = ? LIMIT 1");
        ppsm.setInt(1,sheetID);

        ResultSet rsData = ppsm.executeQuery();
        rsData.next();

        JSONObject userEventData = new JSONObject();
        userEventData.put("year",rsData.getInt("year"));
        userEventData.put("year_num",rsData.getInt("year_num"));
        userEventData.put("sheet_id",sheetID);

        JSONObject sendData = new JSONObject();
        sendData.put("type", ClientEvents.SHEET_RECEIVE.getString());
        sendData.put("data",userEventData);

        this.messager.send(sendData.toString(),this.sessionID);
    }

}
