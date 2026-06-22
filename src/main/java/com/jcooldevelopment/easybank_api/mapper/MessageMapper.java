package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.Message;
import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;

@Component
public class MessageMapper {
    private final ModelMapper modelMapper;
    
    public MessageMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Message CreateMessageDtoToEntity(CreateMessageDto createMessageDto) {
       return modelMapper.map(createMessageDto, Message.class);
    }

    public Message UpdateMessageDtoToEntity(UpdateMessageDto updateMessageDto) {
       return modelMapper.map(updateMessageDto, Message.class);
    }

    public MessageDto EntityToDto(Message message) {
        return modelMapper.map(message, MessageDto.class);
    }
}
