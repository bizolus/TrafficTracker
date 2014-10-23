package com.junglesoft.traffictracker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.joda.time.DateTime;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class InputDataActivity extends Activity {
    @InjectView(R.id.datePicker)
    DatePicker datePicker;
    @InjectView(R.id.numHrs)
    TextView numHours;
    @InjectView(R.id.btnSave)
    Button btnSave;
    String strPeriod="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        ButterKnife.inject(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.input_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPeriodClick(View view) {
// Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radMorning:
                if (checked)
                    strPeriod = "Morning";
               // Toast.makeText(getApplicationContext(), "Morning Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radEvening:
                if (checked)
                    strPeriod = "Evening";
                //Toast.makeText(getApplicationContext(), "Evening Selected", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void onSaveClick(View view) {
        try {
            if (strPeriod.isEmpty()) {
                YoYo.with(Techniques.Flash).duration(1500).playOn(btnSave);
                Toast.makeText(getApplicationContext(), "Select traffic period", Toast.LENGTH_LONG).show();
                return;
            }
            if (numHours.getText().length() == 0) {
                YoYo.with(Techniques.Shake).duration(1500).playOn(numHours);
                Toast.makeText(getApplicationContext(), "Enter number of hours", Toast.LENGTH_LONG).show();
                return;
            }

            TimeInTraffic tt = new TimeInTraffic();
            tt.trafficPeriod = strPeriod;
            tt.hoursSpent = Double.parseDouble(numHours.getText().toString());
            tt.trafficDay= new DateTime(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),0,0).toString("yyyy-MM-dd");
            //Toast.makeText(getApplicationContext(), tt.trafficDay, Toast.LENGTH_SHORT).show();

            tt.save();
            numHours.setText("");
            Toast.makeText(getApplicationContext(), "Traffic Details Saved!", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.d(getString(R.string.app_name), ex.getCause().getMessage());
        }
    }
}
