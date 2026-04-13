package rus.cheremisin.itktasksspringmvc.DTO;

import java.util.List;

public record MyPageResponse<T> (
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last
)
{}
