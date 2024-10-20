package com.productApp.FirstProductApp.specification;

import com.productApp.FirstProductApp.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {
    private final Map<String, Object> filters;


    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();


        //Filter by category
        if(filters.containsKey("category")){
            predicates.add(criteriaBuilder.equal(root.get("category"),filters.get("category")));
        }
        // Filtering by brand
        if (filters.containsKey("brand")) {
            predicates.add(criteriaBuilder.equal(root.get("brand"), filters.get("brand")));
        }

        // Filtering by gender
        if (filters.containsKey("gender")) {
            predicates.add(criteriaBuilder.equal(root.get("gender"), filters.get("gender")));
        }
        // Filtering by price range (minPrice and maxPrice)
        if (filters.containsKey("minPrice") && filters.containsKey("maxPrice")) {
            predicates.add(criteriaBuilder.between(root.get("sellingPrice"),
                    (Double) filters.get("minPrice"), (Double) filters.get("maxPrice")));
        }

        // Filtering by product name (partial match)
        if (filters.containsKey("productName")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")),
                    "%" + filters.get("productName").toString().toLowerCase() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
