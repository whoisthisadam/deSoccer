package com.kasperovich.desoccer.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "users")
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "line_1")
    String lineOne;

    @Column(name = "line_2")
    String lineTwo;

    @Column(name = "line_3")
    String lineThree;

    @Column(name = "city")
    String city;

    @Column(name = "province")
    String province;

    @Column(name = "country")
    String country;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    Set<User>users;
}
