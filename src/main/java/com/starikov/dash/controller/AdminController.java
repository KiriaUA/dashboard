package com.starikov.dash.controller;

import com.starikov.dash.entity.Epic;
import com.starikov.dash.entity.Release;
import com.starikov.dash.service.IEpicService;
import com.starikov.dash.service.IReleaseService;
import com.starikov.dash.validator.EpicValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Controller responsible for admin page
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EpicValidator epicValidator;

    @Autowired
    private IEpicService epicService;

    @Autowired
    private IReleaseService releaseService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("epics", epicService.getEpics());
        model.addAttribute("releases", releaseService.getAllReleases());
        model.addAttribute("epic", new Epic());
        return "admin/index";
    }

    @RequestMapping(path = "add", method = RequestMethod.POST)
    public String addNewEpic(@Valid @ModelAttribute Epic epic, BindingResult bindingResult) {
        epicValidator.validate(epic, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/index";
        }
        epicService.addEpic(epic);
        return "redirect:/admin";
    }

    @RequestMapping(path = "delete", method = RequestMethod.DELETE)
    public String deleteEpic(@RequestParam("epicId") int epicId) {
        Logger.getLogger(AdminController.class).warn("Remove epic with id = " + epicId);
        epicService.removeEpicById(epicId);
        return "redirect:/admin";
    }

    @RequestMapping(path = "update", method = RequestMethod.PUT)
    public String updateEpic(@Valid Epic epic, BindingResult bindingResult) {
        epicValidator.validate(epic, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/index";
        }
        epicService.updateEpic(epic);
        return "redirect:/admin";
    }

    @RequestMapping(path = "releases", method = RequestMethod.GET)
    public String releases(Model model) {
        model.addAttribute("releases", releaseService.getAllReleases());
        model.addAttribute("release", new Release());
        return "admin/releases";
    }

    @RequestMapping(path = "releases/add", method = RequestMethod.POST)
    public String addRelease(@Valid @ModelAttribute Release release, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/releases";
        }
        releaseService.saveRelease(release);
        return "redirect:/admin/releases";
    }

    @RequestMapping(path = "releases/update", method = RequestMethod.PUT)
    public String updateEpic(@Valid Release release, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/releases";
        }
        releaseService.saveRelease(release);
        return "redirect:/admin/releases";
    }

}
