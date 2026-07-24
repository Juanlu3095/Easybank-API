package com.jcooldevelopment.easybank_api.service.Branch;

import java.util.List;

import com.jcooldevelopment.easybank_api.dto.Branch.BranchDto;
import com.jcooldevelopment.easybank_api.dto.Branch.CreateBranchDto;
import com.jcooldevelopment.easybank_api.dto.Branch.UpdateBranchDto;

public interface BranchService {

    List<BranchDto> getAll();

    BranchDto getById(Long id);

    BranchDto create(CreateBranchDto createBranchDto);

    BranchDto update(Long id, UpdateBranchDto updateBranchDto);

    boolean delete(Long id);
}
