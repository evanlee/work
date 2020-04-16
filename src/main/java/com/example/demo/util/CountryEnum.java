package com.example.demo.util;

import lombok.Getter;

@Getter
public enum CountryEnum {
    ONE(1,"齐国"),
    TWO(2,"楚国"),
    THREE(3,"燕国"),
    FOUR(4,"赵国"),
    FIVE(5,"魏国"),
    SIX(6,"韩国");

     private Integer retCode;
     private String retMsg;
    CountryEnum(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public static CountryEnum foreach_enum(int index){
        CountryEnum[] values = CountryEnum.values();
        for(CountryEnum e:values){
            if(index==e.getRetCode()){
                return e;
            }
        }
        return null;
    }
}
