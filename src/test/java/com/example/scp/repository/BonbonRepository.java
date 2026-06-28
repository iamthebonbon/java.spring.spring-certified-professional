package com.example.scp.repository;

import com.example.scp.entity.BonBon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BonbonRepository extends JpaRepository<BonBon, Long> {

    @Query("select o from BonBon o where o.candyType = :type")
    BonBon getByTypeJavaPersistenceQueryLangauge(String type);

    @Query(value = "select * from bon_bon o where o.candy_type = :type", nativeQuery = true)
    BonBon getByTypeNativeSql(String type);

    /**
     * doesn't work. there is no @Modifying
     */
    @Query(value = "insert into bon_bon(id, candy_type) values (:id, :type)", nativeQuery = true)
    int createByNativeSqlWithoutModifying(Long id, String type);

    @Modifying
    @Query(value = "insert into bon_bon(id, candy_type) values (:id, :type)", nativeQuery = true)
    int createByNativeSql(Long id, String type);

    /**
     * doesn't work. there is no @Modifying
     */
    @Query("UPDATE BonBon o SET o.candyType = :type WHERE o.id = :id")
    void updateByJpqlWithoutModifying(Long id, String type);

    /**
     * Cache problem due ORM
     */
    @Modifying
    @Query("UPDATE BonBon o SET o.candyType = :type WHERE o.id = :id")
    int updateByJpqlWithoutClearCache(@Param("id") Long id, @Param("type") String type);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE BonBon o SET o.candyType = :type WHERE o.id = :id")
    int updateByJpql(@Param("id") Long id, @Param("type") String type);

}
