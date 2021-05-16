package in.specialsoft.bloodvault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.specialsoft.bloodvault.Api.ApiClient;
import in.specialsoft.bloodvault.Api.ApiInterface;
import in.specialsoft.bloodvault.DonorDetails.DonorDetails;
import in.specialsoft.bloodvault.LoginInOut.LoginInput;
import in.specialsoft.bloodvault.LoginInOut.LoginOutput;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginPhone,etLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);

        etLoginPhone = findViewById(R.id.etLoginPhone);
        etLoginPassword = findViewById(R.id.etLoginPassword);
    }

    public void onLogin(View view) {
        String phone,pass;
        phone = etLoginPhone.getText().toString().trim();
        pass = etLoginPassword.getText().toString().trim();
        if (!phone.equals("") && !pass.equals(""))
        {
            if (isValidMob(phone)){
                toLoginUser(phone,pass);
            }
            else {
                Toast.makeText(this, "Please check your mobile !", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "All fields are required !", Toast.LENGTH_SHORT).show();
        }
    }

    //to Register Avtivity
    public void toRegister(View view) {
        Intent regIntent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(regIntent);
    }

    //Validate Mobile Number
    public static boolean isValidMob(String s)
    {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    //Login API Call
    private void toLoginUser(String phone, String pass) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        LoginInput i = new LoginInput();
        i.setPhone(phone);
        i.setPassword(pass);

        api.getLogin(i).enqueue(new Callback<LoginOutput>() {
            @Override
            public void onResponse(Call<LoginOutput> call, Response<LoginOutput> response) {
                if (response.body().getOutput()!=null){
                    Toast.makeText(LoginActivity.this, "Invalid Credentials !!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String id = response.body().getId();
                    Toast.makeText(LoginActivity.this, "Login Success ! "+id, Toast.LENGTH_SHORT).show();
                    Paper.book().write(DonorDetails.UserIDKey,id);
                    //after user authintication
                    Intent homeIntent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(homeIntent);
                }
            }

            @Override
            public void onFailure(Call<LoginOutput> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error in API CALL !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}