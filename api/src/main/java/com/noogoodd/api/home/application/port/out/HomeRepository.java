package com.noogoodd.api.home.application.port.out;

import com.noogoodd.common.entity.api.HomeEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeRepository {
    Optional<HomeEntity> getActiveNotice();
    Optional<HomeEntity> getHomeDataById(Long id);
}
