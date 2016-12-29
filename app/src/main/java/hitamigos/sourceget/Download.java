/*
 * Copyright (c) 2016.
 * 个人版权所有
 * kuangmeng.net
 */

package hitamigos.sourceget;

/**
 * Created by kuangmeng on 2016/12/22.
 */

public class Download{
    private String title;
    private String info;
    public Download(String t,String i){
        this.title = t;
        this.info = i;
    }
    public Download(){}
    public void setTitle(String title){
        this.title = title;
    }
    public void setInfo(String info){
        this.info = info;
    }
    public String getTitle(){
        return title;
    }
    public String getInfo(){
        return info;
    }
}
