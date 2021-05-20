package in.specialsoft.bloodvault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import in.specialsoft.bloodvault.AdminBloodGroupUpdate.AdminBloodGroupUpdateIn;
import in.specialsoft.bloodvault.AdminBloodRequestInOut.AdminGenralOutputs;
import in.specialsoft.bloodvault.Api.ApiClient;
import in.specialsoft.bloodvault.Api.ApiInterface;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUpdateBankStatusActivity extends AppCompatActivity {

    Spinner adminBloodSpinner;
    EditText etBloodCount;
    String[] bloodGroups = {"A-","A+","AB-","AB+","B-","B+","O-","O+"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_bank_status);

        Paper.init(this);
        adminBloodSpinner = findViewById(R.id.adminBloodSpinner);
        etBloodCount = findViewById(R.id.etBloodCount);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminUpdateBankStatusActivity.this, android.R.layout.simple_list_item_1,bloodGroups);
        adminBloodSpinner.setAdapter(adapter);
    }

    public void toUpdateBlood(View view) {

        String count = etBloodCount.getText().toString().trim();
        if (count.equals("")){
            Toast.makeText(this, "give Zero insted of empty", Toast.LENGTH_SHORT).show();
        }
        else {
            updateBloodBank(adminBloodSpinner.getSelectedItem().toString(),count);
        }

    }

    private void updateBloodBank(String group, String count) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        AdminBloodGroupUpdateIn i = new AdminBloodGroupUpdateIn();
        i.setBloodGroup(group);
        i.setGroupcount(count);

        api.getBloodUpdate(i).enqueue(new Callback<AdminGenralOutputs>() {
            @Override
            public void onResponse(Call<AdminGenralOutputs> call, Response<AdminGenralOutputs> response) {
                if (response.body().getOutput().equals("Success"))
                {
                    Toast.makeText(AdminUpdateBankStatusActivity.this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AdminUpdateBankStatusActivity.this, "Error in Update !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminGenralOutputs> call, Throwable t) {

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
                Intent adminPannelIntent = new Intent(AdminUpdateBankStatusActivity.this,AdminPannelActivity.class);
                startActivity(adminPannelIntent);
                finish();
                break;
            case R.id.blood_request:
                Intent updateIntent = new Intent(AdminUpdateBankStatusActivity.this,AdminBloodRequestsActivity.class);
                startActivity(updateIntent);
                finish();
                break;
            case R.id.blood_bank_update:
                break;
            case R.id.logout_admin:
                Paper.book().destroy();
                Intent logoutIntent = new Intent(AdminUpdateBankStatusActivity.this,LoginActivity.class);
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