package com.example.scp.repository;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.projection.BonbonProjection;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BonbonRepository extends JpaRepository<BonBon, Long>, JpaSpecificationExecutor<BonBon> {

    BonbonProjection findByCandyType(String type);

    <T extends BonbonProjection> T findByCandyType(String typ, Class<T> tClass);

    static Specification<BonBon> candyTypeEqual(String owner) {
        return (root, query, cb) -> cb.equal(root.get("candyType"), owner);
    }

    static Specification<BonBon> idBetween(Long min, Long max) {
        return (root, query, cb) -> cb.between(root.get("id"), min, max);
    }

    static Specification<BonBon> candyTypeLike(String name) {
        return (root, query, cb) -> cb.like(
                cb.lower(root.get("candyType")), "%" + name.toLowerCase() + "%"
        );
    }

}
