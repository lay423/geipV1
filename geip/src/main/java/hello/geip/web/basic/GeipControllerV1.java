package hello.geip.web.basic;

import hello.geip.dao.MatchDao;
import hello.geip.dao.MatchRepository;
import hello.geip.domain.Match;
import hello.geip.dto.SummonerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;
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
    public String search(@RequestParam String name, Model model) throws IOException, SQLException, ClassNotFoundException {


        summonerDTO = search.getSummoner(name);

        MatchDao matchDao = new MatchDao();
        log.info("rjtlrl={}", summonerDTO.getName());

        List<Match> listMatchs = matchDao.get(summonerDTO.getName());

        if (listMatchs.isEmpty()) {
            Match[] matchs;
            try {
                matchs = search.getMatchArr();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            for(int i=0; i<20; i++){
                matchDao.add(matchs[i], summonerDTO.getName());
            }
            listMatchs = matchDao.get(summonerDTO.getName());
        }

        model.addAttribute("matches", listMatchs);
        model.addAttribute("summoner", summonerDTO);
        return "basic/search";
    }
    @GetMapping("getApi")
    public String getApi(@RequestParam String name, Model model) throws IOException, SQLException, ClassNotFoundException {

        MatchDao matchDao = new MatchDao();
        summonerDTO = search.getSummoner(name);

        Match[] matchs;
        try {
            matchs = search.getMatchArr();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<Match> listMatchs = matchDao.get(name);

        if (listMatchs.isEmpty()) {
            for(int i=0; i<20; i++){
                matchDao.add(matchs[i], summonerDTO.getName());

            }
        } else {
            matchDao.update(matchs, summonerDTO.getName());
        }

        for(int i=0; i<20; i++){
            matchRepository.save(matchs[i]);

        }




        List<Match> listMatchDaos = matchRepository.findAll();
        model.addAttribute("matches", listMatchDaos);
        model.addAttribute("summoner", summonerDTO);
        return "basic/search";
    }

    @GetMapping("spinnerTest")
    public String test() {
        return "basic/spinnerTest";
    }

    @GetMapping("search1")
    public String search1(@RequestParam String name, Model model) throws IOException {


        summonerDTO = search.getSummoner(name);

        Match[] matchDaos;
        try {
            matchDaos = search.getMatchArr();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for(int i=0; i<20; i++)
            matchRepository.save(matchDaos[i]);


        List<Match> listMatchDaos = matchRepository.findAll();
        model.addAttribute("matches", listMatchDaos);
        model.addAttribute("summoner", summonerDTO);
        return "basic/search";
    }
}