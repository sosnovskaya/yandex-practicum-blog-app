package edu.misosnovskaya.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Paging {
    private int pageNumber;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;

    public static Paging getPaging(int pageSize, int pageNumber, int totalCount) {
        boolean hasNext = pageNumber * pageSize < totalCount;
        boolean hasPrevious = pageNumber > 1;
        return new Paging(pageNumber, pageSize, hasNext, hasPrevious);
    }

    public int pageSize() {
        return pageSize;
    }

    public boolean hasPrevious() {
        return hasPrevious;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public int pageNumber() {
        return pageNumber;
    }
}
