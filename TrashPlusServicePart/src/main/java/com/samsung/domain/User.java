package com.samsung.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "address")
    private String address;

    // TODO: improve system with Date class
    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    public User(String nickName, String address, String birthDate, String email, String password) {
        this.nickName = nickName;
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }
}
