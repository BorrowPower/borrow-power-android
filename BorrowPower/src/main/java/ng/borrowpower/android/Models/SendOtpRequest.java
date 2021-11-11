package ng.borrowpower.android.Models;

public class SendOtpRequest {
    String partnerMeta;
    String phone;

    public String getPartnerMeta() {
        return partnerMeta;
    }

    public void setPartnerMeta(String partnerMeta) {
        this.partnerMeta = partnerMeta;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTransaction_pin() {
        return transaction_pin;
    }

    public void setTransaction_pin(String transaction_pin) {
        this.transaction_pin = transaction_pin;
    }

    public SendOtpRequest(String partnerMeta, String phone, String transaction_pin) {
        this.partnerMeta = partnerMeta;
        this.phone = phone;
        this.transaction_pin = transaction_pin;
    }

    String transaction_pin;
}
