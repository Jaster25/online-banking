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
