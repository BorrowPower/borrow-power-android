package ng.borrowpower.android.Models;

public class CustomerData {
    boolean customerExists,hasMeter;
    String meterNumber;

    public boolean isCustomerExists() {
        return customerExists;
    }

    public void setCustomerExists(boolean customerExists) {
        this.customerExists = customerExists;
    }

    public boolean isHasMeter() {
        return hasMeter;
    }

    public void setHasMeter(boolean hasMeter) {
        this.hasMeter = hasMeter;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public CustomerData(boolean customerExists, boolean hasMeter, String meterNumber) {
        this.customerExists = customerExists;
        this.hasMeter = hasMeter;
        this.meterNumber = meterNumber;
    }
}
