package ng.borrowpower.android.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ng.borrowpower.android.Core.BorrowPower;
import ng.borrowpower.android.Listeners.onError;
import ng.borrowpower.android.Listeners.onFailed;
import ng.borrowpower.android.Listeners.onSuccess;
import ng.borrowpower.android.Utils.Environment;

public class MainActivity extends AppCompatActivity {
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         start = findViewById(R.id.start);
         start.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new BorrowPower()
                         .with("partnerId", Environment.SANDBOX)
                         .build(MainActivity.this, new onSuccess() {
                             @Override
                             public void onSuccess() {

                             }
                         }, new onFailed() {
                             @Override
                             public void onFailed(String error) {
                                 Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                             }
                         });
             }
         });

    }
}