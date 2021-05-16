package in.specialsoft.bloodvault.LoginInOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginOutput {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("output")
    @Expose
    private String output;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
