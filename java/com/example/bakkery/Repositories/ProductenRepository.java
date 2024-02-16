package com.example.bakkery.Repositories;

import com.example.bakkery.Modellen.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductenRepository extends JpaRepository<Product, Long> {
}
