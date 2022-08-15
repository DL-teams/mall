package priv.jesse.mall.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import priv.jesse.mall.entity.Brand;

public interface BrandService {

    Page<Brand> findAll(Pageable pageable);

    List<Brand> findAll();

    void delById(Long id);

    Long create(Brand brand);

    Brand findById(Long id);

    void update(Brand announcement);
}
