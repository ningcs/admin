package com.zhongqi.controller;

import com.zhongqi.dto.PageManageInfo;
import com.zhongqi.dto.WebSiteInfo;
import com.zhongqi.entity.common.User;
import com.zhongqi.service.WebSiteService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by ningcs on 2017/8/25.
 */
@Controller
@RequestMapping()
public class AdminPageController {

    @Autowired
    private WebSiteService webSiteService;
    //登录页面
    @RequestMapping("/login")
    public  String getLoginPage(){
        return "login/login";
    }

    //登录接口页面跳转
    @RequestMapping("/index")
    public String login(String name,String email,String code){
        return "shouye/index";
    }

    //网站设置
    @RequestMapping("/websiteSet")
    public ModelAndView websiteSet(HttpServletRequest request){
        ModelAndView view =new ModelAndView();
        Integer userId=0;
        User user=(User)request.getSession().getAttribute("user");
        if (user!=null){
            userId=user.getId();
        }
        WebSiteInfo webSiteInfo =webSiteService.getWebSiteInfo(userId);
        if (webSiteInfo==null){
            view.setViewName("admin/webSite/info");
        }else{
            view.setViewName("admin/webSite/updateinfo");
            view.addObject("webSiteInfo",webSiteInfo);
        }
        return view;
    }

    //网站设置信息展示
    @RequestMapping("/websiteSetInfo")
    public ModelAndView websiteSetInfo(HttpServletRequest request){
        ModelAndView view =new ModelAndView("admin/webSite/updateinfo");
        Integer userId=getUserId(request);
        WebSiteInfo webSiteInfo =webSiteService.getWebSiteInfo(userId);
        view.addObject("webSiteInfo",webSiteInfo);
        return view;
    }

    //修改密码
    @RequestMapping("/updatePasswardPage")
    public String updatePasswardPage(){
        return "admin/pass";
    }

    //单页管理
    @RequestMapping("/singlePage")
    public String   singlePage(){
        return "admin/singlePage/page";
    }

    //单页列表
    @RequestMapping("/singlePageList")
    public ModelAndView   singlePageList(HttpServletRequest request){
        ModelAndView view =new ModelAndView("admin/singlePage/list");
        Integer userId=getUserId(request);
        WebSiteInfo webSiteInfo =webSiteService.getWebSiteInfo(userId);
        PageManageInfo pageManageInfo=null;
        Integer page=1;
        Integer page_size=20;
        Map<String,Object> map =new HashedMap();
        if (webSiteInfo!=null){
            map =webSiteService.getPageManageInfo(webSiteInfo.getId(),page,page_size);
        }
        view.addObject("list",map.get("list"));
        view.addObject("page",map.get("page"));
        view.addObject("page_size",map.get("page_size"));
        view.addObject("total",map.get("total"));
        return view;
    }
    //首页轮播
    @RequestMapping("/indexSlider")
    public String indexSlider(String name,String email,String code){
        return "lbt/lunbotu";
    }
    //留言管理
    @RequestMapping("/message")
    public String message(String name,String email,String code){
        return "admin/book";
    }
    //栏目管理
    @RequestMapping("/column")
    public String column(String name,String email,String code){
        return "admin/column";
    }

    //内容管理
    @RequestMapping("/list")
    public String list(String name,String email,String code){
        return "admin/list";
    }

    //添加内容
    @RequestMapping("/addList")
    public String addList(String name,String email,String code){
        return "admin/add";
    }

    //分类管理
    @RequestMapping("/sort")
    public String sort(String name,String email,String code){
        return "admin/cate";
    }


    private Integer getUserId(HttpServletRequest request){
        Integer userId=0;
        User user=(User)request.getSession().getAttribute("user");
        if (user!=null){
            userId=user.getId();
        }
        return userId;
    }


}
