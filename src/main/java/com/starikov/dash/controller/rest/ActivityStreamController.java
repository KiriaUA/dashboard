package com.starikov.dash.controller.rest;

import com.starikov.dash.service.IActivityStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stream")
public class ActivityStreamController {

    @Autowired
    private IActivityStreamService activityStreamService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private String stream() {
        return activityStreamService.getStream();
    }


}
