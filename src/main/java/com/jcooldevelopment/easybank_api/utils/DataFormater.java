package com.jcooldevelopment.easybank_api.utils;

import org.springframework.data.domain.Page;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;

public class DataFormater {
    // https://medium.com/@AlexanderObregon/paginating-api-results-with-spring-boot-and-spring-data-b00b5cddb41c
    public static <T> PaginatedResponse<T> paginate (Page<T> page) {
        PaginatedResponse<T> response = new PaginatedResponse<>();
        response.setData(page.getContent());
        response.setCurrentPage(page.getNumber() + 1);
        response.setTotalPages(page.getTotalPages());
        response.setTotalItems(page.getTotalElements());
        response.setPageSize(page.getSize());
        response.setHasNext(page.hasNext());
        response.setHasPrevious(page.hasPrevious());
        return response;
    }
}
