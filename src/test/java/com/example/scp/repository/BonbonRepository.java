package com.example.scp.repository;

import com.example.scp.entity.BonBon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BonbonRepository extends JpaRepository<BonBon, Long> {

    @Query("select o from BonBon o where o.candyType = :type")
    BonBon getByTypeJavaPersistenceQueryLangauge(String type);

    @Query(value = "select * from bon_bon o where o.candy_type = :type", nativeQuery = true)
    BonBon getByTypeNativeSql(String type);

}
