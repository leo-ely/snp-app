package com.app.utils;

import android.content.Intent;
import android.view.View;

import com.app.model.User;
import com.app.yousnp.MainActivity;
import com.app.yousnp.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserController {

    private View view;
    private RestService service;

    public UserController(View view, RestService service) {
        this.view = view;
        this.service = service;
    }

    public void save(User u) {
        if (validateFields(u)) {
            Snackbar.make(view, R.string.toast_fields_error, Snackbar.LENGTH_LONG).show();
            return;
        }

        if (u.getId() == null) {
            service.getService().add(u).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        createIntent(u);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Snackbar.make(view, R.string.toast_insert_error, Snackbar.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        } else {
            service.getService().update(u).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        createIntent(u);
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Snackbar.make(view, R.string.toast_update_error, Snackbar.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        }
    }

    private void createIntent(User user) {
        Intent i = new Intent(view.getContext(), MainActivity.class);
        i.putExtra("user", user);

        view.getContext().startActivity(i);
    }

    public void delete(Integer id) {
        service.getService().delete(id).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    Snackbar.make(view, R.string.toast_delete_success, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Snackbar.make(view, R.string.toast_delete_error, Snackbar.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private boolean validateFields(User u) {
        return u.getName() == null || u.getName().isEmpty() ||
                u.getPassword() == null || u.getPassword().isEmpty() ||
                u.getTag() == null || u.getTag().isEmpty() ||
                u.getStatus() == null;
    }

}
