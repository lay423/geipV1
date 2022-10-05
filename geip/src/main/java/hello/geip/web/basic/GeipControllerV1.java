package hello.geip.web.basic;

import hello.geip.domain.Match;
import hello.geip.domain.MatchRepository;
import hello.geip.domain.Summoner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
@Slf4j
public class GeipControllerV1 {

    private MatchRepository matchRepository;
    Search search = new Search();
    Summoner summoner;

    @Autowired
    public GeipControllerV1(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @GetMapping("{summonerName}")
    public String search(@PathVariable String summonerName, Model model) throws IOException {


        summoner = search.getSummoner(summonerName);

        Match[] match = new Match[0];
        try {
            match = search.getMatchArr();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for(int i=0; i<20; i++)
            matchRepository.save(match[i]);

        List<Match> matches = matchRepository.findAll();
        model.addAttribute("matches", matches);
        model.addAttribute("summoner", summoner);
        return "basic/search";
    }

    @GetMapping("/main")
    public String main(){

        return "basic/main";
    }

    @GetMapping("/index")
    public String index(){

        return "index";
    }
}
