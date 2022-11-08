package hello.geip.web;

import hello.geip.dao.MatchDao;
import hello.geip.dao.MatchRepository;
import hello.geip.domain.Match;
import hello.geip.dto.SummonerDTO;
import hello.geip.dto.TeamBuildingRiotApiDTO;
import hello.geip.service.SearchService;
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

    private final MatchDao matchDao;
    private MatchRepository matchRepository;
    SearchService searchService = new SearchService();
    SummonerDTO summonerDTO;

    @Autowired
    public GeipControllerV1(MatchDao matchDao, MatchRepository matchRepository) {
        this.matchDao = matchDao;
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


        summonerDTO = searchService.getSummoner(name);

        log.info("rjtlrl={}", summonerDTO.getName());


        List<Match> listMatchs = matchDao.get(summonerDTO.getName());
        if (listMatchs == null) {
            Match[] matchs;
            try {
                matchs = searchService.getMatchArr();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            for(int i=0; i<20; i++){
                matchDao.add(matchs[i], summonerDTO.getName());
            }
            listMatchs = matchDao.get(summonerDTO.getName());
        }
        TeamBuildingRiotApiDTO teamBuildingRiotApiDTO;
        teamBuildingRiotApiDTO = searchService.teamBuildingSearchSummoner(summonerDTO.getName());

        model.addAttribute("league", teamBuildingRiotApiDTO);
        model.addAttribute("matches", listMatchs);
        model.addAttribute("summoner", summonerDTO);
        return "basic/search";
    }
    @GetMapping("getApi")
    public String getApi(@RequestParam String name, Model model) throws IOException, SQLException, ClassNotFoundException {

        summonerDTO = searchService.getSummoner(name);

        Match[] matchs;
        try {
            matchs = searchService.getMatchArr();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<Match> listMatchs = matchDao.get(name);

        if (listMatchs.isEmpty()) {
            for(int i=0; i<20; i++){
                matchDao.add(matchs[i], summonerDTO.getName());

            }
        } else {
            List<Integer> match_ids = matchDao.getMatch_ids(summonerDTO.getName());
            for(int i=0; i<20; i++){
                matchDao.update(matchs[i], summonerDTO.getName(), match_ids.get(i));
            }

        }

        for(int i=0; i<20; i++){
            matchRepository.save(matchs[i]);

        }


        TeamBuildingRiotApiDTO teamBuildingRiotApiDTO;
        teamBuildingRiotApiDTO = searchService.teamBuildingSearchSummoner(summonerDTO.getName());

        model.addAttribute("league", teamBuildingRiotApiDTO);

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


        summonerDTO = searchService.getSummoner(name);

        Match[] matchDaos;
        try {
            matchDaos = searchService.getMatchArr();
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