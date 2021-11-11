package ng.borrowpower.android.Models;

import java.util.List;

public class LoanData {
    String paymentReference,meterNumber;

    public String getPaymentReference() {
        return paymentReference;
    }

    List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public LoanData(List<String> data) {
        this.data = data;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public LoanData(String paymentReference, String meterNumber) {
        this.paymentReference = paymentReference;
        this.meterNumber = meterNumber;
    }
}
