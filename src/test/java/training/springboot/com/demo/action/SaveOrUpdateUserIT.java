package training.springboot.com.demo.action;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import training.springboot.com.demo.config.PersistenceTestConfig;
import training.springboot.com.demo.config.PropertiesResolverITConfig;
import training.springboot.com.demo.domain.User;
import training.springboot.com.demo.domain.UserSample;

import java.security.NoSuchAlgorithmException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceTestConfig.class,SaveOrUpdateUser.class, PropertiesResolverITConfig.class}, loader = AnnotationConfigContextLoader.class)
@AutoConfigureDataJpa
@ActiveProfiles("h2")
public class SaveOrUpdateUserIT {

    @Autowired
    private SaveOrUpdateUser saveOrUpdateUser;


    @Test
    public void createWith_mustCreateAUserSuccessfully() throws NoSuchAlgorithmException {

        //GIVEN
        User user = UserSample.anyUser().build();

        //WHEN
        User createdUser = saveOrUpdateUser.createWith(user);

        //THEN
        Assert.assertNotNull(createdUser.getId());
        Assert.assertNotNull(createdUser.getSalt());
        Assert.assertNotEquals(createdUser.getPassword(),user.getPassword());
        Assert.assertEquals(createdUser.getName(),user.getName());

    }

    @Test
    public void withNewPassword_mustUpdateUserPasswordSuccessfully() throws NoSuchAlgorithmException, IllegalAccessException {
        //GIVEN
        User user = UserSample.anyUser().build();
        User createdUser =saveOrUpdateUser.createWith(user);

        User userWithNewPassword = saveOrUpdateUser.withNewPassword(createdUser.getId(),"Hello");

        Assert.assertNotEquals(userWithNewPassword.getPassword(),user.getPassword());
        Assert.assertTrue(userWithNewPassword.isSamePassword("Hello"));
    }

    @Test
    public void updateWith_mustUpdateUserSuccessfully() throws IllegalAccessException, NoSuchAlgorithmException {
        User user = UserSample.anyUser().id(2).build();
        User createdUser =saveOrUpdateUser.createWith(user);

        createdUser.setName("Trung2");
        User updatedUser = saveOrUpdateUser.updateWith(createdUser);

        Assert.assertEquals(updatedUser.getName(),"Trung2");
        Assert.assertEquals(updatedUser.getPassword(),createdUser.getPassword());

    }

}