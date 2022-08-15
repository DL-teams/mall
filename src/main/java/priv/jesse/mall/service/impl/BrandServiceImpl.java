package priv.jesse.mall.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import priv.jesse.mall.dao.BrandDao;
import priv.jesse.mall.entity.Brand;
import priv.jesse.mall.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandDao brandDao;

    @Override
    public Page<Brand> findAll(Pageable pageable) {
        return brandDao.findAll(pageable);
    }

    @Override
    public List<Brand> findAll() {
        return brandDao.findAll();
    }

    @Override
    public void delById(Long id) {
        brandDao.delete(id);
    }

    @Override
    public Long create(Brand brand) {
        return brandDao.save(brand).getId();
    }

    @Override
    public Brand findById(Long id) {
        return brandDao.findOne(id);
    }

    @Override
    public void update(Brand brand) {
        brandDao.save(brand);
    }
}
