package com.jcooldevelopment.easybank_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.dto.Branch.BranchDto;
import com.jcooldevelopment.easybank_api.service.Branch.BranchService;

@RestController
@RequestMapping("/api/client/branch")
public class BranchClientController {

    private final BranchService branchService;

    public BranchClientController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<List<BranchDto>>> getBranches() {
        List<BranchDto> branches = this.branchService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<List<BranchDto>>("Branches were found.", branches));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<BranchDto>> getBranch(@PathVariable Long id){
        BranchDto branch = this.branchService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<BranchDto>("Branch found.", branch));
    }
}
