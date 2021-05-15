package in.specialsoft.bloodvault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import static in.specialsoft.bloodvault.LoginActivity.isValidMob;

public class RegisterActivity extends AppCompatActivity {

    Spinner spinner;
    String[] bloodGroups = {"A-","A+","AB-","AB+","B-","B+","O-","O+"};

    EditText etRegName,etRegContactNo,etRegAddress,etRegCity,etRegPassword;

    RadioGroup radioGroup;
    RadioButton rbMale,rbFemale;
    CheckBox checkboxDonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,bloodGroups);
        spinner.setAdapter(adapter);

        etRegName = findViewById(R.id.etRegName);
        etRegContactNo = findViewById(R.id.etRegContactNo);
        etRegAddress = findViewById(R.id.etRegAddress);
        etRegCity = findViewById(R.id.etRegCity);
        etRegPassword = findViewById(R.id.etRegPassword);

        radioGroup = findViewById(R.id.radioGroup);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        checkboxDonor = findViewById(R.id.checkboxDonor);

        spinner.setSelection(0);
    }

    public void onRegister(View view) {

        String name,phone,address,city,pass,bloodGroup;
        int gender = 1,available = 0;

        name = etRegName.getText().toString();
        phone = etRegContactNo.getText().toString().trim();
        address = etRegAddress.getText().toString();
        city = etRegCity.getText().toString().trim();
        pass = etRegPassword.getText().toString().trim();
        bloodGroup = spinner.getSelectedItem().toString().trim();

        if (rbMale.isChecked()) {
            gender = 1;
        }
        else if (rbFemale.isChecked()){
            gender = 0;
        }

        if (checkboxDonor.isChecked()){
            available = 1;
        }

        if (!name.equals("") && !phone.equals("") && !address.equals("") && !city.equals("") && !pass.equals("") && !bloodGroup.equals("")){

            if (isValidMob(phone)){
                //to Register API Call
                toRegisterUser(name,phone,address,city,pass,bloodGroup,gender,available);
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

    //Register API Call
    private void toRegisterUser(String name, String phone, String address, String city, String pass, String bloodGroup, int gender, int available) {


        //after user registration - to Login Page

        Intent homeIntent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(homeIntent);
    }
}