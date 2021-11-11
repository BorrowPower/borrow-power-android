package ng.borrowpower.android.Models;

import java.util.List;

public class ResponseData {
    List<String> data;
    public List<String> getData() {
        return data;
    }
    public void setData(List<String> data) {
        this.data = data;
    }
    public ResponseData(List<String> data) {
        this.data = data;
    }

}
