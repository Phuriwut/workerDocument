package Worker.workerManager;

import Worker.database.DB;
import Worker.database.DBInstance;
import Worker.message.Messager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.json.JSONObject;

import java.sql.SQLException;

public class Worker {
    String message;
    DBInstance database;
    Messager messager;
    JSONObject data;
    String sessionID;

    Worker(JSONObject data, Messager messanger, String sessionID){
        this.messager = messanger;
        this.data = data;
        this.sessionID = sessionID;
        try {
            this.database = DB.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
