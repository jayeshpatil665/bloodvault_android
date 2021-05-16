package in.specialsoft.bloodvault.LoginInOut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginInput {
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
