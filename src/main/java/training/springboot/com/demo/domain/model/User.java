package training.springboot.com.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable { // implement Serializable for JSON conversion
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String salt;

    private String name;

    private String password;

    private Date lastLogin;

    @LastModifiedDate
    private Date lastUpdate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)// should not use SUBSELECT MODE FOR UNLIMITED SIZE TABLE
    private List<Address> addresses = new ArrayList();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)// should not use SUBSELECT MODE FOR UNLIMITED SIZE TABLE
    private List<Wallet>  wallets = new ArrayList();
}
