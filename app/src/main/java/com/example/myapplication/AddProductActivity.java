package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.bean.ProductBean;
import com.example.myapplication.repository.ProductRepository;

public class AddProductActivity extends AppCompatActivity {
    private TextView txtProductName;
    private TextView txtProductPrice;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtProductName = findViewById(R.id.txtProductName);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        btnSave = findViewById(R.id.Save);
        ProductRepository productRepository = new ProductRepository(this);
        btnSave.setOnClickListener(v -> {
            String productName = txtProductName.getText().toString();
            String productPrice = txtProductPrice.getText().toString();
            ProductBean product = new ProductBean(0,productName, Double.parseDouble(productPrice));
            productRepository.createProduct(product);
        });

    }
}