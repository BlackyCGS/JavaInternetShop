package com.myshop.internetshop.classes.specifications;

import com.myshop.internetshop.classes.dto.*;
import com.myshop.internetshop.classes.entities.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> categoryEquals(String category) {
        return (root, query, cb) ->
                switch (category) {
                    case "GPU" -> cb.isNotNull(root.get("gpu"));
                    case "motherboard" -> cb.isNotNull(root.get("motherBoard"));
                    case "pcCase" -> cb.isNotNull(root.get("pcCase"));
                    case "ram" -> cb.isNotNull(root.get("ram"));
                    case "cpu" -> cb.isNotNull(root.get("cpu"));
                    case "psu" -> cb.isNotNull(root.get("psu"));
                    case "hdd" -> cb.isNotNull(root.get("hdd"));
                    case "ssd" -> cb.isNotNull(root.get("ssd"));
                    default -> null;
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
            GpuFilter filter
    ) {
        return (root, query, cb) -> {
            Join<Product, Gpu> gpuJoin = root.join("gpu", JoinType.LEFT);

            Predicate predicate = cb.conjunction();

            if (filter.getMinVram() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(gpuJoin.get("vram"), filter.getMinVram()));
            if (filter.getMaxVram() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(gpuJoin.get("vram"),
                        filter.getMaxVram()));

            if (filter.getMinTdp() != null)
                predicate = cb.and(predicate,
                        cb.greaterThanOrEqualTo(gpuJoin.get("tdp"), filter.getMinTdp()));
            if (filter.getMaxTdp() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(gpuJoin.get("tdp"),
                        filter.getMaxTdp()));

            if (filter.getMinBoostClock() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(gpuJoin.get(
                        "boostClock"), filter.getMinBoostClock()));
            if (filter.getMaxBoostClock() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(gpuJoin.get(
                        "boostClock"), filter.getMaxBoostClock()));


            return predicate;
        };
    }

    public static Specification<Product> motherboardSpecs(
            MotherboardFilter filter
    ) {
        return (root, query, cb) -> {
            Join<Product, Motherboard> motherboardJoin = root.join("motherBoard",
                    JoinType.LEFT);

            Predicate predicate = cb.conjunction();

            if (filter.getSocket() != null && !filter.getSocket().isBlank())
                predicate = cb.and(predicate, cb.equal(motherboardJoin.get("socket"),
                        filter.getSocket()));

            if (filter.getChipset() != null && !filter.getChipset().isBlank())
                predicate = cb.and(predicate, cb.equal(motherboardJoin.get("chipset"),
                        filter.getChipset()));

            if (filter.getFormFactor() != null && !filter.getFormFactor().isBlank())
                predicate = cb.and(predicate, cb.equal(motherboardJoin.get("formFactor"),
                        filter.getFormFactor()));

            if (filter.getMemoryType() != null && !filter.getMemoryType().isBlank())
                predicate = cb.and(predicate, cb.equal(motherboardJoin.get("memoryType"),
                        filter.getMemoryType()));




            return predicate;
        };


    }
    public static Specification<Product> pcCaseSpecs(
            PcCaseFilter filter
    ) {
        return (root, query, cb) -> {
            Join<Product, PcCase> pcCaseJoin = root.join("pcCase",
                    JoinType.LEFT);

            Predicate predicate = cb.conjunction();

            if (filter.getMotherboard() != null && !filter.getMotherboard().isBlank())
                predicate = cb.and(predicate, cb.equal(pcCaseJoin.get("motherboard"),
                        filter.getMotherboard()));

            if (filter.getPowerSupply() != null && !filter.getPowerSupply().isBlank())
                predicate = cb.and(predicate, cb.equal(pcCaseJoin.get("powerSupply"),
                        filter.getPowerSupply()));
            return predicate;
        };
    }

    public static Specification<Product> ramSpecs(
            RamFilter filter
    ) {
        return (root, query, cb) -> {
            Join<Product, Ram> ramJoin = root.join("ram",
                    JoinType.LEFT);

            Predicate predicate = cb.conjunction();

            if (filter.getMinSize() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(ramJoin.get("size"),
                        filter.getMinSize()));
            if (filter.getMaxSize() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(ramJoin.get("size"),
                        filter.getMaxSize()));

            if (filter.getRamType() != null && !filter.getRamType().isBlank())
                predicate = cb.and(predicate, cb.like(ramJoin.get("ramType"),
                        "%" + filter.getRamType().toLowerCase() + "%"));

            if (filter.getTimings() != null && !filter.getTimings().isBlank())
                predicate = cb.and(predicate, cb.equal(ramJoin.get("powerSupply"),
                        filter.getTimings()));
            return predicate;
        };
    }

    public static Specification<Product> cpuSpecs(
           CpuFilter filter
    ) {
        return (root, query, cb) -> {
            Join<Product, Cpu> cpuJoin = root.join("cpu",
                    JoinType.LEFT);

            Predicate predicate = cb.conjunction();


            if (filter.getMinCores() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuJoin.get(
                        "cores"),
                        filter.getMinCores()));
            if (filter.getMaxCores() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuJoin.get("cores"),
                        filter.getMaxCores()));

            if (filter.getMinThreads() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuJoin.get(
                                "threads"),
                        filter.getMinThreads()));
            if (filter.getMaxThreads() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuJoin.get("threads"),
                        filter.getMaxThreads()));

            if (filter.getMinTdp() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuJoin.get(
                                "tdp"),
                        filter.getMinTdp()));
            if (filter.getMaxTdp() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuJoin.get("tdp"),
                        filter.getMaxTdp()));

            if (filter.getSocket() != null && !filter.getSocket().isBlank())
                predicate = cb.and(predicate, cb.equal(cpuJoin.get("socket"),
                        filter.getSocket()));

            return predicate;
        };
    }

    public static Specification<Product> psuSpecs(PsuFilter filter) {

        return (root, query, cb) -> {
            Join<Product, Cpu> cpuJoin = root.join("cpu",
                    JoinType.LEFT);

            Predicate predicate = cb.conjunction();


            if (filter.getMinWatt() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuJoin.get(
                                "watt"),
                        filter.getMinWatt()));
            if (filter.getMaxWatt() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuJoin.get("watt"),
                        filter.getMaxWatt()));

            if (filter.getSize() != null && !filter.getSize().isBlank())
                predicate = cb.and(predicate, cb.equal(cpuJoin.get("size"),
                        filter.getSize()));
            if (filter.getEfficiencyRating() != null && !filter.getEfficiencyRating().isBlank())
                predicate = cb.and(predicate, cb.equal(cpuJoin.get("efficiencyRating"),
                        filter.getEfficiencyRating()));

            return predicate;
        };
    }

    public static Specification<Product> ssdSpecs(SsdFilter filter) {

        return (root, query, cb) -> {
            Join<Product, Cpu> cpuJoin = root.join("cpu",
                    JoinType.LEFT);

            Predicate predicate = cb.conjunction();


            if (filter.getMinSize() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuJoin.get(
                                "size"),
                        filter.getMinSize()));
            if (filter.getMaxSize() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuJoin.get("size"),
                        filter.getMaxSize()));

            if (filter.getProtocol() != null && !filter.getProtocol().isBlank())
                predicate = cb.and(predicate, cb.equal(cpuJoin.get("protocol"),
                        filter.getProtocol()));
            if (filter.getFormFactor() != null && !filter.getFormFactor().isBlank())
                predicate = cb.and(predicate, cb.equal(cpuJoin.get("formFactor"),
                        filter.getFormFactor()));

            return predicate;
        };
    }

    public static Specification<Product> hddSpecs(HddFilter filter) {

        return (root, query, cb) -> {
            Join<Product, Cpu> cpuJoin = root.join("cpu",
                    JoinType.LEFT);

            Predicate predicate = cb.conjunction();


            if (filter.getMinSize() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(cpuJoin.get(
                                "size"),
                        filter.getMinSize()));
            if (filter.getMaxSize() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(cpuJoin.get("size"),
                        filter.getMaxSize()));

            return predicate;
        };
    }
}
