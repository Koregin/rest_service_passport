package ru.job4j.passport.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.passport.model.Passport;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientRESTController {

    private final RestTemplate restTemplate;

    private static final String API = "http://localhost:8080/passport";

    public ClientRESTController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/findAllPassports")
    public List<Passport> findAllPassports(@RequestParam(required = false) String series) {
        String newApi = API + "/find";
        if (series != null) {
            newApi = newApi + "?series=" + series;
            System.out.println(newApi);
        }
        return restTemplate.exchange(
                newApi, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Passport>>() {
                }).getBody();
    }

    @GetMapping("/findUnavailablePassports")
    public List<Passport> findUnavailablePassports() {
        return restTemplate.exchange(
                API + "/unavailable", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Passport>>() {
                }).getBody();
    }

    @GetMapping("/findReplaceablePassports")
    public List<Passport> findReplaceablePassports() {
        return restTemplate.exchange(
                API + "/find-replaceable", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Passport>>() {
                }).getBody();
    }

    @PostMapping("/createPassport")
    public ResponseEntity<Passport> createPassport(@RequestBody Passport passport) {
        Passport result = restTemplate.postForObject(API + "/save", passport, Passport.class);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/updatePassport")
    public ResponseEntity<Void> updatePassport(@RequestParam int id, @RequestBody Passport passport) {
        restTemplate.put(API + "/update?id=" + id, passport);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletePassport")
    public ResponseEntity<Void> deletePassport(@RequestParam int id) {
        restTemplate.delete(API + "/delete?id=" + id);
        return ResponseEntity.ok().build();
    }
}
