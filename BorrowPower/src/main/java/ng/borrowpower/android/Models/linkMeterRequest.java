package ng.borrowpower.android.Models;

public class linkMeterRequest {
    String phone,partnerMeta,provider,meterNumber;

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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public linkMeterRequest(String phone, String partnerMeta, String provider, String meterNumber) {
        this.phone = phone;
        this.partnerMeta = partnerMeta;
        this.provider = provider;
        this.meterNumber = meterNumber;
    }
}
