package training.springboot.com.demo.action;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.springboot.com.demo.model.User;
import training.springboot.com.demo.model.UserRepository;

import java.util.Optional;

@Service
public class SaveOrUpdateUser {

    private UserRepository userRepository;

    public SaveOrUpdateUser(@Autowired UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User saveWith(User user){
        return this.userRepository.save(user);
    }

    public User updateWith(User user) throws IllegalAccessException {
        Optional<User> foundUser = userRepository.findById(user.getId());
        if(!foundUser.isPresent())
            throw new IllegalAccessException("Cannot find the user" + user.getId());
        return this.userRepository.save(user);
    }


}
