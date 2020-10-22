package training.springboot.com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wallet")
public class Wallet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal balance;

    private String type;

    private String accountId;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true, referencedColumnName = "id")
    @JsonIgnore
    private User user = null;
}
