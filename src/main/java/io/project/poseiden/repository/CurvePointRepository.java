package io.project.poseiden.repository;

import io.project.poseiden.model.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurvePointRepository extends JpaRepository<CurvePoint,Long> {
}
