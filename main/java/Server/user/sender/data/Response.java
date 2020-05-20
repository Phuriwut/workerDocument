package Server.user.sender.data;

public class Response{
    private String type;
    private String session_id;

    private String data;

    public Response(String type, String sessionID, String data) {
        this.type = type;
        this.session_id = sessionID;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSessionID() {
        return session_id;
    }

    public void setSessionID(String sessionID) {
        this.session_id = sessionID;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type='" + type + '\'' +
                ", sessionID='" + session_id + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}