package com.metro.bus.model.user;

import com.metro.bus.model.bus.Agency;
import com.metro.bus.model.bus.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private String id;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Agency> agencies = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets = new ArrayList<>();

    public String getRoleKey() {
        return this.role.getKey();
    }

    public String getFullName() {
        return firstName != null ? firstName.concat(" ").concat(lastName) : "";
    }

}
