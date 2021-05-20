package in.specialsoft.bloodvault.Api;

import in.specialsoft.bloodvault.AdminBloodGroupUpdate.AdminBloodGroupUpdateIn;
import in.specialsoft.bloodvault.AdminBloodRequestInOut.AdminBloodRequestIn;
import in.specialsoft.bloodvault.AdminBloodRequestInOut.AdminBloodRequestOut;
import in.specialsoft.bloodvault.AdminBloodRequestInOut.AdminBloodRequestRemove;
import in.specialsoft.bloodvault.AdminBloodRequestInOut.AdminGenralOutputs;
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
    @POST("admin/showAvailableCount.php")
    Call<BloodGroupCountOutput> getBloodGroupCount(@Body BloodGroupCountInput i);

    //Admin - Show Blood requests API
    @POST("admin/showRequests.php")
    Call<AdminBloodRequestOut> getBloodRequests(@Body AdminBloodRequestIn i);

    //Admin - Make Blood requests API
    @POST("admin/makeRequest.php")
    Call<AdminGenralOutputs> makeBloodRequests(@Body AdminBloodRequestRemove i);

    //Admin - Delete Blood requests API
    @POST("admin/removeRequest.php")
    Call<AdminGenralOutputs> removeBloodRequests(@Body AdminBloodRequestRemove i);

    //Admin - Blood update API
    @POST("admin/removeBlood.php")
    Call<AdminGenralOutputs> getBloodUpdate(@Body AdminBloodGroupUpdateIn i);
}
