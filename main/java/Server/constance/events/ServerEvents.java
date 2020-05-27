package Server.constance.events;

public enum ServerEvents {
    REGISTER("REGISTER"), LOGIN("LOGIN"), LOGOUT("LOGOUT"), SIGNUP("SIGNUP") ,
    SHEET("SHEET"),ORDERTODB("ORDERTODB"), ORDER("ORDER");

    private String messageEvent;

    ServerEvents(String messageEvent) {
        this.messageEvent = messageEvent;
    }

    public String getString(){
        return this.messageEvent;
    }
}
