package com.omega.simpleecommerceapplication.commons;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        int totalPages,
        long totalElements,
        boolean first,
        boolean last,
        long offset
) {
}
