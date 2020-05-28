package Server.dataextracter;

import org.json.JSONObject;

public class RegisterExtracter extends Extracter{
    private String userID,taxID,FirstnameContract,LastnameContract;
    private String numberPhone,nameConsumer,address;

    public RegisterExtracter(String userID, String taxID, String FirstnameContract, String LastnameContract, String numberPhone, String nameConsumer, String address) {
        this.userID = userID;
        this.taxID = taxID;
        this.FirstnameContract = FirstnameContract;
        this.LastnameContract = LastnameContract;
        this.numberPhone = numberPhone;
        this.nameConsumer = nameConsumer;
        this.address = address;
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

    public String getTaxID() { return taxID; }

    public void setTaxID(String taxID) { this.taxID = taxID; }

    public String getFirstnameContract() { return FirstnameContract; }

    public void setFirstnameContract(String firstnameContract) { FirstnameContract = firstnameContract; }

    public String getLastnameContract() {
        return LastnameContract;
    }

    public void setLastnameContract(String lastnameContract) {
        LastnameContract = lastnameContract;
    }

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
        obj.put("FirstnameContract",FirstnameContract);
        obj.put("LastnameContract",LastnameContract);
        obj.put("numberPhone",numberPhone);
        obj.put("nameConsumer",nameConsumer);
        obj.put("address",address);
        obj.put("session_id",sessionID);
        return obj.toString();
    }
}
