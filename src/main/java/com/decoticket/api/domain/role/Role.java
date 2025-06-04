package com.decoticket.api.domain.role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "role")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public enum Values{
        ADMIN(1L),
        CUSTOMER(2L),
        EMPLOYEE(3L);

        long id;

        Values(long id) {
            this.id = id;
        }
    }
}
