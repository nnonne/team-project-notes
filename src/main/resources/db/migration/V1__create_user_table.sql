CREATE TABLE "user" (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) CHECK (LENGTH(name) >4),
    password VARCHAR(100) CHECK (LENGTH(password) >7),
    role VARCHAR(50) CHECK (LENGTH(role) >3)
);


INSERT INTO "user" (id, name, password, role)
VALUES (1,'user@mail.com', '{noop}user_password', 'USER'),
       (2, 'userr', '{bcrypt}$2a$12$NFyO8OCmZ4r2FPuQs4NwIeMC.idrOtHhwZodC45ZA5ogSVXo9QyYy', 'USER'),
       (3,'admin@mail.com', '{bcrypt}$2a$10$7c.F/reuaqXXzYeLq5GpQudP5cDsFtes7Vh.OZalExwDK4BzIWXq2', 'admin,user');