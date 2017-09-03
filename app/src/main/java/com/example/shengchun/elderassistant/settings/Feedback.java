package com.example.shengchun.elderassistant.settings;

import cn.bmob.v3.BmobObject;

/**
 * @author Sencer
 * @create 2017/6/15
 * @vertion 1.0
 * @description
 */

public class Feedback extends BmobObject {
    private String text;
    private String email;
    public Feedback (){

    }
    public Feedback (String text,String email){
        this.text = text;
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
