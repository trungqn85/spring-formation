package training.springboot.com.demo.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.springboot.com.demo.domain.User;
import training.springboot.com.demo.domain.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class SaveOrUpdateUser {

    private UserRepository userRepository;

    public SaveOrUpdateUser(@Autowired UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createWith(User user) throws NoSuchAlgorithmException {
        return this.userRepository.save(user.withObfuscatedPassword());
    }

    public User withNewPassword(Integer userId, String newPassword) throws IllegalAccessException, NoSuchAlgorithmException {
        Optional<User> foundUser = userRepository.findById(userId);
        if(!foundUser.isPresent())
            throw new IllegalAccessException("Cannot find the user" + userId);
        foundUser.get().setPassword(newPassword);
        return this.userRepository.save(foundUser.get().withObfuscatedPassword());
    }
    public User updateWith(User user) throws IllegalAccessException {
        Optional<User> foundUser = userRepository.findById(user.getId());
        if(!foundUser.isPresent())
            throw new IllegalAccessException("Cannot find the user" + user.getId());
        return this.userRepository.save(user);
    }


}
