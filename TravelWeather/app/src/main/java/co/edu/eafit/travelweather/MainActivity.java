package co.edu.eafit.travelweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void callMapsActivity(View view){
        Intent callMapsActivity = new Intent(this,MapsActivity.class);
        startActivity(callMapsActivity);
    }
    public void callLoginActivity(View view){
        Intent callLoginActivity = new Intent(this,LoginActivity.class);
        startActivity(callLoginActivity);
    }
    public void callRoutesActivity(View view){
        Intent callRoutesActivity = new Intent(this,Rutas.class);
        startActivity(callRoutesActivity);
    }
    public void callReportActivity(View view){
        Intent callReportActivity = new Intent(this,Reportes.class);
        startActivity(callReportActivity);
    }
}
