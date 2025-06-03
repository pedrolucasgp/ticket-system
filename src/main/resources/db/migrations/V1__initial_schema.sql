CREATE TABLE coupon (
    id UUID NOT NULL PRIMARY KEY,
    code VARCHAR(255),
    discount FLOAT4 NOT NULL,
    percentage BOOLEAN NOT NULL,
    is_valid BOOLEAN NOT NULL
);

CREATE TABLE "user" (
    id UUID NOT NULL PRIMARY KEY,
    email VARCHAR(255),
    full_name VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    is_active BOOLEAN
);

CREATE TABLE flight (
    id UUID NOT NULL PRIMARY KEY,
    departure VARCHAR(255),
    destination VARCHAR(255),
    departure_date TIMESTAMP(6),
    arrival_date TIMESTAMP(6)
);

CREATE TABLE seat (
    id UUID NOT NULL PRIMARY KEY,
    seat_number VARCHAR(255),
    seat_class VARCHAR(255),
    price FLOAT4 NOT NULL,
    occupied BOOLEAN NOT NULL,
    flight_id UUID,
    user_id UUID UNIQUE
);

CREATE TABLE ticket (
    id UUID NOT NULL PRIMARY KEY,
    purchase_date TIMESTAMP(6),
    final_price FLOAT4 NOT NULL,
    status VARCHAR(255),
    coupon_id UUID,
    flight_id UUID,
    seat_id UUID UNIQUE,
    user_id UUID
);

ALTER TABLE seat
    ADD CONSTRAINT FK_seat_flight FOREIGN KEY (flight_id) REFERENCES flight;

ALTER TABLE seat
    ADD CONSTRAINT FK_seat_user FOREIGN KEY (user_id) REFERENCES event;

ALTER TABLE ticket
    ADD CONSTRAINT FK_ticket_coupon FOREIGN KEY (coupon_id) REFERENCES coupon;

ALTER TABLE ticket
    ADD CONSTRAINT FK_ticket_flight FOREIGN KEY (flight_id) REFERENCES flight;

ALTER TABLE ticket
    ADD CONSTRAINT FK_ticket_seat FOREIGN KEY (seat_id) REFERENCES seat;

ALTER TABLE ticket
    ADD CONSTRAINT FK_ticket_user FOREIGN KEY (user_id) REFERENCES event;