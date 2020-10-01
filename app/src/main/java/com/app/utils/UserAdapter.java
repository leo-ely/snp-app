package com.app.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.app.model.User;
import com.app.yousnp.R;
import com.app.yousnp.UserActivity;

import java.util.List;

public class UserAdapter extends BaseAdapter implements ListAdapter {

    private Context context;
    private UserController controller;

    private List<User> users;

    public UserAdapter(Context context, UserController controller, List<User> users) {
        this.context = context;
        this.controller = controller;
        this.users = users;
    }

    public void updateList(List<User> users) {
        this.users = users;
    }

    public void showAlertDelete(Integer id, int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(R.string.text_delete_dialog);

        dialog.setPositiveButton(R.string.button_yes, (dialogInterface, which) -> {
            dialogInterface.dismiss();
            controller.delete(id);
            users.remove(position);
            notifyDataSetChanged();
        });

        dialog.setNegativeButton(R.string.button_no, (dialogInterface, which) -> dialogInterface.dismiss());

        dialog.create().show();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.users_holder, null);
        }

        TextView label = v.findViewById(R.id.user_label);
        ImageButton delete = v.findViewById(R.id.user_delete);

        if (!users.isEmpty()) {
            label.setText(users.get(position).toString());
            delete.setOnClickListener(d -> showAlertDelete(users.get(position).getId(), position));
        }

        v.setOnClickListener(click -> {
            Intent i = new Intent(context, UserActivity.class);
            i.putExtra("user", users.get(position));
            context.startActivity(i);
        });

        return v;
    }

}
