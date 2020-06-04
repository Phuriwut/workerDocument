package Worker.constance.events;

public enum ServerEvents {
    REGISTER("REGISTER"), ORDERTODB("ORDERTODB")
    ,SHEETTODB("SHEETTODB"),GET_USER_LIST("GET_USER_LIST")
    ,GET_SHEET_LIST("GET_SHEET_LIST"),GET_ORDER_LIST("GET_ORDER_LIST")
    ,DELETE_ORDER_LIST("DELETE_ORDER_LIST");

    private String messageEvent;

    ServerEvents(String messageEvent) {
        this.messageEvent = messageEvent;
    }

    public String getString(){
        return this.messageEvent;
    }
}
