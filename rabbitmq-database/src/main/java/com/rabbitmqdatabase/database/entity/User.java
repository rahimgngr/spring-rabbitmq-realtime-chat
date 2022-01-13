package com.rabbitmqdatabase.database.entity;User


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "user_pass", length = 100)
    private String userPass;

    @Column(name = "user_roles", length = 100)
    private String role;

}
