package Worker.constance.events;

public enum ServerEvents {
    REGISTER("REGISTER"), ORDERTODB("ORDERTODB"), ORDER("ORDER"), SIGNUP("SIGNUP") ,
    SHEETTODB("SHEETTODB"),SHEET("SHEET"),GET_USER_LIST("GET_USER_LIST");

    private String messageEvent;

    ServerEvents(String messageEvent) {
        this.messageEvent = messageEvent;
    }

    public String getString(){
        return this.messageEvent;
    }
}
