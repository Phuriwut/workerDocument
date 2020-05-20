package Server.dataextracter;

import org.json.JSONObject;

public class RegisterExtracter extends Extracter{
    private String userID,taxID,nameContract;
    private String numberPhone,nameConsumer,address;

    public RegisterExtracter(String userID, String taxID, String nameContract, String numberPhone, String nameConsumer, String address) {
        this.userID = userID;
        this.taxID = taxID;
        this.nameContract = nameContract;
        this.numberPhone = numberPhone;
        this.nameConsumer = nameConsumer;
        this.address = address;
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

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("userID",userID);
        obj.put("taxID",taxID);
        obj.put("nameContract",nameContract);
        obj.put("numberPhone",numberPhone);
        obj.put("nameConsumer",nameConsumer);
        obj.put("address",address);
        obj.put("session_id",sessionID);
        return obj.toString();
    }
}
