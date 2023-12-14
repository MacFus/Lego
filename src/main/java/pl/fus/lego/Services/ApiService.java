package pl.fus.lego.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import pl.fus.lego.DTOs.InventorySetsDTO;
import pl.fus.lego.DTOs.PartDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Repositories.EntityRepository;
import pl.fus.lego.UTILS.ApiResponse;

import java.util.List;

@Service
public class ApiService {
    @Value("${api.urlParts}")
    private String urlParts;
    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final SetService setService;
    private final EntityRepository entityRepository;
    private final ObjectMapper objectMapper;

    public ApiService(RestTemplate restTemplate, SetService setService, EntityRepository entityRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.setService = setService;
        this.entityRepository = entityRepository;
        this.objectMapper = objectMapper;
    }

    public Object getParts(String setNum) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", apiKey);
            String modifiedUrl;
            List<PartDTO> parts = null;

            modifiedUrl = replacePathVariable(urlParts, setNum);

            List<SetDTO> set = entityRepository.findSet(setNum);
            if ( set.get(0).getNumParts() == 0) {
                List<InventorySetsDTO> embeddedSets = entityRepository.findEmbeddedSets(setNum);
                return embeddedSets;
            } else {
                parts = entityRepository.findPartsToSetList(List.of(setNum));
                if(parts.size()!=0){
                    int sum = parts.stream()
                            .mapToInt(PartDTO::getQuantity) // Konwertuje PartDTO na int (ilość)
                            .sum();
                    return parts;
                }else{
                    ResponseEntity<String> response = restTemplate.exchange(modifiedUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
//                    return response.getBody();
                    ApiResponse<PartDTO> apiResponse = objectMapper.readValue(response.getBody(), new TypeReference<ApiResponse<PartDTO>>(){});
                    return response.getBody();
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Exception while calling endpoint of external api",
                    e);
        }
    }

    private static String replacePathVariable(String url, String setNumber) {
        return url.replace("{set_num}", setNumber);
    }
//    private static String replaceParam(String url, String replacement, String param) {
//        String patternString = null;
//        switch (param) {
//            case "setNum":
//                patternString = "\\{set_num}";
//                break;
//        }
//        return url.replace(patternString, param);
//    }
}
