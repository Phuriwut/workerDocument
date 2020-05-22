package Worker.database;

import org.json.JSONObject;

public class Sheet {
    private String QNum;
    private String userID;
    private String date;
    private String enddate;
    private int day;
    private String salesman;
    private String session_id;

    public Sheet(String QNum, String userID, String date, String enddate, int day, String salesman) {
        this.QNum = QNum;
        this.userID = userID;
        this.date = date;
        this.enddate = enddate;
        this.day = day;
        this.salesman = salesman;
    }

    public String getQNum() { return QNum; }

    public void setQNum(String QNum) { this.QNum = QNum; }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getEnddate() { return enddate; }

    public void setEnddate(String enddate) { this.enddate = enddate; }

    public int getDay() { return day; }

    public void setDay(int day) { this.day = day; }

    public String getSalesman() { return salesman; }

    public void setSalesman(String salesman) { this.salesman = salesman; }

    public String getSession_id() { return session_id; }

    public void setSession_id(String session_id) { this.session_id = session_id; }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("QNum",QNum);
        obj.put("userID",userID);
        obj.put("date",date);
        obj.put("enddate",enddate);
        obj.put("day",day);
        obj.put("salesname",salesman);
        obj.put("session_id",session_id);

        return obj.toString();
    }

}
