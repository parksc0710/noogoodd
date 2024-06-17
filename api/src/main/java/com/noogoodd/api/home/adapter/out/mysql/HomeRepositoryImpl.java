package com.noogoodd.api.home.adapter.out.mysql;

import com.noogoodd.api.home.adapter.out.jpa.JpaHomeRepository;
import com.noogoodd.api.home.application.port.out.HomeRepository;
import com.noogoodd.common.entity.api.HomeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class HomeRepositoryImpl implements HomeRepository {

    private final JpaHomeRepository jpaHomeRepository;

    @Override
    public Optional<HomeEntity> getActiveNotice() {
        return jpaHomeRepository.getActiveNotice();
    }

    @Override
    public Optional<HomeEntity> getHomeDataById(Long id) {
        return Optional.empty();
    }
}
