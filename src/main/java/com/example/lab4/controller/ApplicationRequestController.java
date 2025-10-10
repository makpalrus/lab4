package com.example.lab4.controller;

import com.example.lab4.model.ApplicationRequest;
import com.example.lab4.repository.ApplicationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/requests")
public class ApplicationRequestController {

    @Autowired
    public ApplicationRequestRepository repository;

    @GetMapping
    public String getAllRequests(Model model) {
        List<ApplicationRequest> requests = repository.findAll();
        model.addAttribute("requests", requests);
        model.addAttribute("filter", "all");
        return "index";
    }

    @GetMapping("/pending")
    public String getPendingRequests(Model model) {
        List<ApplicationRequest> requests = repository.findByHandledFalse();
        model.addAttribute("requests", requests);
        model.addAttribute("filter", "pending");
        return "index";
    }

    @GetMapping("/processed")
    public String getProcessedRequests(Model model) {
        List<ApplicationRequest> requests = repository.findByHandledTrue();
        model.addAttribute("requests", requests);
        model.addAttribute("filter", "processed");
        return "index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("request", new ApplicationRequest());
        return "add";
    }

    @PostMapping("/add")
    public String addRequest(@ModelAttribute ApplicationRequest request) {
        request.setHandled(false);
        repository.save(request);
        return "redirect:/requests";
    }

    @GetMapping("/{id}")
    public String viewRequest(@PathVariable Long id, Model model) {
        ApplicationRequest request = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID: " + id));
        model.addAttribute("request", request);
        return "details";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ApplicationRequest request = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID: " + id));
        model.addAttribute("request", request);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateRequest(@PathVariable Long id, @ModelAttribute ApplicationRequest request) {
        request.setId(id);
        repository.save(request);
        return "redirect:/requests/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteRequest(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/requests";
    }
}
