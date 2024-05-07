package com.ssafy.springbootapi.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


/*
 * TODO:: id to uuid
 */
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name="password", unique = false, nullable = false)
    private String password;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(255) default 'BUYER'")
    private UserType type = UserType.BUYER;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Address> addresses;

}
