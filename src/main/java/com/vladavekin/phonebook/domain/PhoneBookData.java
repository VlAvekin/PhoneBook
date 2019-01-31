package com.vladavekin.phonebook.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "phone_book_data")
public class PhoneBookData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "patronymic")
    private String patronymic;

    @NotNull
    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "address")
    private String address;

    @Column(name = "e_mail")
    private String email;

}
