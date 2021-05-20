package in.specialsoft.bloodvault.AdminBloodGroupUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminBloodGroupUpdateIn {
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("groupcount")
    @Expose
    private String groupcount;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getGroupcount() {
        return groupcount;
    }

    public void setGroupcount(String groupcount) {
        this.groupcount = groupcount;
    }

}
