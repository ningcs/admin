package com.zhongqi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ningcs on 2017/8/25.
 */
@Controller
@RequestMapping()
public class AdminPageController {

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
    public String websiteSet(String name,String email,String code){
        return "admin/info";
    }

    //修改密码
    @RequestMapping("/updatePasswardPage")
    public String updatePasswardPage(String name,String email,String code){
        return "admin/pass";
    }

    //单页管理
    @RequestMapping("/singlePage")
    public String  singlePage(String name,String email,String code){
        return "admin/page";
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





}
