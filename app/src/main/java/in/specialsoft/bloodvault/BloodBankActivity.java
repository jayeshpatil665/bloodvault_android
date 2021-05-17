package in.specialsoft.bloodvault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import io.paperdb.Paper;

public class BloodBankActivity extends AppCompatActivity {

    Spinner spinnerSearchDonor;
    String[] bloodGroups = {"Select","A-","A+","AB-","AB+","B-","B+","O-","O+"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);

        Paper.init(this);
        spinnerSearchDonor = findViewById(R.id.spinnerSearchDonor);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BloodBankActivity.this, android.R.layout.simple_list_item_1,bloodGroups);
        spinnerSearchDonor.setAdapter(adapter);
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