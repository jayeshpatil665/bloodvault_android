package in.specialsoft.bloodvault.AdminBloodRequestInOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminBloodRequestRemove {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
