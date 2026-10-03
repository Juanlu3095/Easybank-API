package com.jcooldevelopment.easybank_api.service.Message;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageAdminDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageFilterDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;

public interface MessageService {

    public PaginatedResponse<MessageAdminDto> getAll(MessageFilterDto filtersDto);

    public MessageAdminDto getById(UUID id);

    public MessageDto create(CreateMessageDto message);

    public MessageAdminDto update(UUID id, UpdateMessageDto message);

    public void delete(UUID id);
}
