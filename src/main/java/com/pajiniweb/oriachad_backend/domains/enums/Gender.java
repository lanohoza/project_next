package com.pajiniweb.oriachad_backend.domains.enums;

public enum Gender {
    male,female;

  public  static Gender getGanderByString(String value){
        if (value.equals("ذكر")) return  Gender.male;
        if (value.equals("أنثى")) return  Gender.female;
        return  Gender.male;
    }
}
