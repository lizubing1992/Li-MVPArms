package com.jess.arms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * string utils.
 */
public class StringUtils {

    //public final static String ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
    //public final static String EMAIL_REGEX = "^[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+$";
    public final static String MOBILE = "^[1][3,4,5,7,8][0-9]{9}$";
    public final static String PASS_WORD = "^.{6,20}$";

    /**
     * 计算预计收益
     *
     * @param amountStr
     * @param rateStr
     * @param deadline  期限，单位（天）
     * @return
     */
    public static String calculateRevenue(String amountStr, String rateStr, int deadline) {
        if (parseDouble(amountStr) > 0) {
            BigDecimal rate = new BigDecimal(rateStr);
            BigDecimal amount = new BigDecimal(amountStr);
            BigDecimal dayRate = new BigDecimal(Math.pow((rate.setScale(15, BigDecimal.ROUND_HALF_UP).doubleValue() + 1), (1.0 / 360)) - 1);//计算日收益率
            BigDecimal income = amount.multiply(new BigDecimal(1).add(dayRate).pow(deadline).subtract(new BigDecimal(1))).setScale(2, BigDecimal.ROUND_HALF_UP);//计算收益，四舍五入保留2位小数
            return String.valueOf(income.doubleValue());
        }
        return "0.00";
    }


    /**
     * 搜索前检查
     */
    public static boolean checkEditText(String tag) {
        if (tag != null && !tag.equals("")) {
            return true;
        }
        return false;
    }
    /**
     * 去除所有指定字符
     *
     * @param str
     * @param symbol
     * @return
     */
    public static String removeSymbol(String str, String symbol) {
        if (StringUtils.isEmpty(str))
            return "";
        if (StringUtils.isEmpty(symbol))
            return str;
        return str.replace(symbol, "");//replace()、replaceAll()都是全部替换，但是后者支持正则表达式，使用请小心
    }

