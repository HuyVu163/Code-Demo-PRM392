package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.bean.UserBean;
import com.example.myapplication.database.DatabaseHandler;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtFirstName;
    private EditText edtLastName;
    private EditText edtEmail;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPrefs = getSharedPreferences("User_prefs", MODE_PRIVATE);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtFirstName = findViewById(R.id.edtFirstname);
        edtLastName = findViewById(R.id.edtLastname);
        edtEmail = findViewById(R.id.edtEmail);
        Button button = findViewById(R.id.btSubmitRegister);
        dbHandler = new DatabaseHandler(this);
        button.setOnClickListener(v -> {
            if (edtUsername.getText().toString().isEmpty()) {
                return;
            } else {
                if (edtPassword.getText().toString().isEmpty()) {
                    return;
                }
            }
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("username", edtUsername.getText().toString());
            editor.putString("password", edtPassword.getText().toString());
            editor.apply();
            finish();

            UserBean userBean = new UserBean(0,
                    edtUsername.getText().toString(),
                    edtPassword.getText().toString(),
                    edtFirstName.getText().toString(),
                    edtLastName.getText().toString(),
                    edtEmail.getText().toString(),
                    "123", "Hola", "Master");
            dbHandler.insertUser(userBean);
        });
    }
}