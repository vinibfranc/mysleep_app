package br.edu.ufcspa.snorlax_angelo;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

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
    PieChart pieChart;
    BarChart barChart;

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
        barChart = (BarChart) myView.findViewById(R.id.chart);
        pieChart= (PieChart) myView.findViewById(R.id.pie_chart);
        montaBarChart();
        montaPieChart();
    }






    public void montaBarChart(){
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

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);

        barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(10);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(true);

        barChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getXAxis().setTextColor(Color.WHITE);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(7);


        YAxis leftAxis = barChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(10f);
        leftAxis.setGranularity(30f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        Legend l = barChart.getLegend();
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
        barChart.setData(barData);


        //chart.setNoDataTextColor(R.color.pallete_green);
        //chart.setGridBackgroundColor(R.color.pallete_clound);
        //chart.setNoDataText("VAMO DALE");*/
        barChart.setBackgroundColor(getResources().getColor(R.color.pallete_black));
        barChart.invalidate();
        barChart.animateXY(2000,3000);
        // refresh

    }

    public void montaPieChart(){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        //pieChart.setCenterTextTypeface(mTfLight);
        pieChart.setCenterText(generateCenterSpannableText());
        //pieChart.setBackgroundColor();

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(getResources().getColor(R.color.pallete_black));

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //pieChart.setOnChartValueSelectedListener(this);

        setData(4, 100);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextColor(Color.WHITE);
        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        //pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(12f);
    }










    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.drawable.circle)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //for (int c : ColorTemplate.VORDIPLOM_COLORS)
            //colors.add(c);

        //for (int c : ColorTemplate.JOYFUL_COLORS)
            //colors.add(c);

        //for (int c : ColorTemplate.COLORFUL_COLORS)
            //colors.add(c);

        //for (int c : ColorTemplate.LIBERTY_COLORS)
            //colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        //PieChart.highlightValues(null);

        pieChart.invalidate();
    }


    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Event Spectrum");
        //s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        //s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        //s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }



    protected String[] mParties = new String[] {
            "Simple snore", "Mild OSA", "Moderate OSA", "Severe OSA"
    };



}
