package com.ody.p2p.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ody.p2p.R;
import com.ody.p2p.base.OdyApplication;


public class DateUtils {
	
	// 分钟
	public static int Content_CacheTime = 5;
	public static int Content_ContentCacheTime = 60 * 24 * 3;
	public static int ImageCacheTime = 60 * 24 * 15;
	public static int Content_DefaultCacheTime = 60 * 24 * 3;

	public static int DiscussCacheTime = 60;

	public static final float defaultWeight = 70;
	public static final float defaultHeight = 170;
	public static float personalWeight = defaultWeight;
	public static float personalHeight = defaultHeight;

	/**
	 * 日期
	 */
	public static final String TODAY = "今天";
	public static final String YESTERDAY = "昨天";
	public static final String TOMORROW = "明天";
	public static final String BEFORE_YESTERDAY = "前天";
	public static final String AFTER_TOMORROW = "后天";
	public static final String SUNDAY = "星期日";
	public static final String MONDAY = "星期一";
	public static final String TUESDAY = "星期二";
	public static final String WEDNESDAY = "星期三";
	public static final String THURSDAY = "星期四";
	public static final String FRIDAY = "星期五";
	public static final String SATURDAY = "星期六";
	
	public static List<Date> dateToWeek(Date mdate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mdate);
		
