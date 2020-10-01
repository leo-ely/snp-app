package com.app.yousnp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.model.User;
import com.app.utils.RestService;
import com.app.utils.UserAdapter;
import com.app.utils.UserController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView usersList;
    private UserAdapter adapter;

    private RestService service;
    private UserController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersList = findViewById(R.id.list_users);
        service = new RestService();
        controller = new UserController(findViewById(R.id.activity_main), service);

        inflateList();
        checkIntentExtra();

        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(click ->
                startActivity(new Intent(getBaseContext(), UserActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        inflateList();
    }

    private void checkIntentExtra() {
        User u = (User) getIntent().getSerializableExtra("user");

        if (u != null) {
            Snackbar.make(findViewById(R.id.activity_main),
                    u.getId() == null ? R.string.toast_insert_success
                            : R.string.toast_update_success,
                    Snackbar.LENGTH_LONG).show();
        }
    }

    public void inflateList() {
        Call<List<User>> call = service.getService().getAll();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    createAdapter(response);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Snackbar.make(findViewById(R.id.activity_main),
                        R.string.toast_error_rest,
                        Snackbar.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void createAdapter(Response<List<User>> response) {
        List<User> users = response.body();

        if (adapter == null) {
            adapter = new UserAdapter(this, controller, users);
            usersList.setAdapter(adapter);
        } else {
            adapter.updateList(users);
            adapter.notifyDataSetChanged();
        }
    }

}