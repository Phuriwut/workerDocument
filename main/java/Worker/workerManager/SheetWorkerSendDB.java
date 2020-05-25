package Worker.workerManager;

import Worker.constance.events.ClientEvents;
import Worker.database.Sheet;
import Worker.message.Messager;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SheetWorkerSendDB extends Worker<Sheet> implements Runnable{
    int num = 0;

    public SheetWorkerSendDB(String message, Messager messanger) {
        super(Sheet.class,message,messanger);
    }

    @Override
    public void run() {
        sendDB();
    }

    public void sendDB(){
        try {
            successSheet();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void successSheet() throws SQLException {
        LocalDate ld =LocalDate.now();
        int year_now = ld.getYear() +543;
        System.out.println("Q" + year_now + "/" + getNumberString());

        data.setQNum("Q" + year_now + "/" + getNumberString());

        PreparedStatement ppsm = database.preparedQuery("INSERT INTO `sheet`(`QNum`, `userID`, `date`, `enddate`, `day`, `salesman`) VALUES (?,?,?,?,?,?)");
        ppsm.setString(1,this.data.getQNum());
        ppsm.setString(2,this.data.getUserID());
        ppsm.setString(3,this.data.getDate());
        ppsm.setString(4,this.data.getEnddate());
        ppsm.setInt(5,this.data.getDay());
        ppsm.setString(6,this.data.getSalesman());
        ppsm.execute();
    }

    public String getNumberString(){
        this.num = num++;
        return String.format("%06d",num);
    }
}
