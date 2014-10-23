package com.junglesoft.traffictracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Abisolu.Adekoya on 13/10/2014.
 */
public class DataItemAdapter extends BaseAdapter {
    private Context context;
    private List<TimeInTraffic> trafficData;

    public DataItemAdapter(Context context, List<TimeInTraffic> trafficData) {
        this.context = context;
        this.trafficData = trafficData;
    }

    @Override
    public int getCount() {
        return trafficData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = layoutInflater.inflate(R.layout.data_item, null);

            // set value into textview
            TextView txtTrafficDay = (TextView) gridView.findViewById(R.id.txtTrafficDay);
            /*GregorianCalendar calendar = new GregorianCalendar();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
*/
            String trafficDay = trafficData.get(position).trafficDay;
            DateTime date = DateTime.parse(trafficDay.toString());

            txtTrafficDay.setText(date.toString("dd MMM yyyy"));

            TextView txtHrsSpent = (TextView) gridView.findViewById(R.id.txtHrsSpent);
            txtHrsSpent.setText(Double.toString(trafficData.get(position).hoursSpent) + " hours");

            TextView txtPeriod = (TextView) gridView.findViewById(R.id.txtPeriod);
            txtPeriod.setText(trafficData.get(position).trafficPeriod.toString());
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
