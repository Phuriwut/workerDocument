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
    private String note, condi;

    public OrderList(String QNum, int seq, String list, int numList, int unitPrice, int price,
                     int license, int customization, int maintenance, int miscellneous,
                     String session_id, String note, String condi) {
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
        this.note = note;
        this.condi= condi;
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

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public String getCondi() { return condi; }

    public void setCondi(String condi) { this.condi = condi; }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("QNum",QNum); //Q2563/00001
        obj.put("seq",seq);  //ลำดับ
        obj.put("list",list); //รายการ
        obj.put("numlist",numList); //จำนวนชิ้น
        obj.put("unitPrice",unitPrice); //ราคาต่อหน่อย
        obj.put("price",price); //ราคาทั้งหมดของชิ้นนั้น (numlist * unitprice)
        obj.put("License",License); //เป็น license ?
        obj.put("Customization",Customization); //เป็น cumtom ?
        obj.put("Maintenance",Maintenance); // เป็น MA ?
        obj.put("Miscellneous",Miscellneous); // เป็น Miscell ?
        obj.put("note",note);  //หมายเหตุ
        obj.put("condi",condi); //เงื่อนไข
        obj.put("session_id",session_id);

        return obj.toString();
    }
}
