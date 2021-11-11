package ng.borrowpower.android.Models;

public class RegisterAccountRequest {
    String transaction_pin;
    String phone;
    String first_name;
    String partner_meta;
    String last_name;

    public String getPartner_meta() {
        return partner_meta;
    }

    public void setPartner_meta(String partner_meta) {
        this.partner_meta = partner_meta;
    }

    public String getTransaction_pin() {
        return transaction_pin;
    }

    public void setTransaction_pin(String transaction_pin) {
        this.transaction_pin = transaction_pin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public RegisterAccountRequest(String transaction_pin, String phone, String first_name, String last_name, String partner_meta) {
        this.transaction_pin = transaction_pin;
        this.phone = phone;
        this.first_name = first_name;
        this.last_name = last_name;
        this.partner_meta = partner_meta;
    }

}
