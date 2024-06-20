package com.application.cab_application.enums;

public enum VehicleType {
    SUV(1),
    SEDAN(2),
    HATCHBACK(3);
    private final int code ;
    VehicleType(int code){
        this.code = code ;
    }

    public int getCode(){
        return code;
    }

    public static VehicleType fromCode(int code){
        for(VehicleType vehicleType : VehicleType.values()){
            if(vehicleType.code == code) return vehicleType;
        }

        throw new IllegalArgumentException("Unknown code" + code) ;
    }
}
