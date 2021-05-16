package in.specialsoft.bloodvault.BloodGroupCountInOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BloodGroupCountOutput {
    @SerializedName("BloodCount")
    @Expose
    private List<BloodCount> bloodCount = null;

    public List<BloodCount> getBloodCount() {
        return bloodCount;
    }

    public void setBloodCount(List<BloodCount> bloodCount) {
        this.bloodCount = bloodCount;
    }
}
