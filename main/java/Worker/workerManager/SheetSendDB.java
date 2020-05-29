package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.database.Sheet;
import Worker.message.Messager;
import org.json.JSONObject;

import javax.jms.JMSException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SheetSendDB extends Worker implements Runnable{
    int num = 0;

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
        LocalDate ld =LocalDate.now();
        int year_now = ld.getYear() +543;
        System.out.println("Q" + year_now + "/" + getNumberString());

        String QNum = ("Q" + year_now + "/" + getNumberString());

        PreparedStatement ppsm = database.preparedQuery("INSERT INTO `sheet`(`QNum`, `userID`, `date`, `enddate`, `day`, `salesman`) VALUES (?,?,?,?,?,?)");
        ppsm.setString(1,this.data.getString(QNum));
        ppsm.setString(2,this.data.getString("userID"));
        ppsm.setString(3,this.data.getString("date"));
        ppsm.setString(4,this.data.getString("enddate"));
        ppsm.setInt(5,this.data.getInt("dat"));
        ppsm.setString(6,this.data.getString("Salesman"));
        ppsm.execute();

        JSONObject userGetQNum = new JSONObject();
        userGetQNum.put("QNum",QNum);

        this.messager.send(userGetQNum.toString(),sessionID);
    }

    public String getNumberString(){
        this.num++;
        return String.format("%06d",num);
    }
}
