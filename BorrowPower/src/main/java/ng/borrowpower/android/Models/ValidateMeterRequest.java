package ng.borrowpower.android.Models;

public class ValidateMeterRequest {
    String meterNumber,phone,partnerMeta,amount,provider;

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPartnerMeta() {
        return partnerMeta;
    }

    public void setPartnerMeta(String partnerMeta) {
        this.partnerMeta = partnerMeta;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public ValidateMeterRequest(String meterNumber, String phone, String partnerMeta, String amount, String provider) {
        this.meterNumber = meterNumber;
        this.phone = phone;
        this.partnerMeta = partnerMeta;
        this.amount = amount;
        this.provider = provider;
    }
}
