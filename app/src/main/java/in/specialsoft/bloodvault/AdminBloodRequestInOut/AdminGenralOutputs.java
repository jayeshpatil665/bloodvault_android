package in.specialsoft.bloodvault.AdminBloodRequestInOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminGenralOutputs {
    @SerializedName("output")
    @Expose
    private String output;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
