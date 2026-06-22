package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.service.Message.MessageService;

import jakarta.validation.Valid;

import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController (MessageService service) {
        this.messageService = service;
    }

    @GetMapping("/")
    public ResponseEntity<Apiresponse<List<MessageDto>>> getMessages() {
        List<MessageDto> messages = this.messageService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<List<MessageDto>>("Messages were found.", messages));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<MessageDto>> getMessage(@PathVariable UUID id) {
        try {
            MessageDto message = this.messageService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<MessageDto>("Message found.", message));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Apiresponse<>("Message not found.", null));
        }
    }

    @PostMapping("/")
    public ResponseEntity<Apiresponse<MessageDto>> postMessage(@Valid @RequestBody CreateMessageDto message) {
        try {
            MessageDto messageSaved = this.messageService.create(message);
            return ResponseEntity.status(HttpStatus.CREATED)
                .location(new URI("/api/message/" + messageSaved.getId()))
                .body(new Apiresponse<MessageDto>("Message saved.", messageSaved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Apiresponse<>("An error ocurred.", null));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<MessageDto>> putMessage(@PathVariable UUID id, @Valid @RequestBody UpdateMessageDto message) {
        try {
            MessageDto updatedMessage = this.messageService.update(id, message);
            return ResponseEntity.status(HttpStatus.OK)
                .location(new URI("/api/message/" + updatedMessage.getId()))
                .body(new Apiresponse<MessageDto>("Message updated.", updatedMessage));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Apiresponse<>("Message not found.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Apiresponse<>("An error ocurred.", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteMessage(UUID id) {
        try {
            boolean result = this.messageService.delete(id);
            if(!result) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Apiresponse<>("Service unavailable.", null));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<Void>("Message deleted.", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Apiresponse<>("Message not found.", null));
        }
    }
}
