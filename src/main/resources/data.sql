/*
    User
*/
-- admin1
insert
into users (user_id, created_at, is_deleted, updated_at, name, password, username)
values (10, '2023-01-21T12:35:31.406332', false, null, '관리자1',
        '{bcrypt}$2a$10$sjL4PxC.0Iz2nVqs0TGh4uI1XQODdfuV5LqpZeU35dxvx2h.v5U0i', 'admin1');
-- user1
insert
into users (user_id, created_at, is_deleted, updated_at, name, password, username)
values (11, '2023-02-21T12:35:31.406332', false, null, '회원1',
        '{bcrypt}$2a$10$sjL4PxC.0Iz2nVqs0TGh4uI1XQODdfuV5LqpZeU35dxvx2h.v5U0i', 'user1');
-- user2
insert
into users (user_id, created_at, is_deleted, updated_at, name, password, username)
values (12, '2023-02-21T12:35:31.406332', false, null, '회원2',
        '{bcrypt}$2a$10$sjL4PxC.0Iz2nVqs0TGh4uI1XQODdfuV5LqpZeU35dxvx2h.v5U0i', 'user2');
-- user3(삭제됨)
insert
into users (user_id, created_at, is_deleted, updated_at, name, password, username)
values (13, '2021-02-21T12:35:31.406332', true, null, '김삭제',
        '{bcrypt}$2a$10$sjL4PxC.0Iz2nVqs0TGh4uI1XQODdfuV5LqpZeU35dxvx2h.v5U0i', 'user3');
-- user4
insert
into users (user_id, created_at, is_deleted, updated_at, name, password, username)
values (14, '2023-01-20T12:35:31.406332', false, null, '회원4',
        '{bcrypt}$2a$10$sjL4PxC.0Iz2nVqs0TGh4uI1XQODdfuV5LqpZeU35dxvx2h.v5U0i', 'user4');


/*
    User Roles
*/
-- admin1
insert
into user_roles (user_id, roles)
values (10, 'USER');
insert
into user_roles (user_id, roles)
values (10, 'ADMIN');
-- user1
insert
into user_roles (user_id, roles)
values (11, 'USER');
-- user2
insert
into user_roles (user_id, roles)
values (12, 'USER');
-- user3
insert
into user_roles (user_id, roles)
values (13, 'USER');
-- user4
insert
into user_roles (user_id, roles)
values (14, 'USER');


/*
    Bank
*/
-- A은행
insert
into bank (created_at, is_deleted, updated_at, branch, code, name)
values ('2023-02-13T15:24:31.970936', false, null, '오목교', '001', 'A은행');
-- B은행
insert
into bank (created_at, is_deleted, updated_at, branch, code, name)
values ('2023-02-14T15:24:31.970936', false, null, '광진구', '001', 'B은행');
-- C은행(삭제됨)
insert
into bank (created_at, is_deleted, updated_at, branch, code, name)
values ('2022-02-14T15:24:31.970936', true, null, '여의도', '001', 'C은행');


/*
    Product
*/
-- Bank1 passbook product1
insert
into product (created_at, is_deleted, updated_at, bank_id, benefit, conditions, content, ended_at, interest_rate, name,
              started_at,
              dtype)
values ('2023-02-12T11:08:29.628638', false, null, 1, '입출금 수수료 면제', '사회초년생', '사회초년생을 위한 상품', '2024-12-20T02:17:35', 3.6,
        '사회초년생 입출금 통장', '2023-02-20T02:17:35', 'PP');
insert
into passbook_product (term, passbook_product_id)
values (365, 1);
-- Bank1 passbook product2
insert
into product (created_at, is_deleted, updated_at, bank_id, benefit, conditions, content, ended_at, interest_rate, name,
              started_at,
              dtype)
values ('2023-02-14T11:08:29.628638', false, null, 1, '고금리', '30대', '30대를 위한 고금리 상품', '2023-12-20T02:17:35', 6.5,
        '30대를 위한 고금리 입출금 통장', '2023-09-10T02:17:35', 'PP');
