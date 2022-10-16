package hello.geip.web.basic;

import hello.geip.dto.MatchDto;
import hello.geip.dto.MatchRepository;
import hello.geip.domain.Summoner;
import hello.geip.dto.SummonerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
@Slf4j
public class GeipControllerV1 {

    private MatchRepository matchRepository;
    Search search = new Search();
    SummonerDTO summonerDTO;
    @Autowired
    public GeipControllerV1(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @GetMapping("searchBar")
    public String searchBar(){
        return "basic/searchBar";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("search")
    public String search(@RequestParam String name, Model model) throws IOException {


        summonerDTO = search.getSummoner(name);

        MatchDto[] matchDtos;
        try {
            matchDtos = search.getMatchArr();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for(int i=0; i<20; i++)
            matchRepository.save(matchDtos[i]);

        List<MatchDto> listMatchDtos = matchRepository.findAll();
        model.addAttribute("matches", listMatchDtos);
        model.addAttribute("summoner", summonerDTO);
        return "basic/search";
    }
}