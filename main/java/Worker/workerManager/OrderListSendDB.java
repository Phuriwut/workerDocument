package Worker.workerManager;
//
//import Worker.database.OrderList;
//import Worker.message.Messager;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class OrderListSendDB extends Worker<OrderList> implements Runnable{
//
//    public OrderListSendDB(String message, Messager messanger) {
//        super(OrderList.class, message, messanger);
//    }
//
//    @Override
//    public void run() {
//        sendDB();
//    }
//
//    public void  sendDB(){
//        try {
//            successOrder();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    public void successOrder() throws SQLException {
//        PreparedStatement ppsm = database.preparedQuery("INSERT INTO `orderlist`(`QNum`, `seq`, `list`, `numList`, `unitPrice`, `price`, `License`, `Customization`, `Maintenance`, `Miscellneous`, `note`, `condi`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
//        ppsm.setString(1,this.data.getQNum());
//        ppsm.setInt(2,this.data.getSeq());
//        ppsm.setString(3,this.data.getList());
//        ppsm.setInt(4,this.data.getNumList());
//        ppsm.setInt(5,this.data.getUnitPrice());
//        ppsm.setInt(6,this.data.getPrice());
//        ppsm.setInt(7,this.data.getLicense());
//        ppsm.setInt(8,this.data.getCustomization());
//        ppsm.setInt(9,this.data.getMaintenance());
//        ppsm.setInt(10,this.data.getMiscellneous());
//        ppsm.setString(11,this.data.getNote());
//        ppsm.setString(12,this.data.getCondi());
//        ppsm.execute();
//    }
//}