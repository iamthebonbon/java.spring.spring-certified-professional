package com.example.scp.repository;

import com.example.scp.entity.BonBon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BonbonSelectRepository extends CrudRepository<BonBon, Long> {

    @Query("select o from BonBon o where o.candyType = :type")
    BonBon getByTypeJavaPersistenceQueryLangauge(String type);

    @Query("select o from BonBon o where o.candyType = :type")
    BonBon getByTypeJavaPersistenceQueryLangaugeParam(@Param("type") String param);

    @Query("select o from BonBon o where o.candyType = ?1")
    BonBon getByTypeJavaPersistenceQueryLangaugePositional(String one);

    @Query("select o from BonBon o where o.candyType = :#{#spel}")
    BonBon getByTypeJavaPersistenceQueryLangaugeSpel(String spel);

    @Query(value = "select * from bon_bon o where o.candy_type = :type", nativeQuery = true)
    BonBon getByTypeNativeSql(String type);

    @Query(value = "select * from bon_bon o where o.candy_type = :type", nativeQuery = true)
    BonBon getByTypeNativeSqlParam(@Param("type") String param);

    @Query(value = "select * from bon_bon o where o.candy_type = ?1", nativeQuery = true)
    BonBon getByTypeNativeSqlPositional(String one);

    @Query(value = "select * from bon_bon o where o.candy_type = :#{#spel}", nativeQuery = true)
    BonBon getByTypeNativeSqlSpel(String spel);


}
