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

import in.specialsoft.bloodvault.AdminBloodRequestInOut.AdminBloodRequestIn;
import in.specialsoft.bloodvault.AdminBloodRequestInOut.AdminBloodRequestOut;
import in.specialsoft.bloodvault.AdminBloodRequestInOut.Bloodrequest;
import in.specialsoft.bloodvault.AdminBloodRequestInOut.RequestAdapter;
import in.specialsoft.bloodvault.Api.ApiClient;
import in.specialsoft.bloodvault.Api.ApiInterface;
import in.specialsoft.bloodvault.BloodGroupCountInOut.BloodCount;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBloodRequestsActivity extends AppCompatActivity {

    RecyclerView recyclerViewRequests;
    List<Bloodrequest> bloodrequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_blood_requests);

        Paper.init(this);
        recyclerViewRequests = findViewById(R.id.recyclerViewRequests);

        checkBloodRequests();
    }

    private void checkBloodRequests() {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        AdminBloodRequestIn i = new AdminBloodRequestIn();
        i.setTable("bloodrequest");
        api.getBloodRequests(i).enqueue(new Callback<AdminBloodRequestOut>() {
            @Override
            public void onResponse(Call<AdminBloodRequestOut> call, Response<AdminBloodRequestOut> response) {
                bloodrequests = response.body().getBloodrequest();
                //Adapter Code
                RequestAdapter requestAdapter = new RequestAdapter(bloodrequests,AdminBloodRequestsActivity.this);
                recyclerViewRequests.setLayoutManager(new LinearLayoutManager(AdminBloodRequestsActivity.this));
                recyclerViewRequests.setAdapter(requestAdapter);
            }

            @Override
            public void onFailure(Call<AdminBloodRequestOut> call, Throwable t) {

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
                Intent adminPannelIntent = new Intent(AdminBloodRequestsActivity.this,AdminPannelActivity.class);
                startActivity(adminPannelIntent);
                finish();
                break;
            case R.id.blood_request:
                break;
            case R.id.blood_bank_update:
                Intent updateIntent = new Intent(AdminBloodRequestsActivity.this,AdminUpdateBankStatusActivity.class);
                startActivity(updateIntent);
                finish();
                break;
            case R.id.logout_admin:
                Paper.book().destroy();
                Intent logoutIntent = new Intent(AdminBloodRequestsActivity.this,LoginActivity.class);
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