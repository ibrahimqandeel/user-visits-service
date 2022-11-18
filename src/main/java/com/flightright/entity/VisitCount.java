package com.flightright.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class VisitCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "count", nullable = false)
    private int count;
}