		int b = calendar.get(Calendar.DAY_OF_WEEK);
		Date fdate;
		List<Date> list = new ArrayList<Date>();
		Long fTime = mdate.getTime() - b * 24 * 3600000;
		for (int a = 1; a <= 7; a++) {
			fdate = new Date();
			fdate.setTime(fTime + (a * 24 * 3600000));
			list.add(a - 1, fdate);
		}
		return list;
	}
	
	public static long parseToTimeMills(String formatTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d H:m");
		Date date = null;
		long timeMills = 0;
		try {
			date = dateFormat.parse(formatTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			timeMills = calendar.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return timeMills;
	}
	
	public static long parseDateToTimeMills(String dateFormatTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		long timeMills = 0;
		try {
			date = dateFormat.parse(dateFormatTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR, 1);
			calendar.set(Calendar.MINUTE, 1);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			timeMills = calendar.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return timeMills;
	}
	
	public static long parseDateToTimeMills2(String dateFormatTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				OdyApplication.gainContext().getString(R.string.choosedate_format));
		Date date = null;
		long timeMills = 0;
		try {
			date = dateFormat.parse(dateFormatTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR, 1);
			calendar.set(Calendar.MINUTE, 1);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			timeMills = calendar.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return timeMills;
	}
	
	public static long getYesterdayTimeMills(long currentTimeMills) {
		Calendar cald = Calendar.getInstance();
		cald.setTimeInMillis(currentTimeMills);
		cald.set(Calendar.DAY_OF_MONTH, cald.get(Calendar.DAY_OF_MONTH) - 1);
		cald.set(Calendar.HOUR, 1);
		cald.set(Calendar.MINUTE, 1);
		cald.set(Calendar.SECOND, 0);
		cald.set(Calendar.MILLISECOND, 0);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
		Date date = new Date(cald.getTimeInMillis());
		return cald.getTimeInMillis();
	}
	
	public static long getTomorrowTimeMills(long currentTimeMills) {
		Calendar cald = Calendar.getInstance();
		cald.setTimeInMillis(currentTimeMills);
		cald.set(Calendar.DAY_OF_MONTH, cald.get(Calendar.DAY_OF_MONTH) + 1);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
		Date date = new Date(cald.getTimeInMillis());
		return cald.getTimeInMillis();
	}
	
	public static String getToday(long currentMills) {
		String today = "";
		
		Calendar cald = Calendar.getInstance();
		cald.setTimeInMillis(currentMills);
		cald.set(Calendar.DAY_OF_MONTH, cald.get(Calendar.DAY_OF_MONTH));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
		Date date = new Date(cald.getTimeInMillis());
		today = dateFormat.format(date);
		
		return today;
	}

	public static String getMonDay(long currentMills) {
		String today = "";
		
		Calendar cald = Calendar.getInstance();
		cald.setTimeInMillis(currentMills);
		cald.set(Calendar.DAY_OF_MONTH, cald.get(Calendar.DAY_OF_MONTH));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(cald.getTimeInMillis());
		today = dateFormat.format(date);
		today = today.replace("-", ".");
		return today;
	}
	
	public static String getTodayTime(long currentMills) {
		String today = "";
		
		Calendar cald = Calendar.getInstance();
		cald.setTimeInMillis(currentMills);
		cald.set(Calendar.DAY_OF_MONTH, cald.get(Calendar.DAY_OF_MONTH));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(cald.getTimeInMillis());
		today = dateFormat.format(date);
		
		return today;
	}
	
	public static String getTomorrow(long currentMills) {
		String tomorrow = "";
		
		Calendar cald = Calendar.getInstance();
		cald.setTimeInMillis(currentMills);
		cald.set(Calendar.DAY_OF_MONTH, cald.get(Calendar.DAY_OF_MONTH) + 1);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(cald.getTimeInMillis());
		tomorrow = dateFormat.format(date);
		
		return tomorrow;
	}
	
	public static String getTomorrowTime(long currentMills) {
		String tomorrow = "";
		
		Calendar cald = Calendar.getInstance();
		cald.setTimeInMillis(currentMills);
		cald.set(Calendar.DAY_OF_MONTH, cald.get(Calendar.DAY_OF_MONTH) + 1);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(cald.getTimeInMillis());
		tomorrow = dateFormat.format(date);
		
		return tomorrow;
	}
	
	public static String getYesterday(long currentMills) {
		String yesterday = "";
		
		Calendar cald = Calendar.getInstance();
		cald.setTimeInMillis(currentMills);
		cald.set(Calendar.DAY_OF_MONTH, cald.get(Calendar.DAY_OF_MONTH) - 1);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
		Date date = new Date(cald.getTimeInMillis());
		yesterday = dateFormat.format(date);
		
		return yesterday;
	}
	
	public static String getYesterdayTime(long currentMills) {
		String yesterday = "";
		
		Calendar cald = Calendar.getInstance();
		cald.setTimeInMillis(currentMills);
		cald.set(Calendar.DAY_OF_MONTH, cald.get(Calendar.DAY_OF_MONTH) - 1);

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date(cald.getTimeInMillis());
		yesterday = dateFormat.format(date);
		
		return yesterday;
	}

	public static long getDay(Date nowToday, Date oldDay) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String strNow = df.format(nowToday);
		String strOld = df.format(oldDay);
		Date nowDate = null;
		Date oldDate = null;
		try {
			nowDate = df.parse(strNow);
			oldDate = df.parse(strOld);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long l = nowDate.getTime() - oldDate.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		return day;
	}
	
	
	public static long getMins(Date nowToday, Date oldDay) {
		long l = nowToday.getTime() - oldDay.getTime();
		long min = ((l / (60 * 1000)));
		return min;
	}

	
	public static long getSecond(Date nowToday, Date oldDay){
		long l = nowToday.getTime() - oldDay.getTime();
		long second = (l /  1000);
		return second;
	}
	
	public static String getStringFromDate3(Date date){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"); 
		
		 return df.format(date);
	}	
	
	public static String getStringFromDate4(Date date){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy.M.d"); 
		
		 return df.format(date);
	}	
	
	public static String getStringFromDate(Date date){
		 SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
		
		 return df.format(date);
	}
	
	public static Date getDateFromString2(String strTime){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
		Date date= null;
		try {
			date = df.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getString2FromDate(Date date){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		 return df.format(date);
	}
	
	public static Date getDateFromString(String strTime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date= null;
		try {
			date = df.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getString3FromDate(Date date){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.ddEE"); 
		
		 return df.format(date);
	}
	
	public static Date getDateFromStringYear(String strTime){
		 SimpleDateFormat df = new SimpleDateFormat(OdyApplication.gainContext().getString(R.string.choosedate_format));
		 Date date= null;
		try {
			date = df.parse(strTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return date;
	}
	
	//yesterday
	 public static int isYeaterday(Date oldTime,Date newTime){  
	        if(newTime==null){  
	            newTime=new Date();  
	        }  
	               //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点  
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	        String todayStr = format.format(newTime);  
	        Date today = null;
			try {
				today = format.parse(todayStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        //昨天 86400000=24*60*60*1000 一天  
	        if((today.getTime()-oldTime.getTime())>0 && (today.getTime()-oldTime.getTime())<=86400000) {  
	            return 0;  
	        }  
	        else if((today.getTime()-oldTime.getTime())<=0){ //至少是今天  
	            return -1;  
	        }  
	        else{ //至少是前天  
	            return 1;  
	        }  
	          
	    } 
	 public static String getMinSec(int voiceTime){
		int min = voiceTime / 60;
	 
		int ss = voiceTime % 60;
		String strSecond = "";
		String strMin = "";
		if (min < 10) {
			strMin = "0" + min;
		}else {
			strMin = "" + min;
		}
		if (ss < 10) {
			strSecond = "0" + ss;
		}else {
			strSecond = ss + "";
		}
		return strMin + " : " + strSecond;
	 }
	 
	 
	 
	 /**
	  * 将日期信息转换成今天、明天、后天、星期
	  * @param date
	  * @return
	  */
	 public static String getDateDetail(String date){//2015-04-24 17:44:00
		 Calendar today = Calendar.getInstance();  
		 Calendar target = Calendar.getInstance();
		 String date_today = getString2FromDate(new Date());

		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 try {
			 today.setTime(df.parse(date_today));//2015-04-25 10:41:00
			 today.set(Calendar.HOUR, 0);  
			 today.set(Calendar.MINUTE, 0);  
			 today.set(Calendar.SECOND, 0); 
			 target.setTime(df.parse(date));
			 target.set(Calendar.HOUR, 0);  
			 target.set(Calendar.MINUTE, 0);  
			 target.set(Calendar.SECOND, 0); 
		 } catch (ParseException e) {
			 e.printStackTrace();
			 return null;
		 }
		 long intervalMilli = target.getTimeInMillis() - today.getTimeInMillis(); 
		 int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
		 
		 if(xcts == 0){
			 return date.trim().substring(11, 19);
		 }else if(xcts < -7 || xcts > 2){
			 return date.trim().substring(0, 10);
		 }else{
			 return showDateDetail(xcts,target);
		 }
	 }
	 /**
	  * 将日期差显示为日期或者星期
	  * @param xcts
	  * @param target
	  * @return
	  */
	 private static String showDateDetail(int xcts, Calendar target){
		 switch(xcts){
		 case 0:
			 return TODAY;
		 case 1:
			 return TOMORROW;
		 case 2:
		     return AFTER_TOMORROW;
		 case -1:
			 return YESTERDAY;
		 case -2:
			 return BEFORE_YESTERDAY;
		 default:
			 int dayForWeek = 0;
			 dayForWeek = target.get(Calendar.DAY_OF_WEEK);
			 switch(dayForWeek){
			 case 1: return SUNDAY;
			 case 2: return MONDAY;
			 case 3: return TUESDAY;
			 case 4: return WEDNESDAY;
			 case 5: return THURSDAY;
			 case 6: return FRIDAY;
			 case 7: return SATURDAY;
			 default:return null;
			 }
	   
		 }
	 }
	 
	    /** 
	     * 判断时间是否在时间段内 
	     *  
	     * @param date 当前时间 yyyy-MM-dd HH:mm:ss
	     * @return 
	     */  
	    public static boolean isIn00To07(Date date) {  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String strDate = sdf.format(date);  
	        // 截取当前时间时分秒  
	        int strDateH = Integer.parseInt(strDate.substring(11, 13));  
	        int strDateM = Integer.parseInt(strDate.substring(14, 16));  
	        int strDateS = Integer.parseInt(strDate.substring(17, 19));  
	        // 截取开始时间时分秒  
	        int strDateBeginH = 00;  
	        int strDateBeginM = 00;  
	        int strDateBeginS = 00;  
	        // 截取结束时间时分秒  
	        int strDateEndH = 07;  
	        int strDateEndM = 00;  
	        int strDateEndS = 00;  
	        if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {  
	            // 当前时间小时数在开始时间和结束时间小时数之间  
	            if (strDateH > strDateBeginH && strDateH < strDateEndH) {  
	                return true;  
	                // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间  
	            } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM  
	                    && strDateM <= strDateEndM) {  
	                return true;  
	                // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间  
	            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM  
	                    && strDateS >= strDateBeginS && strDateS <= strDateEndS) {  
	                return true;  
	            }  
	            // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数  
	            else if (strDateH >= strDateBeginH && strDateH == strDateEndH  
	                    && strDateM <= strDateEndM) {  
	                return true;  
	                // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数  
	            } else if (strDateH >= strDateBeginH && strDateH == strDateEndH  
	                    && strDateM == strDateEndM && strDateS <= strDateEndS) {  
	                return true;  
	            } else {  
	                return false;  
	            }  
	        } else {  
	            return false;  
	        }  
	    }  
}

