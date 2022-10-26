package hello.geip.web;

import hello.geip.dto.WarNameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TeamBuildingApiController {

    @PostMapping("/api/teambuilding")
    public HttpStatus teamBuild(@RequestBody WarNameDTO warNameDTO) throws IOException {
        System.out.println(warNameDTO.toString());
        return HttpStatus.OK;
    }

    @PostMapping("/api")
    public void postTest(Map<String, Object> requestData){
        requestData.forEach((key, value) -> {
            System.out.println("key : " + key);
            System.out.println("value : " + value);
        });
    }

}
