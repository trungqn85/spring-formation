package training.springboot.com.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

    @JsonIgnore
    private String salt;

    private String name;

    private String password;

    @JsonIgnore
    private Date lastLogin;


    @JsonIgnore
    @LastModifiedDate
    private Date lastUpdate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)// should not use SUBSELECT MODE FOR UNLIMITED SIZE TABLE
    private List<Address> addresses = new ArrayList();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)// should not use SUBSELECT MODE FOR UNLIMITED SIZE TABLE
    private List<Wallet>  wallets = new ArrayList();

    public User withObfuscatedPassword() throws NoSuchAlgorithmException {
        Long newSalt = SecureRandom.getInstanceStrong().nextLong();
        return User.builder()
                .addresses(this.addresses)
                .id(this.id)
                .lastLogin(this.lastLogin)
                .name(this.name)
                .lastUpdate(this.lastUpdate)
                .password(this.getSHA512SecurePassword(this.password,newSalt.toString()))
                .salt(newSalt.toString())
                .build();
    }

    public Boolean isSamePassword(String validatingPassword){
        return getSHA512SecurePassword(validatingPassword,this.salt).equals(this.password);
    }

    private String getSHA512SecurePassword(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
