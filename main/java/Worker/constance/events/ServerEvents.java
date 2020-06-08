package Worker.constance.events;

public enum ServerEvents {
    REGISTER("REGISTER"), ORDERTODB("ORDERTODB")
    ,SHEETTODB("SHEETTODB"),GET_USER_LIST("GET_USER_LIST")
    ,GET_SHEET_LIST("GET_SHEET_LIST"),GET_ORDER_LIST("GET_ORDER_LIST")
    ,DELETE_ORDER_LIST("DELETE_ORDER_LIST"),EDIT_ORDER_LIST("EDIT_ORDER_LIST")
    ,EDIT_SHEET_LIST("EDIT_SHEET_LIST"),GET_SHEET_ALL_DATA("GET_SHEET_ALL_DATA")
    ,GET_SHEET_BILL_LIST("GET_SHEET_BILL_LIST")
    ,GET_EDIT_REGISTER("GET_EDIT_REGISTER");

    private String messageEvent;

    ServerEvents(String messageEvent) {
        this.messageEvent = messageEvent;
    }

    public String getString(){
        return this.messageEvent;
    }
}
