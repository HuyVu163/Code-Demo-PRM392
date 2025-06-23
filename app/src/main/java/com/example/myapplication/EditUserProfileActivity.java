package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditUserProfileActivity extends AppCompatActivity {

    private EditText edtFirstnameProfile;
    private EditText edtLastnameProfile;
    private EditText edtMobileNoProfile;
    private EditText edtAddressProfile;
    private EditText edtEmailProfile;
    private Button btSaveProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {


            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtFirstnameProfile = findViewById(R.id.edtFirstnameProfile);
        edtLastnameProfile = findViewById(R.id.edtLastnameProfile);
        edtMobileNoProfile = findViewById(R.id.edtMobileNoProfile);
        edtAddressProfile = findViewById(R.id.edtAddressProfile);
        edtEmailProfile = findViewById(R.id.edtEmailProfile);
        btSaveProfile = findViewById(R.id.btSaveProfile);
        if (getIntent() != null) {
            String firstName = getIntent().getStringExtra("firstName");
            String lastName = getIntent().getStringExtra("lastName");
            String mobileNo = getIntent().getStringExtra("mobileNo");
            String email = getIntent().getStringExtra("email");
            String address = getIntent().getStringExtra("address");
            edtFirstnameProfile.setText(firstName);
            edtLastnameProfile.setText(lastName);
            edtEmailProfile.setText(email);
            edtAddressProfile.setText(address);
            edtMobileNoProfile.setText(mobileNo);
        }

        btSaveProfile.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("firstName", edtFirstnameProfile.getText().toString());
            intent.putExtra("lastName", edtLastnameProfile.getText().toString());
            intent.putExtra("email", edtEmailProfile.getText().toString());
            intent.putExtra("mobileNo", "");
            intent.putExtra("address", "");
            setResult(RESULT_OK, intent);
            finish();
        });
//        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback() {
//            @Override
//            public void handleOnBackPressed() {
//                Toast.makeText(EditUserProfileActivity.this, "Back pressed", Toast.LENGTH_SHORT).show();
////                finish();
//            }
//        });
    }
}