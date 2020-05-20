package Worker.database;

import org.json.JSONObject;

public class Register {
    private String userID,taxID,nameContract;
    private String numberPhone,nameConsumer,address;
    private String session_id;

    public Register(String userID, String taxID, String nameContract
            , String numberPhone, String nameConsumer, String address
            , String session_id) {
        this.userID = userID;
        this.taxID = taxID;
        this.nameContract = nameContract;
        this.numberPhone = numberPhone;
        this.nameConsumer = nameConsumer;
        this.address = address;
        this.session_id = session_id;
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

    public String getTaxID() { return taxID; }

    public void setTaxID(String taxID) { this.taxID = taxID; }

    public String getNameContract() { return nameContract; }

    public void setNameContract(String nameContract) { this.nameContract = nameContract; }

    public String getNumberPhone() { return numberPhone; }

    public void setNumberPhone(String numberPhone) { this.numberPhone = numberPhone; }

    public String getNameConsumer() { return nameConsumer; }

    public void setNameConsumer(String nameConsumer) { this.nameConsumer = nameConsumer; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getSession_id() { return session_id; }

    public void setSession_id(String session_id) { this.session_id = session_id; }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("userID",userID);
        obj.put("taxID",taxID);
        obj.put("nameContract",nameContract);
        obj.put("numberPhone",numberPhone);
        obj.put("nameConsumer",nameConsumer);
        obj.put("address",address);
        return obj.toString();
    }
}
