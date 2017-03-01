package com.bi.cmp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.unicacorp.interact.flowchart.macrolang.storedobjs.CalloutException;
import com.unicacorp.interact.flowchart.macrolang.storedobjs.IAffiniumExternalCallout;
import com.unicacorp.interact.session.AudienceId;

public class sendMonitoring implements IAffiniumExternalCallout{
	
	private static Logger logger = null;
	public final static int SOCKET_PORT = 13267;	
	private static String CampaignCode = "";
	private static String CampaignEffectiveDate = "";
	private static String CampaignExpirationDate = "";
	private static String NotificationMask = "dtac";
	private static String NotificationShortCode = "";
	private static String FulfillmentMask = "dtac";
	private static String FulfilmentShortCode = "";
	private static String Notification_Message = "";
	private static String Fulfillment_Message = "";	
	private static String OfferNotificationChannel = "SMSC";
	private static String FulfillmentNotificationChannel = "SMSC";
	private static String LangPrefUSSD = "";
	private static String DeliveryReport = "";	
	private static String EffectiveWinTime = "";
	private static String ExpiryWinTime = "";
	private static String ProvisioningSystem = "CBS-Onetimebonus";
	private static String ProvisioningType = "EventPersistMonitorFulFill";	
	private static String Package_code = "";
	private static String Bonus_Type = "";
	private static String PocketValue = "";
	private static String PocketValidity = "";
	private static String Reason_Code = "";
	private static String BILL_PROD_CD = "02";
	private static String NextCycle = "";
	private static String GroupFlag = "";	
	private static String AssetType = "";
	private static String WaiveFee = "";
	private static String IMSI_NUM = "";
	private static String HLR = "";
	private static String OfferPrice = "";
	private static String RuleID = "DTAC_REFILL_ROCKET_RESP";
	private static String OfferCode = "";	
	private static String RecurringEvent = "N";	
	private static String TRRIGER_TYPE_RTP = "RealTime";
	private static String CampaignType1 = "C";
	private static String EventValue1 = "";	
	private static String EventValue2 = "0";
	private static String ValueComparison = "GTE";	
	private static String NoEvent = "N";
	private static String HoursNoEvent = "0";
	private static String PartialEvent = "N";
	private static String HoursPartialEvent = "0";
	private static String ReminderEvent = "N";
	private static String HoursReminderEvent = "0";
	private static String IntermediateReminder = "N";	
	private static String UrgentCampaign = "";
	private static String RoutingType = "RTPE";
	private static String Accumulation = "";	
	private static String MonitoringDuration = "0";
	private static String NoofIRE = "0";
	private static String NoofRECEvent = "0";
	private static String TriggerImmediate = "Y";
	private static String IS_Control = "N";
	private static String InteractiveFCName = "Fix";
	private static String BundlePackage = "N";	
	private static String PackageGrpCode = "";
	private static String Bonus_Unit = "";	
	private static String CBS_Offer_ID = "";
	private static String MaxCapBonus = "";
	private static String NumberofReminderEvents = "0";
	private static String TriggerMonitorValue = "";
	private static String IsReminder = "N";
	private static String FLOWID = "1234";
	private static String BonusPercent = "";
	
	public void initialize(Map<String, String> configurationData)
			throws CalloutException {

	}

	public int getNumberOfArguments() {
		return 7;
	}
	
