package com.app.utils;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class CustomValueFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(0);

        return df.format(value);
    }

    @Override
    public String getPieLabel(float value, PieEntry pieEntry) {
        return getFormattedValue(value);
    }

}
