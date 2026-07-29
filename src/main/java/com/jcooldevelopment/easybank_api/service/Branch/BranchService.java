package com.jcooldevelopment.easybank_api.service.Branch;

import java.util.List;

import com.jcooldevelopment.easybank_api.dto.Branch.BranchAdminDto;
import com.jcooldevelopment.easybank_api.dto.Branch.BranchDto;
import com.jcooldevelopment.easybank_api.dto.Branch.CreateBranchDto;
import com.jcooldevelopment.easybank_api.dto.Branch.UpdateBranchDto;

public interface BranchService {

    /**
    * Returns all branches for client role.
    * @return List of branches dto.
    */
    List<BranchDto> getAll();

    /**
    * Returns all branches for admin role.
    * @return List of branches dto.
    */
    List<BranchAdminDto> getAllForAdmin();

    /**
     * Return a branch by given id.
     * @param id The Long id of branch to search.
     * @return BranchDto for client role.
     */
    BranchDto getById(Long id);

    /**
     * Return a branch by given id.
     * @param id The Long id of branch to search.
     * @return BranchDto for admin role.
     */
    BranchAdminDto getByIdForAdmin(Long id);

    /**
     * Creates a branch. Only for admin role.
     * @param createBranchDto Dto for creating a new branch.
     * @return BranchDto for admin role.
     */
    BranchAdminDto create(CreateBranchDto createBranchDto);

    /**
     * Updates a branch by given id. Only for admin role.
     * @param id The Long id of branch to search.The Long id of branch to search.
     * @param createBranchDto Dto for updating a new branch.
     * @return BranchDto for admin role.
     */
    BranchAdminDto update(Long id, UpdateBranchDto updateBranchDto);

    /**
     * Deletes a branch by given id. Only for admin role.
     * @param id The Long id of branch to search. 
     */
    void delete(Long id);
}
