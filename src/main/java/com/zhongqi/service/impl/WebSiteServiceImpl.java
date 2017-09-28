package com.zhongqi.service.impl;

import com.zhongqi.dao.WebSiteDao;
import com.zhongqi.dto.WebSiteInfo;
import com.zhongqi.entity.PageManage;
import com.zhongqi.service.WebSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by ningcs on 2017/9/11.
 */
@Service
public class WebSiteServiceImpl implements WebSiteService {

    @Autowired
    private WebSiteDao webSiteDao;

    @Override
    @Transactional
    public void addwebSite(WebSiteInfo webSiteInfo) {
        webSiteDao.addwebSite(webSiteInfo);
    }

    @Override
    @Transactional
    public void addPageManage(PageManage pageManage) {
        webSiteDao.addPageManage(pageManage);
    }

    @Override
    public WebSiteInfo getWebSiteInfo(Integer userId) {
        return webSiteDao.getWebSiteInfo(userId);
    }

    @Override
    public Map<String,Object> getPageManageInfo(Integer webSiteId, Integer page, Integer page_size){
        return webSiteDao.getPageManageInfo(webSiteId,page,page_size);
    }
}
