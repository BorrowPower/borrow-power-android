package ng.borrowpower.android.Models;

public class linkMeterResponse {
    String msg;
    boolean status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public linkMeterResponse(String msg, boolean status) {
        this.msg = msg;
        this.status = status;
    }
}
