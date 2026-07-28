package com.jcooldevelopment.easybank_api.service.Branch;

import java.util.List;

import com.jcooldevelopment.easybank_api.dto.Branch.BranchAdminDto;
import com.jcooldevelopment.easybank_api.dto.Branch.CreateBranchDto;
import com.jcooldevelopment.easybank_api.dto.Branch.UpdateBranchDto;

public interface BranchService {

    List<BranchAdminDto> getAll();

    BranchAdminDto getById(Long id);

    BranchAdminDto create(CreateBranchDto createBranchDto);

    BranchAdminDto update(Long id, UpdateBranchDto updateBranchDto);

    boolean delete(Long id);
}
