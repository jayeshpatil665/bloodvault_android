package in.specialsoft.bloodvault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import in.specialsoft.bloodvault.Api.ApiClient;
import in.specialsoft.bloodvault.Api.ApiInterface;
import in.specialsoft.bloodvault.DonorByGroupInOut.DonorByGroupInput;
import in.specialsoft.bloodvault.DonorByGroupInOut.DonorByGroupOutput;
import in.specialsoft.bloodvault.DonorDetails.Donor;
import in.specialsoft.bloodvault.DonorDetails.DonorAdapter;
import in.specialsoft.bloodvault.DonorDetails.DonorDetails;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.specialsoft.bloodvault.DonorDetails.DonorDetails.UserIDKey;
import static in.specialsoft.bloodvault.DonorDetails.DonorDetails.UserNameKey;

public class MainActivity extends AppCompatActivity {

    Spinner spinner_main;
    String[] bloodGroups = {"Select","A-","A+","AB-","AB+","B-","B+","O-","O+"};
    RecyclerView recyclerView;

    List<Donor> donorList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);
        spinner_main = findViewById(R.id.spinner_main);
        recyclerView = findViewById(R.id.recyclerView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,bloodGroups);
        spinner_main.setAdapter(adapter);

        spinner_main.setSelection(6);
        spinner_main.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bloodGroup = bloodGroups[position];
                getDonorList(bloodGroup);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDonorList(String bloodGroup) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        DonorByGroupInput i = new DonorByGroupInput();
        i.setBloodGroup(bloodGroup);

        api.getDonorByGroup(i).enqueue(new Callback<DonorByGroupOutput>() {
            @Override
            public void onResponse(Call<DonorByGroupOutput> call, Response<DonorByGroupOutput> response) {

                if (response.body().getDonor()!=null){
                    donorList = response.body().getDonor();
                    DonorAdapter donorAdapter = new DonorAdapter(donorList,MainActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(donorAdapter);
                }
                else {
                    Toast.makeText(MainActivity.this, "No Donor available for this BloodGroup !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DonorByGroupOutput> call, Throwable t) {

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
                break;
            case R.id.blood_bank:
                Intent bloodBankIntent = new Intent(MainActivity.this,BloodBankActivity.class);
                startActivity(bloodBankIntent);
                finish();
                break;
            case R.id.my_details:
                Intent myDetailsIntent = new Intent(MainActivity.this,MyDetailsActivity.class);
                startActivity(myDetailsIntent);
                finish();
                break;
            case R.id.logout:
                Paper.book().destroy();
                Intent logoutIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(logoutIntent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Notice :");
        builder.setMessage("Do you want to exit , Press yes to exit !");
        builder.setIcon(R.drawable.ic_baseline_info_24);
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}