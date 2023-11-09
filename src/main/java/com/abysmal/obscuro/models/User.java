package com.abysmal.obscuro.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String username;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(columnDefinition = "VARCHAR(255)")
    private String profilePicture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SweetDreams> sweetDreams;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
    private List<Nightmare> nightmare;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SleepParalysis> sleepParalysis;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GameSettings> gameSettings;

    public User(User copy) {
        this.id = copy.id;
        this.username = copy.username;
        this.password = copy.password;
    }
}
