package com.kapsta.sectorpickerback.controller.web;


import com.kapsta.sectorpickerback.data.request.UpdateOrCreateWorkerAndAttachSectorsRequest;
import com.kapsta.sectorpickerback.model.Sector;
import com.kapsta.sectorpickerback.model.Worker;
import com.kapsta.sectorpickerback.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;


@Controller
@Validated
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/")
    public String index(Model model, UpdateOrCreateWorkerAndAttachSectorsRequest workerForm, HttpServletRequest request) {
        model.addAttribute("workerForm", workerForm);
        model.addAttribute("sectors", indexService.getSectorsInHierarchicalList());
        try {
            Worker worker = indexService
                    .getWorker(UUID.fromString(request.getSession().getAttribute("workerId").toString()));
            model.addAttribute("worker", worker);
            model.addAttribute("selectedSectorIds",
                    worker.getUserSectors().stream().map(Sector::getId).toList());
        } catch (Exception e) {
            // Do nothing
        }
        return "main";
    }

    @PostMapping("/")
    public String indexSubmit(
            @Valid @ModelAttribute UpdateOrCreateWorkerAndAttachSectorsRequest dto,
            BindingResult result,
            HttpServletRequest request,
            Model model
    ) {
        Worker worker = indexService.handleUpdateOrCreateRequest(dto);
        request.getSession().setAttribute("workerId", worker.getId());
        model.addAttribute("errors", null);
        model.addAttribute("success", "Form request was successful");
        model.addAttribute("worker", worker);
        model.addAttribute("selectedSectorIds",
                worker.getUserSectors().stream().map(Sector::getId).toList());
        model.addAttribute("sectors", indexService.getSectorsInHierarchicalList());
        return "main";
    }
}
