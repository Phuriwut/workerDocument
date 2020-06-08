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

public class SheetReceiptSendDB extends Worker implements Runnable{
    SheetReceiptSendDB(JSONObject data, Messager messanger, String sessionID) {
        super(data, messanger, sessionID);
    }

    @Override
    public void run() {
        try {
            success();
        } catch (JMSException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void success() throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.getCon().prepareStatement("INSERT INTO `sheetreceipt` " +
                "(`year`, `year_num`, `refer_year`, `refer_year_num`, `date`, `salesman`, `note`, `day`) " +
                "SELECT YEAR(CURRENT_DATE)+543 AS year, (CASE WHEN MAX(year_num) IS NULL " +
                "THEN 1 " +
                "ELSE MAX(year_num) + 1 " +
                "END " +
                "As year_num ,sheetbill.year AS refer_year, sheetbill.year_num AS refer_year_num, " +
                "? AS date, ? AS salesman, ? AS note, ? AS day FROM `sheetbill` " +
                "WHERE sheet_bill_id = ? LIMIT 1", Statement.RETURN_GENERATED_KEYS);
        ppsm.setInt(5,this.data.getInt("sheet_bill_id"));
        ppsm.setString(1,this.data.getString("date"));
        ppsm.setString(2,this.data.getString("salesman"));
        ppsm.setString(3,this.data.getString("note"));
        ppsm.setInt(4,this.data.getInt("day"));
        ppsm.executeUpdate();
        ResultSet rs = ppsm.getGeneratedKeys();
        rs.next();
        int sheetID = rs.getInt(1);
        sendTIVNum(sheetID);
        sendList();
    }

    public void sendTIVNum(int sheetID) throws SQLException {
        PreparedStatement ppsm = this.database.preparedQuery("SELECT `year`, `year_num` FROM `sheetreceipt` WHERE sheet_receipt_id = ? LIMIT 1");
        ppsm.setInt(1,sheetID);

        ResultSet rsData = ppsm.executeQuery();
        rsData.next();

        JSONObject object = new JSONObject();
        object.put("year",rsData.getString("year"));
        object.put("year_num",rsData.getString("year_num"));
        object.put("sheet_receipt_id",sheetID);

        JSONObject sendData = new JSONObject();
        sendData.put("type", ClientEvents.NOTIFICATE.toString());
    }

    public void sendList() throws SQLException, JMSException {
        PreparedStatement ppsm = this.database.preparedQuery("SELECT `sheet_receipt_id` , `year`, `year_num`, `date` " +
                "FROM `sheetreceipt` ORDER BY `sheet_receipt_id` DESC ");
        ppsm.execute();

        ResultSet rs = ppsm.getResultSet();

        JSONArray array = new JSONArray();

        while (rs.next()){
            JSONObject object = new JSONObject();
            object.put("sheet_receipt_id", rs.getInt("sheet_receipt_id"));
            object.put("year",rs.getInt("year"));
            object.put("year_num",rs.getInt("year_num"));
            object.put("date",rs.getString("date"));
            array.put(object);
        }

        JSONObject sendObject = new JSONObject();
        sendObject.put("list",array);

        JSONObject sendData = new JSONObject();
        sendData.put("type",ClientEvents.RECEIVE_SHEET_BILL_LIST.toString());
        sendData.put("data",sendObject);

        this.messager.send(sendData.toString(),this.sessionID);
    }
}
