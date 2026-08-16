package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Movement;
import com.jcooldevelopment.easybank_api.dto.Movement.MovementDto;
import com.jcooldevelopment.easybank_api.dto.Movement.MovementPerOperationDto;
import com.jcooldevelopment.easybank_api.dto.Movement.MovementPerOperationOnlyIban;
import com.jcooldevelopment.easybank_api.projections.movement.MovementProjection;

@Component
public class MovementMapper {

    private final ModelMapper modelMapper;
    private final AccountMapper accountMapper;

    public MovementMapper (ModelMapper modelMapper, AccountMapper accountMapper){
        this.modelMapper = modelMapper;
        this.accountMapper = accountMapper;
    }

    public MovementDto EntityToDto(Movement movement) {
        MovementDto movementDto = new MovementDto();
        movementDto.setId(movement.getId());
        movementDto.setOperationId(movement.getOperation().getId());
        if (movement.getAccount() != null) movementDto.setAccount(this.accountMapper.EntityToDto(movement.getAccount()));
        if (movement.getExternalAccount() != null) movementDto.setExternalAccount(movement.getExternalAccount());
        movementDto.setAmount(movement.getAmount());
        movementDto.setCreatedAt(movement.getCreatedAt());
        movementDto.setUpdatedAt(movement.getUpdatedAt());
        return movementDto;
    }

    /**
     * Entity to Dto with no operation, since this movements will come with the operation in data
     * @param movement The entity Movement to transform.
     * @return Movement Dto with no operation.
     */
    public MovementPerOperationDto EntityToMovementPerOperationDto(Movement movement){
        MovementPerOperationDto movementDto = new MovementPerOperationDto();
        movementDto.setId(movement.getId());
        if (movement.getAccount() != null) movementDto.setAccount(this.accountMapper.EntityToDtoNoUsers(movement.getAccount()));
        if (movement.getExternalAccount() != null) movementDto.setExternalAccount(movement.getExternalAccount());
        movementDto.setAmount(movement.getAmount());
        movementDto.setCreatedAt(movement.getCreatedAt());
        movementDto.setUpdatedAt(movement.getUpdatedAt());
        return movementDto;
    }

    public MovementPerOperationOnlyIban EntityToMovementOnlyIban(Movement movement){
        MovementPerOperationOnlyIban movementOnlyIban = new MovementPerOperationOnlyIban();
        movementOnlyIban.setId(movement.getId());
        movementOnlyIban.setAmount(movement.getAmount());
        movementOnlyIban.setOperationId(movement.getOperation().getId());

        if(movement.getAccount().getIban() != null) {
            movementOnlyIban.setAccountIban(movement.getAccount().getIban());
        } else {
            movementOnlyIban.setAccountIban(movement.getExternalAccount());
        }

        return movementOnlyIban;
    }

    public MovementPerOperationOnlyIban MovementProjectionToMovementOnlyIban(MovementProjection movementProjection){
        MovementPerOperationOnlyIban movementOnlyIban = new MovementPerOperationOnlyIban();
        movementOnlyIban.setId(movementProjection.id());
        movementOnlyIban.setAmount(movementProjection.amount());
        movementOnlyIban.setOperationId(movementProjection.operationId());

        if(movementProjection.accountIban() != null) {
            movementOnlyIban.setAccountIban(movementProjection.accountIban());
        } else {
            movementOnlyIban.setAccountIban(movementProjection.externalAccountIban());
        }

        return movementOnlyIban;
    }

}
