package com.project.ems.person;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract  class Person {

    private String name;

    private String email;

    private String password;

    private String mobile;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) &&
              email.equals(person.email) &&
              password.equals(person.password) &&
              mobile.equals(person.mobile) &&
              address.equals(person.address) &&
              birthday.equals(person.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, mobile, address, birthday);
    }
}
