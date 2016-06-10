package com.starikov.dash.controller;

import com.starikov.dash.entity.Release;
import com.starikov.dash.service.IEpicService;
import com.starikov.dash.service.IReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/releases")
public class ReleaseController {

    @Autowired
    private IReleaseService releaseService;

    @Autowired
    private IEpicService epicService;

    @RequestMapping(method = RequestMethod.GET)
    public String releases(Model model) {
        model.addAttribute("releases", releaseService.getAllReleases());
        model.addAttribute("release", new Release());
        return "admin/releases";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addRelease(@Valid @ModelAttribute Release release, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/releases";
        }
        releaseService.saveRelease(release);
        return "redirect:/admin/releases";
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public String updateRelease(@Valid Release release, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/releases";
        }
        releaseService.saveRelease(release);
        return "redirect:/admin/releases?saved=success";
    }
}