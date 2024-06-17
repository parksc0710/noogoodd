package com.noogoodd.api.home.adapter.out.jpa;

import com.noogoodd.common.entity.api.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface JpaHomeRepository extends JpaRepository<HomeEntity, Long> {
    @Query("SELECT h FROM HomeEntity h WHERE h.act_flg = true")
    Optional<HomeEntity> getActiveNotice();

}
