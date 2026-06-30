package com.example.scp.controller;

import com.example.scp.component.BonbonComponent;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bonbon")
public class BonbonController {

    private final BonbonComponent bonbonComponent;

    public BonbonController(BonbonComponent bonbonComponent) {
        this.bonbonComponent = bonbonComponent;
    }

    @GetMapping
    public Map<String, String> get() {
        bonbonComponent.comCheck();
        return Map.of("status", "health");
    }

    @PreAuthorize("hasAnyRole('USER') && #request.owner == authentication.name")
    @PostMapping("/pre-authorize")
    public BonbonResponse preAuthorize(
            @RequestBody BonbonResponse request
    ) {
        bonbonComponent.comCheck();
        return request;
    }

    @PreAuthorize("hasAnyRole('USER') && #request.?[owner != #authentication.name].isEmpty()")
    @PostMapping("/pre-authorize-list")
    public List<BonbonResponse> preAuthorizeList(
            Authentication authentication,
            @RequestBody List<BonbonResponse> request
    ) {
        bonbonComponent.comCheck();
        return request;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostAuthorize("hasAnyRole('USER') && returnObject.owner == authentication.name")
    @PostMapping("/post-authorize")
    public BonbonResponse postAuthorize(
            @RequestBody BonbonResponse request
    ) {
        bonbonComponent.comCheck();
        return request;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostAuthorize("hasAnyRole('USER') && returnObject.?[owner != #authentication.name].isEmpty()")
    @PostMapping("/post-authorize-list")
    public List<BonbonResponse> postAuthorizeList(
            Authentication authentication,
            @RequestBody List<BonbonResponse> request
    ) {
        bonbonComponent.comCheck();
        return request;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostAuthorize("hasAnyRole('USER')")
    @PreFilter(value = "filterObject.owner == authentication.name", filterTarget = "request")
    @PostMapping("/pre-filter")
    public List<BonbonResponse> preFilter(
            Authentication auth,
            @RequestBody List<BonbonResponse> request
    ) {
        bonbonComponent.comCheck();
        return request;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostAuthorize("hasAnyRole('USER')")
    @PostFilter(value = "filterObject.owner == authentication.name")
    @PostMapping("/post-filter")
    public List<BonbonResponse> postFilter(
            Authentication auth,
            @RequestBody List<BonbonResponse> request
    ) {
        bonbonComponent.comCheck();
        return request;
    }

    public record BonbonResponse(String owner) {

    }

}
