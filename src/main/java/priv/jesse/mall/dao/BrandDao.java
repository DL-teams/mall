package priv.jesse.mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import priv.jesse.mall.entity.Brand;

public interface BrandDao extends JpaRepository<Brand, Long> {
}
