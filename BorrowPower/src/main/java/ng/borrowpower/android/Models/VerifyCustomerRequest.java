package ng.borrowpower.android.Models;

public class VerifyCustomerRequest {
    String phone,partnerMeta;

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

    public VerifyCustomerRequest(String phone, String partnerMeta) {
        this.phone = phone;
        this.partnerMeta = partnerMeta;
    }
}
