package com.infy.qrcode.repo;

import com.infy.qrcode.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
