package com.omega.simpleecommerceapplication.user.token;

import java.util.List;
import java.util.Objects;

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
