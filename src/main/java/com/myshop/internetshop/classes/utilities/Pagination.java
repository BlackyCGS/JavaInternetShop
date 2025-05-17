package com.myshop.internetshop.classes.utilities;

import java.util.Map;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pagination {

    private Pagination() {
    }


    public static Sort getSort(String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);

        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        } else if (sortDir.equals("desc")) {
            sort = sort.descending();
        } else {
            throw new NotImplementedException();
        }

        return sort;
    }


    public static Pageable getPageable(int pageNum, int itemsAmount) {
        return PageRequest.of(pageNum, itemsAmount);
    }

    public static Pageable getPageable(Map<String, String> params) {

        int pageNum = (params.containsKey("pageNum")) ? Integer.parseInt(params.get("pageNum")) : 0;
        int pageSize = (params.containsKey("pageSize")) ? Integer
                .parseInt(params.get("pageSize")) : 20;

        return PageRequest.of(pageNum, pageSize);
    }
}
