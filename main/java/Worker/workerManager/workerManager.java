package Worker.workerManager;

import Worker.constance.events.ServerEvents;
import Worker.message.Messager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.jms.JMSException;
import java.util.ArrayList;

public class workerManager {
    static ArrayList<Thread> workers = new ArrayList<Thread>();
    Messager messager;
    private static int WORKER_NUMBER = 5;
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
                if(this.workers.size() >= 5) continue;
                String newMessage = this.messager.recieve();
                JsonObject objectFromString = jsonParser.parse(newMessage).getAsJsonObject();
                String type = objectFromString.get("type").getAsString();
                String data = objectFromString.get("data").getAsString();
                if(type.equals(ServerEvents.REGISTER.getString())){
                    Thread th = new Thread(new RegisterWorker(data, this.messager));
                    workers.add(th);
                    System.out.println(this.workers.size());
                    th.start();
                }else if (type.equals(ServerEvents.SHEETTODB.getString())){
                    Thread th = new Thread(new SheetSendDB(data,this.messager));
                    workers.add(th);
                    System.out.println(this.workers.size());
                    th.start();
               }else if(type.equals(ServerEvents.SHEET.getString())){
                    Thread th = new Thread(new SheetWorker(data,this.messager));
                    workers.add(th);
                    System.out.println(this.workers.size());
                    th.start();
                }else if (type.equals(ServerEvents.ORDERTODB.getString())){
                    Thread th = new Thread(new OrderListSendDB(data,this.messager));
                    workers.add(th);
                    System.out.println(this.workers.size());
                    th.start();
                }
            } catch (JMSException e) {
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
