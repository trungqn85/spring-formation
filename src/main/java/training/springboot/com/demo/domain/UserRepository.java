package training.springboot.com.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import training.springboot.com.demo.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
