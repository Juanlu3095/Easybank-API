package com.jcooldevelopment.easybank_api.service.Branch;

import com.jcooldevelopment.easybank_api.mapper.BranchMapper;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.Branch;
import com.jcooldevelopment.easybank_api.contracts.entity.Country;
import com.jcooldevelopment.easybank_api.dto.Branch.BranchAdminDto;
import com.jcooldevelopment.easybank_api.dto.Branch.CreateBranchDto;
import com.jcooldevelopment.easybank_api.dto.Branch.UpdateBranchDto;
import com.jcooldevelopment.easybank_api.exception.ResourceAlreadyExists;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.repository.BranchRepository;
import com.jcooldevelopment.easybank_api.repository.CountryRepository;

@Service
public class BranchServiceImpl implements BranchService{

    private final BranchMapper branchMapper;
    private final BranchRepository branchRepository;
    private final CountryRepository countryRepository; // If only for reading without any rule (ifs), is better to user repository than service
    
    public BranchServiceImpl(BranchRepository branchRepository, CountryRepository countryRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.countryRepository = countryRepository;
        this.branchMapper = branchMapper;
    }

    @Override
    public List<BranchAdminDto> getAll() {
        List<Branch> branches = this.branchRepository.findAll();
        return branches.stream()
            .map(branch -> this.branchMapper.AdminEntityToDto(branch))
            .toList();
    }

    @Override
    public BranchAdminDto getById(Long id) {
        Branch branch = this.branchRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        
        return this.branchMapper.AdminEntityToDto(branch);
    }

    @Override
    public BranchAdminDto create(CreateBranchDto createBranchDto) {
        Country country = this.countryRepository.findById(createBranchDto.getCountryId())
            .orElseThrow(() -> new ResourceNotFoundException("This country does not exist."));

        int countByIban = this.branchRepository.countByIbanCode(createBranchDto.getIbanCode());
        int countByBic = this.branchRepository.countByBicCode(createBranchDto.getBicCode());

        if(countByIban > 0) throw new ResourceAlreadyExists("A branch with this IBAN code already exists.");
        if(countByBic > 0 && createBranchDto.getBicCode() != null) throw new ResourceAlreadyExists("A branch with this SWIFT/BIC code already exists.");

        Branch branchToSave = this.branchMapper.CreateBranchDtoToEntity(createBranchDto);
        branchToSave.setCountry(country);
        Branch savedBranch = this.branchRepository.save(branchToSave);
        return this.branchMapper.AdminEntityToDto(savedBranch);
    }

    @Override
    public BranchAdminDto update(Long id, UpdateBranchDto updateBranchDto) {
        Branch branch = this.branchRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        int countByIban = this.branchRepository.countByIbanCode(updateBranchDto.getIbanCode());
        int countByBic = this.branchRepository.countByBicCode(updateBranchDto.getBicCode());

        if(!updateBranchDto.getBicCode().equals(branch.getBicCode())) {
            if(countByBic > 0) throw new ResourceAlreadyExists("A branch with this SWIFT/BIC code already exists.");
        } else {
            if(countByBic > 1) throw new ResourceAlreadyExists("A branch with this SWIFT/BIC code already exists.");
        }

        if(!updateBranchDto.getIbanCode().equals(branch.getIbanCode())) {
            if(countByIban > 0) throw new ResourceAlreadyExists("A branch with this IBAN code already exists.");
        } else {
            if(countByIban > 1) throw new ResourceAlreadyExists("A branch with this IBAN code already exists.");
        }

        if(branch.getCountry().getId() != updateBranchDto.getCountryId()) {
            Country country = this.countryRepository.findById(updateBranchDto.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

            branch.setCountry(country);
        }

        branch.setAddress(updateBranchDto.getAddress());
        branch.setBicCode(updateBranchDto.getBicCode());
        branch.setCity(updateBranchDto.getCity());
        branch.setIbanCode(updateBranchDto.getIbanCode());
        branch.setLocalizationCode(updateBranchDto.getLocalizationCode());
        branch.setName(updateBranchDto.getName());

        Branch savedBranch = this.branchRepository.save(branch);
        return this.branchMapper.AdminEntityToDto(savedBranch);
    }

    @Override
    public boolean delete(Long id) {
        Branch branch = this.branchRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        this.branchRepository.delete(branch);
        return true;
    }
    
}
