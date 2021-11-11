package ng.borrowpower.android.Models;

public class VerifyCustomerResponse {
    boolean status;
    String msg;
    CustomerData data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CustomerData getData() {
        return data;
    }

    public void setData(CustomerData data) {
        this.data = data;
    }

    public VerifyCustomerResponse(boolean status, String msg, CustomerData data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
