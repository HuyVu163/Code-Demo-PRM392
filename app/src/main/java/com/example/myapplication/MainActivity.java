package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.bean.UserBean;
import com.example.myapplication.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
     private EditText edtUsername;
     private EditText edtPassword;
     private Spinner spinnerCampus;
     private RadioButton radioButtonStaff;
     private RadioButton radioButtonManager;
     private CheckBox checkBoxRememberMe;
     private Button btLogin;

     private Button btSignup;

    private DatabaseHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        spinnerCampus = findViewById(R.id.spinnerCampus);
        radioButtonStaff = findViewById(R.id.radioButtonStaff);
        radioButtonManager = findViewById(R.id.radioButtonManager);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        dbHandler = new DatabaseHandler(this);
        btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(this);
        spinnerCampus.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.campus, android.R.layout.simple_spinner_dropdown_item);
        btSignup = findViewById(R.id.btSignup);
        btSignup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterUserActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String campus = spinnerCampus.getSelectedItem().toString();
        String role = radioButtonStaff.isChecked() ? "Staff" : "Manager";
        boolean rememberMe = checkBoxRememberMe.isChecked();
        Toast.makeText(MainActivity.this, "username" + username, Toast.LENGTH_SHORT).show();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "user name: " + username, Toast.LENGTH_SHORT).show();
            return;
        }

        UserBean user = dbHandler.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            Toast.makeText(MainActivity.this, "Invalid username or password" + username, Toast.LENGTH_SHORT).show();
            return;
        }


        SharedPreferences sharedPreferences = getSharedPreferences("User_prefs",MODE_PRIVATE);
        String prefusername = sharedPreferences.getString("username","");
        String prefpassword = sharedPreferences.getString("password","");
        if(!username.equals(prefusername) || !password.equals(prefpassword))
        {
            Toast.makeText(MainActivity.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(MainActivity.this, ViewUserProfileActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("campus", campus);
        intent.putExtra("role", role);
        intent.putExtra("rememberMe", rememberMe);
        startActivity(intent);

////        test list danh sách data
//        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
//        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedCampus = parent.getItemAtPosition(position).toString();
        Toast.makeText(MainActivity.this, "Selected Campus: " + selectedCampus, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Selected Campus: " + selectedCampus);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}