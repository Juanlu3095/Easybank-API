package com.jcooldevelopment.easybank_api.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.service.Message.MessageService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/message")
@Validated // Need this to use @Min for RequestParam: https://www.codejava.net/frameworks/spring-boot/rest-api-validate-query-parameters-examples
public class MessageController {

    private final MessageService messageService;

    public MessageController (MessageService service) {
        this.messageService = service;
    }

    // For RequestParams validation: https://docs.hibernate.org/validator/5.1/reference/en-US/html/chapter-message-interpolation.html#section-interpolation-with-message-expressions
    @GetMapping("")
    public ResponseEntity<Apiresponse<PaginatedResponse<MessageDto>>> getMessages(
        @RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "Page minimal value is 1.") int page, // The page to retrieve, the name of the variable is the same for the url
        @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "Page size minimal value is 1.") int size // The size of data in page
    ) {
        PaginatedResponse<MessageDto> messages = this.messageService.getAll(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<PaginatedResponse<MessageDto>>("Messages were found.", messages));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiresponse<MessageDto>> getMessage(@PathVariable UUID id){
        MessageDto message = this.messageService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<MessageDto>("Message found.", message));
    }

    @PostMapping("")
    public ResponseEntity<Apiresponse<MessageDto>> postMessage(@Valid @RequestBody CreateMessageDto message) {
        MessageDto messageSaved = this.messageService.create(message);
        return ResponseEntity.status(HttpStatus.CREATED)
            // URI.create() instead of new URI() because the last has a URISyntaxException that must be handled by
            // try/catch oor throws URISyntaxException
            .location(URI.create("/api/message/" + messageSaved.getId())) 
            .body(new Apiresponse<MessageDto>("Message saved.", messageSaved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Apiresponse<MessageDto>> putMessage(@PathVariable UUID id, @Valid @RequestBody UpdateMessageDto message) {
        MessageDto updatedMessage = this.messageService.update(id, message);
        return ResponseEntity.status(HttpStatus.OK)
            .location(URI.create("/api/message/" + updatedMessage.getId()))
            .body(new Apiresponse<MessageDto>("Message updated.", updatedMessage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse<Void>> deleteMessage(UUID id) {
        boolean result = this.messageService.delete(id);
        if(!result) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Apiresponse<>("Service unavailable.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Apiresponse<Void>("Message deleted.", null));
    }
}
