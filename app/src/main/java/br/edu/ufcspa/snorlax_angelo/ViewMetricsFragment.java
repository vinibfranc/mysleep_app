package br.edu.ufcspa.snorlax_angelo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcspa.snorlax_angelo.model.Metrics;
import ufcspa.edu.br.snorlax_angelo.R;

/**
 * Created by icaromsc on 19/06/2017.
 */

public class ViewMetricsFragment extends Fragment {


    View myView;

    public ViewMetricsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView= inflater.inflate(R.layout.fragment_graphics, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BarChart chart = (BarChart) myView.findViewById(R.id.chart);
        Metrics[] metrics = {new Metrics(25,1),new Metrics(70, (float) 2),new Metrics(50,5),new Metrics(60, (float) 3), new Metrics(80, (float) 4)};

        List<BarEntry> entries = new ArrayList<BarEntry>();

        for (Metrics m : metrics) {

            // turn your data into Entry objects
            entries.add(new BarEntry(m.getHoursPeriod(),m.getnRespEvents()));

        }
       /* Metrics metrics = new Metrics();
        for (double e :metrics.events){
            entries.add(new BarEntry((float) e,1));
        }*/


        //LineDataSet dataSet = new LineDataSet(entries, "Night Respiratory Events"); // add entries to dataset

        BarDataSet dataset = new BarDataSet(entries, "Number of respiratory events");
        dataset.setValueTextColor(R.color.pallete_white);
        dataset.setValueTextSize(15);
        //dataset.setColor(R.color.pallete_night2);

        BarData barData = new BarData(dataset);
        chart.setData(barData);
        /*chart.setNoDataTextColor(R.color.pallete_green);
        chart.setGridBackgroundColor(R.color.pallete_clound);
        chart.setNoDataText("VAMO DALE");*/
        chart.setBackgroundColor(getResources().getColor(R.color.pallete_gray));
        chart.invalidate();
        chart.animateXY(1000, 2000);
        // refresh


    }



}
