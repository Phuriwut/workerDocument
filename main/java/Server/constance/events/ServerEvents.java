package Server.constance.events;

public enum ServerEvents {
    REGISTER("REGISTER"), LOGIN("LOGIN"), LOGOUT("LOGOUT") ;

    private String messageEvent;

    ServerEvents(String messageEvent) {
        this.messageEvent = messageEvent;
    }

    public String getString(){
        return this.messageEvent;
    }
}
