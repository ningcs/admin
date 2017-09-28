package com.zhongqi.service;

import com.zhongqi.dto.WebSiteInfo;
import com.zhongqi.entity.PageManage;

import java.util.Map;

/**
 * Created by ningcs on 2017/9/11.
 */
public interface WebSiteService {
    public void addwebSite(WebSiteInfo webSiteInfo);

    //添加单页信息
    public void addPageManage(PageManage pageManage);

    //获取用户网站信息
    public WebSiteInfo getWebSiteInfo(Integer userId);

    //获取单页信息
    public Map<String,Object> getPageManageInfo(Integer webSiteId, Integer page, Integer page_size);


}
