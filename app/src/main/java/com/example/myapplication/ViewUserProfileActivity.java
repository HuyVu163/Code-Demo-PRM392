package com.example.myapplication;

import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class ViewUserProfileActivity extends AppCompatActivity {

    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvMobileNo;
    private TextView tvEmail;
    private TextView tvAddress;
    private ImageView imageView;

    private Button btShowProductList;
    private Button btAddProduct;



    private final ActivityResultLauncher<Intent> editProfileLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent data = result.getData();
                                if (data != null) {
                                    String firstName = data.getStringExtra("firstName");
                                    String lastName = data.getStringExtra("lastName");
                                    String mobileNo = data.getStringExtra("mobileNo");
                                    String email = data.getStringExtra("email");
                                    String address = data.getStringExtra("address");
                                    tvFirstName.setText(firstName);
                                    tvLastName.setText(lastName);
                                    tvMobileNo.setText(mobileNo);
                                    tvEmail.setText(email);
                                    tvAddress.setText(address);
                                }
                            }
                        }
                    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvFirstName = findViewById(R.id.tvFirstName);
        tvFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ViewUserProfileActivity.this, tvFirstName);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.menu_detail_profile) {
                            Toast.makeText(ViewUserProfileActivity.this, "Show Details", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        tvLastName = findViewById(R.id.tvLastName);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        tvMobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: " + tvMobileNo.getText().toString()));
                startActivity(intent);
            }
        });
        if (getIntent() != null) {
            String username = getIntent().getStringExtra("username");
            tvFirstName.setText("username: " + username);
        }
        Button btEditProfile = findViewById(R.id.btEditProfile);
        btEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ViewUserProfileActivity.this, EditUserProfileActivity.class);
            intent.putExtra("firstName", tvFirstName.getText().toString());
            intent.putExtra("lastName", tvLastName.getText().toString());
            intent.putExtra("mobileNo", tvMobileNo.getText().toString());
            intent.putExtra("email", tvEmail.getText().toString());
            intent.putExtra("address", tvFirstName.getText().toString());
//            startActivity(intent);
            editProfileLauncher.launch(intent);
        });
        imageView = findViewById(R.id.imgAvatar);
        imageView.setOnClickListener(v -> selectImage());
        String url = "https://static.wikia.nocookie.net/character-stats-and-profiles/images/5/52/Sahur2.webp/revision/latest/scale-to-width-down/350?cb=20250510085254%27";
        Picasso.with(this).load(url)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);

        btShowProductList = findViewById(R.id.btShowProductList);
        btShowProductList.setOnClickListener(v -> {
            Intent intent = new Intent(ViewUserProfileActivity.this, ProductListActivity.class);
            startActivity(intent);
        });

        btAddProduct = findViewById(R.id.btAddProduct);
        btAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ViewUserProfileActivity.this, AddProductActivity.class);
            startActivity(intent);
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imageSelectLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> imageSelectLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getData() != null) {
                        Uri uri = o.getData().getData();
                        imageView.setImageURI(uri);

                    }
                }
            }
    );
}