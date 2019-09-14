package com.example.backend.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private AuthorityName name;
    @OneToMany(mappedBy = "authority", cascade = CascadeType.ALL)
    private Set<User> user = new HashSet<>();

    public Authority() {
    }

    public Authority(AuthorityName name) {
        this.name = name;
    }

    public AuthorityName getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }
}
