package com.starikov.dash.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AbstractJiraService {

    @Value("${jira.user}")
    protected String userAndPass;

    @Autowired
    @Qualifier("jsonMapper")
    protected ObjectMapper jsonMapper;


    protected String getData(String url) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + userAndPass);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> exchange = restTemplate.exchange(
                url,
                HttpMethod.GET, entity, String.class);

        return exchange.getBody();
    }
}
