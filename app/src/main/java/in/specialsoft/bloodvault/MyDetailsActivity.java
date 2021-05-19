package in.specialsoft.bloodvault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import in.specialsoft.bloodvault.Api.ApiClient;
import in.specialsoft.bloodvault.Api.ApiInterface;
import in.specialsoft.bloodvault.DonorDetails.Donor;
import in.specialsoft.bloodvault.DonorDetails.DonorDetails;
import in.specialsoft.bloodvault.RegisterInOut.RegisterOutput;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.specialsoft.bloodvault.LoginActivity.isValidMob;

public class MyDetailsActivity extends AppCompatActivity {

    Spinner myDetailsSpinner;
    String[] bloodGroups = {"Select","A-","A+","AB-","AB+","B-","B+","O-","O+"};

    EditText etDetailName,etDetailMobile,etDetailAddress,etDetailCity;
    CheckBox checkboxDetails;
    RadioButton rbMale,rbFemale;
    ProgressBar progressBar3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details);

        Paper.init(this);
        myDetailsSpinner = findViewById(R.id.myDetailsSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyDetailsActivity.this, android.R.layout.simple_list_item_1,bloodGroups);
        myDetailsSpinner.setAdapter(adapter);

        etDetailName = findViewById(R.id.etDetailName);
        etDetailMobile = findViewById(R.id.etDetailMobile);
        etDetailAddress = findViewById(R.id.etDetailAddress);
        etDetailCity = findViewById(R.id.etDetailCity);

        checkboxDetails = findViewById(R.id.checkboxDetails);

        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        progressBar3 = findViewById(R.id.progressBar3);
        init();

    }

    private void init() {
        String name,phone,address,city,bloodGroup,gender,available;

        name = Paper.book().read(DonorDetails.UserNameKey);
        phone = Paper.book().read(DonorDetails.UserPhoneKey);
        address = Paper.book().read(DonorDetails.UserAddressKey);
        city = Paper.book().read(DonorDetails.UserCityKey);
        bloodGroup = Paper.book().read(DonorDetails.UserBloodGroupKey);

        available = Paper.book().read(DonorDetails.UserAvailableKey);
        gender = Paper.book().read(DonorDetails.UserGenderKey);

        etDetailName.setText(name);
        etDetailAddress.setText(address);
        etDetailCity.setText(city);
        etDetailMobile.setText(phone);

        for (int i = 0;i<bloodGroups.length;i++){
            if (bloodGroups[i].equals(bloodGroup)){
                myDetailsSpinner.setSelection(i);
            }
        }

        if (available.equals("1")){
            checkboxDetails.setChecked(true);
        }

        if (gender.equals("1")){
            rbMale.setChecked(true);
        }
        else if (gender.equals("0")){
            rbFemale.setChecked(true);
        }
    }


    public void toUpdateUser(View view) {

        String name,phone,address,city,pass,bloodGroup;
        int gender=1,available = 0;

        name = etDetailName.getText().toString();
        phone = etDetailMobile.getText().toString().trim();
        address = etDetailAddress.getText().toString();
        city = etDetailCity.getText().toString().trim();
        bloodGroup = myDetailsSpinner.getSelectedItem().toString().trim();

        if (rbMale.isChecked()) {
            gender = 1;
        }
        else if (rbFemale.isChecked()){
            gender = 0;
        }

        if (checkboxDetails.isChecked()){
            available = 1;
        }

        if (!name.equals("") && !phone.equals("") && !address.equals("") && !city.equals("") && !bloodGroup.equals("")){

            if (isValidMob(phone)){
                //to Update API Call
                progressBar3.setVisibility(View.VISIBLE);
                toUpdateUserData(name,phone,address,city,bloodGroup,gender,available);
            }
            else {
                Toast.makeText(this, "Please check your mobile !", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "All Fields are require !", Toast.LENGTH_SHORT).show();
        }
    }

    private void toUpdateUserData(String name, String phone, String address, String city, String bloodGroup, int gender, int available) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Donor i = new Donor();
        i.setId(Paper.book().read(DonorDetails.UserIDKey));
        i.setName(name);
        i.setPhone(phone);
        i.setAddress(address);
        i.setCity(city);
        i.setBloodGroup(bloodGroup);
        i.setGender(""+gender);
        i.setAvailable(""+available);

        api.getuserDataUpdate(i).enqueue(new Callback<RegisterOutput>() {
            @Override
            public void onResponse(Call<RegisterOutput> call, Response<RegisterOutput> response) {
                progressBar3.setVisibility(View.GONE);
                if (response.body().getOutput().equals("Success")){
                    //after user update
                    Toast.makeText(MyDetailsActivity.this, "Data Updated "+response.body().getOutput(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MyDetailsActivity.this,SplashScreen.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(MyDetailsActivity.this, "Unable to Update user !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterOutput> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.search_donor:
                Intent mainIntent = new Intent(MyDetailsActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
                break;
            case R.id.blood_bank:
                Intent bloodBankIntent = new Intent(MyDetailsActivity.this,BloodBankActivity.class);
                startActivity(bloodBankIntent);
                finish();
                break;
            case R.id.my_details:
                break;
            case R.id.logout:
                Paper.book().destroy();
                Intent logoutIntent = new Intent(MyDetailsActivity.this,LoginActivity.class);
                startActivity(logoutIntent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //
    }
}