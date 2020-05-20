package Server.dataextracter;

import java.util.UUID;

public class Extracter {
    UUID sessionID;

    Extracter(){
        super();
    }

    public UUID getSessionID() {
        return sessionID;
    }

    public void setSessionID(UUID sessionID) {
        this.sessionID = sessionID;
    }
}
