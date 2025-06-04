CREATE TABLE role (
    id BIGINT NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE users_roles (
    user_id UUID NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE users_roles
ADD CONSTRAINT FK_users_roles_role
FOREIGN KEY (role_id) REFERENCES role(id);

ALTER TABLE users_roles
ADD CONSTRAINT FK_users_roles_user
FOREIGN KEY (user_id) REFERENCES "user"(id);