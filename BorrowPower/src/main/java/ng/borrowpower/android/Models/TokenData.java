package ng.borrowpower.android.Models;

public class TokenData {
    String stdToken;
    String units;
    String unitsType;

    public String getStdToken() {
        return stdToken;
    }

    public void setStdToken(String stdToken) {
        this.stdToken = stdToken;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getUnitsType() {
        return unitsType;
    }

    public void setUnitsType(String unitsType) {
        this.unitsType = unitsType;
    }

    public TokenData(String stdToken, String units, String unitsType) {
        this.stdToken = stdToken;
        this.units = units;
        this.unitsType = unitsType;
    }
}
