package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.dto.Branch.BranchAdminDto;
import com.jcooldevelopment.easybank_api.dto.Branch.CreateBranchDto;
import com.jcooldevelopment.easybank_api.dto.Branch.UpdateBranchDto;
import com.jcooldevelopment.easybank_api.service.Branch.BranchService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/branch")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("")
    public ResponseEntity<Apiresponse<List<BranchAdminDto>>> getBranches() {
        List<BranchAdminDto> branches = this.branchService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<List<BranchAdminDto>>("Branches were found.", branches));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<BranchAdminDto>> getBranch(@PathVariable Long id){
        BranchAdminDto branch = this.branchService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<BranchAdminDto>("Branch found.", branch));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<BranchAdminDto>> postBranch(@Valid @RequestBody CreateBranchDto createBranchDto){
        BranchAdminDto savedBranch = this.branchService.create(createBranchDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .location(URI.create("/api/branch/" + savedBranch.getId()))
            .body(new Apiresponse<BranchAdminDto>("Branch saved.", savedBranch));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<BranchAdminDto>> putBranch(
        @PathVariable Long id,
        @Valid @RequestBody UpdateBranchDto updateBranchDto
    ) {
        BranchAdminDto updatedBranch = this.branchService.update(id, updateBranchDto);
        return ResponseEntity.status(HttpStatus.OK)
            .location(URI.create("/api/branch/" + updatedBranch.getId()))
            .body(new Apiresponse<BranchAdminDto>("Branch updated.", updatedBranch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteBranch(@PathVariable Long id){
        this.branchService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Apiresponse<>("Branch deleted.", null));
    }
}
