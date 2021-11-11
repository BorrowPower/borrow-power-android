package ng.borrowpower.android.Models;

public class getRechargeTokenResponse {


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

    public TokenData getData() {
        return data;
    }

    public void setData(TokenData data) {
        this.data = data;
    }

    String msg;
    boolean status;
    TokenData data;

    public getRechargeTokenResponse(String msg, boolean status, TokenData data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }
}
