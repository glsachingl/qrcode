package com.sachin.qrcode.repo;

import com.sachin.qrcode.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
