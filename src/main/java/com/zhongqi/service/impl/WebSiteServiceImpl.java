package com.zhongqi.service.impl;

import com.zhongqi.dao.WebSiteDao;
import com.zhongqi.dto.WebSiteInfo;
import com.zhongqi.service.WebSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
