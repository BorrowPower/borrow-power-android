package ng.borrowpower.android.Models;

public class DisburseLoanResponse {
    boolean status;
    String msg;
    LoanData data;


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

    public LoanData getData() {
        return data;
    }

    public void setData(LoanData data) {
        this.data = data;
    }

    public DisburseLoanResponse(boolean status, String msg, LoanData data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

}
