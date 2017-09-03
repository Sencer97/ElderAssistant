package com.example.shengchun.elderassistant.settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FeedbackActivity extends Activity {
    private EditText content,mail;
    private Button submit;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        //初始化Bmob
        Bmob.initialize(this,"ea245002cb6523600aac65baa47a01db");
        init();
    }
    private void init() {
        content = (EditText) findViewById(R.id.et_advice);
        mail = (EditText) findViewById(R.id.et_mail);
        submit = (Button) findViewById(R.id.submit_btn);
        toolbar = (Toolbar) findViewById(R.id.feedback_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesg = content.getText().toString();
                String email = mail.getText().toString();
                Feedback feedbackObj = new Feedback(mesg,email);
                if(mesg.equals("")){
                    Toast.makeText(getBaseContext(),"亲~反馈不能为空哦！",Toast.LENGTH_SHORT).show();
                }else {
                    feedbackObj.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                //success
                                Toast.makeText(getBaseContext(), "反馈成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), "反馈失败！请查看网络连接~", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                /**
                 if(mesg.equals("")){
                 Toast.makeText(getBaseContext(),"亲~反馈不能为空哦！",Toast.LENGTH_SHORT).show();
                 }else {
                 new Thread(){
                 public void run(){
                 try {
                 //       new SimpleMailSender().sendTextMail(new MailSenderInfo("意见反馈",mesg,"1468504442@qq.com","1095035974@qq.com",
                 //           "1468504442@qq.com","yvszkprdsvyiffdg"));  //yvszkprdsvyiffdg 授权码
                 Log.d("send","successfully!");
                 } catch (Exception e) {
                 e.printStackTrace();
                 }
                 }
                 }.start();
                 Toast.makeText(getBaseContext(),"反馈成功！", Toast.LENGTH_SHORT).show();
                 finish();
                 } */
            }
        });
    }
    class MailSenderInfo{
        //发送邮件的服务器IP和端口
        private String mailServerHost = "smtp.qq.com",mailServerPort = "25";
        // 邮件发送和接收的地址
        private String fromAddress,toAddress;
        //登录邮件
        String userName,password;
        // 是否需要身份验证
        private boolean validate = true;
        // 邮件主题
        private String subject;
        // 邮件的文本内容

        public MailSenderInfo(String subject, String content, String fromAddress, String toAddress,
            String userName,String password) {
            this.subject = subject;
            this.content = content;
            this.fromAddress = fromAddress;
            this.toAddress = toAddress;
            this.userName = userName;
            this.password = password;

        }
        private String content;

        public String getMailServerHost() {
            return mailServerHost;
        }

        public void setMailServerHost(String mailServerHost) {
            this.mailServerHost = mailServerHost;
        }

        public String getMailServerPort() {
            return mailServerPort;
        }

        public void setMailServerPort(String mailServerPort) {
            this.mailServerPort = mailServerPort;
        }

        public String getFromAddress() {
            return fromAddress;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isValidate() {
            return validate;
        }

        public void setValidate(boolean validate) {
            this.validate = validate;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        /**
         * 获得邮件会话属性
         */
        public Properties getProperties() {
            Properties p = new Properties();
            p.setProperty("mail.debug", "true");
            p.setProperty("mail.transport.protocol", "smtp");
            p.put("mail.smtp.socketFactory.port", "465");
            p.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");  //ssl方式
            p.put("mail.smtp.socketFactory.fallback", "false");
            p.setProperty("mail.smtp.quitwait", "false");
            p.put("mail.smtp.host", "smtp.qq.com");
            p.put("mail.smtp.port", 25);
            p.put("mail.smtp.auth", validate ? "true" : "false");
            return p;
        }
    }

    /**
     * 简单邮件（不带附件的邮件）发送器  以文本格式发送邮件
     */
    class SimpleMailSender {
        public SimpleMailSender() {
        }
        public boolean sendTextMail(MailSenderInfo mailInfo) {

            Log.e("==", mailInfo.getUserName().toString() + "");
            Log.e("==", mailInfo.getPassword().toString() + "");
            Log.e("==", mailInfo.getFromAddress().toString() + "");
            Log.e("==", mailInfo.getToAddress().toString() + "");
            Log.e("==", mailInfo.getSubject().toString() + "");
            Log.e("==", mailInfo.getContent().toString() + "");

            // 判断是否需要身份认证
            MyAuthenticator authenticator = null;
            Properties pro = mailInfo.getProperties();
            if (mailInfo.isValidate()) {
                // 如果需要身份认证，则创建一个密码验证器
                 authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
            }
            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
            try {
                // 根据session创建一个邮件消息
                Message mailMessage = new MimeMessage(sendMailSession);
                // 创建邮件发送者地址
                Address from = new InternetAddress(mailInfo.getFromAddress());
                // 设置邮件消息的发送者
                mailMessage.setFrom(from);
                // 创建邮件的接收者地址，并设置到邮件消息中
                Address to = new InternetAddress(mailInfo.getToAddress());
                mailMessage.setRecipient(Message.RecipientType.TO, to);
                // 设置邮件消息的主题
                mailMessage.setSubject(mailInfo.getSubject());
                // 设置邮件消息发送的时间
                mailMessage.setSentDate(new Date());
                // 设置邮件消息的主要内容
                String mailContent = mailInfo.getContent();
                mailMessage.setText(mailContent);
                mailMessage.saveChanges();
                // 发送邮件
                Transport.send(mailMessage);
                return true;
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
    class MyAuthenticator extends Authenticator {
        String userName = null;
        String password = null;
        public MyAuthenticator() {
        }
        public MyAuthenticator(String username, String password) {
            this.userName = username;
            this.password = password;
        }
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }

}
