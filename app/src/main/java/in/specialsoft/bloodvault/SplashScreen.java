package in.specialsoft.bloodvault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import in.specialsoft.bloodvault.DonorDetails.DonorDetails;
import io.paperdb.Paper;

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

    private void allowAccess(String useIdKey) {
        //check user Login

        //storeData

        //if exist Login
        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void storeData() {

        Paper.book().write(UserIDKey,"ID");

        Paper.book().write(DonorDetails.UserNameKey,"Name");
        Paper.book().write(DonorDetails.UserPhoneKey,"phone");
        Paper.book().write(DonorDetails.UserAddressKey,"Address");
        Paper.book().write(DonorDetails.UserCityKey,"city");
        Paper.book().write(DonorDetails.UserBloodGroupKey,"BloodGroup");

        Paper.book().write(DonorDetails.UserGenderKey,"1/0");
        Paper.book().write(DonorDetails.UserAvailableKey,"1/0");
    }

}