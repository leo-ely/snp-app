package com.app.yousnp;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.model.User;
import com.app.model.UserStatus;
import com.app.utils.RestService;
import com.app.utils.UserController;

public class UserActivity extends AppCompatActivity {

    private Button buttonAccess;
    private Button buttonSave;
    private CheckBox checkStatus;
    private EditText textId;
    private EditText textUsername;
    private EditText textPassword;
    private EditText textTag;
    private Tag tag;

    private RestService service;
    private User user;
    private UserController controller;

    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initializeFields();

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        user = (User) getIntent().getSerializableExtra("user");

        service = new RestService();
        controller = new UserController(findViewById(R.id.constraint_user), service);

        if (user == null) {
            configFieldsInsert();
        } else {
            configFieldsUpdate();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        StringBuilder result = new StringBuilder();

        for (byte aByte : tag.getId()) {
            result.append(String.format("%02x", aByte).toUpperCase());
        }

        textTag.setText(result.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Intent i = new Intent().addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    private void initializeFields() {
        checkStatus = findViewById(R.id.check_status);
        textId = findViewById(R.id.text_id);
        textUsername = findViewById(R.id.text_username);
        textPassword = findViewById(R.id.text_password);
        textTag = findViewById(R.id.text_tag);
        buttonAccess = findViewById(R.id.button_access);
        buttonSave = findViewById(R.id.button_save);

        buttonAccess.setOnClickListener(click -> {
            Intent i = new Intent(this, AccessActivity.class);
            i.putExtra("user", user);

            startActivity(i);
        });

        buttonSave.setOnClickListener(click -> saveUser());
    }

    private void saveUser() {
        user.setName(textUsername.getText().toString());
        user.setPassword(textPassword.getText().toString());
        user.setTag(textTag.getText().toString());
        user.setStatus(checkStatus.isChecked() ? UserStatus.A : UserStatus.I);

        controller.save(user);
    }

    private void configFieldsInsert() {
        user = new User();

        checkStatus.setChecked(true);
        textId.setVisibility(View.GONE);
    }

    private void configFieldsUpdate() {
        checkStatus.setChecked(user.getStatus().isStatus());
        textId.setVisibility(View.VISIBLE);
        textId.setText(String.valueOf(user.getId()));
        textUsername.setText(user.getName());
        textPassword.setText(user.getPassword());
        textTag.setText(user.getTag());
    }

}