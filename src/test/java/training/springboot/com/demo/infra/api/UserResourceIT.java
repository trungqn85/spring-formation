package training.springboot.com.demo.infra.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import training.springboot.com.demo.SpringBootTrainingApplication;
import training.springboot.com.demo.action.SaveOrUpdateUser;
import training.springboot.com.demo.config.PersistenceTestConfig;
import training.springboot.com.demo.config.PropertiesResolverITConfig;
import training.springboot.com.demo.domain.User;
import training.springboot.com.demo.domain.UserSample;

import java.security.NoSuchAlgorithmException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {PersistenceTestConfig.class, PropertiesResolverITConfig.class})
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = SpringBootTrainingApplication.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class UserResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    SaveOrUpdateUser saveOrUpdateUser;

    private User user ;

    private ObjectMapper defaultObjectMapper = Jackson2ObjectMapperBuilder.json()
            .build();

    @Before
    public void setUp() throws NoSuchAlgorithmException {
        user = aUserInDatabase();
    }

    @Test
    public void getAUserSuccessfully() throws Exception {
        ResultActions result = mockMvc
                .perform(get("/v1/user/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        Assert.assertNotNull(result);
        String responseAsString = result.andReturn().getResponse().getContentAsString();
        User responseUser = defaultObjectMapper.readValue(responseAsString, User.class);

        Assert.assertEquals(responseUser.getName(), "Trung");
        Assert.assertEquals(responseUser.getPassword(), user.getPassword());
    }

    @Test
    public void createAUser() throws Exception {
        ResultActions result = mockMvc
                .perform(post("/v1/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nguyen\",\"password\":\"hello\"}")
                )
                .andExpect(status().isCreated())
                .andDo(print());

        Assert.assertNotNull(result);
        String responseAsString = result.andReturn().getResponse().getContentAsString();
        User responseUser = defaultObjectMapper.readValue(responseAsString, User.class);
        Assert.assertNotEquals(responseUser.getPassword(), "hello");
    }

    @Test
    public void updateAUser() throws Exception {
        String content = "{\"id\":" + user.getId() + ",\"name\":\"Nguyen2\",\"password\":\"" + user.getPassword() + "\"}";

        ResultActions result = mockMvc
                .perform(put("/v1/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andDo(print());

        Assert.assertNotNull(result);
        String responseAsString = result.andReturn().getResponse().getContentAsString();
        User responseUser = defaultObjectMapper.readValue(responseAsString, User.class);
        Assert.assertEquals(responseUser.getName(), "Nguyen2");
    }

    @Test
    public void deleteAUser() throws Exception {
                 mockMvc
                .perform(delete("/v1/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    private User aUserInDatabase() throws NoSuchAlgorithmException {
        return  saveOrUpdateUser.createWith(UserSample.anyUser().build());
    }
}