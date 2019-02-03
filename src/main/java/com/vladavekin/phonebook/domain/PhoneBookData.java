package com.vladavekin.phonebook.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "phone_book_data")
public class PhoneBookData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @JoinColumn(name = "last_name")
    @Size(min = 4, message = "Last name minimum 4 char")
    private String lastName;

    @NotNull
    @JoinColumn(name = "first_name")
    @Size(min = 4, message = "First name minimum 4 char")
    private String firstName;

    @NotNull
    @JoinColumn(name = "patronymic")
    @Size(min = 4, message = "Patronymic minimum 4 char")
    private String patronymic;

    @NotNull
    @JoinColumn(name = "mobile_phone")
    @NotBlank(message = "Please fill the mobile phone")
    @Size(max = 13, message = "Mobile phone maximum 13 char format +380(66)1234567")
    private String mobilePhone;

    @JoinColumn(name = "home_phone")
    private String homePhone;

    @JoinColumn(name = "address")
    private String address;

    @JoinColumn(name = "e_mail")
    @Email(message = "Email is not correct")
    private String email;

   @JoinColumn(name = "user_id")
   @ManyToOne(fetch = FetchType.EAGER)
   private User author;

}
