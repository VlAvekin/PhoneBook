delete from phone_book_data;

insert into phone_book_data(id, last_name, first_name, patronymic, mobile_phone, home_phone, address, email, user_id) values
(1, 'First', 'Ones', 'First', '+380991234567', '', '', '', 1),
(2, 'Second', 'Twos', 'Second', '+380661234567', '', '', '', 1),
(3, 'Third', 'Three', 'Third', '+380951234567', '', '', '', 1),
(4, 'Fourth', 'Four', 'Fourth', '+380931234567', '', '', '', 1),
(5, 'Fiveth', 'Five', 'Fiveth', '+380931234567', '', '', '', 2),
(6, 'Sixth', 'Sixs', 'Sixth', '+380931234567', '1234', '1234567', 'test@test.com', 2);

alter sequence hibernate_sequence restart with 10;