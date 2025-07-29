package ru.nonamejack.audioshop.repository;

import ru.nonamejack.audioshop.model.Product;
import ru.nonamejack.audioshop.util.IRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository< Product, Integer>, JpaSpecificationExecutor<Product> {


    @Query("select min(p.price) as minValue, max(p.price) as maxValue from Product p where p.category.categoryId = :cid")
    IRange findPriceRange(@Param("cid") Integer categoryId);

}
