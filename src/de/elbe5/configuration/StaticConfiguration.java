/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.configuration;

import jakarta.servlet.ServletContext;

import java.util.*;

public class StaticConfiguration {

    static final Map<String,Locale> locales = new HashMap<>();
    public static String ENCODING = "UTF-8";
    static String appTitle = "";
    static String databaseKey = "";
    static String salt = "";
    static Locale locale = Locale.GERMAN;
    static int timeOffset = 0;
    static boolean showDateTime = false;
    static boolean useReadRights = true;
    static boolean useReadGroup = false;
    static boolean useEditorGroup = false;

    static{
        StaticConfiguration.locales.put("de",Locale.GERMAN);
        StaticConfiguration.locales.put("en",Locale.ENGLISH);
    }

    public static void initialize(ServletContext context){
        appTitle = getSafeInitParameter(context, "title");
        databaseKey = getSafeInitParameter(context, "database");
        salt = getSafeInitParameter(context, "salt");
        locale = locales.get(getSafeInitParameter(context, "locale"));
        timeOffset = Integer.parseInt(getSafeInitParameter(context, "timeOffset"));
        showDateTime = Boolean.parseBoolean(getSafeInitParameter(context, "showDateTime"));
        useReadRights = Boolean.parseBoolean(getSafeInitParameter(context, "useReadRights"));
        useReadGroup = Boolean.parseBoolean(getSafeInitParameter(context, "useReadGroup"));
        useEditorGroup = Boolean.parseBoolean(getSafeInitParameter(context, "useEditorGroup"));
    }

    public static String getSafeInitParameter(ServletContext servletContext, String key){
        String s=servletContext.getInitParameter(key);
        return s==null ? "" : s;
    }

    public static String getAppTitle() {
        return appTitle;
    }

    public static String getDatabaseKey() {
        return databaseKey;
    }

    public static String getSalt() {
        return salt;
    }

    public static Locale getLocale() {
        return locale;
    }

    public static int getTimeOffset() {
        return timeOffset;
    }

    public static boolean showDateTime() {
        return showDateTime;
    }

    public static boolean useReadRights() {
        return useReadRights;
    }

    public static boolean useReadGroup() {
        return useReadGroup;
    }

    public static boolean useEditorGroup() {
        return useEditorGroup;
    }

}
