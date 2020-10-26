package training.springboot.com.demo.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.springboot.com.demo.domain.User;
import training.springboot.com.demo.domain.UserRepository;

import java.util.Optional;

@Service
public class DeleteUser {

    private UserRepository userRepository;

    public DeleteUser(@Autowired UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void with(Integer id){
        Optional<User> user = this.userRepository.findById(id);
        if(!user.isPresent())
            throw new IllegalArgumentException("Cannot find the userId" + id);
        this.userRepository.delete(user.get());
    }
}
