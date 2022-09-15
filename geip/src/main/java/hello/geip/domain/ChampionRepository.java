package hello.geip.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChampionRepository {

    private static final Map<Long, Champion> store= new HashMap<>();
    private static long sequence = 0L;

    public Champion save(Champion champion) {
        champion.setId(++sequence);
        store.put(champion.getId(), champion);
        return champion;
    }

    public Champion findById(Long id) {
        return store.get(id);
    }

    public List<Champion> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long championId, Champion updateParam) {
        Champion findChampion = findById(championId);
        findChampion.setChampionName(updateParam.getChampionName());
        findChampion.setLine(updateParam.getLine());
        findChampion.setRole(updateParam.getRole());
    }

    public void clearStore() {
        store.clear();
    }
}
