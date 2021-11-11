package ng.borrowpower.android.Models;

public class VerifyPartnerRequest {
    String partnerMeta;

    public String getPartnerMeta() {
        return partnerMeta;
    }

    public void setPartnerMeta(String partnerMeta) {
        this.partnerMeta = partnerMeta;
    }

    public VerifyPartnerRequest(String partnerMeta) {
        this.partnerMeta = partnerMeta;
    }
}
