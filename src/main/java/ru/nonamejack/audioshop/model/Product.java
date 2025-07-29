package ru.nonamejack.audioshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;


    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "main_image")
    private String mainImage;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<ProductImage> productImages;


    @OneToMany(mappedBy = "product")
    private List<ProductAttribute> productAttributeList;


    @OneToMany(mappedBy = "product")
    private List<ReviewProduct> productReviewList;

    private Boolean active;
}
