package ng.borrowpower.android.Models;

import java.util.List;

public class VerifyPartnerResponse {
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

    public VerifyPartnerResponse(String msg, boolean status) {
        this.msg = msg;
        this.status = status;
    }
}
