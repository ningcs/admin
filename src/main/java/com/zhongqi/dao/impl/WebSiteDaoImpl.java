package com.zhongqi.dao.impl;

import com.zhongqi.dao.WebSiteDao;
import com.zhongqi.dto.WebSiteInfo;
import com.zhongqi.util.ResponseResult;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * Created by ningcs on 2017/9/11.
 */
@Repository
public class WebSiteDaoImpl implements WebSiteDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void addwebSite(WebSiteInfo webSiteInfo) {
        Map<String,Object> params =new HashedMap();
        String sql ="insert into WebSite(webTitle,webLogo, domainName,Keyword, webDescription, contactPeople," +
                " CompanyPhone, mobile, email, qq, faxes, address, webstat,status,createDate) " +
                "values(:webTitle,:webLogo,:domainName,:Keyword,:webDescription, :contactPeople," +
                ":CompanyPhone, :mobile, :email, :qq, :faxes, :address, :webstat,:status,:createDate) ";
        params.put("webTitle",webSiteInfo.getWebTitle());
        params.put("webLogo",webSiteInfo.getWebLogo());
        params.put("domainName",webSiteInfo.getDomainName());
        params.put("Keyword",webSiteInfo.getKeyword());
        params.put("webDescription",webSiteInfo.getWebDescription());
        params.put("contactPeople",webSiteInfo.getContactPeople());
        params.put("CompanyPhone",webSiteInfo.getCompanyPhone());
        params.put("mobile",webSiteInfo.getMobile());
        params.put("email",webSiteInfo.getEmail());
        params.put("qq",webSiteInfo.getQq());
        params.put("faxes",webSiteInfo.getFaxes());
        params.put("address",webSiteInfo.getAddress());
        params.put("webstat",webSiteInfo.getWebstat());
        params.put("status",0);
        params.put("createDate",new Date());
        jdbcTemplate.update(sql,params);

    }
}
