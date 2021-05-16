package in.specialsoft.bloodvault.RegisterInOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterOutput {
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
