package in.specialsoft.bloodvault.DonorByGroupInOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonorByGroupInput {
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
