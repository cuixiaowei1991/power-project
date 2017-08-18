package com.cn.common;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 工具类
 * @author songzhili
 * 2016年6月29日下午1:19:19
 */
@SuppressWarnings("unchecked")
public class CommonHelper {
   
	public static boolean isEmpty(String source){
		if(source == null || source.length() == 0 || "null".equals(source)){
			return true;
		}
		return false;
	}
	
   public static boolean isNullOrEmpty(Object source){
		
		if(source == null || source.toString().trim().length() == 0
				|| "null".equals(source)){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param clazz
	 * @param source
	 * @return
	 */
	public static <T> List<T> converseMapToObject(Class<T> clazz,Map<String, ? extends Object> source){
		
		Object object = source.get("data");
		List<T> listObject = new ArrayList<T>();
		try {
			if(object instanceof List){
				List<Object> objectOne = (List<Object>)object;
				Field [] fields = clazz.getDeclaredFields();
				for(Object objectTwo : objectOne){
					if(objectTwo instanceof Map){
						Map<String,Object> objectThree = (Map<String,Object>)objectTwo;
						T objectT = clazz.newInstance();
						for(Field field : fields){
							String fieldName = field.getName();
							Object objectFour = objectThree.get(fieldName);
							if(objectFour != null){
								field.setAccessible(true);

									field.set(objectT, objectFour);

							}
						}
						listObject.add(objectT);
					}
				}
			}
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		return listObject;
	}
	/**
	 *
	 * @param clazz
	 * @param source
	 * @return
	 */
	public static <T> List<T> converseStringToObject(Class<T> clazz,Map<String, ? extends Object> source){

		List<T> listObject = new ArrayList<T>();
		JSONObject jsonObject = new JSONObject(source);
		try {
			Field [] fields = clazz.getDeclaredFields();
			T objectT = clazz.newInstance();
			for(Field field : fields){
				String fieldName = field.getName();
				if(!jsonObject.isNull(fieldName)){
					Object objectFour = jsonObject.get(fieldName);
					if(objectFour != null){
						field.setAccessible(true);
						field.set(objectT, objectFour);
					}
				}
			}
			listObject.add(objectT);
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		return listObject;
	}
	/**
	 *
	 * @param clazz
	 * @param source
	 * @return
	 */
	public static <T> List<T> converseNexineMapToObject(Class<T> clazz,Map<String, ? extends Object> source){

		Object object = source.get("data");
		List<T> listObject = new ArrayList<T>();
		try {
			if(object instanceof List){
				List<Object> objectOne = (List<Object>)object;
				Field [] fields = clazz.getDeclaredFields();
				for(Object objectTwo : objectOne){
					if(objectTwo instanceof Map){
						Map<String,Object> objectThree = (Map<String,Object>)objectTwo;
						Object objectFour =  objectThree.get("awardTimeRule");
						if(objectFour instanceof List){
							List<Object> objectFive = (List<Object>)objectFour;
							for(Object objectSix : objectFive){
								if(objectSix instanceof Map){
									Map<String,Object> objectSeven = (Map<String,Object>)objectSix;
									T objectT = clazz.newInstance();
									for(Field field : fields){
										String fieldName = field.getName();
										Object objectEight = objectSeven.get(fieldName);
										if(objectEight != null){
											field.setAccessible(true);

											field.set(objectT, objectEight);

										}
									}
									listObject.add(objectT);
								}
							}
						}
					}
				}
			}
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		return listObject;
	}
	/**
	 * 
	 * @param key
	 * @param source
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String obtainValue(String key,Map<String, Object> source){
		
		Object object = source.get("data");
		if(object instanceof List){
			List objectOne = (List)object;
			for(Object objectTwo : objectOne){
				if(objectTwo instanceof Map){
					Map objectThree = (Map)objectTwo;
					if(objectThree.get(key) != null){
						return objectThree.get(key).toString();
					}
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * @param source
	 * @param keys
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> obtianValues(Map<String, Object> source,List<String> keys){
		
		Map<String, String> resultMap = new HashMap<String, String>();
		Object object = source.get("data");
		if(object instanceof List){
			List objectOne = (List)object;
			for(Object objectTwo : objectOne){
				if(objectTwo instanceof Map){
					Map objectThree = (Map)objectTwo;
					for(String key : keys){
						Object objectFour = objectThree.get(key);
						if(objectFour != null){
							resultMap.put(key, objectFour.toString());
						}else{
							resultMap.put(key, null);
						}
					}
				}
			}
		}
		return resultMap;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> obtainParams(String key){
		List<String> keys = new ArrayList<String>();
		keys.add(key);
		keys.add("curragePage");
		keys.add("pageSize");
		return keys;
	}
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int obtainSpaceDays(String startTime, String endTime) {
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Long countOne = 0l;
		try {
			Date dateOne = format.parse(startTime);
			Date dateTwo = format.parse(endTime);
			long timeOne = dateOne.getTime();
			long timeTwo = dateTwo.getTime();
			long count = (timeTwo - timeOne) / (24 * 3600 * 1000);
			countOne = Math.abs(count);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return countOne.intValue();
	}

	/**
	 * 获取现在时间
	 * @return返回长时间格式 yyyy-MM-dd HH:mm:ss
	 */

	public static Date getNowDateShort() {
		Date currentTime = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		Date dateString1 = null;
		try {
			dateString1 = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString1;
	}

	/**
	 * 将时间转为指定的格式
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String formatTime(Object date,String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String date_e = null;
		try {
			date_e = sdf.format(date);
			return date_e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 返回当前日期的23时59分59秒的日期格式
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 返回当前日期的0时0分0秒的日期格式
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取指定位数的随机数(纯数字)
	 * @param length 随机数的位数
	 * @return String
	 */
	public static String getRandomNum(int length) {
		if (length <= 0) {
			length = 1;
		}
		StringBuilder res = new StringBuilder();
		Random random = new Random();
		int i = 0;
		while (i < length) {
			res.append(random.nextInt(10));
			i++;
		}
		return res.toString();
	}

	/**
	 * String转Date
	 * @param dateString 传入的时间类型字符串
	 * @param dateFormat 需要转义的时间类型格式 例如：yyyy-MM-dd
	 * @return
	 */
	public static Date getStringToDate(String dateString,String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = sdf.parse(dateString);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public static boolean isNumeric(String str){
	   if(isEmpty(str)){
		   return false;
	   }
	   for(int i = str.length();--i >= 0;){  
	      int chr = str.charAt(i);  
	      if(chr<48 || chr>57){
	    	  return false;  
	      }
	   }  
	   return true;  
	}

	/**
	 * 设置表的公共字段
	 * @param operation
	 * @return
	 */
	public static JSONObject getPublicFileds(String operation, String old,String userName){
		if(null != old && old.length()>1024)
		{
			old=old.substring(0,1024);
		}

		JSONObject resultJO = new JSONObject();
		if(isEmpty(userName)){
			userName = "allpay";
		}

		Date now = getNowDateShort();
		String nowTime = formatTime(now, "yyyy-MM-dd HH:mm:ss");
		StringBuilder together = new StringBuilder(userName);
		together.append("-").append(nowTime).append("-");
		together.append(operation);
		if(!isEmpty(old)){
			together.append(","+old);
		}
		String record = together.toString();

		resultJO.put("userName", userName);
		resultJO.put("now", nowTime);
		resultJO.put("record", record);
		return resultJO;
	}

	/**
	 * 将空对象转化成空字符串""
	 * @param object
	 * @return
	 */
	public static String nullToString(Object object){
		String result=object==null?"":object.toString();
		return result;
	}
	public static void main(String[] args) {
		System.out.println(obtainSpaceDays("20161228", "20161228"));
	}
}
