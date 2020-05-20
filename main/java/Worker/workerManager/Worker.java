package Worker.workerManager;

import Worker.database.DB;
import Worker.database.DBInstance;
import Worker.message.Messager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.sql.SQLException;

public class Worker<T> {
    String message;
    DBInstance database;
    Messager messager;
    T data;

    Worker(Class<T> type, String message, Messager messanger){
        this.messager = messanger;
        this.message = message;
        System.out.println(this.message);
        Gson gson = new Gson();
        try {
            this.data = (T) gson.fromJson(this.message, type);
        }catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        try {
            this.database = DB.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
