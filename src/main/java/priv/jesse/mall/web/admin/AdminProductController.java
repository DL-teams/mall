package priv.jesse.mall.web.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import priv.jesse.mall.entity.Classification;
import priv.jesse.mall.entity.Product;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.ClassificationService;
import priv.jesse.mall.service.ProductService;
import priv.jesse.mall.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ClassificationService classificationService;

    @RequestMapping("/toList.html")
    public String toList() {
        return "admin/product/list";
    }

    @RequestMapping("/toAdd.html")
    public String toAdd() {
        return "admin/product/add";
    }

    @RequestMapping("/toEdit.html")
    public String toEdit(int id, Map<String, Object> map) {
        Product product = productService.findById(id);
        Classification classification = classificationService.findById(product.getCsid());
        product.setCategorySec(classification);
        map.put("product", product);
        return "admin/product/edit";
    }

    @ResponseBody
    @RequestMapping("/list.do")
    public ResultBean<List<Product>> listProduct(int pageindex,
                                                 @RequestParam(value = "pageSize", defaultValue = "15") int pageSize) {
        Pageable pageable = new PageRequest(pageindex, pageSize, null);
        // 根据你查询条件查询出有页面要显示哪些数据，通过getContent获取
        // [{id=1, title="裤子"},{id=2, title="衣服"}]
        List<Product> list = productService.findAll(pageable).getContent();
        return new ResultBean<>(list);
    }

    @ResponseBody
    @RequestMapping("/getTotal")
    public ResultBean<Integer> getTotal() {
        // PageRequest()
        // 参数1 是pageNumber 第几页
        // 参数2 是pageSize 一页显示几条
        // 是否排序
        Pageable pageable = new PageRequest(1, 15, null);
        // 根据你查询条件计算出一共有几页 通过getTotalPages获取
        // 根据你查询条件计算出数据库中一共有多少条数据 通过getTotalElements获取
        // 根据你查询条件查询出有哪些内容，通过getContent获取
        int total = (int) productService.findAll(pageable).getTotalElements();
        return new ResultBean<>(total);
    }

    @RequestMapping("/del.do")
    @ResponseBody
    public ResultBean<Boolean> del(int id) {
        productService.delById(id);
        return new ResultBean<>(true);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add.do")
    public void add(MultipartFile image,
                    String title,
                    Double marketPrice,
                    Double shopPrice,
                    int isHot,
                    String desc,
                    int csid,
                    HttpServletRequest request,
                    HttpServletResponse response) throws Exception {


        Product product = new Product();
        product.setTitle(title);
        product.setMarketPrice(marketPrice);
        product.setShopPrice(shopPrice);
        product.setDesc(desc);
        product.setIsHot(isHot);
        product.setCsid(csid);
        product.setPdate(new Date());
        String imgUrl = FileUtil.saveFile(image);
        product.setImage(imgUrl);
        int id = 0;
        if (product.getTitle() == null || product.getDesc() == null || product.getCsid() == null || product.getImage() == null){
             id = -1;
        }else {
             id = productService.create(product);
        }
        if (id <= 0) {
            request.setAttribute("message", "添加失败！");
            request.getRequestDispatcher("toAdd.html").forward(request, response);
        } else {
            request.getRequestDispatcher("toEdit.html?id=" + id).forward(request, response);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update.do")
    public void update(int id,
                       String title,
                       Double marketPrice,
                       Double shopPrice,
                       String desc,
                       int csid,
                       int isHot,
                       MultipartFile image,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        Product product = productService.findById(id);
        product.setTitle(title);
        product.setMarketPrice(marketPrice);
        product.setShopPrice(shopPrice);
        product.setDesc(desc);
        product.setIsHot(isHot);
        product.setCsid(csid);
        product.setPdate(new Date());
        String imgUrl = FileUtil.saveFile(image);
        if (StringUtils.isNotBlank(imgUrl)) {
            product.setImage(imgUrl);
        }
        boolean flag = false;
        try {
            productService.update(product);
            flag = true;
        } catch (Exception e) {
            throw new Exception(e);
        }
        if (!flag) {
            request.setAttribute("message", "更新失败！");
        }
        response.sendRedirect("toList.html");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/img/{filename:.+}")
    public void getImage(@PathVariable(name = "filename", required = true) String filename,
                         HttpServletResponse res) throws IOException {
        File file = new File("file/" + filename);
        if (file != null && file.exists()) {
            res.setHeader("content-type", "application/octet-stream");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            res.setContentLengthLong(file.length());
            Files.copy(Paths.get(file.toURI()), res.getOutputStream());
        }
    }


}
