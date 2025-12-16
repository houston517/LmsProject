package org.example.lmsproject.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class LmsProjectUtil {
    public static Pageable getPageable(Integer pageSize, Integer pageNumber) {
        int number = 0, size = 100;
        if(pageNumber != null) {
            number = pageNumber;
        }
        if(pageSize != null) {
            size = pageSize;
        }
        return PageRequest.of(number, size);
    }
}
