package com.jcooldevelopment.easybank_api.service.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.Message;
import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.MessageMapper;
import com.jcooldevelopment.easybank_api.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl (MessageRepository repository, MessageMapper mapper) {
        this.messageRepository = repository;
        this.messageMapper = mapper;
    }

    @Override
    public List<MessageDto> getAll() {
        List<Message> messages = this.messageRepository.findAll();
        List<MessageDto> messagesToShow = new ArrayList<MessageDto>();
        messagesToShow.addAll(messages.stream() // It adds all transformed messages to DTO
            .map(message -> messageMapper.EntityToDto(message))
            .toList());
        return messagesToShow;
    }

    @Override
    public MessageDto getById(UUID id) throws ResourceNotFoundException{
        Message message = this.messageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Message not found."));

        return messageMapper.EntityToDto(message);
    }

    @Override
    public MessageDto create(CreateMessageDto message) {
        Message messageToSave = messageMapper.CreateMessageDtoToEntity(message);
        Message savedMessage = messageRepository.save(messageToSave);
        return messageMapper.EntityToDto(savedMessage);
    }

    @Override
    public MessageDto update(UUID id, UpdateMessageDto message) throws ResourceNotFoundException {
        // Since we need a Message Entity, we use messageRepository instead of this class's getById method
        Message messageToUpdate = this.messageRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Message not found."));
        
        messageToUpdate.setName(message.getName());
        messageToUpdate.setSurname(message.getSurname());
        messageToUpdate.setEmail(message.getEmail());
        messageToUpdate.setPhone(message.getPhone());
        messageToUpdate.setMessage(message.getMessage());
        Message savedMessage = messageRepository.save(messageToUpdate);
        return messageMapper.EntityToDto(savedMessage);
    }

    @Override
    public boolean delete(UUID id) throws ResourceNotFoundException {
        Message messageToDelete = this.messageRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Message not found."));

        messageRepository.delete(messageToDelete);
        return true;
    }


}
