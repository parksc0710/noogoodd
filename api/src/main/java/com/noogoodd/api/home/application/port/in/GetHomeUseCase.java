package com.noogoodd.api.home.application.port.in;

import com.noogoodd.api.home.application.dto.HomeDto;

public interface GetHomeUseCase {
    HomeDto geHomeMainData(Long loginUserId);
}
