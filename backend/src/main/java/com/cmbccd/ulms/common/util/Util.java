package com.cmbccd.ulms.common.util;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import java.math.RoundingMode;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月25日 下午12:36:16
 * @Version 1.0 Description:
 */
public class Util {
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };
	static final String UNKNOWN = "unknown";
	
	static Pattern casePattern = Pattern.compile("[A-Z]");

	static final int PAGE_SIZE = 20;
	static final int PAGE_NUMBER = 1;
	static final int UUID_LENGTH = 8;

	private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
	private static final ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	private static final ThreadLocal<SimpleDateFormat> MONTH_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM"));
	
	public static String getsecret(String code) {
		char[] mm = new char[code.length() + 1];
		char[] aa = new char[code.length() * 2];

		mm = code.toCharArray();
		for (int i = 0; i < code.length(); i++) {
			int j = mm[i];
			aa[(i * 2)] = ((char) (j / 30));
			aa[(i * 2 + 1)] = ((char) (j % 30));
		}
		String mw = String.valueOf(aa);
		return mw;
	}

	public static String getdesecret(String code) {
		char[] mm = new char[code.length() / 2];
		char[] aa = new char[code.length() + 1];

		aa = code.toCharArray();
		for (int i = 0; i < code.length() / 2; i++) {
			mm[i] = ((char) (aa[(i * 2)] * '\036' + aa[(i * 2 + 1)]));
		}
		String mw = String.valueOf(mm);
		return mw;
	}

	public static String stringNulltoEmpty(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	public static boolean isNullorEmpty(String str) {
		if (null == str) {
			return true;
		}
		if ("".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isNullorEmpty(Object object) {
		if (null == object) {
			return true;
		}
		if ("".equals(object)) {
			return true;
		}
		return false;
	}


	/**
	 * 判断是否为整数
	 * @param str 传入的字符串
	 * @return boolean 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}

	public static String listToString(List<?> list, char separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append(separator);
		}
		return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
	}

	public static String camel4underline(String param) {

		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = casePattern.matcher(param);
		int i = 0;
		while (mc.find()) {
			builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
			i++;
		}

		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}

	/**
	 * 安全构建排序子句，防止 SQL 注入（ORDER BY 拼接）。
	 * order 仅允许字母/数字/下划线（列名），orderType 仅允许 asc/desc；非法输入返回 null（表示不排序）。
	 *
	 * @param order     排序字段（camelCase 或下划线形式）
	 * @param orderType 排序方向（asc/desc，不区分大小写，为空默认 asc）
	 * @return 安全的 order by 子句，或 null 表示非法/未指定
	 */
	public static String buildOrderByClause(String order, String orderType) {
		if (isNullorEmpty(order) || !order.matches("[a-zA-Z0-9_]+")) {
			return null;
		}
		String direction = "asc";
		if (!isNullorEmpty(orderType)) {
			String t = orderType.trim().toLowerCase();
			if ("desc".equals(t) || "asc".equals(t)) {
				direction = t;
			} else {
				return null;
			}
		}
		return camel4underline(order) + " " + direction;
	}

	/**
	 * 校验 SQL 标识符（表名/列名）是否安全：仅允许字母、数字、下划线，防止动态表/列名注入。
	 */
	public static boolean isValidSqlIdentifier(String identifier) {
		return identifier != null && identifier.matches("[A-Za-z0-9_]+");
	}

	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c ==  '_') {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(Character.toLowerCase(param.charAt(i)));
			}
		}
		return sb.toString();
	}

	public static Map<String, Integer> innitTablePages(Map<String, String> params) {
		Map<String, Integer> pageParams = new HashMap<String, Integer>();
		if (Util.isNullorEmpty(params.get("pageSize"))) {
			pageParams.put("pageSize", PAGE_SIZE);
		} else {
			pageParams.put("pageSize", Integer.parseInt(params.get("pageSize")));
		}
		if (Util.isNullorEmpty(params.get("pageNum"))) {
			pageParams.put("pageNum", PAGE_NUMBER);
		} else {
			pageParams.put("pageNum", Integer.parseInt(params.get("pageNum")));
		}
		return pageParams;
	}

	/**
	 * 获取过去第几天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = DATE_FORMAT.get();
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取未来 第 past 天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = DATE_FORMAT.get();
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取两个时间差newTime-oldTime
	 * 
	 * @param oldTime 过去时间 newTime 现在时间 yyyy-MM-dd HH:mm:ss
	 * @return s(秒)
	 * @throws ParseException
	 */
	public static long diffDateTime(String oldTime, String newTime) throws ParseException {
		SimpleDateFormat df = DATE_TIME_FORMAT.get();
		Date nTime = df.parse(newTime);
		Date oTime = df.parse(oldTime);
		long diff = (nTime.getTime() - oTime.getTime()) / 1000;
		return diff;
	}

	public static String getDateToday() {
		SimpleDateFormat df = DATE_FORMAT.get();
		Date date = new Date();
		return df.format(date);
	}

	public static String getMonth() {
		SimpleDateFormat df = MONTH_FORMAT.get();
		Date date = new Date();
		return df.format(date);
	}

	public static String getTime() {
		SimpleDateFormat df = DATE_TIME_FORMAT.get();
		Date date = new Date();
		return df.format(date).substring(11);
	}

	public static String getCurrentTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000L);
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");

		if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.indexOf(",") != -1) {
				ip = ip.split(",")[0].trim();
			}
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String currentDateTime() {
		DateFormat df = DATE_TIME_FORMAT.get();
		return df.format(new Date());
	}


	public static String getShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < UUID_LENGTH; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	// 获取全局唯一标识符
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

public static double retainTwoDecimal(BigDecimal number) {
    double reVal = number.setScale(2, RoundingMode.HALF_UP).doubleValue();
    return reVal;
}
	public static double formatDouble(double d) {
		return (double) Math.round(d * 100) / 100;
	}

	public static String timeStampToDate(String timestampString, String formats) {
		if (Util.isNullorEmpty(formats)) {
			formats = "yyyy-MM-dd HH:mm:ss";
		}
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
		return date;
	}
	
	public static Long dateTimeToUnixTIme(String dateTime) throws ParseException {
		DateFormat df = DATE_TIME_FORMAT.get();
		long unixTime = df.parse(dateTime).getTime() / 1000;
		return unixTime;
	}

	/**
	 * 通过Sa-Token获取用户的userId,如未登录则返回null
	 *
	 * @return
	 */
	public static String userIdByShiro() {
		try {
			return StpUtil.getLoginIdAsString();
		} catch (cn.dev33.satoken.exception.NotLoginException e) {
			return null;
		}
	}

	// object 转 List<>
	public static <T> List<T> objectToList(Object obj, Class<T> clazz) {
		List<T> result = new ArrayList<>();
		if (obj instanceof List<?>) {
			for (Object o : (List<?>) obj) {
				result.add(clazz.cast(o));
			}
			return result;
		}
		return null;
	}

	// object 转 List<>
	public static <T> List<T> objToList(Object obj, Class<T> clazz) {
		List<T> result = new ArrayList<>();
		result = JSONObject.parseArray(JSONObject.toJSONString(obj), clazz);
		return result;
	}

	public static String fourDigiToTime(String digi) {
		if(Util.isNullorEmpty(digi)) return "00:00";
		String time= "";
		if(digi.length() == 4) {
			time = digi.substring(0, 2) + ":" + digi.substring(2);
		} else {
			time ="00:00";
		}
		return time;
	}

}