	public List<String> getValue(AudienceId audienceId, Map<String, String> configData, Object... arguments) throws CalloutException {		
		
		DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(20);
        
		Double SUBSCRIPTIONID = (Double) arguments[0];	
		String sSUBSCRIPTIONID = df.format(SUBSCRIPTIONID);
		String MSISDN = (String) arguments[1];
		Double ACCOUNTID = (Double) arguments[2];	
		String sACCOUNTID = df.format(ACCOUNTID);
		String AccountType = (String) arguments[3];
		String LangPrefSMS = (String) arguments[4];
		String TreatmentCode = (String) arguments[5];		
		String CompanyName = (String) arguments[6];
		String PackageID;
		String MonStartDate;	
		String MonEndDate;	
		String EffectiveDate;	
		String ExpirationDate;	
		
		Date date = new Date();
		SimpleDateFormat fSDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");		
		MonStartDate = fSDate.format(date);
		EffectiveDate = MonStartDate;
		
		SimpleDateFormat fEDate = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(date); // Now use today date.
        c.add(Calendar.DATE, 1); // Adding 1 days
        MonEndDate = fEDate.format(c.getTime()) + " 00:00:00";
        ExpirationDate = MonEndDate;
        
		logger = Logger.getLogger(sendMonitoring.class);
		logger.info("**** Start External Callout *****" + Fulfillment_Message);		
		System.out.println("=== Start Create file ===");
		
		String rStatus = "0";
		SimpleDateFormat dforamt = new SimpleDateFormat("ddMMyyHHmmss");
		String sDate = dforamt.format(date);
		String fileName = "/MonitorFile/No_CMP-169999999_" + MSISDN + "_" + sDate + "_1.csv";
		FLOWID = MSISDN + "-Inbound-" + RuleID + "-" + sDate;
		PackageID = sDate;

		PrintWriter writer;
		try {
			if(!MonStartDate.equals("") && !MonEndDate.equals("") && !TreatmentCode.equals("")){
				writer = new PrintWriter(fileName,"UTF-8");			
				writer.println("SUBSCRIPTION_ID|MSISDN|ACCOUNTID|ACCT_TYPE_NM|CampaignCode|CampaignEffectiveDate|CampaignExpirationDate|NotificationMask|NotificationShortCode|FulfillmentMask|FulfilmentShortCode|NotificationMessage1|FulfillmentMessage|OfferNotificationChannel|FulfillmentNotificationChannel|LangPrefSMS|LangPrefUSSD|DeliveryReport|TreatmentCode|PackageID|EffectiveWinTime|ExpiryWinTime|ProvisioningSystem|ProvisioningType|PackageCode1|PocketName|PocketValue|PocketValidity|BusinessCode|BillProdCd|NextCycle|GroupFlag|AssetType|WaiveFee|IMSEI|HLR|OfferPrice|RuleID|OFFERCODE1|MonStartDT|MonEndDate1|RecurringEvent|TriggerType|Campaign_Type|EventValue1|EventValue2|ValueComparison|NoEvent|HoursNoEvent|PartialEvent|HoursPartialEvent|ReminderEvent|HoursReminderEvent|IntermediateReminder|EffectiveDate|Pkg_End|UrgentCampaign|RoutingType|Accumulation|MonitoringDuration|COMPANYNAME|NoofIRE|NoofRECEvent|TriggerImmediate|IS_Control|InteractiveFCName|BundlePackage|packageGroupCode|Bonus_Unit|CBS_Offer_ID|MaxCapBonus|NumberofReminderEvents|TriggerMonitorValue|IsReminder|FLOWID");
				writer.println(
								 sSUBSCRIPTIONID + "|" +
								 MSISDN + "|" + 
								 sACCOUNTID + "|" + 
								 AccountType + "|" + 
								 CampaignCode + "|" + 
								 CampaignEffectiveDate + "|" + 
								 CampaignExpirationDate + "|" + 
								 NotificationMask + "|" + 
								 NotificationShortCode + "|" + 
								 FulfillmentMask + "|" + 
								 FulfilmentShortCode + "|" + 
								 Notification_Message + "|" + 
								 Fulfillment_Message + "|" + 
								 OfferNotificationChannel + "|" + 
								 FulfillmentNotificationChannel + "|" + 
								 LangPrefSMS + "|" + 
								 LangPrefUSSD + "|" + 
								 DeliveryReport + "|" + 					 
								 TreatmentCode + "|" +
								 PackageID + "|" + 
								 EffectiveWinTime + "|" + 
								 ExpiryWinTime + "|" + 
								 ProvisioningSystem + "|" + 
								 ProvisioningType + "|" + 
								 Package_code + "|" + 
								 Bonus_Type + "|" + 
								 PocketValue + "|" + 
								 PocketValidity + "|" + 
								 Reason_Code + "|" + 
								 BILL_PROD_CD + "|" + 
								 NextCycle + "|" + 
								 GroupFlag + "|" + 
								 AssetType + "|" + 
								 WaiveFee + "|" + 
								 IMSI_NUM + "|" + 
								 HLR + "|" + 
								 OfferPrice + "|" + 
								 RuleID + "|" + 
								 OfferCode + "|" + 
								 MonStartDate + "|" + 
								 MonEndDate + "|" + 
								 RecurringEvent + "|" + 
								 TRRIGER_TYPE_RTP + "|" + 
								 CampaignType1 + "|" + 
								 EventValue1 + "|" + 
								 EventValue2 + "|" + 
								 ValueComparison + "|" + 
								 NoEvent + "|" + 
								 HoursNoEvent + "|" + 
								 PartialEvent + "|" + 
								 HoursPartialEvent + "|" + 
								 ReminderEvent + "|" + 
								 HoursReminderEvent + "|" + 					 
								 IntermediateReminder + "|" +
								 EffectiveDate + "|" + 
								 ExpirationDate + "|" + 
								 UrgentCampaign + "|" + 
								 RoutingType + "|" + 
								 Accumulation + "|" + 
								 MonitoringDuration + "|" + 					 
								 CompanyName + "|" +
								 NoofIRE + "|" + 
								 NoofRECEvent + "|" + 
								 TriggerImmediate + "|" + 
								 IS_Control + "|" + 
								 InteractiveFCName + "|" + 
								 BundlePackage + "|" + 					 
								 PackageGrpCode + "|" +
								 Bonus_Unit + "|" +
								 CBS_Offer_ID + "|" +
								 MaxCapBonus + "|" +
								 NumberofReminderEvents + "|" +
								 TriggerMonitorValue + "|" +					 
								 IsReminder + "|" +
								 FLOWID
								);
				writer.close();
				
				final File file = new File(fileName);
				file.setReadable(true, false);
				file.setExecutable(true, false);
				file.setWritable(true, false);
			}
									
		} catch (Exception e) {
			rStatus = "1";		
		}
						
		System.out.println("=== End Create file ===");
		logger.info("**** End External Callout *****");
		
		List<String> lresult = new ArrayList<String>();
		lresult.add(rStatus);			
		return lresult;
	}
	
