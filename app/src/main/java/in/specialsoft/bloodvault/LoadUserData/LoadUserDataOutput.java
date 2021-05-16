package in.specialsoft.bloodvault.LoadUserData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import in.specialsoft.bloodvault.DonorDetails.Donor;

public class LoadUserDataOutput {
    @SerializedName("donor")
    @Expose
    private List<Donor> donor = null;

    public List<Donor> getDonor() {
        return donor;
    }

    public void setDonor(List<Donor> donor) {
        this.donor = donor;
    }
}
