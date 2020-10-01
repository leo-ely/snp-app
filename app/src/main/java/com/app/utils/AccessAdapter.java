package com.app.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.app.model.Access;
import com.app.yousnp.R;

import java.util.List;

public class AccessAdapter extends BaseAdapter implements ListAdapter {

    private Context context;
    private List<Access> accesses;

    public AccessAdapter(Context context, List<Access> accesses) {
        this.context = context;
        this.accesses = accesses;
    }

    public void updateList(List<Access> accesses) {
        this.accesses = accesses;
    }

    @Override
    public int getCount() {
        return accesses.size();
    }

    @Override
    public Object getItem(int position) {
        return accesses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return accesses.get(position).getUser_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.access_holder, null);
        }

        TextView access_label = v.findViewById(R.id.access_label);

        if (!accesses.isEmpty()) {
            access_label.setText(accesses.get(position).toString());
        }

        return v;
    }

}
