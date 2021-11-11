package ng.borrowpower.android.Models;

import java.util.List;

public class getUtilityServiceProviderResponse {
    boolean status;
    String msg;

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

    public List<Discos> getData() {
        return data;
    }

    public void setData(List<Discos> data) {
        this.data = data;
    }

    public getUtilityServiceProviderResponse(boolean status, String msg, List<Discos> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    List<Discos> data;
}
