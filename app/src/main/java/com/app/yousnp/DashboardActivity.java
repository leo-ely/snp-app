package com.app.yousnp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app.report.TypeReport;
import com.app.report.UserReport;
import com.app.utils.CustomValueFormatter;
import com.app.utils.RestService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private PieChart chartTop;
    private PieChart chartBottom;

    private RestService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab_dashboard);
        fab.setOnClickListener(click ->
                startActivity(new Intent(getBaseContext(), MainActivity.class)));

        service = new RestService();
        initializeCharts();
        findChartData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        findChartData();
    }

    private void findChartData() {
        Call<List<TypeReport>> types = service.getService().getTypeAccessQuantity();
        Call<List<UserReport>> users = service.getService().getUserAccessQuantity();

        types.enqueue(new Callback<List<TypeReport>>() {
            @Override
            public void onResponse(Call<List<TypeReport>> call,
                                   Response<List<TypeReport>> response) {
                initializeChartTop(response);
            }

            @Override
            public void onFailure(Call<List<TypeReport>> call, Throwable t) {
                Snackbar.make(findViewById(R.id.activity_dashboard),
                        R.string.toast_error_rest,
                        Snackbar.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

        users.enqueue(new Callback<List<UserReport>>() {
            @Override
            public void onResponse(Call<List<UserReport>> call,
                                   Response<List<UserReport>> response) {
                initializeChartBottom(response);
            }

            @Override
            public void onFailure(Call<List<UserReport>> call, Throwable t) {
                Snackbar.make(findViewById(R.id.activity_dashboard),
                        R.string.toast_error_rest,
                        Snackbar.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void initializeCharts() {
        chartTop = findViewById(R.id.chart_top);
        chartBottom = findViewById(R.id.chart_bottom);

        chartTop.setCenterText(null);
        chartTop.setNoDataText(null);
        chartTop.setDescription(null);
        chartTop.setDrawHoleEnabled(false);
        chartTop.getLegend().setEnabled(false);

        chartBottom.setCenterText(null);
        chartBottom.setNoDataText(null);
        chartBottom.setDescription(null);
        chartBottom.setDrawHoleEnabled(false);
        chartBottom.getLegend().setEnabled(false);
    }

    private void initializeChartTop(Response<List<TypeReport>> response) {
        List<TypeReport> types = response.body();
        List<PieEntry> entries = new ArrayList<>();
        types.forEach(type ->
                entries.add(new PieEntry(type.getQuantity(), type.getType().getName())));

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new CustomValueFormatter());
        data.setValueTextSize(32f);
        data.setValueTextColor(getColor(R.color.colorWhite));

        chartTop.setData(data);
        chartTop.invalidate();
    }

    private void initializeChartBottom(Response<List<UserReport>> response) {
        List<UserReport> users = response.body();
        List<PieEntry> entries = new ArrayList<>();
        users.forEach(user -> entries.add(new PieEntry(user.getQuantity(), user.getName())));

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new CustomValueFormatter());
        data.setValueTextSize(18f);
        data.setValueTextColor(getColor(R.color.colorWhite));

        chartBottom.setData(data);
        chartBottom.invalidate();
    }

}