package ng.borrowpower.android.Models;

public class DisburseLoanRequest {
    String phone,amount,partnerMeta,otp_pin;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPartnerMeta() {
        return partnerMeta;
    }

    public void setPartnerMeta(String partnerMeta) {
        this.partnerMeta = partnerMeta;
    }

    public String getOtp_pin() {
        return otp_pin;
    }

    public void setOtp_pin(String otp_pin) {
        this.otp_pin = otp_pin;
    }

    public DisburseLoanRequest(String phone, String amount, String partnerMeta, String otp_pin) {
        this.phone = phone;
        this.amount = amount;
        this.partnerMeta = partnerMeta;
        this.otp_pin = otp_pin;
    }

}
