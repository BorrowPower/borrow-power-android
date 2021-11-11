package ng.borrowpower.android.Models;

public class getRechargeTokenRequest {
    String paymentReference,provider;

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public getRechargeTokenRequest(String paymentReference, String provider) {
        this.paymentReference = paymentReference;
        this.provider = provider;
    }
}
