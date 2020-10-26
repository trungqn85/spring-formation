package training.springboot.com.demo.domain;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

class UserTest {

    @Test
    void withObfuscatedPassword() throws NoSuchAlgorithmException {
        User user = UserSample.anyUser().build();
        User obfuscatedPasswordUser = user.withObfuscatedPassword();
        Assert.assertNotNull(obfuscatedPasswordUser.getPassword());
        Assert.assertNotNull(obfuscatedPasswordUser.getSalt());
        Assert.assertNotEquals(user.getPassword(),obfuscatedPasswordUser.getPassword());

    }

    @Test
    void isSamePassword() {
        User user = UserSample.anyUser().build();
        Assert.assertTrue(user.isSamePassword("hello"));
    }
}