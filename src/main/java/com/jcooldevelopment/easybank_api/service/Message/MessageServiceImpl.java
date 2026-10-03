package com.jcooldevelopment.easybank_api.service.Message;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.contracts.entity.Message;
import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageAdminDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageFilterDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.MessageMapper;
import com.jcooldevelopment.easybank_api.repository.MessageRepository;
import com.jcooldevelopment.easybank_api.specs.message.MessageSpecs;
import com.jcooldevelopment.easybank_api.utils.DataFormater;

@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl (MessageRepository repository, MessageMapper mapper) {
        this.messageRepository = repository;
        this.messageMapper = mapper;
    }

    @Override
    public PaginatedResponse<MessageAdminDto> getAll(MessageFilterDto filtersDto) {
        Specification<Message> filters = Specification
            .where(MessageSpecs.findByName(filtersDto.getName()))
            .and(MessageSpecs.findBySurname(filtersDto.getSurname()))
            .and(MessageSpecs.findByEmail(filtersDto.getEmail()))
            .and(MessageSpecs.findByPhone(filtersDto.getPhone()))
            .and(MessageSpecs.findByMessage(filtersDto.getMessage()));
        Pageable pageable = PageRequest.of(filtersDto.getPage() - 1, filtersDto.getSize(), Sort.by(Message::getCreatedAt).descending());
        Page<Message> messages = this.messageRepository.findAll(filters, pageable);
        Page<MessageAdminDto> messagesToShow = messages.map(message ->
            this.messageMapper.EntityToAdminDto(message)
        );
        return DataFormater.paginate(messagesToShow);
    }

    @Override
    public MessageAdminDto getById(UUID id){
        Message message = this.messageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Message not found."));

        return messageMapper.EntityToAdminDto(message);
    }

    @Override
    public MessageDto create(CreateMessageDto message) {
        Message messageToSave = messageMapper.CreateMessageDtoToEntity(message);
        // Since createdAt is null in API but gets Datetime in DB, we need to create it here for the user later
        messageToSave.setCreatedAt(LocalDateTime.now());
        Message savedMessage = messageRepository.save(messageToSave);
        return messageMapper.EntityToDto(savedMessage);
    }

    @Override
    public MessageAdminDto update(UUID id, UpdateMessageDto message) {
        // Since we need a Message Entity, we use messageRepository instead of this class's getById method
        Message messageToUpdate = this.messageRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Message not found."));
        
        messageToUpdate.setName(message.getName());
        messageToUpdate.setSurname(message.getSurname());
        messageToUpdate.setEmail(message.getEmail());
        messageToUpdate.setPhone(message.getPhone());
        messageToUpdate.setMessage(message.getMessage());
        // It actually returns the row in database, not the data from form because messageToUpdate has createdAt
        Message savedMessage = messageRepository.save(messageToUpdate);
        return messageMapper.EntityToAdminDto(savedMessage);
    }

    @Override
    public void delete(UUID id) {
        Message messageToDelete = this.messageRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Message not found."));

        messageRepository.delete(messageToDelete);
    }

}
