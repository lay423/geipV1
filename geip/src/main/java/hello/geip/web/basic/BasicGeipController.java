package hello.geip.web.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class BasicGeipController {

    @GetMapping("/searchV1")
    public String search(){


        return "basic/index";
    }
}
