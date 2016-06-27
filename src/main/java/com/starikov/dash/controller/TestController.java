package com.starikov.dash.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starikov.dash.repository.ReleaseRepository;
import com.starikov.dash.service.IJiraUserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    @Qualifier("jsonMapper")
    private ObjectMapper jsonMapper;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    @Qualifier("jiraUserService")
    private IJiraUserService jiraUserService;

    @RequestMapping(path = "/2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String st() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Basic " + Base64.encodeBase64String("admin:admin".getBytes()));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(
                "http://localhost:8080/rest/api/2/search?jql=type= Task AND status = \"To Do\"  AND component = \"Games (ASH)\"",
                HttpMethod.GET, entity, String.class);
        try {
            JsonNode jsonNode = jsonMapper.readTree(exchange.getBody());
            return jsonNode.get("issues").get(0).get("fields").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String test(Model model) {
        return jiraUserService.getUserAvatar("kyrylos");
    }

    @RequestMapping(path = "release", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String releases(Model model) {
        try {
            return String.valueOf(jsonMapper.writeValueAsString(releaseRepository.findAll()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
