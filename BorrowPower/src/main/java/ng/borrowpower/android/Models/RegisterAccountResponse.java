package ng.borrowpower.android.Models;

import java.util.List;

public class RegisterAccountResponse {
    String msg;
    boolean status;
    RegisterData data;

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

    public RegisterData getData() {
        return data;
    }

    public void setData(RegisterData data) {
        this.data = data;
    }

    public RegisterAccountResponse(String msg, boolean status, RegisterData data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }
}
