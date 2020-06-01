package Worker.constance.events;

public enum ClientEvents {
    LOGIN_RECEIVE("LOGIN_RECEIVE"), NOTIFICATE("NOTIFICATE"),
    RECEIVE_PROFILE("RECEIVE_PROFILE"), SHEET_RECEIVE("SHEET_RECEIVE")
    ,ORDER_RECEIVE("ORDER_RECEIVE"),RECEIVE_USER_LIST("RECEIVE_USER_LIST");

    private String messageEvents;

    ClientEvents(String messageEvents) {
        this.messageEvents = messageEvents;
    }

    public String getString() {
        return messageEvents;
    }
}
