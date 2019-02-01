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
    @JoinColumn(name = "last_name")
    private String lastName;

    @NotNull
    @JoinColumn(name = "first_name")
    private String firstName;

    @NotNull
    @JoinColumn(name = "patronymic")
    private String patronymic;

    @NotNull
    @JoinColumn(name = "mobile_phone")
    private String mobilePhone;

    @JoinColumn(name = "home_phone")
    private String homePhone;

    @JoinColumn(name = "address")
    private String address;

    @JoinColumn(name = "e_mail")
    private String email;

   @JoinColumn(name = "user_id")
   @ManyToOne(fetch = FetchType.EAGER)
   private User author;

}
