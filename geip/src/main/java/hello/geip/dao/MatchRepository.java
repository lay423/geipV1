package hello.geip.dao;

import hello.geip.domain.Match;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MatchRepository {
    private static final Map<Integer, Match> store = new HashMap<>();

    public Match save(Match match) {
        store.put(match.getId(), match);
        return match;
    }

    public Match findById(int id) {
        return store.get(id);
    }

    public List<Match> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}