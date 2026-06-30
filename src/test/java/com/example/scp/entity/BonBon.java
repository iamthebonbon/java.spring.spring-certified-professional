package com.example.scp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class BonBon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String candyType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandyType() {
        return candyType;
    }

    public void setCandyType(String candyType) {
        this.candyType = candyType;
    }

    @Override
    public String toString() {
        return "BonBon{" +
                "id=" + id +
                ", candyType='" + candyType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BonBon bonBon = (BonBon) o;
        return Objects.equals(id, bonBon.id) && Objects.equals(candyType, bonBon.candyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, candyType);
    }
}
