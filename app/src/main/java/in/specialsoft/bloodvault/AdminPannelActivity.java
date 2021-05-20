package in.specialsoft.bloodvault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import in.specialsoft.bloodvault.Api.ApiClient;
import in.specialsoft.bloodvault.Api.ApiInterface;
import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodCount;
import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodGroupCountAdapter;
import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodGroupCountInput;
import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodGroupCountOutput;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPannelActivity extends AppCompatActivity {

    RecyclerView recyclerViewBank;
    List<BloodCount> bloodCounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);

        Paper.init(this);
        recyclerViewBank = findViewById(R.id.recyclerViewBank);
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
                BloodGroupCountAdapter bloodGroupCountAdapter = new BloodGroupCountAdapter(bloodCounts,AdminPannelActivity.this);
                recyclerViewBank.setLayoutManager(new LinearLayoutManager(AdminPannelActivity.this));
                recyclerViewBank.setAdapter(bloodGroupCountAdapter);

            }

            @Override
            public void onFailure(Call<BloodGroupCountOutput> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.blood_bank_home:
                break;
            case R.id.blood_request:
                Intent requestsIntent = new Intent(AdminPannelActivity.this,AdminBloodRequestsActivity.class);
                startActivity(requestsIntent);
                finish();
                break;
            case R.id.blood_bank_update:
                Intent updateIntent = new Intent(AdminPannelActivity.this,AdminUpdateBankStatusActivity.class);
                startActivity(updateIntent);
                finish();
                break;
            case R.id.logout_admin:
                Paper.book().destroy();
                Intent logoutIntent = new Intent(AdminPannelActivity.this,LoginActivity.class);
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