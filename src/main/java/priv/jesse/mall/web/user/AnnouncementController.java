package priv.jesse.mall.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.jesse.mall.entity.Announcement;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.AnnouncementService;

import java.util.List;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/get.do")
    @ResponseBody
    public ResultBean<List<Announcement>> get() {
        List<Announcement> announcementList = announcementService.findAll();
        return new ResultBean<>(announcementList);
    }
}
