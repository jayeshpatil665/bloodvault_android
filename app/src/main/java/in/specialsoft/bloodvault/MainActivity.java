package in.specialsoft.bloodvault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Spinner spinner_main;
    String[] bloodGroups = {"Select","A-","A+","AB-","AB+","B-","B+","O-","O+"};
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);
        spinner_main = findViewById(R.id.spinner_main);
        recyclerView = findViewById(R.id.recyclerView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,bloodGroups);
        spinner_main.setAdapter(adapter);
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