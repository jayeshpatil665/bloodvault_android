package in.specialsoft.bloodvault.AdminBloodRequestInOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminBloodRequestOut {
    @SerializedName("bloodrequest")
    @Expose
    private List<Bloodrequest> bloodrequest = null;

    public List<Bloodrequest> getBloodrequest() {
        return bloodrequest;
    }

    public void setBloodrequest(List<Bloodrequest> bloodrequest) {
        this.bloodrequest = bloodrequest;
    }
}
