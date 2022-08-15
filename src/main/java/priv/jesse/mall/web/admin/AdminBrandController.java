package priv.jesse.mall.web.admin;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.jesse.mall.entity.Brand;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.BrandService;

@Controller
@RequestMapping("/admin/brand")
public class AdminBrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 打开公告列表页面
     * @return
     */
    @RequestMapping(value = "/toList.html",method = RequestMethod.GET)
    public String toList() {
        return "admin/brand/list";
    }

    @ResponseBody
    @GetMapping("/list.do")
    public ResultBean<List<Brand>> listAnnouncement(int pageindex,
                                                           @RequestParam(value = "pageSize", defaultValue = "15") int pageSize) {
        Pageable pageable = new PageRequest(pageindex, pageSize, null);
        List<Brand> list = brandService.findAll(pageable).getContent();
        return new ResultBean<>(list);
    }

    @ResponseBody
    @RequestMapping("/getTotal")
    public ResultBean<Integer> getTotal() {
        Pageable pageable = new PageRequest(1, 15, null);
        int total = (int) brandService.findAll(pageable).getTotalElements();
        return new ResultBean<>(total);
    }

    @RequestMapping("/del.do")
    @ResponseBody
    public ResultBean<Boolean> del(Long id) {
        brandService.delById(id);
        return new ResultBean<>(true);
    }

    @RequestMapping("/toEdit.html")
    public String toEdit(int id, Map<String, Object> map) {
        Brand brand = brandService.findById((long) id);
        map.put("brand", brand);
        return "admin/brand/edit";
    }

    @RequestMapping("/toAdd.html")
    public String toAdd() {
        return "admin/brand/add";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add.do")
    public void add(String name,
                    String reason,
                    String people,
                    Boolean recommendedFlag,
                    HttpServletRequest request,
                    HttpServletResponse response) throws Exception {

        Brand brand = new Brand();
        brand.setName(name);
        brand.setReason(reason);
        brand.setPeople(people);
        brand.setRecommendedFlag(recommendedFlag != null && recommendedFlag);
        Long id = brandService.create(brand);
        if (id <= 0) {
            request.setAttribute("message", "添加失败！");
            request.getRequestDispatcher("toAdd.html").forward(request, response);
        } else {
            request.getRequestDispatcher("toEdit.html?id=" + id).forward(request, response);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update.do")
    public void update(Long id,
                       String name,
                       String reason,
                       String people,
                       Boolean recommendedFlag,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        Brand brand = brandService.findById(id);
        brand.setName(name);
        brand.setReason(reason);
        brand.setPeople(people);
        brand.setRecommendedFlag(recommendedFlag != null && recommendedFlag);

        Boolean flag = false;
        try {
            brandService.update(brand);
            flag = true;
        } catch (Exception e) {
            throw new Exception(e);
        }
        if (!flag) {
            request.setAttribute("message", "更新失败！");
        }
        response.sendRedirect("toList.html");
    }
}
