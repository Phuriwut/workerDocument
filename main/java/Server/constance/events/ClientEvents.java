package Server.constance.events;

public enum ClientEvents {
    LOGIN_RECEIVE("LOGIN_RECEIVE"), NOTIFICATE("NOTIFICATE"),
    RECEIVE_PROFILE("RECEIVE_PROFILE"), SHEET_RECEIVE("SHEET_RECEIVE")
    ;

    private String messageEvents;

    ClientEvents(String messageEvents) {
        this.messageEvents = messageEvents;
    }

    public String getString() {
        return messageEvents;
    }
}
