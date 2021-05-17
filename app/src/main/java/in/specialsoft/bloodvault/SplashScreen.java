package in.specialsoft.bloodvault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import in.specialsoft.bloodvault.Api.ApiClient;
import in.specialsoft.bloodvault.Api.ApiInterface;
import in.specialsoft.bloodvault.DonorDetails.Donor;
import in.specialsoft.bloodvault.DonorDetails.DonorDetails;
import in.specialsoft.bloodvault.LoadUserData.LoadUserDataInput;
import in.specialsoft.bloodvault.LoadUserData.LoadUserDataOutput;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.specialsoft.bloodvault.DonorDetails.DonorDetails.UserIDKey;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);

        if (isNetworkConnected())
        {
            String useIdKey = Paper.book().read(UserIDKey);
            if(useIdKey!=null)
            {
                allowAccess(useIdKey);
            }
            else
            {
                newUser();
            }
        }
        else
        {
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
            Intent networkError = new Intent(SplashScreen.this,NetworkErrorActivity.class);
            startActivity(networkError);
            finish();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void newUser() {
        //to Login/ Registration Page
        Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void allowAccess(String useIdKey) {
        //check user Login
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        LoadUserDataInput i = new LoadUserDataInput();
        i.setId(useIdKey);

        api.getuserData(i).enqueue(new Callback<LoadUserDataOutput>() {
            @Override
            public void onResponse(Call<LoadUserDataOutput> call, Response<LoadUserDataOutput> response) {
                Toast.makeText(SplashScreen.this, "Welcome "+response.body().getDonor().get(0).getName(), Toast.LENGTH_SHORT).show();
                //storeData
                storeData(response.body().getDonor().get(0));
                //if exist Login
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<LoadUserDataOutput> call, Throwable t) {
                Toast.makeText(SplashScreen.this, "Error in API CALL !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeData(Donor donor) {
       // Paper.book().write(UserIDKey,"ID");

        Paper.book().write(DonorDetails.UserNameKey,donor.getName());
        Paper.book().write(DonorDetails.UserPhoneKey,donor.getPhone());
        Paper.book().write(DonorDetails.UserAddressKey,donor.getAddress());
        Paper.book().write(DonorDetails.UserCityKey,donor.getCity());
        Paper.book().write(DonorDetails.UserBloodGroupKey,donor.getBloodGroup());

        Paper.book().write(DonorDetails.UserGenderKey,donor.getGender());
        Paper.book().write(DonorDetails.UserAvailableKey,donor.getAvailable());
    }

}