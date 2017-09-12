package com.zhongqi.entity.common;

/**
 * Created by ningcs on 2017/9/6.
 */
public class MailText {

    private String mailTheme;
    private String receiveDate;
    private Boolean read;
    private String addresser;
    private String mailMessageId;
    private String mailText;

    public String getMailTheme() {
        return mailTheme;
    }

    public void setMailTheme(String mailTheme) {
        this.mailTheme = mailTheme;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getAddresser() {
        return addresser;
    }

    public void setAddresser(String addresser) {
        this.addresser = addresser;
    }

    public String getMailMessageId() {
        return mailMessageId;
    }

    public void setMailMessageId(String mailMessageId) {
        this.mailMessageId = mailMessageId;
    }

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }
}
