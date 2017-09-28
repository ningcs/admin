package com.zhongqi.entity;

import java.util.Date;

/**
 * Created by ningcs on 2017/9/15.
 */
public class PageManage {
    private Integer id;
    private Integer webSiteId;
    private String headline; //标题
    private String pageImgMd5;//图片MD5
    private String content; //内容
    private Date createDateTime;
    private Integer status;

    public Integer getWebSiteId() {
        return webSiteId;
    }

    public void setWebSiteId(Integer webSiteId) {
        this.webSiteId = webSiteId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getPageImgMd5() {
        return pageImgMd5;
    }

    public void setPageImgMd5(String pageImgMd5) {
        this.pageImgMd5 = pageImgMd5;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
