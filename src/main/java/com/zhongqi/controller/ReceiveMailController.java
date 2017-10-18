//package com.zhongqi.controller;
//
//import com.zhongqi.entity.common.MailText;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeUtility;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
//
///**
// * @author hj
// *
// */
//@Controller
//@RequestMapping(value = "/mail")
//public class ReceiveMailController {
//
//	private MimeMessage mimeMessage = null;
//	private String saveAttachPath = ""; // 附件下载后的存放目录
//	private StringBuffer bodyText = new StringBuffer(); // 存放邮件内容的StringBuffer对象
//	private String dateFormat = "yy-MM-dd HH:mm"; // 默认的日前显示格式
//
//	/**
//	 * 构造函数,初始化一个MimeMessage对象
//	 */
//	public ReceiveMailController() {
//	}
//
//	public ReceiveMailController(MimeMessage mimeMessage) {
//		this.mimeMessage = mimeMessage;
//		System.out.println("创建一个ReceiveEmail对象....");
//	}
//
//	public void setMimeMessage(MimeMessage mimeMessage) {
//		this.mimeMessage = mimeMessage;
//		System.out.println("设置一个MimeMessage对象...");
//	}
//
//	/**
//	 * 　*　获得发件人的地址和姓名 　
//	 */
//	public String getFrom() throws Exception {
//		InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
//		String from = address[0].getAddress();
//		if (from == null) {
//			from = "";
//			System.out.println("无法知道发送者.");
//		}
//		String personal = address[0].getPersonal();
//
//		if (personal == null) {
//			personal = "";
//			System.out.println("无法知道发送者的姓名.");
//		}
//
//		String fromAddr = null;
//		if (personal != null || from != null) {
//			fromAddr = personal + "<" + from + ">";
//			System.out.println("发送者是：" + fromAddr);
//		} else {
//			System.out.println("无法获得发送者信息.");
//		}
//		return fromAddr;
//	}
//
//	/**
//	 * 　*　获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
//	 * 　*　"to"----收件人　"cc"---抄送人地址　"bcc"---密送人地址 　
//	 */
//	public String getMailAddress(String type) throws Exception {
//		String mailAddr = "";
//		String addType = type.toUpperCase();
//
//		InternetAddress[] address = null;
//		if (addType.equals("TO") || addType.equals("CC")
//				|| addType.equals("BCC")) {
//
//			if (addType.equals("TO")) {
//				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
//			} else if (addType.equals("CC")) {
//				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
//			} else {
//				address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
//			}
//
//			if (address != null) {
//				for (int i = 0; i < address.length; i++) {
//					String emailAddr = address[i].getAddress();
//					if (emailAddr == null) {
//						emailAddr = "";
//					} else {
//						System.out.println("转换之前的emailAddr: " + emailAddr);
//						emailAddr = MimeUtility.decodeText(emailAddr);
//						System.out.println("转换之后的emailAddr: " + emailAddr);
//					}
//					String personal = address[i].getPersonal();
//					if (personal == null) {
//						personal = "";
//					} else {
//						System.out.println("转换之前的personal: " + personal);
//						personal = MimeUtility.decodeText(personal);
//						System.out.println("转换之后的personal: " + personal);
//					}
//					String compositeto = personal + "<" + emailAddr + ">";
//					System.out.println("完整的邮件地址：" + compositeto);
//					mailAddr += "," + compositeto;
//				}
//				mailAddr = mailAddr.substring(1);
//			}
//		} else {
//			throw new Exception("错误的电子邮件类型!");
//		}
//		return mailAddr;
//	}
//
//	/**
//	 * 　*　获得邮件主题 　
//	 */
//	public String getSubject() throws MessagingException {
//		String subject = "";
//		try {
//			System.out.println("转换前的subject：" + mimeMessage.getSubject());
//			subject = MimeUtility.decodeText(mimeMessage.getSubject());
//			System.out.println("转换后的subject: " + mimeMessage.getSubject());
//			if (subject == null) {
//				subject = "";
//			}
//		} catch (Exception exce) {
//			exce.printStackTrace();
//		}
//		return subject;
//	}
//
//	/**
//	 * 　*　获得邮件发送日期 　
//	 */
//	public String getSentDate() throws Exception {
//		Date sentDate = mimeMessage.getSentDate();
//		System.out.println("发送日期 原始类型: " + dateFormat);
//		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
//		String strSentDate = format.format(sentDate);
//		System.out.println("发送日期 可读类型: " + strSentDate);
//		return strSentDate;
//	}
//
//	/**
//	 * 　*　获得邮件正文内容 　
//	 */
//	public String getBodyText() {
//		return bodyText.toString();
//	}
//
//	/**
//	 * 　　*　解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
//	 * 　　*　主要是根据MimeType类型的不同执行不同的操作，一步一步的解析 　　
//	 */
//
//	public void getMailContent(Part part) throws Exception {
//
//		String contentType = part.getContentType();
//		// 获得邮件的MimeType类型
//		System.out.println("邮件的MimeType类型: " + contentType);
//
//		int nameIndex = contentType.indexOf("name");
//
//		boolean conName = false;
//
//		if (nameIndex != -1) {
//			conName = true;
//		}
//
//		System.out.println("邮件内容的类型:　" + contentType);
//
//		if (part.isMimeType("text/plain") && conName == false) {
//			// text/plain 类型
//			bodyText.append((String) part.getContent());
//		} else if (part.isMimeType("text/html") && conName == false) {
//			// text/html 类型
//			bodyText.append((String) part.getContent());
//		} else if (part.isMimeType("multipart/*")) {
//			// multipart/*
//			Multipart multipart = (Multipart) part.getContent();
//			int counts = multipart.getCount();
//			for (int i = 0; i < counts; i++) {
//				getMailContent(multipart.getBodyPart(i));
//			}
//		} else if (part.isMimeType("message/rfc822")) {
//			// message/rfc822
//			getMailContent((Part) part.getContent());
//		} else {
//
//		}
//	}
//
//	/**
//	 * 　　*　判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false" 　
//	 */
//	public boolean getReplySign() throws MessagingException {
//
//		boolean replySign = false;
//
//		String needReply[] = mimeMessage
//				.getHeader("Disposition-Notification-To");
//
//		if (needReply != null) {
//			replySign = true;
//		}
//		if (replySign) {
//			System.out.println("该邮件需要回复");
//		} else {
//			System.out.println("该邮件不需要回复");
//		}
//		return replySign;
//	}
//
//	/**
//	 *　获得此邮件的Message-ID 　　
//	 */
//	public String getMessageId() throws MessagingException {
//		String messageID = mimeMessage.getMessageID();
//		System.out.println("邮件ID: " + messageID);
//		return messageID;
//	}
//
//	/**
//	 * 判断此邮件是否已读，如果未读返回false,反之返回true
//	 */
//	public boolean isNew() throws MessagingException {
//		boolean isNew = false;
//		Flags flags = ((Message) mimeMessage).getFlags();
//		Flags.Flag[] flag = flags.getSystemFlags();
//		System.out.println("flags的长度:　" + flag.length);
//		for (int i = 0; i < flag.length; i++) {
//			if (flag[i] == Flags.Flag.SEEN) {
//				isNew = true;
//				System.out.println("seen email...");
//				// break;
//			}
//		}
//		return isNew;
//	}
//
//	/**
//	 * 判断此邮件是否包含附件
//	 */
//	public boolean isContainAttach(Part part) throws Exception {
//		boolean attachFlag = false;
//		// String contentType = part.getContentType();
//		if (part.isMimeType("multipart/*")) {
//			Multipart mp = (Multipart) part.getContent();
//			for (int i = 0; i < mp.getCount(); i++) {
//				BodyPart mPart = mp.getBodyPart(i);
//				String disposition = mPart.getDisposition();
//				if ((disposition != null)
//						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition
//						.equals(Part.INLINE))))
//					attachFlag = true;
//				else if (mPart.isMimeType("multipart/*")) {
//					attachFlag = isContainAttach((Part) mPart);
//				} else {
//					String conType = mPart.getContentType();
//
//					if (conType.toLowerCase().indexOf("application") != -1)
//						attachFlag = true;
//					if (conType.toLowerCase().indexOf("name") != -1)
//						attachFlag = true;
//				}
//			}
//		} else if (part.isMimeType("message/rfc822")) {
//			attachFlag = isContainAttach((Part) part.getContent());
//		}
//		return attachFlag;
//	}
//
//	/**
//	 * 　*　保存附件 　
//	 */
//
//	public void saveAttachMent(Part part) throws Exception {
//		String fileName = "";
//		if (part.isMimeType("multipart/*")) {
//			Multipart mp = (Multipart) part.getContent();
//			for (int i = 0; i < mp.getCount(); i++) {
//				BodyPart mPart = mp.getBodyPart(i);
//				String disposition = mPart.getDisposition();
//				if ((disposition != null)
//						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition
//						.equals(Part.INLINE)))) {
//					fileName = mPart.getFileName();
//					if (fileName.toLowerCase().indexOf("gb2312") != -1) {
//						fileName = MimeUtility.decodeText(fileName);
//					}
//					if("".equals(mPart.getInputStream())){
//						saveFile(fileName, mPart.getInputStream());
//					}
//				} else if (mPart.isMimeType("multipart/*")) {
//					saveAttachMent(mPart);
//				} else {
//					fileName = mPart.getFileName();
//					if ((fileName != null)
//							&& (fileName.toLowerCase().indexOf("GB2312") != -1)) {
//						fileName = MimeUtility.decodeText(fileName);
//						saveFile(fileName, mPart.getInputStream());
//					}
//				}
//			}
//		} else if (part.isMimeType("message/rfc822")) {
//			saveAttachMent((Part) part.getContent());
//		}
//	}
//
//	/**
//	 *　设置附件存放路径
//	 */
//	public void setAttachPath(String attachPath) {
//		this.saveAttachPath = attachPath;
//	}
//
//	/**
//	 * 　*　设置日期显示格式 　
//	 */
//	public void setDateFormat(String format) throws Exception {
//		this.dateFormat = format;
//	}
//
//	/**
//	 * 　*　获得附件存放路径 　
//	 */
//	public String getAttachPath() {
//		return saveAttachPath;
//	}
//
//	/**
//	 * 　*　真正的保存附件到指定目录里 　
//	 */
//	private void saveFile(String fileName, InputStream in) throws Exception {
//		String osName = System.getProperty("os.name");
//		String storeDir = getAttachPath();
//		String separator = "";
//		if (osName == null) {
//			osName = "";
//		}
//		if (osName.toLowerCase().indexOf("win") != -1) {
//			separator = "\\";
//			if (storeDir == null || storeDir.equals(""))
//				storeDir = "c:\\tmp";
//		} else {
//			separator = "/";
//			storeDir = "/tmp";
//		}
//		File storeFile = new File(storeDir + separator + fileName);
//		System.out.println("附件的保存地址:　" + storeFile.toString());
//		// for(int　i=0;storefile.exists();i++){
//		// storefile　=　new　File(storedir+separator+fileName+i);
//		// }
//		BufferedOutputStream bos = null;
//		BufferedInputStream bis = null;
//
//		try {
//			bos = new BufferedOutputStream(new FileOutputStream(storeFile));
//			bis = new BufferedInputStream(in);
//			int c;
//			while ((c = bis.read()) != -1) {
//				bos.write(c);
//				bos.flush();
//			}
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			throw new Exception("文件保存失败!");
//		} finally {
//			bos.close();
//			bis.close();
//		}
//	}
//
//	/**
//	 * ReceiveEmail邮件基本信息
//	 * @throws Exception
//	 */
//	@RequestMapping(value = { "getMail" },method = RequestMethod.GET)
//	public ModelAndView getMail(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		String host = "pop.163.com";
//		String username = "strure0601";
//		String password = "hj123456";
//
//		Properties props = new Properties();
//		Session session = Session.getDefaultInstance(props, null);
//
//		Store store = session.getStore("pop3");
//		store.connect(host, username, password);
//
//		Folder folder = store.getFolder("INBOX");
//		folder.open(Folder.READ_ONLY);
//		Message message[] = folder.getMessages();
//		System.out.println("邮件数量:　" + message.length);
//		ReceiveMailController re = null;
//		ModelAndView mav = new ModelAndView();
//		List<MailText> kils = new ArrayList<MailText>();
//		for (int i = 0; i < message.length; i++) {
//			MailText mt = new MailText();
//			re = new ReceiveMailController((MimeMessage) message[i]);
//			mt.setMailTheme(re.getSubject());
//			System.out.println("邮件　" + i + "　主题:　" + re.getSubject());
//			mav.addObject("sentDate", re.getSentDate());
//			mt.setReceiveDate(re.getSentDate());
//			System.out.println("邮件　" + i + "　发送时间:　" + re.getSentDate());
//			mav.addObject("replySign", re.getReplySign());
//			System.out.println("邮件　" + i + "　是否需要回复:　" + re.getReplySign());
//			mav.addObject("isNew", re.isNew());
//			mt.setRead(re.isNew());
//			System.out.println("邮件　" + i + "　是否已读:　" + re.isNew());
//			mav.addObject("isContainAttach", re.isContainAttach((Part) message[i]));
//			System.out.println("邮件　" + i + "　是否包含附件:　"+ re.isContainAttach((Part) message[i]));
//			mav.addObject("form", re.getFrom());
//			System.out.println("邮件　" + i + "　发送人地址:　" + re.getFrom());
//			mt.setAddresser(re.getFrom());
//			mav.addObject("mailAddress", re.getMailAddress("to"));
//			System.out.println("邮件　" + i + "　收信人地址:　" + re.getMailAddress("to"));
//			mav.addObject("cc", re.getMailAddress("cc"));
//			System.out.println("邮件　" + i + "　抄送:　" + re.getMailAddress("cc"));
//			mav.addObject("bcc", re.getMailAddress("bcc"));
//			System.out.println("邮件　" + i + "　暗抄:　" + re.getMailAddress("bcc"));
//			mav.addObject("date", re.getSentDate());
//			re.setDateFormat("yy年MM月dd日　HH:mm");
//			System.out.println("邮件　" + i + "　发送时间:　" + re.getSentDate());
//			mav.addObject("messid", re.getMessageId());
//			mt.setMailMessageId(re.getMessageId());
//			System.out.println("邮件　" + i + "　邮件ID:　" + re.getMessageId());
//			mav.addObject("text", re.getBodyText());
//			re.getMailContent((Part) message[i]);
//			System.out.println("邮件　" + i + "　正文内容:　\r\n" + re.getBodyText());
//			mt.setMailText(re.getBodyText());
//			re.setAttachPath("d:\\");
//			//re.saveAttachMent((Part) message[i]);
//			kils.add(mt);
//		}
//		Collections.reverse(kils);//排序
//		mav.addObject("mail", kils);
//		mav.addObject("mailNumber", message.length);
//		mav.setViewName("mail/receive_mail");
//		return mav;
//	}
//
//	/**
//	 * 查看邮件内容
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = { "getText" },method = RequestMethod.GET)
//	public ModelAndView getText(String mailMessageId,String receiveDate,HttpServletRequest request,HttpServletResponse response) throws Exception {
//
//		String host = "pop.163.com";
//		String username = "strure0601";
//		String password = "hj123456";
//
//		Properties props = new Properties();
//		Session session = Session.getDefaultInstance(props, null);
//
//		Store store = session.getStore("pop3");
//		store.connect(host, username, password);
//
//		Folder folder = store.getFolder("INBOX");
//		folder.open(Folder.READ_ONLY);
//		Message message[] = folder.getMessages();
//		ReceiveMailController re = null;
//		ModelAndView mav = new ModelAndView();
//
//		MailText mts = new MailText();
//		for (int i = 0; i < message.length; i++) {
//			re = new ReceiveMailController((MimeMessage) message[i]);
//			mts.setMailTheme(re.getSubject());
//			re.getMailContent((Part) message[i]);
//			if(receiveDate.equals(re.getSentDate())){
//				mts.setAddresser(re.getFrom());
//				mts.setMailTheme(re.getSubject());
//				re.setDateFormat("yy年MM月dd日　HH:mm");
//				mts.setReceiveDate(re.getSentDate());
//				mts.setMailText(re.getBodyText());
//				break;
//			}
//		}
//
//		mav.addObject("mts", mts);
//		mav.addObject("mailNum", message.length);
//		mav.setViewName("mail/mail_text");
//		return mav;
//	}
//}