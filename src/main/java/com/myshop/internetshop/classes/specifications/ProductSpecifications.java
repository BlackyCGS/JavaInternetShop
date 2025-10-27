package com.myshop.internetshop.classes.specifications;

import com.myshop.internetshop.classes.entities.Gpu;
import com.myshop.internetshop.classes.entities.Motherboard;
import com.myshop.internetshop.classes.entities.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> categoryEquals(String category) {
        return (root, query, cb) ->
        {
            if (category.equals("GPU")) {
                return cb.isNotNull(root.get("gpu"));
            }
            else if (category.equals("motherboard")) {
                return cb.isNotNull(root.get("motherBoard"));
            }
            return null;
        };
    }

    public static Specification<Product> priceBetween(Float min, Float max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("price"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("price"), min);
            return cb.between(root.get("price"), min, max);
        };
    }

    public static Specification<Product> gpuSpecs(
            Integer minVram, Integer maxVram,
            Integer minTdp, Integer maxTdp,
            Integer minBoost, Integer maxBoost
    ) {
        return (root, query, cb) -> {
            Join<Product, Gpu> gpuJoin = root.join("gpu", JoinType.LEFT);

            Predicate predicate = cb.conjunction();

            if (minVram != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(gpuJoin.get("vram"), minVram));
            if (maxVram != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(gpuJoin.get("vram"), maxVram));

            if (minTdp != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(gpuJoin.get("tdp"), minTdp));
            if (maxTdp != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(gpuJoin.get("tdp"), maxTdp));

            if (minBoost != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(gpuJoin.get("boostClock"), minBoost));
            if (maxBoost != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(gpuJoin.get("boostClock"), maxBoost));


            return predicate;
        };
    }

    public static Specification<Product> motherboardSpecs(
            String socket, String chipset,
            String formFactor, String memoryType
    ) {
        return (root, query, cb) -> {
            Join<Product, Motherboard> motherboardJoin = root.join("motherBoard",
                    JoinType.LEFT);

            Predicate predicate = cb.conjunction();

            if (socket != null && !socket.isBlank())
                predicate = cb.and(predicate, cb.equal(motherboardJoin.get("producer"),
                        socket));

            if (chipset != null && !chipset.isBlank())
                predicate = cb.and(predicate, cb.equal(motherboardJoin.get("chipset"),
                        chipset));

            if (formFactor != null && !formFactor.isBlank())
                predicate = cb.and(predicate, cb.equal(motherboardJoin.get("formFactor"),
                        formFactor));

            if (memoryType != null && !memoryType.isBlank())
                predicate = cb.and(predicate, cb.equal(motherboardJoin.get("memoryType"),
                        memoryType));




            return predicate;
        };
    }
}
