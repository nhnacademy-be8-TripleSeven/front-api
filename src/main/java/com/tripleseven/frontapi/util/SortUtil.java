package com.tripleseven.frontapi.util;


import org.springframework.data.domain.Sort;

public class SortUtil {


    public static Sort createSort(String field, String direction) {
        if (field == null || field.trim().isEmpty()) {
            return Sort.unsorted();
        }
        if (direction == null) {
            return Sort.by(field).ascending(); // 기본값 ASC
        }

        // direction이 ASC 또는 DESC 이외의 값이면 기본(unsorted) 처리하거나 예외를 던져도 됨
        if (direction.equalsIgnoreCase("asc")) {
            return Sort.by(field).ascending();
        } else if (direction.equalsIgnoreCase("desc")) {
            return Sort.by(field).descending();
        } else {
            return Sort.unsorted();
        }
    }
}

