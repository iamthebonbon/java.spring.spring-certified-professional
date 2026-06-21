package com.example.scp.repository;

import com.example.scp.entity.BonBon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonBonRepository extends JpaRepository<BonBon, Long> {
}
