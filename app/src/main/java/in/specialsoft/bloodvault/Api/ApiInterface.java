package in.specialsoft.bloodvault.Api;

import in.specialsoft.bloodvault.LoginInOut.LoginInput;
import in.specialsoft.bloodvault.LoginInOut.LoginOutput;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    //product list
    @POST("donor/login.php")
    Call<LoginOutput> getLogin(@Body LoginInput i);

}
