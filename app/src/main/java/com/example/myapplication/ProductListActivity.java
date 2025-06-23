package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.bean.ProductBean;
import com.example.myapplication.repository.ProductRepository;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProductList;
    private ProductAdapter productAdapter;
    private List<ProductBean> productList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewProductList = findViewById(R.id.recylerViewProductList);
        productAdapter = new ProductAdapter(productList, this);
        recyclerViewProductList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProductList.setAdapter(productAdapter);
        fetchProductList();
        //registerForContextMenu(recyclerViewProductList);
    }

    private void fetchProductList() {
        ProductRepository productRepository = new ProductRepository(this);
        productList.clear();
        productList.addAll(productRepository.getAllProducts());
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu );
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId() == R.id.menu_view){
            showDetails();
            return true;
        } else if (item.getItemId() == R.id.menu_edit){
            showEdit();
            return true;
        } else if (item.getItemId() == R.id.menu_delete){
            showDelete();
            return true;
        }
        return super.onContextItemSelected(item);
    }



    private void showDetails() {
        Toast.makeText(this, "Details is clicked", Toast.LENGTH_SHORT).show();
    }

    private void showEdit() {
        Toast.makeText(this, "Edit is clicked", Toast.LENGTH_SHORT).show();
    }

    private void showDelete() {
        Toast.makeText(this, "Delete is clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            showSetting();
            return true;
        } else if (item.getItemId() == R.id.menu_favourite) {
            showFavourite();
            return true;
        } else if (item.getItemId() == R.id.menu_logout){
            showLogout();
        } else if (item.getItemId() == R.id.menu_request_gps) {
            Toast.makeText(this, "Request GPS is clicked", Toast.LENGTH_SHORT).show();
            RequestGPS();
        }
        return false;
    }
    private void RequestGPS(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Permission Required")
                        .setMessage("Please allow GPS permission")
                        .setPositiveButton("OK",
                                (dialog,
                                 which) -> {
                                    dialog.dismiss();
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                                })
                        .setCancelable(false);
                builder.create().show();
            }
            else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
            }
        }
        else {
            Toast.makeText(this, "GPS is granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestScore, @NonNull String[] permission, int[] grantResults){
        super.onRequestPermissionsResult(requestScore,permission,grantResults);
        switch (requestScore){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"GPS is granted",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"GPS is denied",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void showFavourite() {
        Toast.makeText(this, "Favourite", Toast.LENGTH_SHORT).show();
    }

    private void showSetting() {
        Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
    }

    private void showLogout() {
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu){
        if(menu != null)
        {
            try{
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu,true);
            }
            catch (Exception e){
                Log.e("MenuOptions","Error setting optional icons visibility",e);
            }
        }
        return super.onMenuOpened(featureId,menu);
    }
}
