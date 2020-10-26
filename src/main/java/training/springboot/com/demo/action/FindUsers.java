package training.springboot.com.demo.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.springboot.com.demo.domain.User;
import training.springboot.com.demo.domain.UserRepository;

import java.util.Optional;

@Service
public class FindUsers {

    private UserRepository userRepository;

    public FindUsers(@Autowired UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User with(Integer userId){
        Optional<User> user = this.userRepository.findById(userId);
        if(!user.isPresent())
            throw new IllegalArgumentException("Cannot find the user with id" + userId);
        return user.get();
    }
}
