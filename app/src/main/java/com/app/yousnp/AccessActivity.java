package com.app.yousnp;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.model.Access;
import com.app.model.User;
import com.app.utils.AccessAdapter;
import com.app.utils.RestService;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessActivity extends AppCompatActivity {

    private AccessAdapter adapter;
    private ListView list_access;
    private TextView label_no_records;

    private RestService service;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        user = (User) getIntent().getSerializableExtra("user");

        label_no_records = findViewById(R.id.label_no_records);
        list_access = findViewById(R.id.list_access);

        service = new RestService();
        inflateList(user);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inflateList(user);
    }

    private void inflateList(User user) {
        if (user != null && user.getId() != null) {
            Call<List<Access>> call = service.getService().getAccess(user.getId());

            call.enqueue(new Callback<List<Access>>() {
                @Override
                public void onResponse(Call<List<Access>> call, Response<List<Access>> response) {
                    if (response.isSuccessful()) {
                        createAdapter(response);
                    }
                }

                @Override
                public void onFailure(Call<List<Access>> call, Throwable t) {
                    Snackbar.make(findViewById(R.id.activity_main),
                            R.string.toast_error_rest,
                            Snackbar.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        } else {
            label_no_records.setVisibility(View.VISIBLE);
        }
    }

    private void createAdapter(Response<List<Access>> response) {
        List<Access> accesses = response.body();

        if (adapter == null) {
            adapter = new AccessAdapter(this, accesses);
            list_access.setAdapter(adapter);
        } else {
            adapter.updateList(accesses);
            adapter.notifyDataSetChanged();
        }

        updateRecordsLabel();
    }

    private void updateRecordsLabel() {
        label_no_records.setVisibility(!list_access.getAdapter().isEmpty()
                ? View.GONE : View.VISIBLE);
    }

}