package com.jcooldevelopment.easybank_api.service.Message;

import java.util.List;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;

public interface MessageService {

    public List<MessageDto> getAll();

    public MessageDto getById(UUID id) throws ResourceNotFoundException;

    public MessageDto create(CreateMessageDto message);

    public MessageDto update(UUID id, UpdateMessageDto message) throws ResourceNotFoundException;

    public boolean delete(UUID id) throws ResourceNotFoundException;
}
