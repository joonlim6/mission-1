package db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wifi {
//	"X_SWIFI_MGR_NO":"---EP000001",
//	"X_SWIFI_WRDOFC":"은평구",
//	"X_SWIFI_MAIN_NM":"갈현1동주민센터",
//	"X_SWIFI_ADRES1":"갈현동 갈현로 301",
//	"X_SWIFI_ADRES2":"갈현1동 1층",
//	"X_SWIFI_INSTL_FLOOR":"",
//	"X_SWIFI_INSTL_TY":"7-1. 공공 - 행정",
//	"X_SWIFI_INSTL_MBY":"의견(자치)",
//	"X_SWIFI_SVC_SE":"공공WiFi",
//	"X_SWIFI_CMCWR":"자가망_U무선망",
//	"X_SWIFI_CNSTC_YEAR":"2011",
//	"X_SWIFI_INOUT_DOOR":"실내",
//	"X_SWIFI_REMARS3":"",
//	"LAT":"126.9167",
//	"LNT":"37.62364",
//	"WORK_DTTM":"2023-07-31 10:58:30.0" 
	
	private String mgrNumber;
	private String wrdofc;
	private String mainNumber;
	private String address1;
	private String address2;
	private String installFloor;
	private String installTy;
	private String installMby;
	private String svcSe;
	private String cmcwr;
	private int cnstcYear;
	private String inOutDoor;
	private String remars3;
	private double latitude;
	private double longitude;
	private String workDttm;
	private double distance;
	
}
