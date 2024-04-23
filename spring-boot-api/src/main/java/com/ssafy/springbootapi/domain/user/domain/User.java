package com.ssafy.springbootapi.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name="password", unique = false, nullable = false)
    private String password;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @OneToMany(mappedBy = "user")
    @Column(name = "addresses" , nullable = true)
    private List<Address> addresses;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
