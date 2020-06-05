package Worker.constance.events;

public enum ClientEvents {
    NOTIFICATE("NOTIFICATE"),
    SHEET_RECEIVE("SHEET_RECEIVE"),
    RECEIVE_USER_LIST("RECEIVE_USER_LIST"),
    RECEIVE_SHEET_LIST("RECEIVE_SHEET_LIST"),
    RECEIVE_ORDER_LIST("RECEIVE_ORDER_LIST"),
    RECEIVE_SHEET_ALL_DATA("RECEIVE_SHEET_ALL_DATA");

    private String messageEvents;

    ClientEvents(String messageEvents) {
        this.messageEvents = messageEvents;
    }

    public String getString() {
        return messageEvents;
    }
}
