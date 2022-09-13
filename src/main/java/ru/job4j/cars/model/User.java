package ru.job4j.cars.model;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    @OneToMany (mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
}