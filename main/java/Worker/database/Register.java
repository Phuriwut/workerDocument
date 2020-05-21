package Worker.database;

import org.json.JSONObject;

public class Register {
    private String number;
    private String userID,taxID,FirstnameContract,LastnameContract;
    private String numberPhone,nameConsumer,address;
    private String session_id;

    public Register(String number, String userID, String taxID, String firstnameContract, String lastnameContract, String numberPhone, String nameConsumer, String address, String session_id) {
        this.number = number;
        this.userID = userID;
        this.taxID = taxID;
        this.FirstnameContract = firstnameContract;
        this.LastnameContract = lastnameContract;
        this.numberPhone = numberPhone;
        this.nameConsumer = nameConsumer;
        this.address = address;
        this.session_id = session_id;
    }

    public String  getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public String getFirstnameContract() {
        return FirstnameContract;
    }

    public void setFirstnameContract(String firstnameContract) {
        FirstnameContract = firstnameContract;
    }

    public String getLastnameContract() {
        return LastnameContract;
    }

    public void setLastnameContract(String lastnameContract) {
        LastnameContract = lastnameContract;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getNameConsumer() {
        return nameConsumer;
    }

    public void setNameConsumer(String nameConsumer) {
        this.nameConsumer = nameConsumer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("userID",userID);
        obj.put("taxID",taxID);
        obj.put("FirstnameContract", FirstnameContract);
        obj.put("LastnameContract", LastnameContract);
        obj.put("numberPhone",numberPhone);
        obj.put("nameConsumer",nameConsumer);
        obj.put("address",address);
        return obj.toString();
    }
}
