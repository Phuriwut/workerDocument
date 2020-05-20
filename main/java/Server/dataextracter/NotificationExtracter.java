package Server.dataextracter;

public class NotificationExtracter {
    private int status;
    private String title,detail;

    //constructor
    public NotificationExtracter(int status, String title, String detail) {
        this.status = status;
        this.title = title;
        this.detail = detail;
    }

    public NotificationExtracter() {
    }

    //getter setter
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "{" +
                "\"status\": " + status +
                ", \"title\": \"" + title + "\"" +
                ", \"detail\": \"" + detail + "\"" +
                "}";
    }
}


