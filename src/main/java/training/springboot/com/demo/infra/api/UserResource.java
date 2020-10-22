package training.springboot.com.demo.infra.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.springboot.com.demo.action.DeleteUser;
import training.springboot.com.demo.action.FindUsers;
import training.springboot.com.demo.action.SaveOrUpdateUser;
import training.springboot.com.demo.domain.model.User;

@RestController
@RequestMapping(value = "/v1/")
public class UserResource {

    public UserResource(@Autowired SaveOrUpdateUser saveOrUpdateUser,
                        @Autowired FindUsers findUsers,
                        @Autowired DeleteUser deleteUser){
        this.saveOrUpdateUser = saveOrUpdateUser;
        this.findUsers = findUsers;
        this.deleteUser = deleteUser;
    }

    SaveOrUpdateUser saveOrUpdateUser;
    FindUsers findUsers;
    DeleteUser deleteUser;

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<Object> createAUser(@RequestBody  User user){
        try{
            return new ResponseEntity(saveOrUpdateUser.saveWith(user), HttpStatus.CREATED);
        }catch (IllegalArgumentException illegalArgumentException){
            return new ResponseEntity<>("Cannot create the user", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Cannot create the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user")
    @ResponseBody
    public ResponseEntity saveAUser(@RequestBody User user){
        try{
            return new ResponseEntity(saveOrUpdateUser.updateWith(user), HttpStatus.OK);
        }catch (IllegalArgumentException illegalArgumentException){
            return new ResponseEntity<>("Cannot create the user", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity getUser(@PathVariable Integer id){
        try{
            return new ResponseEntity(this.findUsers.with(id),HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity("Cannot find the user",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable Integer id){
        try{
            this.deleteUser.with(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IllegalArgumentException illegalArgumentException){
            return new ResponseEntity<>("Cannot delete the user", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
