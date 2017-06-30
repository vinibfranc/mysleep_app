package br.edu.ufcspa.snorlax_angelo;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcspa.snorlax_angelo.model.Metrics;
import ufcspa.edu.br.snorlax_angelo.R;

/**
 * Created by icaromsc on 19/06/2017.
 *
 * Fragment para exibição das métricas da gravação
 *
 *
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
        Metrics[] metrics = {new Metrics(1,(float)0.5),new Metrics(58, (float) 1),new Metrics(320,(float)1.5),new Metrics(11 ,(float) 2), new Metrics(4,4),new Metrics(2,(float)4.5),new Metrics(12,5),new Metrics(10,(float)5.5)};

        List<BarEntry> entries = new ArrayList<BarEntry>();

        for (Metrics m : metrics) {

            // turn your data into Entry objects
            entries.add(new BarEntry(m.getHoursPeriod(),m.getnRespEvents()));

        }



       /* Metrics metrics = new Metrics();
        for (double e :metrics.events){
            entries.add(new BarEntry((float) e,1));
        }*/



        /// api

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(10);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(true);

        chart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);

        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(7);


        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(10f);
        leftAxis.setGranularity(30f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setTextColor(Color.WHITE);

        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });


        //LineDataSet dataSet = new LineDataSet(entries, "Night Respiratory Events"); // add entries to dataSet

        BarDataSet dataset = new BarDataSet(entries, "Number of respiratory events");
        //cor dos numeros dentro do grafico
        dataset.setValueTextColor(Color.WHITE);
        dataset.setValueTextSize(15);

        dataset.setColor(getResources().getColor(R.color.pallete_gray));

        BarData barData = new BarData(dataset);

        barData.setBarWidth(0.50f);
        chart.setData(barData);


        //chart.setNoDataTextColor(R.color.pallete_green);
        //chart.setGridBackgroundColor(R.color.pallete_clound);
        //chart.setNoDataText("VAMO DALE");*/
        chart.setBackgroundColor(getResources().getColor(R.color.pallete_black));
        chart.invalidate();
        chart.animateXY(2000,3000);
        // refresh


    }



}
