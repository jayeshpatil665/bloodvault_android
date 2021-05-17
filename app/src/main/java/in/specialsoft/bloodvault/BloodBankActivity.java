package in.specialsoft.bloodvault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class BloodBankActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<BloodCount> bloodCounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);

        Paper.init(this);
        recyclerView = findViewById(R.id.recyclerView);

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
}