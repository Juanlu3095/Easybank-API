package com.jcooldevelopment.easybank_api.service.Message;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.contracts.entity.Message;
import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.MessageMapper;
import com.jcooldevelopment.easybank_api.repository.MessageRepository;
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
    public PaginatedResponse<MessageDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Message::getCreatedAt).descending());
        Page<Message> messages = this.messageRepository.findAll(pageable);
        Page<MessageDto> messagesToShow = new PageImpl<MessageDto>(messages.getContent() // PageImpl is the implementation of interface Page
            .stream()
            .map(message -> messageMapper.EntityToDto(message))
            .toList());
        PaginatedResponse<MessageDto> paginatedResult = DataFormater.paginate(messagesToShow);
        return paginatedResult;
    }

    @Override
    public MessageDto getById(UUID id){
        Message message = this.messageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Message not found."));

        return messageMapper.EntityToDto(message);
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
    public MessageDto update(UUID id, UpdateMessageDto message) {
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
        return messageMapper.EntityToDto(savedMessage);
    }

    @Override
    public boolean delete(UUID id) {
        Message messageToDelete = this.messageRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Message not found."));

        messageRepository.delete(messageToDelete);
        return true;
    }


}
