package com.example.scp.repository;

import com.example.scp.entity.BonBon;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BonbonUpdateJpqlRepository extends CrudRepository<BonBon, Long> {

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