insert
into passbook_product (term, passbook_product_id)
values (1500, 2);

/*
    Passbook
*/
-- user1 입출금 통장1
insert
into passbook(passbook_id, created_at, is_deleted, updated_at, account_number, balance, bank_id, interest_rate,
              passbook_product_id,
              password, user_id, dtype)
values (10, '2023-02-22T19:11:52.900180', false, null, '1-003-92834493', 150000, 1, 3.5, 1, 123456, 11, 'DW');
insert
into deposit_withdraw (transfer_limit, deposit_withdraw_id)
values (1000000, 10);
-- user1 입출금 통장2
insert
into passbook(passbook_id, created_at, is_deleted, updated_at, account_number, balance, bank_id, interest_rate,
              passbook_product_id,
              password, user_id, dtype)
values (11, '2023-03-01T10:11:52.900180', false, null, '1-003-30032493', 40000, 1, 2.3, 1, 123456, 11, 'DW');
insert
into deposit_withdraw (transfer_limit, deposit_withdraw_id)
values (10000, 11);
-- user2 입출금 통장1
insert
into passbook(passbook_id, created_at, is_deleted, updated_at, account_number, balance, bank_id, interest_rate,
              passbook_product_id,
              password, user_id, dtype)
values (12, '2023-03-01T10:11:52.900180', false, null, '2-103-69208003', 3640000, 2, 4.3, 2, 111155, 12, 'DW');
insert
into deposit_withdraw (transfer_limit, deposit_withdraw_id)
values (50000000, 12);
-- user4 입출금 통장1
insert
into passbook(passbook_id, created_at, is_deleted, updated_at, account_number, balance, bank_id, interest_rate,
              passbook_product_id,
              password, user_id, dtype)
values (13, '2023-02-01T10:11:52.900180', false, null, '4-502-40008003', 83500, 2, 2.3, 1, 555511, 14, 'DW');
insert
into deposit_withdraw (transfer_limit, deposit_withdraw_id)
values (5000, 13);


/*
    Transaction
*/
-- user1 입출금 통장1 -> user1 입출금 통장2
insert
into transaction_history(transaction_history_id, created_at, is_deleted, updated_at, amount, commission,
                         deposit_account_number,
                         deposit_passbook_id, memo, withdraw_account_number, withdraw_passbook_id)
values (20, '2023-01-22T22:06:28.644832', false, null, 2000, 500, '1-003-30032493', 11,
        'user1 입출금 통장1 -> user1 입출금 통장2',
        '1-003-92834493', 10);
-- user1 입출금 통장1 -> user2 입출금 통장1
insert
into transaction_history(transaction_history_id, created_at, is_deleted, updated_at, amount, commission,
                         deposit_account_number,
                         deposit_passbook_id, memo, withdraw_account_number, withdraw_passbook_id)
values (21, '2023-01-20T12:16:28.644832', false, null, 100000, 500, '2-103-69208003', 12,
        'user1 입출금 통장1 -> user2 입출금 통장1',
        '1-003-92834493', 10);
-- user2 입출금 통장1 -> user1 입출금 통장1
insert
into transaction_history(transaction_history_id, created_at, is_deleted, updated_at, amount, commission,
                         deposit_account_number,
                         deposit_passbook_id, memo, withdraw_account_number, withdraw_passbook_id)
values (22, '2023-02-20T12:16:28.644832', false, null, 1000, 500, '1-003-92834493', 10,
        'user2 입출금 통장1 -> user1 입출금 통장1',
        '2-103-69208003', 12);
-- user2 입출금 통장1 -> user4 입출금 통장1
insert
into transaction_history(transaction_history_id, created_at, is_deleted, updated_at, amount, commission,
                         deposit_account_number,
                         deposit_passbook_id, memo, withdraw_account_number, withdraw_passbook_id)
values (23, '2023-01-18T20:40:28.644832', false, null, 5000, 500, '4-502-40008003', 13,
        'user2 입출금 통장1 -> user4 입출금 통장1',
        '2-103-69208003', 12);