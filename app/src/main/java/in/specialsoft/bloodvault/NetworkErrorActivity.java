package in.specialsoft.bloodvault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NetworkErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_error);
    }

    public void toRetry(View view) {

        Intent intent = new Intent(NetworkErrorActivity.this,SplashScreen.class);
        startActivity(intent);
        finish();
    }
}