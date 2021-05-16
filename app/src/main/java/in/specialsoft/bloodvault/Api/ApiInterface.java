package in.specialsoft.bloodvault.Api;

import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodGroupCountInput;
import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodGroupCountOutput;
import in.specialsoft.bloodvault.DonorByGroupInOut.DonorByGroupInput;
import in.specialsoft.bloodvault.DonorByGroupInOut.DonorByGroupOutput;
import in.specialsoft.bloodvault.DonorDetails.Donor;
import in.specialsoft.bloodvault.LoadUserData.LoadUserDataInput;
import in.specialsoft.bloodvault.LoadUserData.LoadUserDataOutput;
import in.specialsoft.bloodvault.LoginInOut.LoginInput;
import in.specialsoft.bloodvault.LoginInOut.LoginOutput;
import in.specialsoft.bloodvault.RegisterInOut.RegisterInput;
import in.specialsoft.bloodvault.RegisterInOut.RegisterOutput;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    //Login API
    @POST("donor/login.php")
    Call<LoginOutput> getLogin(@Body LoginInput i);

    //Register API
    @POST("donor/signup.php")
    Call<RegisterOutput> getRegister(@Body RegisterInput i);

    //check - Load User Data API
    @POST("donor/checkUser.php")
    Call<LoadUserDataOutput> getuserData(@Body LoadUserDataInput i);

    //Donor By Grooup API
    @POST("donor/bloodGroupCategory.php")
    Call<DonorByGroupOutput> getDonorByGroup(@Body DonorByGroupInput i);

    //User Data Update API
    @POST("donor/donorDataUpdate.php")
    Call<RegisterOutput> getuserDataUpdate(@Body Donor i);

    //Blood Group Count API
    @POST("donor/donorDataUpdate.php")
    Call<BloodGroupCountOutput> getBloodGroupCount(@Body BloodGroupCountInput i);
}
