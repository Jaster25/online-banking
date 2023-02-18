/*
    User
*/
-- admin1
insert
into users (user_id, created_at, is_deleted, updated_at, name, password, username)
values (1, '2023-01-21T12:35:31.406332', false, null, '관리자1',
        '{bcrypt}$2a$10$sjL4PxC.0Iz2nVqs0TGh4uI1XQODdfuV5LqpZeU35dxvx2h.v5U0i', 'admin1');
-- user1
insert
into users (user_id, created_at, is_deleted, updated_at, name, password, username)
values (2, '2023-02-21T12:35:31.406332', false, null, '회원1',
        '{bcrypt}$2a$10$sjL4PxC.0Iz2nVqs0TGh4uI1XQODdfuV5LqpZeU35dxvx2h.v5U0i', 'user1');
-- user2
insert
into users (user_id, created_at, is_deleted, updated_at, name, password, username)
values (3, '2023-02-21T12:35:31.406332', false, null, '회원2',
        '{bcrypt}$2a$10$sjL4PxC.0Iz2nVqs0TGh4uI1XQODdfuV5LqpZeU35dxvx2h.v5U0i', 'user2');

/*
    User Roles
*/
-- admin1
insert
into user_roles (user_id, roles)
values (1, 'USER');
insert
into user_roles (user_id, roles)
values (1, 'ADMIN');
-- user1
insert
into user_roles (user_id, roles)
values (2, 'USER');
-- user2
insert
into user_roles (user_id, roles)
values (3, 'USER');
