package com.jcooldevelopment.easybank_api.service.Message;

import java.util.List;
import java.util.UUID;

import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;

public interface MessageService {

    public List<MessageDto> getAll();

    public MessageDto getById(UUID id);

    public MessageDto create(CreateMessageDto message);

    public MessageDto update(UUID id, UpdateMessageDto message);

    public boolean delete(UUID id);
}
