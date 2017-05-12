package co.edu.eafit.travelweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.Weather;

public class WeatherActivity extends AppCompatActivity {
    private TextView cityName;
    private TextView temp;
    private ImageView iconView;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView updated;
    private LocationManager locationManager;

    Weather weather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityName = (TextView) findViewById(R.id.cityText);
        iconView = (ImageView) findViewById(R.id.thumbnailIcon);
        temp = (TextView) findViewById(R.id.tempText);
        description = (TextView) findViewById(R.id.cloudText);
        humidity = (TextView) findViewById(R.id.humidText);
        pressure = (TextView) findViewById(R.id.pressureText);
        updated = (TextView) findViewById(R.id.updateText);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        renderWeatherData(location);
    }

    public void renderWeatherData(Location location){
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{"lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&units=metric&APPID=618d836a10157d7ced4780effd71db2b"});
    }

    private class WeatherTask extends AsyncTask<String, Void, Weather> {
        @Override

        protected void onPostExecute(Weather weather) {

            DateFormat df = DateFormat.getTimeInstance();
            String updateDate = df.format(new Date(weather.place.getLastupdate()));

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());

            super.onPostExecute(weather);
            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText("" + tempFormat + "°C");
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure() + "hPa");
            updated.setText("Last Updated: " + updateDate);
            description.setText("Condition: " + weather.currentCondition.getCondition() + " (" +
                    weather.currentCondition.getDescription() + ")");

        }

        @Override
        protected Weather doInBackground(String... params) {
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));
            weather = JSONWeatherParser.getWeather(data);
            Log.v("Data: ",weather.place.getCity());
            return weather;
        }
    }
}
