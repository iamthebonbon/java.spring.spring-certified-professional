package com.example.scp.repository;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.projection.BonbonProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonbonRepository extends CrudRepository<BonBon, Long> {

    BonbonProjection findByCandyType(String type);

    <T extends BonbonProjection> T findByCandyType(String typ, Class<T> tClass);

}
