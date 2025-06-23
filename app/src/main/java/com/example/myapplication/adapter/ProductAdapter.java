package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ProductListActivity;
import com.example.myapplication.R;
import com.example.myapplication.bean.ProductBean;
import com.example.myapplication.repository.ProductRepository;

import java.util.List;

public class ProductAdapter extends
        RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductBean> productList;
    private Context context;

    private  int selectedPosition;
    public ProductAdapter(List<ProductBean> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            ProductBean product = productList.get(position);
            holder.tvProductId.setText(String.valueOf(product.getId()));
            holder.tvProductName.setText(String.valueOf(product.getName()));
            holder.tvPrice.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public  class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
        private TextView tvProductId;
        private TextView tvProductName;
        private TextView tvPrice;



       public  ProductViewHolder(@NonNull View itemView) {
           super(itemView);
           tvProductId = itemView.findViewById(R.id.tvProductId);
           tvProductName = itemView.findViewById(R.id.tvProductName);
           tvPrice = itemView.findViewById(R.id.tvPrice);
           tvProductName.setOnClickListener(this);
           itemView.setOnLongClickListener(this);
           itemView.setOnCreateContextMenuListener(this);
           if (context instanceof ProductListActivity) {
               ((ProductListActivity) context).registerForContextMenu(tvProductName);
           }
       }
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Product name: " + tvProductName.getText(), Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to delete?\nProduct ID: " + tvProductId.getText() +
                            "\nProduct name: " + tvProductName.getText())
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Tạo repository với context
                        ProductRepository productRepository = new ProductRepository(context);

                        // Tạo ProductBean từ dữ liệu giao diện
                        String name = tvProductName.getText().toString();
                        int id = Integer.parseInt(tvProductId.getText().toString());
                        double price = Double.parseDouble(tvPrice.getText().toString()); // đảm bảo bạn có tvProductPrice
                        ProductBean productBean = new ProductBean(id, name, price);

                        // Gọi xóa
                        productRepository.deleteProduct(productBean);

                        // Xoá khỏi danh sách và cập nhật UI
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            productList.remove(position);
                            notifyItemRemoved(position);
                        }

                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        }




        @Override
        public boolean onLongClick(View v) {
            selectedPosition = getAdapterPosition();
            Toast.makeText(context, "Long Clicked", Toast.LENGTH_SHORT).show();
            v.showContextMenu();
            return true;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            MenuInflater inflater = ((ProductListActivity) context)
        }
    }
}
