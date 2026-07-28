package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Branch;
import com.jcooldevelopment.easybank_api.dto.Branch.CreateBranchDto;
import com.jcooldevelopment.easybank_api.dto.Branch.BranchAdminDto;
import com.jcooldevelopment.easybank_api.dto.Branch.BranchDto;
import com.jcooldevelopment.easybank_api.dto.Branch.UpdateBranchDto;

@Component
public class BranchMapper {
    private final ModelMapper modelMapper;
    
    public BranchMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.setConfiguration();
    }

    // https://modelmapper.org/getting-started/
    // https://www.baeldung.com/java-modelmapper
    private void setConfiguration(){
        this.modelMapper.typeMap(CreateBranchDto.class, Branch.class)
            .addMappings(mapper -> {
                mapper.skip(src -> src.getCountryId(), Branch::setId); // The library mistakes CreateBranchDto.countryId with Branch.id
            });
            
    }

    public Branch CreateBranchDtoToEntity(CreateBranchDto createBranchDto) {
       return modelMapper.map(createBranchDto, Branch.class);
    }

    public Branch UpdateBranchDtoToEntity(UpdateBranchDto updateBranchDto) {
       return modelMapper.map(updateBranchDto, Branch.class);
    }

    public BranchAdminDto AdminEntityToDto(Branch branch) {
        return modelMapper.map(branch, BranchAdminDto.class);
    }

    public BranchDto EntityToDto(Branch branch) {
        return modelMapper.map(branch, BranchDto.class);
    }
}
