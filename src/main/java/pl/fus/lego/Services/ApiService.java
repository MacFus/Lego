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
import pl.fus.lego.UTILS.Result;

import java.util.List;

@Service
public class ApiService {
    @Value("${api.urlParts}")
    private String urlParts;
    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final EntityRepository entityRepository;
    private final ObjectMapper objectMapper;

    public ApiService(RestTemplate restTemplate, EntityRepository entityRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.entityRepository = entityRepository;
        this.objectMapper = objectMapper;
    }

//    public ApiResponse getParts(String setNum) {
//
//        List<PartDTO> parts = null;
//
//        List<SetDTO> set = entityRepository.findSet(setNum);
//        if (set.get(0).getNumParts() == 0) {
//            List<InventorySetsDTO> embeddedSets = entityRepository.findEmbeddedSets(setNum);
//            return new ApiResponse<>(embeddedSets, embeddedSets.size(), 0, true);
//        } else {
//            parts = entityRepository.findPartsToSetList(List.of(setNum));
//            if (parts.size() != 0) {
//                int sum = parts.stream()
//                        .mapToInt(PartDTO::getQuantity) // Konwertuje PartDTO na int (ilość)
//                        .sum();
//                return new ApiResponse<>(parts, parts.size(), sum);
//            }
//            return null;
//        }

//    public ApiResponse getParts(String setNum) {
//        try {
//
//            String modifiedUrl;
//            List<PartDTO> parts = null;
//
//            List<SetDTO> set = entityRepository.findSet(setNum);
//            if (set.get(0).getNumParts() == 0) {
//                List<InventorySetsDTO> embeddedSets = entityRepository.findEmbeddedSets(setNum);
//                return new ApiResponse<>(embeddedSets, embeddedSets.size(), 0, true);
//            } else {
//                parts = entityRepository.findPartsToSetList(List.of(setNum));
//                if (parts.size() != 0) {
//                    int sum = parts.stream()
//                            .mapToInt(PartDTO::getQuantity) // Konwertuje PartDTO na int (ilość)
//                            .sum();
//                    return new ApiResponse<>(parts, parts.size(), sum);
//                } else {
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.add("Authorization", apiKey);
//                    modifiedUrl = replacePathVariable(urlParts, setNum);
//                    ResponseEntity<String> response = restTemplate.exchange(modifiedUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
//                    ApiResponse apiResponse = objectMapper.readValue(response.getBody(), ApiResponse.class);
//                    return apiResponse;
//                }
//            }
//        } catch (Exception e) {
//            throw new ResponseStatusException(
//                    HttpStatus.INTERNAL_SERVER_ERROR,
//                    "Exception while calling endpoint of external api",
//                    e);
//        }
//    }


//    private static String replacePathVariable(String url, String setNumber) {
//        return url.replace("{set_num}", setNumber);
//    }
//}
//    }
}