	public void shutdown(Map<String, String> configurationData)
			throws CalloutException {

	}
			
	public static void main(String[] args)  {
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("the-file-name.txt", "UTF-8");
			writer.println("The first line");
			writer.println("สวัสดี");
			writer.close();
			
			final File file = new File("the-file-name.txt");
			file.setReadable(true, false);
			file.setExecutable(true, false);
			file.setWritable(true, false);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("xxx.csv", true),"tis620"));
			bw.newLine();
			bw.write("String Test");
			bw.write("สวัสดี");
			bw.close();
			
			/*
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/opt/IBM/Unica/Interact/batchProcess/file/No_CMP-150217237_CNTC_231215114136_2.csv", true),"UTF-8"));
			bw.newLine();
			bw.write(
							 sSUBSCRIPTIONID + "|" +
							 MSISDN + "|" + 
							 sACCOUNTID + "|" + 
							 ACCT_TYPE_NUM + "|" + 
							 CampaignCode + "|" + 
							 CampaignEffectiveDate + "|" + 
							 CampaignExpirationDate + "|" + 
							 NotificationMask + "|" + 
							 NotificationShortCode + "|" + 
							 FulfillmentMask + "|" + 
							 FulfilmentShortCode + "|" + 
							 Notification_Message + "|" + 
							 Fulfillment_Message + "|" + 
							 OfferNotificationChannel + "|" + 
							 FulfillmentNotificationChannel + "|" + 
							 LangPrefSMS + "|" + 
							 LangPrefUSSD + "|" + 
							 DeliveryReport + "|" + 					 
							 TreatmentCode + "|" +
							 PackageID + "|" + 
							 EffectiveWinTime + "|" + 
							 ExpiryWinTime + "|" + 
							 ProvisioningSystem + "|" + 
							 ProvisioningType + "|" + 
							 Package_code + "|" + 
							 Bonus_Type + "|" + 
							 PocketValue + "|" + 
							 PocketValidity + "|" + 
							 Reason_Code + "|" + 
							 BILL_PROD_CD + "|" + 
							 NextCycle + "|" + 
							 GroupFlag + "|" + 
							 AssetType + "|" + 
							 WaiveFee + "|" + 
							 IMSI_NUM + "|" + 
							 HLR + "|" + 
							 OfferPrice + "|" + 
							 RuleID + "|" + 
							 OfferCode + "|" + 
							 MonStartDate + "|" + 
							 MonEndDate + "|" + 
							 RecurringEvent + "|" + 
							 TRRIGER_TYPE_RTP + "|" + 
							 CampaignType1 + "|" + 
							 EventValue1 + "|" + 
							 EventValue2 + "|" + 
							 ValueComparison + "|" + 
							 NoEvent + "|" + 
							 HoursNoEvent + "|" + 
							 PartialEvent + "|" + 
							 HoursPartialEvent + "|" + 
							 ReminderEvent + "|" + 
							 HoursReminderEvent + "|" + 					 
							 IntermediateReminder + "|" +
							 EffectiveDate + "|" + 
							 ExpirationDate + "|" + 
							 UrgentCampaign + "|" + 
							 RoutingType + "|" + 
							 Accumulation + "|" + 
							 MonitoringDuration + "|" + 					 
							 COMP_NM + "|" +
							 NoofIRE + "|" + 
							 NoofRECEvent + "|" + 
							 TriggerImmediate + "|" + 
							 IS_Control + "|" + 
							 InteractiveFCName + "|" + 
							 BundlePackage + "|" + 					 
							 PackageGrpCode + "|" +
							 Bonus_Unit + "|" +
							 CBS_Offer_ID + "|" +
							 MaxCapBonus + "|" +
							 NumberofReminderEvents + "|" +
							 TriggerMonitorValue + "|" +					 
							 IsReminder
					);
			bw.close();
			*/
			
		} catch (Exception e) {			
			e.printStackTrace();
		} 								
	}
	
}
