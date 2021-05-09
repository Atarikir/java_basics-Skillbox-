package main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DefaultController {

    @RequestMapping("/")
    public String index() {
        //return (new Date()).toString();
        return String.valueOf((int) (1 + 100 * Math.random()));
    }
}
