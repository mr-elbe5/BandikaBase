/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.application;

import jakarta.servlet.ServletContext;
import java.util.*;

public class Configuration {

    public static String ENCODING = "UTF-8";

    private static String appTitle = "";
    private static String salt = "";
    private static int timerInterval = 30;
    private static Locale locale = Locale.GERMAN;
    private static final Map<String,Locale> locales = new HashMap<>();

    static{
        locales.put("de",Locale.GERMAN);
        locales.put("en",Locale.ENGLISH);
    }

    // base data

    public static String getAppTitle() {
        return appTitle;
    }

    public static void setAppTitle(String appTitle) {
        Configuration.appTitle = appTitle;
    }

    public static String getSalt() {
        return salt;
    }

    public static void setSalt(String salt) {
        Configuration.salt = salt;
    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        if (locale == null || !locales.containsValue(locale))
            return;
        Configuration.locale = locale;
    }

    public static int getTimerInterval() {
        return timerInterval;
    }

    public static void setTimerInterval(int timerInterval) {
        Configuration.timerInterval = timerInterval;
    }

    // read from config file

    public static String getSafeInitParameter(ServletContext servletContext, String key){
        String s=servletContext.getInitParameter(key);
        return s==null ? "" : s;
    }

    public static void setConfigs(ServletContext servletContext) {
        setSalt(getSafeInitParameter(servletContext,"salt"));
        setTimerInterval(Integer.parseInt(getSafeInitParameter(servletContext,"timerInterval")));
        String language = getSafeInitParameter(servletContext,"defaultLanguage");
        try {
            setLocale(new Locale(language));
        } catch (Exception ignore) {
        }
        System.out.println("language is "+ getLocale().getDisplayName());
    }

}
