package Worker.database;

import org.json.JSONObject;

public class OrderList {
    private String QNum;
    private int seq;
    private String list;
    private int numList;
    private int unitPrice;
    private int price;
    private int License;
    private int Customization;
    private int Maintenance;
    private int Miscellneous;
    private String session_id;

    public OrderList(String QNum, int seq, String list, int numList, int unitPrice, int price, int license, int customization, int maintenance, int miscellneous, String session_id) {
        this.QNum = QNum;
        this.seq = seq;
        this.list = list;
        this.numList = numList;
        this.unitPrice = unitPrice;
        this.price = price;
        this.License = license;
        this.Customization = customization;
        this.Maintenance = maintenance;
        this.Miscellneous = miscellneous;
        this.session_id = session_id;
    }

    public String getQNum() { return QNum; }

    public void setQNum(String QNum) { this.QNum = QNum; }

    public int getSeq() { return seq; }

    public void setSeq(int seq) { this.seq = seq; }

    public String getList() { return list; }

    public void setList(String list) { this.list = list; }

    public int getNumList() { return numList; }

    public void setNumList(int numList) { this.numList = numList; }

    public int getUnitPrice() { return unitPrice; }

    public void setUnitPrice(int unitPrice) { this.unitPrice = unitPrice; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public int getLicense() { return License; }

    public void setLicense(int license) { License = license; }

    public int getCustomization() { return Customization; }

    public void setCustomization(int customization) { Customization = customization; }

    public int getMaintenance() { return Maintenance; }

    public void setMaintenance(int maintenance) { Maintenance = maintenance; }

    public int getMiscellneous() { return Miscellneous; }

    public void setMiscellneous(int miscellneous) { Miscellneous = miscellneous; }

    public String getSession_id() { return session_id; }

    public void setSession_id(String session_id) { this.session_id = session_id; }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("QNum",QNum);
        obj.put("seq",seq);
        obj.put("list",list);
        obj.put("numlist",numList);
        obj.put("unitPrice",unitPrice);
        obj.put("price",price);
        obj.put("License",License);
        obj.put("Customization",Customization);
        obj.put("Maintenance",Maintenance);
        obj.put("Miscellneous",Miscellneous);
        obj.put("session_id",session_id);

        return obj.toString();
    }
}