    /**
     * 计算现金券使用的投资金额下限
     *
     * @param value      面值
     * @param usePercent 使用比例 1.00%
     * @return
     */
    public static long calculateCouponLimit(String value, String usePercent) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(usePercent)) {
            return 0l;
        }
        double d = parseDouble(value) / parseDouble(usePercent.replaceAll("%", ""));
        NumberFormat formater = new DecimalFormat("####");
        formater.setMinimumIntegerDigits(1);
        return parseLong(String.valueOf(formater.format(d))) * 100;
    }

    /**
     * 手机号码(3+4+4)
     *
     * @param editText
     */
    public static void toPhoneNumSpace(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence str, int arg1, int arg2, int arg3) { // 已经改变了的。
                String contents = str.toString();
                int length = contents.length();
                if (length == 4) {
                    if (contents.substring(3).equals(new String(" "))) { // -
                        contents = contents.substring(0, 3);
                        editText.setText(contents);
                        editText.setSelection(contents.length());
                    } else { // +
                        contents = contents.substring(0, 3) + " " + contents.substring(3);
                        editText.setText(contents);
                        editText.setSelection(contents.length());
                    }
                } else if (length == 9) {
                    if (contents.substring(8).equals(new String(" "))) { // -
                        contents = contents.substring(0, 8);
                        editText.setText(contents);
                        editText.setSelection(contents.length());
                    } else {// +
                        contents = contents.substring(0, 8) + " " + contents.substring(8);
                        editText.setText(contents);
                        editText.setSelection(contents.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence str, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {// TODO Auto-generated method stub

            }
        });
    }

    public static CharSequence formatPhoneNumberWith4(String mobile) {
        String fname;
        if (mobile == null || "".equals(mobile)) {
            return "";
        }
        int len = mobile.length();
        if (len == 11) {
            fname = mobile.substring(0, 3) + " " + mobile.substring(3, 7) + " " + mobile.substring(7, len);
        } else {
            fname = mobile;
        }
        return fname;
    }

    /**
     * string内容转化为int
     *
     * @param str
     * @return
     */
    public static int parseInt(String str) {
        int num = 0;
        try {
            if (StringUtils.isNotEmpty(str)) {
                num = Integer.parseInt(str);
            }
        } catch (Exception e) {
        }
        return num;
    }

    /**
     * string内容转化为long
     *
     * @param str
     * @return
     */
    public static long parseLong(String str) {
        long num = 0;
        try {
            if (StringUtils.isNotEmpty(str)) {
                num = Long.parseLong(str);
            }
        } catch (Exception e) {
        }
        return num;
    }

    /**
     * string内容转化为double
     *
     * @param str
     * @return
     */
    public static double parseDouble(String str) {
        double num = 0;
        try {
            if (StringUtils.isNotEmpty(str)) {
                num = Double.parseDouble(str);
            }
        } catch (Exception e) {
        }
        return num;
    }

    /**
     * string内容转化为float
     *
     * @param str
     * @return
     */
    public static float parseFloat(String str) {
        float num = 0;
        try {
            if (StringUtils.isNotEmpty(str)) {
                num = Float.parseFloat(str);
            }
        } catch (Exception e) {
        }
        return num;
    }

    /**
     * 判断是否是新版本
     *
     * @param currentVersion 当前版本名  格式：1.0.0
     * @param latestVersion  最新发布的版本名   格式：1.0.0
     * @return true 有最新版本
     */
    public static boolean hasNewVersion(String currentVersion, String latestVersion) {
        if (StringUtils.isNotEmpty(latestVersion) && StringUtils.isNotEmpty(currentVersion)
                && !StringUtils.equals(latestVersion, currentVersion)) {
            String lv = latestVersion.replaceAll("\\.", "");//去小数点
            String cv = currentVersion.replaceAll("\\.", "");//去小数点
            if (lv.length() < 3) lv += "0";//转化为3位数
            if (cv.length() < 3) cv += "0";//转化为3位数
            if (Double.valueOf(lv) > Double.valueOf(cv)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 转化为百
     *
     * @param str
     * @return
     */
    public static String formatHundred(String str) {
        double dd = parseDouble(str) / 100;
        NumberFormat formater = new DecimalFormat("####.00");
        formater.setMinimumIntegerDigits(1);
        return formater.format(dd);
    }

    /**
     * 转化为万
     *
     * @param str
     * @return
     */
    public static String formatTenThousands(String str) {
        double dd = parseDouble(str) / 10000;
        NumberFormat formater = new DecimalFormat("####.00");
        formater.setMinimumIntegerDigits(1);
        return formater.format(dd);
    }

    /**
     * 将小数转化成百分率
     *
     * @param rate
     * @return
     */
    public static String formatPercent(String rate) {
        double d = 0;
        try {
            d = Double.valueOf(rate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        double dd = d * 100;
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(dd);
    }

    /**
     * 将小数转化成百分率
     *
     * @param rate
     * @return
     */
    public static String formatPercent2(String rate) {
        double d = 0;
        try {
            d = Double.valueOf(rate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        double dd = d * 100;
        DecimalFormat format = new DecimalFormat("0.0");
        return format.format(dd);
    }

    /**
     * 将小数转化成百分率
     *
     * @param rate
     * @return
     */
    public static String formatPercentNumber(String rate) {
        double d = 0;
        try {
            d = Double.valueOf(rate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        double dd = d * 100;
        DecimalFormat format = new DecimalFormat("0.0");
        return format.format(dd);
    }

    /**
     * 将小数转化成百分率
     *
     * @param rate
     * @return
     */
    public static String formatPercent(String rate, DecimalFormat format) {
        double d = 0;
        try {
            d = Double.valueOf(rate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        double dd = d * 100;
        return format.format(dd);
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy.MM.dd");
        }
    };

    /**
     * 将字符串转位日期类型yyyy-MM-dd HH:mm:ss
     *
     * @param sdate
     * @return
     */
    public static String toDateTime(String sdate) {
        if (sdate == null || sdate.length() == 0)
            return "--";
        try {
            return dateFormater.get().format(sdate);
        } catch (Exception e) {
            return "--";
        }
    }

    /**
     * 将1个月20天这样的dealine转化下
     *
     * @param time
     * @param size1
     * @param size2
     * @return
     */
    public static SpannableString formatDeadline(String time, int size1, int size2) {
        if (time.contains("个月")) {
            SpannableString styledText = new SpannableString(time);
            int monIndex = time.indexOf("个月");
            styledText.setSpan(new AbsoluteSizeSpan(size1, true), 0, monIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new AbsoluteSizeSpan(size2, true), monIndex, monIndex + "个月".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (time.contains("天")) {
                int dayIndex = time.indexOf("天");
                styledText.setSpan(new AbsoluteSizeSpan(size1, true), monIndex + "个月".length(), dayIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new AbsoluteSizeSpan(size2, true), dayIndex, dayIndex + "天".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return styledText;
        } else if (time.contains("月")) {
            time = time.replace("月", "个月");
            return formatDeadline(time, size1, size2);
        } else {
            return StringUtils.setSpannableCustom(time.replace("天", ""), "天", size1, size2);
        }
    }


    /**
     * 转化格式为 12.0% + 奖3.0% 的年化率
     *
     * @param rate
     * @return
     */
    /*public static String formatRealRate(String rate){

        rate = rate.replaceAll("%", "");
        if(rate.contains("+奖")){
            int i = rate.indexOf("+奖");
            String rate1 = rate.substring(0, i);
            String rate2 = rate.substring(i+"+奖".length(), rate.length());
            LogUtils.i(rate1 + "/" + rate2);
            rate = Float.parseFloat(rate1) + Float.parseFloat(rate2) + "";
        }
        return rate;
    }*/
    public static String formatRealRate(String rate) {
        rate = rate.replaceAll("%", "").replace("奖", "");
        return rate;
    }

    /**
     * 去掉小数点和无用的0
     *
     * @param num
     * @return
     */
    public static String removeDot(String num) {
        if (StringUtils.isNotEmpty(num) && num.indexOf(".") > 0) {
            //正则表达
            num = num.replaceAll("0+?$", "");//去掉后面无用的零
            num = num.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return num;
    }

    /**
     * 验证短信验证码格式
     *
     * @param msgCode
     * @return
     */
    public static boolean isMsgCode(String msgCode) {
        if (StringUtils.isEmpty(msgCode)
                || msgCode.length() != 6)
            return false;
        return true;
    }

    /**
     * 验证手机号格式
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNum(String phoneNum) {
        return Pattern.matches(MOBILE, phoneNum);
    }

    /**
     * 验证密码格式
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(PASS_WORD, password);
    }

    /**
     * 是否 不为空字符串 或 null
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    /**
     * 是否 为空字符串 或 null
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || s.length() <= 0;
    }

    /**
     * 字符串对比
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        if (str1 != null)
            return str1.equals(str2);
        return str2 == null;
    }

    /*
     * 金额格式化 ####,###.00
	 */
    public static String formatAmount(String s) {
        int len = 2;//小数位数
        if (s == null || s.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###,###");
        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,###.00");
            for (int i = 0; i < len; i++) {
                buff.append("#");
            }
            formater = new DecimalFormat(buff.toString());
            formater.setMinimumIntegerDigits(1);
        }
        return formater.format(num);
    }

    /*
     * 金额格式化 ####,###.00
	 */
    public static String formatNumberThousandAmount(String s) {
        int len = 0;//小数位数
        if (s == null || s.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###,###");
        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,###.00");
            for (int i = 0; i < len; i++) {
                buff.append("#");
            }
            formater = new DecimalFormat(buff.toString());
            formater.setMinimumIntegerDigits(1);
        }
        return formater.format(num);
    }

    /**
     * 格式化金额
     *
     * @param s
     * @param hasDot 是否去除小数点后无用的0
     * @return
     */
    public static String formatAmount(String s, boolean hasDot) {
        if (hasDot) {
            return formatAmount(s);
        } else {
            return removeDot(formatAmount(s));
        }
    }

    /*
     * 金额格式化 ####.00
	 */
    public static String formatAmount2(String s) {
        double num = Double.parseDouble(s);
        NumberFormat formater = new DecimalFormat("####.00");
        formater.setMinimumIntegerDigits(1);
        return formater.format(num);
    }

    /*
     * 金额格式化 ####
	 */
    public static String formatAmount3(String s) {
        double num = Double.parseDouble(s);
        NumberFormat formater = new DecimalFormat("####");
        formater.setMinimumIntegerDigits(1);
        return formater.format(num);
    }

    /**
     * 获取指定格式日期的当前时间
     *
     * @param strFormat
     * @return
     */
    public static String getCurrDate(String strFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
        Date date1 = new Date();
        return formatter.format(date1);
    }

    /**
     * 获取惠农团关联借款标的借款人头像url
     *
     * @param url
     * @return
     */
    public static String getHnProjectDetailAvatar(String url) {
        String startImgUrl = "http://jms.yhqz.com:53102/cguarantee/oAuth/image/guarantor/";
        try {
            int startIndex = url.lastIndexOf("/") + 1;
            return startImgUrl + url.substring(startIndex, url.length());
        } catch (Exception e) {
            e.printStackTrace();
            return startImgUrl;
        }
    }

    /*
     * 身份证号码格式化******
     */
    public static String formatCertNo(String certNo) {
        String fname = "";
        if (certNo == null || "".equals(certNo)) {
            return fname;
        }
        int len = certNo.length();
        if (len == 18) {
            fname = certNo.substring(0, 4) + "**********" + certNo.substring(len - 4, len);
        } else {
            fname = certNo;
        }
        return fname;
    }

    /*
     * 电话号码格式化*******
	 */
    public static String formatMobile(String mobile) {
        String fname;
        if (mobile == null || "".equals(mobile)) {
            return "";
        }
        int len = mobile.length();
        if (len == 11) {
            fname = mobile.substring(0, 3) + "****" + mobile.substring(len - 4, len);
        } else {
            fname = mobile;
        }
        return fname;
    }

    /*
     * 用户名格式化
     */
    public static String formatUserName(String name) {
        String fname;
        if (isEmpty(name)) {
            return "";
        }
        int len = name.length();
        if (len == 1) {
            return name;
        } else if (len == 2) {
            fname = name.substring(0, 1) + "*";
        } else if (len == 3) {
            fname = name.substring(0, 1) + "**";
        } else if (len == 4) {
            fname = name.substring(0, 2) + "**";
        } else if (len == 5) {
            fname = name.substring(0, 2) + "***";
        } else if (len == 6) {
            fname = name.substring(0, 3) + "***";
        } else {
            fname = name.substring(0, 3) + "****";
        }
        return fname;
    }

    /**
     * @param data     字符串
     * @param suffix   后缀
     * @param textSize 内容字体大小
     * @param suffix   后缀字体大小
     * @return
     */
    public static SpannableString setSpannableCustom(String data, String suffix, int textSize, int suffixSize) {
        data = data + suffix;
        SpannableString styledText = new SpannableString(data);
        styledText.setSpan(new AbsoluteSizeSpan(textSize, true), 0, data.length() - suffix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new AbsoluteSizeSpan(suffixSize, true), data.length() - suffix.length(), data.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return styledText;
    }

    /**
     * @param data       字符串
     * @param rateMax    大的年化率
     * @param rateMini   小的年化率
     * @param textSize   内容字体大小
     * @param suffixSize 后缀字体大小
     * @return
     */
    public static SpannableString setSpannableSizeAndColor(String data, String rateMini, String rateMax, int textSize,
                                                           int suffixSize, String textColor, String suffixColor) {
        String suffix = rateMini + "%-" + rateMax + "%";
        //17.0 -- 22.0
        String text = data + suffix;
        SpannableString styledText = new SpannableString(text);
        ForegroundColorSpan firstSpan = new ForegroundColorSpan(Color.parseColor(textColor + ""));
        ForegroundColorSpan secondSpan = new ForegroundColorSpan(Color.parseColor(suffixColor + ""));
        styledText.setSpan(new AbsoluteSizeSpan(textSize, true), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new AbsoluteSizeSpan(suffixSize, true), data.length(),
                data.length() + rateMini.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new AbsoluteSizeSpan(suffixSize, true), text.length() - rateMax.length() - 1,
                text.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(firstSpan, 0, data.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(secondSpan, data.length(), text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return styledText;
    }

    /**
     * 改变特定字符的颜色
     * @param data        字符串
     * @param suffix      后缀
     * @param textColor   内容字体颜色
     * @param suffixColor 后缀字体颜色
     * @return
     */
    public static SpannableStringBuilder setSpannableColor(String data, String suffix, String textColor, String suffixColor) {
        String text = data + suffix;
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan firstSpan = new ForegroundColorSpan(Color.parseColor(textColor + ""));
        ForegroundColorSpan secondSpan = new ForegroundColorSpan(Color.parseColor(suffixColor + ""));
        builder.setSpan(firstSpan, 0, data.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(secondSpan, data.length(), text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return builder;
    }
    /**
     * 改变特定字符的颜色
     *
     * @param prefix  字符前缀
     * @param data        字符串
     * @param suffix      后缀
     * @param prefixColor   前缀字体颜色
     * @param dataColor   字符串字体颜色
     * @param suffixColor 前后缀字体颜色
     * @param textSize 前后缀不包括字符串的字体大小
     * @param suffixSize 前后缀字体大小
     * @return
     */
    public static SpannableStringBuilder setSpannableColor(String prefix,String data, String suffix,
                                                           String prefixColor, String dataColor,String suffixColor,
                                                           int textSize, int suffixSize) {
        String text = prefix + data + suffix;
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan prefixSpan = new ForegroundColorSpan(Color.parseColor(prefixColor + ""));
        ForegroundColorSpan dataSpan = new ForegroundColorSpan(Color.parseColor(dataColor + ""));
        ForegroundColorSpan suffixSpan = new ForegroundColorSpan(Color.parseColor(suffixColor + ""));
        builder.setSpan(new AbsoluteSizeSpan(suffixSize, true), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(textSize, true), prefix.length(),
                prefix.length()+data.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(prefixSpan, 0, prefix.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(dataSpan, prefix.length(), prefix.length()+data.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(suffixSpan, prefix.length() + data.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
    /**
     * 改变特定字符的颜色
     *
     * @param prefix  字符前缀
     * @param data        字符串
     * @param suffix      后缀
     * @param textColor   内容字体颜色
     * @param suffixColor 前后缀字体颜色
     * @return
     */
    public static SpannableStringBuilder setSpannableColor(String prefix,String data, String suffix, String textColor, String suffixColor) {
        String text = prefix + data + suffix;
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan firstSpan = new ForegroundColorSpan(Color.parseColor(textColor + ""));
        ForegroundColorSpan secondSpan = new ForegroundColorSpan(Color.parseColor(suffixColor + ""));
        builder.setSpan(secondSpan, 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(firstSpan, prefix.length(), +prefix.length() + data.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static void startActivity(Context context, Class<?> clz) {
        Intent intent = new Intent(context, clz);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> clz, Bundle data) {
        Intent intent = new Intent(context, clz);
        intent.putExtras(data);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity ativity, Class<?> clz, Bundle data, int requestCode) {
        Intent intent = new Intent(ativity, clz);
        if (data != null) {
            intent.putExtras(data);
        }
        ativity.startActivityForResult(intent, requestCode);
    }

    /**
     * 身份证验证
     *
     * @param idCard
     * @return
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 是否包含字母
     *
     * @param text
     * @return
     */
    public static boolean isIncludeEnglish(String text) {
        String pattern = "[a-zA-Z]";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 验证银卡卡号
     *
     * @param cardNo
     * @return
     */
    public static boolean isBankCard(String cardNo) {
        Pattern p = Pattern.compile("^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$");
        Matcher m = p.matcher(cardNo);
        return m.matches();
    }

    /**
     * 银行卡四位加空格
     *
     * @param mEditText
     */

    public static void bankCardNumAddSpace(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }



    public static float setRate(String str) {
        float i = Float.valueOf(str);
        return i / 100;
    }

    public static String getRate(String str) {
        float i = Float.valueOf(str);
        return (i * 100) + "%";
    }

    /**
     * 获取我的投标 相关状态
     *
     * @param str
     * @param isHnt
     * @return
     */
    public static String getInvestStatus(String str, boolean isHnt) {
        /*筹标中（惠农团：YFB、DFK，散标：TBZ、DFK，债权：ZRZ、DFK）
        还款中（惠农团：HKZ，散标：HKZ，债权：YWC）
        已回收（惠农团：YWC，散标：YJQ）*/
        switch (str) {
            case "DFK":
                return "待放款";
            case "TZZ":
            case "YFB":
            case "TBZ":
            case "ZRZ":
                return "筹标中";
            case "HKZ":
            case "YZR":
                return "还款中";
            case "YHS":
            case "YJQ":
                if (isHnt) {
                    return "还款中";
                } else {
                    return "已回收";
                }
            case "YWC":
                if (isHnt) {
                    return "已回收";
                } else {
                    return "还款中";
                }
            default:
                return "--";
        }
    }

    /**
     * 如果返回字符串是空的话，返回“ -- ”
     *
     * @param str str
     * @return str
     */
    public static String getAvailableString(String str) {
        if (TextUtils.isEmpty(str)) {
            return "--";
        } else {
            return str;
        }
    }

    /**
     * 获取 我的债权转让 状态
     *
     * @param str 状态值
     * @return
     */
    public static String getTransferStatus(String str) {
        String status;
        switch (str) {
            case "SQZ":
                status = "申请中";
                break;
            case "ZRZ":
                status = "转让中";
                break;
            case "DFK":
                status = "待放款";
                break;
            case "YJS":
                status = "已结束";
                break;
            case "YQX":
                status = "已取消";
                break;
            case "YJJ":
                status = "已拒绝";
                break;
            case "YWC":
                status = "已完成";
                break;
            default:
                status = "--";
        }
        return status;
    }

    /**
     * kb转化成mb
     *
     * @param kbSize
     * @return
     */
    public static String parseToMB(double kbSize) {
        double d = kbSize / 1024 / 1024;
        NumberFormat formater = new DecimalFormat("####.00");
        formater.setMinimumIntegerDigits(1);
        return formater.format(d);
    }


    /**
     * 字符全角化
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }



    /**
     * 通过开始时间和结束时间获取标签内容
     *
     * @param start 开始时间（秒）
     * @param end   结束时间（秒）
     * @return
     */
    public static String getOverTag(long start, long end) {
        if (start > 0 && end > 0 && end > start) {
            long time = end - start;
            if (time <= 600) {
                return "10分钟抢光";
            } else if (time <= 3600) {
                return "1小时抢光";
            } else if (time <= 3600 * 24) {
                return "1天抢光";
            }
        }
        return "";
    }

    /**
     * 字节大小转换为mb
     *
     * @param bytes
     * @return
     */
    public static String bytes2MB(long bytes) {
        double d = (double) bytes / 1024.0 / 1024.0;
        NumberFormat formater = new DecimalFormat("####.00");
        formater.setMinimumIntegerDigits(1);
        return formater.format(d);
    }

    /**
     * 是否是url地址或html类容
     *
     * @param str
     * @return
     */
    public static boolean isUrlOrHtml(String str) {
        return str.startsWith("http") || str.contains("html");
    }

    /**
     * Url decode by "UTF-8"
     *
     * @param str
     * @return
     */
    public static String decodeUrl(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
