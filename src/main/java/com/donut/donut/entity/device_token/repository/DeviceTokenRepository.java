package com.donut.donut.entity.device_token.repository;

import com.donut.donut.entity.device_token.DeviceToken;
import com.donut.donut.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
    boolean existsByDeviceToken(String deviceToken);
    Optional<DeviceToken> findByDeviceTokenAndUser(String deviceToken, User user);
    List<DeviceToken> findAllByUser(User user);
    boolean existsByDeviceTokenAndUser(String deviceToken, User user);
    void deleteByDeviceTokenAndUser(String deviceToken, User user);
}
