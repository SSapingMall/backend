package com.ssafy.springbootapi.domain.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "address")
    private String address;

    @Column(name = "isDefault")
    @Enumerated(EnumType.STRING)
    private IsDefault isDefault;

    @ManyToOne // optional true => nullable
    @JoinColumn(name = "id")
    private User user;

}

