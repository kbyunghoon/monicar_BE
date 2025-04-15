package org.rabbitmqcollector.location.application.port.out;

import org.rabbitmqcollector.location.infrastructure.jpa.entity.CycleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationJpaRepository extends JpaRepository<CycleInfoEntity, Long> {
}
