package com.zhongqi.dao.impl;

import com.zhongqi.dao.WebSiteDao;
import com.zhongqi.dto.PageManageInfo;
import com.zhongqi.dto.WebSiteInfo;
import com.zhongqi.entity.PageManage;
import com.zhongqi.entity.WebSite;
import com.zhongqi.util.BaseUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        String sql ="insert into WebSite(userId,webTitle,webLogo, domainName,Keyword, webDescription, contactPeople," +
                " CompanyPhone, mobile, email, qq, faxes, address, webstat,status,createDate) " +
                "values(:userId,:webTitle,:webLogo,:domainName,:Keyword,:webDescription, :contactPeople," +
                ":CompanyPhone, :mobile, :email, :qq, :faxes, :address, :webstat,:status,:createDate) ";
        params.put("userId",webSiteInfo.getUserId());
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

    @Override
    public void addPageManage(PageManage pageManage) {
        Map<String,Object> params =new HashedMap();
        String sql ="insert into PageManage(webSiteId,headline, pageImgMd5, content, createDateTime, status) values " +
                "(:webSiteId,:headline,:pageImgMd5, :content, :createDateTime, :status)";
        params.put("webSiteId",pageManage.getWebSiteId());
        params.put("headline",pageManage.getHeadline());
        params.put("pageImgMd5",pageManage.getPageImgMd5());
        params.put("content",pageManage.getContent());
        params.put("createDateTime",new Date());
        params.put("status",0);
        jdbcTemplate.update(sql,params);
    }

    @Override
    public WebSiteInfo getWebSiteInfo(Integer userId) {
        Map<String,Object> params =new HashedMap();
        String sql="select * from WebSite where userId=:userId";
        params.put("userId",userId);
        WebSite webSite=null;
        WebSiteInfo webSiteInfo=new WebSiteInfo();
        try {
            webSite=jdbcTemplate.queryForObject(sql,params,new BeanPropertyRowMapper<WebSite>(WebSite.class));
            BeanUtils.copyProperties(webSite,webSiteInfo);
            webSiteInfo.setCreateDateStr(BaseUtils.formatDateToStrDay(webSite.getCreateDate()));
        }catch (EmptyResultDataAccessException e){
            return null;
        }
        return webSiteInfo;
    }

    @Override
    public  Map<String,Object> getPageManageInfo(Integer webSiteId, Integer page, Integer page_size) {
        Map<String,Object> params =new HashedMap();
        Map<String,Object> map =new HashedMap();
        List<PageManage> pageManages =null;
        List<PageManageInfo> pageManageInfos =new ArrayList<>();
        String sql="select * from PageManage where webSiteId=:webSiteId";
        String count_sql="select count(*) from PageManage where webSiteId=:webSiteId";
        sql = sql + " order by createDateTime asc";
        if (page != null && page_size != null && page != 0 && page_size != 0) {
            sql = sql + " limit :row , :page_size ";
            Integer row = (page - 1) * page_size;
            params.put("row", row);
            params.put("page_size", page_size);
        }

        params.put("webSiteId",webSiteId);
        PageManage  pageManage=null;
        PageManageInfo pageManageInfo=null;
        Integer total=0;
        try {
            pageManages=jdbcTemplate.query(sql,params,new BeanPropertyRowMapper<PageManage>(PageManage.class));
            total=jdbcTemplate.queryForObject(count_sql,params,Integer.class);
            for (int i=0;i<pageManages.size();i++){
                pageManageInfo=new PageManageInfo();
                pageManage=pageManages.get(i);
                BeanUtils.copyProperties(pageManage,pageManageInfo);
                pageManageInfo.setCreateDateTimeStr(BaseUtils.formatDateToStrDay(pageManage.getCreateDateTime()));
                pageManageInfos.add(pageManageInfo);
            }
        }catch (EmptyResultDataAccessException e){
            return null;
        }
        map.put("page",page);
        map.put("page_size",page_size);
        map.put("total",total);
        map.put("list",pageManageInfos);
        return map;

    }
}
