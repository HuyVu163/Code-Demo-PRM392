package com.example.myapplication.repository;

import android.content.Context;

import com.example.myapplication.bean.ProductBean;
import com.example.myapplication.dao.ProductDao;
import com.example.myapplication.dao.ProductRoomDatabase;
import com.example.myapplication.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private ProductDao productDao;

    public ProductRepository(Context context) {
        ProductRoomDatabase database = ProductRoomDatabase.getInstance(context);
        productDao = database.productDao();
    }

    public void createProduct(ProductBean productBean) {
        Product product = new Product(productBean.getName(),
                productBean.getPrice());
        productDao.insert(product);
    }

    public void updateProduct(ProductBean productBean) {
        Product product = new Product(productBean.getName(),
                productBean.getPrice());
        product.setId(product.getId());
        productDao.update(product);
    }

    public void deleteProduct(ProductBean productBean) {
        Product product = new Product(productBean.getName(), productBean.getPrice());
        productDao.delete(product);
    }

    public List<ProductBean> getAllProducts() {
        List<Product> products = productDao.findAllProducts();
        List<ProductBean> productBeans = new ArrayList<>();
        for (Product product : products) {
            ProductBean productBean = new ProductBean(product.getId(), product.getName(), product.getPrice());
            productBeans.add(productBean);
        }
        return productBeans;
    }
}
