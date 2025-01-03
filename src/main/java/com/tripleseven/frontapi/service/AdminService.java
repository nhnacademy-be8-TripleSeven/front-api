package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.MemberFeignClient;
import com.tripleseven.frontapi.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private static final String DEFAULT_SORT_PROPERTY = "id";
    private static final String DEFAULT_SORT_ORDER = "ASC";

    private final MemberFeignClient memberFeignClient;

    public Page<MemberDTO> getMembers(String name, Pageable pageable) {
        String sort = DEFAULT_SORT_PROPERTY;
        String sortOrder = DEFAULT_SORT_ORDER;

        if (!pageable.getSort().isEmpty()) {
            sort = pageable.getSort().iterator().next().getProperty();
            sortOrder = pageable.getSort().iterator().next().getDirection().name();
        }

        return memberFeignClient.getMembers(name, pageable.getPageNumber(),
                pageable.getPageSize(), sort, sortOrder);
    }
}
