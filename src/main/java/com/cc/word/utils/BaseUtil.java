package com.cc.word.utils;


import com.cc.word.model.common.ReturnCode;
import com.cc.word.model.common.ReturnResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class BaseUtil {

	private static Logger logger = LoggerFactory.getLogger(BaseUtil.class);
	/**
	 * 参数非空校验
	 * @throws Exception 
	 */
	public static void parameterNotNullCheck(Map<String, String> map) throws Exception {
		Set keys = map.keySet();
		if (keys != null) {
			Iterator iterator = keys.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				String value = map.get(key);
				if(StringUtils.isBlank(value)){
					throw new RuntimeException("传入"+key+"为空");
				}
			}
		}
	}
	
	/**
	 * 参数非空校验 返回map
	 * @param map
	 */
	public static ReturnResult parameterNotBankCheck(Map<String, String> map) {
		ReturnResult result=new ReturnResult();
		Set keys = map.keySet();
		if (keys != null) {
			Iterator iterator = keys.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				String value = map.get(key);
				if(StringUtils.isBlank(value)){
					logger.info("传入"+key+"为空");
					result.setReturnCode(ReturnCode.param_miss.returnCode);
					result.setReturnMsg("传入"+key+"为空");
					return result;
				}
			}
		}
		result.setReturnCode(ReturnCode.success.returnCode);
		result.setReturnMsg(ReturnCode.success.returnMsg);
		return result;
	}
	/**
	 * 生成随机验证码
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 将数据库中的字段名从大写转为小写
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> listToLower(List<Map<String, Object>> list){
		
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		
		for (Map<String, Object> oldMap : list) {

			Map<String, Object> newMap = new HashMap<>();
			
			Set<Map.Entry<String, Object>> entries = oldMap.entrySet();
			
			for(Map.Entry<String,Object> e : entries){
				
				String key = e.getKey().toLowerCase();
				String value = Objects.toString(e.getValue(), "");
				
				newMap.put(key, value);
			}
			data.add(newMap);
		}
		return data;
	}
	
	
	/**
	 * 参数数字格式校验 返回map
	 * 
	 * @param map
	 */
	public static ReturnResult parameterIsNumberCheck(Map<String, String> map) {
		ReturnResult result=new ReturnResult();
		Set keys = map.keySet();
		if (keys != null) {
			Iterator iterator = keys.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				String value = map.get(key);
				if (StringUtils.isNotBlank(value) && !CheckUtil.isNumber(value)) {
					logger.info("传入" + key + "必须是数字格式");
					result.setReturnCode(ReturnCode.param_illegal.returnCode);
					result.setReturnMsg("传入" + key + "必须是数字格式");
					return result;
				}
			}
		}
		result.setReturnCode(ReturnCode.success.returnCode);
		result.setReturnMsg(ReturnCode.success.returnMsg);
		return result;
	}
	
	/**
	 * 参数日期格式校验 返回map
	 * 
	 * @param dateMap
	 * @return
	 */
	public static ReturnResult parameterIsDateCheck(Map<String, String> map) {
		ReturnResult result=new ReturnResult();
		Set keys = map.keySet();
		if (keys != null) {
			Iterator iterator = keys.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				String value = map.get(key);
				if (StringUtils.isNotBlank(value) && !CheckUtil.checkDate(value)) {
					logger.info("传入" + key + "必须是日期格式");
					result.setReturnCode(ReturnCode.param_illegal.returnCode);
					result.setReturnMsg("传入" + key + "必须是日期格式");
					return result;
				}
			}
		}
		result.setReturnCode(ReturnCode.success.returnCode);
		result.setReturnMsg(ReturnCode.success.returnMsg);
		return result;
	}
	
	/**
	 * 参数长度校验 返回map
	 * 
	 * @param dateMap
	 * @param lengthMap长度
	 * @return
	 */
	public static ReturnResult checkLength(Map<String, String> map,Map<String, Integer> lengthMap) {
		ReturnResult result=new ReturnResult();
		Set keys = map.keySet();
		if (keys != null) {
			Iterator iterator = keys.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				String value = map.get(key);
				if (StringUtils.isNotBlank(value) && value.length() > lengthMap.get(key)) {
					logger.info("传入" + key + "值不能超过"+ lengthMap.get(key) +"位");
					result.setReturnCode(ReturnCode.param_illegal.returnCode);
					result.setReturnMsg("传入" + key + "值不能超过"+ lengthMap.get(key) +"位");
					return result;
				}
			}
		}
		result.setReturnCode(ReturnCode.success.returnCode);
		result.setReturnMsg(ReturnCode.success.returnMsg);
		return result;
	}


	/**
	 *
	 * Map转String
	 * @param map
	 * @return
	 */
	public static String getMapToString(Map<String,Object> map){
		Set<String> keySet = map.keySet();
		//将set集合转换为数组
		String[] keyArray = keySet.toArray(new String[keySet.size()]);
		//给数组排序(升序)
		Arrays.sort(keyArray);
		//因为String拼接效率会很低的，所以转用StringBuilder
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keyArray.length; i++) {
			// 参数值为空，则不参与签名 这个方法trim()是去空格
			if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
				sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
			}
			if(i != keyArray.length-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 *
	 * String转map
	 * @param str
	 * @return
	 */
	public static Map<String,Object> getStringToMap(String str){
		//根据逗号截取字符串数组
		String[] str1 = str.split(",");
		//创建Map对象
		Map<String,Object> map = new HashMap<>();
		//循环加入map集合
		for (int i = 0; i < str1.length; i++) {
			//根据":"截取字符串数组
			String[] str2 = str1[i].split(":");
			//str2[0]为KEY,str2[1]为值
			map.put(str2[0],str2[1]);
		}
		return map;
	}

}
