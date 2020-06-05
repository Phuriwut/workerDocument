package Worker.workerManager;

import Worker.constance.events.ServerEvents;
import Worker.message.Messager;
import com.google.gson.JsonParser;
import org.json.JSONObject;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.ArrayList;

public class workerManager {
    static ArrayList<Thread> workers = new ArrayList<Thread>();
    Messager messager;
    private static int WORKER_NUMBER = 10;
    private static int no = 0;

    public workerManager(){
        try {
            messager = new Messager();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    void updateQueue(){
        JsonParser jsonParser = new JsonParser();
        while(true){
            try {
                this.clearThread();
                if(this.workers.size() >= 10) continue;
                Message message = this.messager.recieve();
                TextMessage textMessage = (TextMessage) message;
                String newMessage = textMessage.getText();
                System.out.println(newMessage);

                String sessionID = message.getStringProperty("session_id");
                JSONObject objectFromString = new JSONObject(newMessage);

                String type = objectFromString.getString("type");
                JSONObject data = objectFromString.getJSONObject("data");
                if(type.equals(ServerEvents.REGISTER.getString())){
                    Thread th = new Thread(new RegisterWorker(data, this.messager, sessionID));
                    workers.add(th);
                    System.out.println("REGISTER GO TO DB :: "+this.workers.size());
                    th.start();
                }else if (type.equals(ServerEvents.SHEETTODB.getString())){
                    Thread th = new Thread(new SheetSendDB(data,this.messager, sessionID));
                    workers.add(th);
                    System.out.println("SHEET_TO_DB :: "+this.workers.size());
                    th.start();
                }else if(type.equals(ServerEvents.ORDERTODB.getString())){
                    Thread th = new Thread(new OrderListSendDB(data, this.messager, sessionID));
                    workers.add(th);
                    System.out.println("ORDERLIST_TO_DB :: " + this.workers.size());
                    th.start();
                }else if(type.equals(ServerEvents.GET_USER_LIST.getString())){
                    Thread th = new Thread(new UserListWorker(data,this.messager,sessionID));
                    workers.add(th);
                    th.start();
                    System.out.println("==> userlistWorker");
                }else if(type.equals(ServerEvents.GET_SHEET_LIST.getString())){
                    Thread th = new Thread(new SheetListWorker(data,this.messager,sessionID));
                    workers.add(th);
                    th.start();
                    System.out.println("==> sheetListWorker");
                }else if (type.equals(ServerEvents.GET_ORDER_LIST.getString())){
                    Thread th = new Thread(new OrderListWorker(data,this.messager,sessionID));
                    workers.add(th);
                    th.start();
                    System.out.println("==> orderListWorker");
                }else if (type.equals(ServerEvents.EDIT_SHEET_LIST.getString())){
                    Thread th =new Thread(new SheetUpdateWorker(data,this.messager,sessionID));
                    workers.add(th);
                    th.start();
                    System.out.println("==> sheetUpdateWorker");
                }else if (type.equals(ServerEvents.DELETE_ORDER_LIST.getString())){
                    Thread th = new Thread(new OrderListDeleteWorker(data,this.messager,sessionID));
                    workers.add(th);
                    th.start();
                    System.out.println("==> orderListDeletedWorker");
                }else if (type.equals(ServerEvents.EDIT_ORDER_LIST.getString())){
                    Thread th = new Thread(new OrderListUpdateWorker(data,this.messager,sessionID));
                    workers.add(th);
                    th.start();
                    System.out.println("==> orderListUpdateWorker");
                }else if (type.equals(ServerEvents.GET_SHEET_ALL_DATA.getString())){
                    Thread th = new Thread(new DataWorker(data,this.messager,sessionID));
                    workers.add(th);
                    th.start();
                    System.out.println("==> sendALLDataWorker");
                }else if (type.equals(ServerEvents.GET_SHEET_BILL_LIST.getString())){
                    Thread th = new Thread(new SheetBillSendDB(data,this.messager,sessionID));
                    workers.add(th);
                    th.start();
                    System.out.println("==> sheet_Bill_TO_DB" + workers.size());
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    void clearThread(){
        for (int i = 0; i < this.workers.size(); ) {
            if(workers.get(i).isAlive()) {
                i++;
                continue ;
            }
            workers.remove(i);
        }
    }

    public void start(){
        this.updateQueue();
        for (Thread th: this.workers) {
            th.start();
        }
        while (true){
            try {
                Messager receiver = new Messager();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
