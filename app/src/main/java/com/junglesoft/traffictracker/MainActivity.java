package com.junglesoft.traffictracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity {

    @InjectView(R.id.btnInputData)
    Button btnInputData;
    @InjectView(R.id.btnViewData)
    Button btnViewData;
    @InjectView(R.id.btnDelData)
    Button btnDelData;
    @InjectView(R.id.btnChart)
    Button btnViewChart;
    @InjectView(R.id.btnMap)
    Button btnViewMap;
    @InjectView(R.id.btnLocation)
    Button btnLocation;
    @InjectView(R.id.btnGeofence)
    Button btnGeofence;
    @InjectView(R.id.btnActivityRecog)
    Button btnActivityRecog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //do some anims
        //YoYo.with(Techniques.SlideInUp).duration(1000).playOn(btnInputData);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnActivityRecog)
    public void ButtonActRecogClick() {
        startActivity(new Intent(MainActivity.this, ActivityRecognitionActivity.class));
    }

    @OnClick(R.id.btnGeofence)
    public void ButtonGeofenceClick() {
        startActivity(new Intent(MainActivity.this, GeofenceDetectionActivity.class));
    }

    @OnClick(R.id.btnChart)
    public void ButtonViewChartClick() {
        startActivity(new Intent(MainActivity.this, ChartActivity.class));
    }

    @OnClick(R.id.btnMap)
    public void ButtonViewMapClick() {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
    }

    @OnClick(R.id.btnLocation)
    public void ButtonLocationClick() {
        startActivity(new Intent(MainActivity.this, GooglePlayServicesUtilActivity.class));
    }

    @OnClick(R.id.btnViewData)
    public void ButtonViewDataClick() {
        startActivity(new Intent(MainActivity.this, ListDataActivity.class));
    }

    @OnClick(R.id.btnDelData)
    public void ButtonDeleteDataClick() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to delete all traffic data ?");
        alertDialogBuilder.setPositiveButton("Delete All",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Integer count = TimeInTraffic.listAll(TimeInTraffic.class).size();
                        TimeInTraffic.deleteAll(TimeInTraffic.class);
                        Toast delToast = Toast.makeText(getApplicationContext(), String.format("Deleted %s records", Integer.toString(count)), Toast.LENGTH_LONG);
                        delToast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                        delToast.show();
                    }
                });
        alertDialogBuilder.setNegativeButton("Nope",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "LOL! You changed your mind...", Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @OnClick(R.id.btnInputData)
    public void ButtonInputDataClick() {
       /* YoYo.with(Techniques.SlideOutDown).duration(500)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(btnInputData)
        ;*/
        startActivity(new Intent(MainActivity.this, InputDataActivity.class));
    }


}
