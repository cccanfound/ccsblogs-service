package com.cc.word.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CheckUtil {
    /**
     * 不存在特殊字符
     *
     * @param v
     * @return
     */
    public static boolean notExistSpecialChar(String v) {
        final String regex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5\\s·]+$";
        return Pattern.matches(regex, v);
    }

    /**
     * 是否为数字
     *
     * @param v
     * @return
     */
    public static boolean isNumber(String v) {
        final String regex = "^(-)?\\d+(\\.\\d+)?$";
        final String replaceRegex = "^\\s+|\\s+$";
        v = v.replaceAll(replaceRegex, "");
        return Pattern.matches(regex, v);
    }

    /**
     * email校验
     */
    public static boolean checkEmail(String email) {
        final String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return Pattern.matches(regex, email);
    }

    /**
     * 是否整数
     */
    public static boolean isNaturalNumber(String nn) {
        final String regex = "\\d+";
        return Pattern.matches(regex, nn);
    }

    /**
     * 日期校验
     */
    public static boolean checkDate(String date) {
        String regex = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|" +
                "([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|" +
                "(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|" +
                "([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|" +
                "([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|" +
                "(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        return Pattern.matches(regex, date);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }




    /**
     * 超前限制检查
     */

    /**
     * 根据传入的索引获取一个不可能在数据库存在的主键
     */
    public interface PKMapper {
        String pk(int index);
    }

    /**
     * 主键产生器
     */
    public static class PKGenerator {
        private Map<String, Integer> indexSrc = new HashMap<>();
        public static Map<String, PKMapper> mappers = new HashMap<>();

        public String pk(String tname) {
            Integer index = indexSrc.get(tname);
            if (index == null) {
                index = 0;
            }
            indexSrc.put(tname, index + 1);
            PKMapper mp = mappers.get(tname);
            if (mp == null) {
                throw new RuntimeException("无此主键映射器");
            }
            return mp.pk(index);
        }
    }

    /**
     * 更新
     * pkname 主键名
     * tname 表名
     * fnv 字段名与值的映射
     * fnref字段名与引用的映射
     */
    public static class Update {
        String pkname;
        String tname;
        boolean check;
        Map<String, String> fnv = new HashMap<>();
        Map<String, Integer> fnref = new HashMap<>();

        public Update(String tname, String pkname) {
            this.pkname = pkname;
            this.tname = tname;
        }

        public Update add(String fn, String v) {
            if (fnref.containsKey(fn)) {
                fnref.remove(fn);
            }
            fnv.put(fn, v);
            return this;
        }

        public Update add(String fn, Integer ref) {
            if (fnv.containsKey(fn)) {
                fnv.remove(fn);
            }
            fnref.put(fn, ref);
            return this;
        }
    }


    /**
     * 初始化超前检查参数
     *
     * @param updates 更新序列
     * @return
     */
    public static Map<String, Object> initPreCheckParam(List<Update> updates) {
        Map<String, Set<String>> deletes = new HashMap<>();
        Map<String, List<Map<String, String>>> inserts = new HashMap<>();
        Map<String, Object> ret = new HashMap<>();
        ret.put("deletes", deletes);
        ret.put("inserts", inserts);
        PKGenerator pkg = new PKGenerator();
        Map<String, Map<String, Integer>> tnamePkIndexMap = new HashMap<>();
        for (Update upd : updates) {
            Set<Map.Entry<String, Integer>> entrys = upd.fnref.entrySet();
            for (Map.Entry<String, Integer> entry : entrys) {
                Update updRef = updates.get(entry.getValue());
                upd.fnv.put(entry.getKey(), updRef.fnv.get(updRef.pkname));
            }
            String pk = upd.fnv.get(upd.pkname);
            if (StringUtils.isNotBlank(pk)) {
                Set<String> dels = deletes.get(upd.tname);
                if (dels == null) {
                    dels = new HashSet<>();
                    deletes.put(upd.tname, dels);
                }
                dels.add(upd.fnv.get(upd.pkname));
            } else {
                pk = pkg.pk(upd.tname);
                upd.fnv.put(upd.pkname, pk);
            }
            List<Map<String, String>> adds = inserts.get(upd.tname);
            if (adds == null) {
                adds = new ArrayList<>();
                inserts.put(upd.tname, adds);
                tnamePkIndexMap.put(upd.tname, new HashMap<>());
            }
            Integer index = tnamePkIndexMap.get(upd.tname).get(upd.fnv.get(upd.pkname));
            if (index != null) {
                if (adds.get(index).size() > 1) {
                    adds.set(index, upd.fnv);
                }
            } else {
                tnamePkIndexMap.get(upd.tname).put(upd.fnv.get(upd.pkname), adds.size());
                adds.add(upd.fnv);
            }
        }
        Set<Map.Entry<String, List<Map<String, String>>>> entrys = inserts.entrySet();
        for (Map.Entry<String, List<Map<String, String>>> entry : entrys) {
            List<Map<String, String>> fnvs = new ArrayList<>();
            for (Map<String, String> fnv : entry.getValue()) {
                if (fnv.size() > 1) {
                    fnvs.add(fnv);
                }
            }
            inserts.put(entry.getKey(), fnvs);
        }
        return ret;
    }

    /**
     * 验证手机号11位
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isPhone(String str) throws PatternSyntaxException {
        String regExp = "^(1)\\d{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isEmail(String string) {
        if (string == null) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 校验字符串是否X位数字
     *
     * @param x
     * @param arg 需要校验的字符串
     * @return
     */
    public static boolean isXDigits(int x, String arg) {
        if (arg == null) {
            return false;
        }
        String regEx1 = "\\d{" + x + "}";
        if (Pattern.matches(regEx1, arg)) {
            return true;
        }
        return false;
    }

}
