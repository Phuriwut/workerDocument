package Worker.constance.events;

public enum ServerEvents {
    REGISTER("REGISTER"), LOGIN("LOGIN"), LOGOUT("LOGOUT"), SIGNUP("SIGNUP") ,
    SHEETTODB("SHEETTODB"),SHEET("SHEET");

    private String messageEvent;

    ServerEvents(String messageEvent) {
        this.messageEvent = messageEvent;
    }

    public String getString(){
        return this.messageEvent;
    }
}
