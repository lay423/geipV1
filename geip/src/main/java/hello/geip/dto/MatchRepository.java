package hello.geip.dto;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MatchRepository {
    private static final Map<Integer, MatchDto> store = new HashMap<>();

    public MatchDto save(MatchDto matchDto) {
        store.put(matchDto.getId(), matchDto);
        return matchDto;
    }

    public MatchDto findById(int id) {
        return store.get(id);
    }

    public List<MatchDto> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}