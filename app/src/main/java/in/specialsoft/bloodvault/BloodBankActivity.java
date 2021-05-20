package in.specialsoft.bloodvault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import in.specialsoft.bloodvault.AdminBloodRequestInOut.AdminBloodRequestRemove;
import in.specialsoft.bloodvault.AdminBloodRequestInOut.AdminGenralOutputs;
import in.specialsoft.bloodvault.Api.ApiClient;
import in.specialsoft.bloodvault.Api.ApiInterface;
import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodCount;
import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodGroupCountAdapter;
import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodGroupCountInput;
import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodGroupCountOutput;
import in.specialsoft.bloodvault.DonorDetails.DonorDetails;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodBankActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<BloodCount> bloodCounts;
    CheckBox checkBoxUser;
    Spinner userBloodSpinner;
    String[] bloodGroups = {"A-","A+","AB-","AB+","B-","B+","O-","O+"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);

        Paper.init(this);
        recyclerView = findViewById(R.id.recyclerView);
        userBloodSpinner = findViewById(R.id.userBloodSpinner);
        checkBoxUser = findViewById(R.id.checkBoxUser);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BloodBankActivity.this, android.R.layout.simple_list_item_1,bloodGroups);
        userBloodSpinner.setAdapter(adapter);

        getBloodBankStatus();
    }

    private void getBloodBankStatus() {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        BloodGroupCountInput i = new BloodGroupCountInput();
        i.setTable("donor");

        api.getBloodGroupCount(i).enqueue(new Callback<BloodGroupCountOutput>() {
            @Override
            public void onResponse(Call<BloodGroupCountOutput> call, Response<BloodGroupCountOutput> response) {

                    bloodCounts = response.body().getBloodCount();
                    BloodGroupCountAdapter bloodGroupCountAdapter = new BloodGroupCountAdapter(bloodCounts,BloodBankActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(BloodBankActivity.this));
                    recyclerView.setAdapter(bloodGroupCountAdapter);

            }

            @Override
            public void onFailure(Call<BloodGroupCountOutput> call, Throwable t) {

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
                Intent mainIntent = new Intent(BloodBankActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
                break;
            case R.id.blood_bank:
                break;
            case R.id.my_details:
                Intent bloodBankIntent = new Intent(BloodBankActivity.this,MyDetailsActivity.class);
                startActivity(bloodBankIntent);
                finish();
                break;
            case R.id.logout:
                Paper.book().destroy();
                Intent logoutIntent = new Intent(BloodBankActivity.this,LoginActivity.class);
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

    public void toRequestBlood(View view) {
        String group = userBloodSpinner.getSelectedItem().toString();
        if (checkBoxUser.isChecked()){
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            AdminBloodRequestRemove i = new AdminBloodRequestRemove();
            i.setId(Paper.book().read(DonorDetails.UserIDKey).toString());
            i.setBloodGroup(userBloodSpinner.getSelectedItem().toString());
            i.setDateTime(gateTimeDate());

            api.makeBloodRequests(i).enqueue(new Callback<AdminGenralOutputs>() {
                @Override
                public void onResponse(Call<AdminGenralOutputs> call, Response<AdminGenralOutputs> response) {
                    if (response.body().getOutput().equals("Success"))
                    {
                        Toast.makeText(BloodBankActivity.this, "Blood Requested succesfully !", Toast.LENGTH_SHORT).show();
                        checkBoxUser.setChecked(false);
                    }
                    else {
                        Toast.makeText(BloodBankActivity.this, "error in request !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AdminGenralOutputs> call, Throwable t) {

                }
            });
        }
    }

    public String gateTimeDate() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        return saveCurrentDate+" "+saveCurrentTime;
    }
}