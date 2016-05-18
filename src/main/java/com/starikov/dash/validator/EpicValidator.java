package com.starikov.dash.validator;

import com.starikov.dash.entity.Epic;
import com.starikov.dash.entity.Release;
import com.starikov.dash.service.IReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EpicValidator implements Validator {

    @Autowired
    private IReleaseService releaseService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Epic.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String releaseName = ((Epic) target).getReleaseName();
        if (releaseName != null) {
            Release releaseByName = releaseService.getReleaseByName(releaseName);
            ((Epic) target).setRelease(releaseByName);
        }
    }
}
