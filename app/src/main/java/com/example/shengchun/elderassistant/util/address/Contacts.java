package com.example.shengchun.elderassistant.util.address;

/**
 * @author Sencer
 * @create 2017/3/24
 * @vertion 1.0
 * @description
 */

public class Contacts implements Comparable<Contacts> {

    private String name;
    private String phoneNum;
    private String spell;
    private String firstLetter;  //拼写首字母

    public Contacts(){
    }
    public Contacts(String name,String phoneNum){
        this.name = name;
        this.phoneNum = phoneNum;
        spell = Cn2Spell.getSpell(name);
        firstLetter = spell.substring(0,1).toUpperCase();
        if(!firstLetter.matches("[A-Z]")){       //匹配字母，正则表达式
            firstLetter = "#";
        }
    }

    public String getSpell() {
        return spell;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum(){ return phoneNum;}

    public String getFirstLetter() {
        return firstLetter;
    }


    /**
     *  a.compareTo(b) a>b --- return 1；
     *     a<b  return -1;
     *
     * @param another 另一个联系人
     * @return 对联系人进行排序，都为#或字母是才进行排序
     */
    @Override
    public int compareTo(Contacts another) {
        if(firstLetter.equals("#") && !another.getFirstLetter().equals("#")){
            return 1;
        }else if(!firstLetter.equals("#")  && another.getFirstLetter().equals("#")){
            return -1;
        }else {
            return spell.compareToIgnoreCase(another.getSpell());
        }

    }
}
