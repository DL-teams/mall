package priv.jesse.mall.web.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.jesse.mall.entity.Brand;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.BrandService;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/get.do")
    @ResponseBody
    public ResultBean<List<Brand>> get() {
        List<Brand> brandList = brandService.findAll();
        return new ResultBean<>(brandList);
    }
}
