package com.jcooldevelopment.easybank_api.service.Message;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;

public interface MessageService {

    public PaginatedResponse<MessageDto> getAll(int page, int size);

    public MessageDto getById(UUID id);

    public MessageDto create(CreateMessageDto message);

    public MessageDto update(UUID id, UpdateMessageDto message);

    public boolean delete(UUID id);
}
