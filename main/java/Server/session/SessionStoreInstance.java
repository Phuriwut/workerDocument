package Server.session;

import Server.constance.events.ClientEvents;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class SessionStoreInstance {
    HashMap<String , SessionData> sessionmap = new HashMap<String, SessionData>();

    public void addSessionData(String uuid, SessionData sessiondata){
        this.sessionmap.put(uuid.toString(), sessiondata);
    }

    public SessionData getSessionData(String uuid){
        return this.sessionmap.get(uuid);
    }

    public void removeSessionData(String uuid){
        sessionmap.remove(uuid);
    }

    public void syncSession(){
        for (Map.Entry me : sessionmap.entrySet()) {
            SessionData ssd = (SessionData) me.getValue();
            System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue() + ssd.realtime());

            if(ssd.realtime())continue;
            if(ssd.isLogin()){
                JSONObject obj = new JSONObject();
                obj.put("status",1);
                obj.put("title","Session is expired");
                obj.put("detail","Login again");
                ssd.getClient().sendEvent(ClientEvents.NOTIFICATE.getString(),obj.toString());

                ssd.setLogin(false);
                ssd.getClient().sendEvent(ClientEvents.RECEIVE_PROFILE.getString(),ssd.toString());
            }
//            ssd.getClient().disconnect();
            removeSessionData((String) me.getKey());

        }

    }
}
