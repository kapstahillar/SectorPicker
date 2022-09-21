package com.kapsta.sectorpicker.util;

import org.springframework.web.util.HtmlUtils;

public class StringTool {

    private StringTool() {}

    public static String addNbspToString(String string, int occurrences) {
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < occurrences; i++) {
            stringBuilder.insert(0, HtmlUtils.htmlUnescape("&nbsp;&nbsp;&nbsp;&nbsp;"));
        }
        return stringBuilder.toString();
    }

    public static String replaceKnownCharactersInString(String string) {
        string = HtmlUtils.htmlUnescape(string);
        string = string.replace('’', '\'');
        string = string.replace("â€™", "'");
        return string;
    }
}
