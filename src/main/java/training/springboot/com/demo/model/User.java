package training.springboot.com.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String salt;

    private String name;

    private String password;

    private Date lastLogin;

    private Date lastUpdate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private List<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private List<Wallet>  wallets;
}
