package training.springboot.com.demo.infra.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/")
public class HelloController {

    @RequestMapping(value="/{name}", method= RequestMethod.GET)
    public String index(@PathVariable("name") String name ){
        return "Hello World" + name;
    }
}
