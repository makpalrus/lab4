package com.example.lab4.controller;

import com.example.lab4.model.ApplicationRequest;
import com.example.lab4.model.Operators;
import com.example.lab4.repository.ApplicationRequestRepository;
import com.example.lab4.repository.CoursesRepository;
import com.example.lab4.repository.OperatorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApplicationRequestController {

    private final ApplicationRequestRepository requestRepo;
    private final CoursesRepository coursesRepo;
    private final OperatorsRepository operatorsRepo;

    @GetMapping("/")
    public String home() {
        return "redirect:/requests";
    }

    @GetMapping("/requests")
    public String listRequests(@RequestParam(required = false) String filter, Model model) {
        List<ApplicationRequest> requests;

        if ("new".equals(filter)) {
            requests = requestRepo.findByHandledFalse();
            model.addAttribute("title", "New Requests");
        } else if ("handled".equals(filter)) {
            requests = requestRepo.findByHandledTrue();
            model.addAttribute("title", "Processed Requests");
        } else {
            requests = requestRepo.findAll();
            model.addAttribute("title", "All Requests");
        }

        model.addAttribute("requests", requests);
        return "requests";
    }

    @GetMapping("/add-request")
    public String addRequestPage(Model model) {
        model.addAttribute("courses", coursesRepo.findAll());
        return "add-request";
    }

    @PostMapping("/add-request")
    public String addRequest(ApplicationRequest request) {
        request.setHandled(false);
        requestRepo.save(request);
        return "redirect:/requests";
    }

    @GetMapping("/assign/{id}")
    public String assignPage(@PathVariable Long id, Model model) {
        ApplicationRequest request = requestRepo.findById(id).orElse(null);

        if (request == null) {
            model.addAttribute("error", "Request with ID " + id + " not found.");
            return "error";
        }

        model.addAttribute("request", request);
        model.addAttribute("operators", operatorsRepo.findAll());
        return "assign";
    }

    @PostMapping("/assign/{id}")
    public String assignOperators(@PathVariable Long id, @RequestParam List<Long> operatorIds) {
        ApplicationRequest request = requestRepo.findById(id).orElseThrow();
        List<Operators> selectedOps = operatorsRepo.findAllById(operatorIds);
        request.setOperators(selectedOps);
        request.setHandled(true);
        requestRepo.save(request);
        return "redirect:/requests";
    }

    @GetMapping("/requests/{id}")
    public String viewRequest(@PathVariable Long id, Model model) {
        ApplicationRequest request = requestRepo.findById(id).orElse(null);

        if (request == null) {
            model.addAttribute("error", "Request with ID " + id + " not found.");
            return "error";
        }

        model.addAttribute("request", request);
        return "request-detail";
    }
}
