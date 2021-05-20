package in.specialsoft.bloodvault.AdminBloodRequestInOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminBloodRequestIn {
    @SerializedName("table")
    @Expose
    private String table;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
