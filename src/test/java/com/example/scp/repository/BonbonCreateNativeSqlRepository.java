package com.example.scp.repository;

import com.example.scp.entity.BonBon;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonbonCreateNativeSqlRepository extends CrudRepository<BonBon, Long> {

    /**
     * doesn't work. there is no @Modifying
     */
    @Query(value = "insert into bon_bon(id, candy_type) values (:id, :type)", nativeQuery = true)
    int createByNativeSqlWithoutModifying(Long id, String type);

    @Modifying
    @Query(value = "insert into bon_bon(id, candy_type) values (:id, :type)", nativeQuery = true)
    int createByNativeSql(Long id, String type);

}
