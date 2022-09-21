package com.kapsta.sectorpicker.controller.web;


import com.kapsta.sectorpicker.data.request.UpdateOrCreateWorkerAndAttachSectorsRequest;
import com.kapsta.sectorpicker.model.Sector;
import com.kapsta.sectorpicker.model.Worker;
import com.kapsta.sectorpicker.service.WorkerAssignmentService;
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
    private WorkerAssignmentService workerAssignmentService;

    @GetMapping("/")
    public String index(Model model, UpdateOrCreateWorkerAndAttachSectorsRequest workerForm, HttpServletRequest request) {
        model.addAttribute("workerForm", workerForm);
        model.addAttribute("sectors", workerAssignmentService.getSectorsInHierarchicalList());
        try {
            Worker worker = workerAssignmentService
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
        Worker worker = workerAssignmentService.handleUpdateOrCreateRequest(dto);
        request.getSession().setAttribute("workerId", worker.getId());
        model.addAttribute("errors", null);
        model.addAttribute("success", "Form request was successful");
        model.addAttribute("worker", worker);
        model.addAttribute("selectedSectorIds",
                worker.getUserSectors().stream().map(Sector::getId).toList());
        model.addAttribute("sectors", workerAssignmentService.getSectorsInHierarchicalList());
        return "main";
    }
}
