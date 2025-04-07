package com.pajiniweb.oriachad_backend.domains.enums;

public enum StudyType {
    external,
    internal,
    half_internal;

    public  static StudyType getStudyTypeByString(String value){
        if (value.equals("نصف داخلي")) return  StudyType.half_internal;
        if (value.equals("خارجي")) return  StudyType.external;
        if (value.equals("داخلي")) return  StudyType.internal;
        return  StudyType.half_internal;
    }
}
