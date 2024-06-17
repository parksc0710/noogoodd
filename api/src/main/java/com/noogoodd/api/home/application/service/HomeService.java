package com.noogoodd.api.home.application.service;


import com.noogoodd.api.home.application.dto.HomeDto;
import com.noogoodd.api.home.application.port.in.GetHomeUseCase;
import com.noogoodd.api.home.application.port.out.HomeRepository;
import com.noogoodd.api.user.application.dto.UserDto;
import com.noogoodd.common.entity.api.HomeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeService implements GetHomeUseCase {
    private final HomeRepository homeRepository;

    @Override
    public HomeDto geHomeMainData(Long loginUserId) {
        Optional<HomeEntity> homeEntity = homeRepository.getActiveNotice();
        HomeEntity entity = homeEntity.get();
        return new HomeDto(entity.getId(), entity.getNotice(), entity.isAct_flg(), entity.getReg_dt(), entity.getChg_dt());
    }
}
