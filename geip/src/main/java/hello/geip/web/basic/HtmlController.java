package hello.geip.web.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/search")
    public String search(){

        return "basic/index";
    }

    @GetMapping("/search1")
    public String search1(){

        return "index";
    }
}
