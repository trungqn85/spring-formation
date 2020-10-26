package training.springboot.com.demo.action;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import training.springboot.com.demo.domain.User;
import training.springboot.com.demo.domain.UserRepository;
import training.springboot.com.demo.domain.UserSample;

import java.security.NoSuchAlgorithmException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SaveOrUpdateUserTest {


    private SaveOrUpdateUser saveOrUpdateUser;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init(){
        saveOrUpdateUser = new SaveOrUpdateUser(userRepository);
    }

    @Test
    public void createWith_userRepositoryIsCalled () throws NoSuchAlgorithmException {

        User user = UserSample.anyUser().build();

        saveOrUpdateUser.createWith(user);

        verify(userRepository).save(any());

    }

    @Test
    public void withNewPassword() throws NoSuchAlgorithmException, IllegalAccessException {
        User user = UserSample.anyUser().id(1).build();
        User newPasswordUser = UserSample.anyUser().password("newPassword").id(1).build();


        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any())).thenReturn(newPasswordUser.withObfuscatedPassword());
        User savedUserwithNewPassword =  saveOrUpdateUser.withNewPassword(1,"newPassword");

        Assert.assertNotEquals(user.getPassword(),savedUserwithNewPassword.getPassword());
        Assert.assertTrue(savedUserwithNewPassword.isSamePassword("newPasssword"));
    }


}