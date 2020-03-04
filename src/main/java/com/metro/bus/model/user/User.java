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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Agency> agencies = new ArrayList<>();

//    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
//    private List<Ticket> tickets = new ArrayList<>();

    public String getFullName() {
        return firstName != null ? firstName.concat(" ").concat(lastName) : "";
    }

}
