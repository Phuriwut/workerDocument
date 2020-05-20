package Server.dataextracter;

import org.json.JSONObject;

import java.util.UUID;

public class RegisterExtracter extends  Extracter{
    private String firstname, lastname;
    private String email;
    private String password;
    private int age;
    private int career ,income;
    private String bank_id ;
    private int bank_name;

    //constructor
    public RegisterExtracter(String firstname, String lastname, String email, String password, int age, int career, int income, String bank_id, int bank_name) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password  = password;
        this.age = age;
        this.career = career;
        this.income = income;
        this.bank_id = bank_id;
        this.bank_name = bank_name;
    }

    public RegisterExtracter() {
    }

    //getter setter
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCareer() {
        return career;
    }

    public void setCareer(int career) {
        this.career = career;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public int getBank_name() {
        return bank_name;
    }

    public void setBank_name(int bank_name) {
        this.bank_name = bank_name;
    }



    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("firstname", firstname);
        obj.put("lastname",lastname);
        obj.put("email", email);
        obj.put("password", password);
        obj.put("age",age);
        obj.put("career", career);
        obj.put("income", income);
        obj.put("bank_id", bank_id);
        obj.put("bank_name", bank_name);
        obj.put("session_id", sessionID);
        return obj.toString();
    }
}

