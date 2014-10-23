package com.junglesoft.traffictracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.telerik.widget.chart.engine.databinding.PropertyNameDataPointBinding;
import com.telerik.widget.chart.engine.series.combination.ChartSeriesCombineMode;
import com.telerik.widget.chart.visualization.behaviors.ChartPanAndZoomBehavior;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.SplineAreaSeries;
import com.telerik.widget.primitives.legend.RadLegendView;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ChartActivity extends Activity {
    @InjectView(R.id.chartLayout)
    LinearLayout chartLayout;

    private static Runnable mEndAction = new Runnable() {
        @Override
        public void run() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.inject(this);

        //updateLineChart();
        displayRadChart();
    }

    private void displayRadChart() {
        List<TimeInTraffic> trafficListM = Select.from(TimeInTraffic.class).where(Condition.prop("traffic_period").eq("Morning")).list();
        List<TimeInTraffic> trafficListE = Select.from(TimeInTraffic.class).where(Condition.prop("traffic_period").eq("Evening")).list();
        //Toast.makeText(getApplicationContext(), Integer.toString(trafficListM.size()), Toast.LENGTH_LONG);

        RadCartesianChartView chartView = new RadCartesianChartView(this);

        ChartPanAndZoomBehavior behavior = new ChartPanAndZoomBehavior();
        chartView.getBehaviors().add(behavior);



        SplineAreaSeries seriesM =new SplineAreaSeries (this);
        seriesM.setCategoryBinding(new PropertyNameDataPointBinding("TrafficDay"));
        seriesM.setValueBinding(new PropertyNameDataPointBinding("HoursSpent"));
        seriesM.setData(trafficListM);
        seriesM.setLegendTitle("Morning");
        seriesM.setShowLabels(true);
        seriesM.setCombineMode(ChartSeriesCombineMode.CLUSTER);
        chartView.getSeries().add(seriesM);

        SplineAreaSeries  seriesE =new SplineAreaSeries (this);
        seriesE.setCategoryBinding(new PropertyNameDataPointBinding("TrafficDay"));
        seriesE.setValueBinding(new PropertyNameDataPointBinding("HoursSpent"));
        seriesE.setData(trafficListE);
        seriesE.setLegendTitle("Evening");
        seriesE.setShowLabels(true);
        seriesE.setCombineMode(ChartSeriesCombineMode.CLUSTER);
        chartView.getSeries().add(seriesE);


        CategoricalAxis horizontalAxis = new CategoricalAxis(this);
        chartView.setHorizontalAxis(horizontalAxis);

        LinearAxis verticalAxis = new LinearAxis(this);
        chartView.setVerticalAxis(verticalAxis);

        RadLegendView legendView = new RadLegendView(this);
        legendView.setLegendProvider(chartView);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(460,100);
        params.setMargins(10,10,10,10);
        legendView.setLayoutParams(params);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(legendView);
        linearLayout.addView(chartView);


        chartLayout.addView(linearLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chart, menu);
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

    public void updateLineChart() {


        try {
            //load data
            GraphView.GraphViewData[] dataArray = getGraphViewDatas("Morning");

            GraphViewSeries dataSeries = new GraphViewSeries(dataArray);
            GraphView graphView = new LineGraphView(this, "Traffic Chart");
            graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
                @Override
                public String formatLabel(double v, boolean isValueX) {
                    if (isValueX) {
                        return "Day " + v;
                    }
                    return null;
                }
            });
            graphView.addSeries(dataSeries);
            graphView.setScalable(true);
            graphView.setScrollable(true);
            graphView.setShowLegend(true);
            graphView.setBackgroundColor(3);
            chartLayout.addView(graphView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GraphView.GraphViewData[] getGraphViewDatas(String period) {
        List<TimeInTraffic> trafficList = Select.from(TimeInTraffic.class).where(Condition.prop("traffic_period").eq(period)).list();
        // TimeInTraffic.listAll(TimeInTraffic.class);//

        Toast.makeText(getApplicationContext(), Integer.toString(trafficList.size()), Toast.LENGTH_LONG);

        GraphView.GraphViewData[] dataArray = new GraphView.GraphViewData[trafficList.size()];

        int cnt = 0;
        for (TimeInTraffic item : trafficList) {

            GraphView.GraphViewData viewData = new GraphView.GraphViewData(Float.valueOf(DateTime.parse(item.trafficDay).getDayOfMonth()), item.hoursSpent);
            dataArray[cnt] = viewData;
            cnt++;

        }
        return dataArray;
    }


}
