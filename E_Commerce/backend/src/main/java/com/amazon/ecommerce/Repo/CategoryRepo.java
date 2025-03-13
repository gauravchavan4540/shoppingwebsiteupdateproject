package com.amazon.ecommerce.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amazon.ecommerce.Model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

	@Query(value = "select id from category where cat_name=?1",nativeQuery = true)
	int findByName(String name);
}
