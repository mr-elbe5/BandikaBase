/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2018 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.base;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SuppressWarnings("unchecked")
public class JsonObject extends JSONObject {

    public JsonObject add(String key, String val){
        if (val != null && !val.isEmpty())
            put(key, val);
        return this;
    }

    public JsonObject add(String key, int val){
        if (val != 0)
            put(key, val);
        return this;
    }

    public JsonObject add(String key, double val){
        if (val != 0)
            put(key, val);
        return this;
    }

    public JsonObject add(String key, LocalDateTime val){
        if (val != null)
            put(key, DateHelper.toISODateTime(val));
        return this;
    }

    public JsonObject add(String key, LocalDate val){
        if (val != null)
            put(key, DateHelper.toISODate(val));
        return this;
    }

    public JsonObject add(String key, boolean val){
        put(key, val);
        return this;
    }

    public JsonObject add(String key, JSONArray val){
        if (val != null && !val.isEmpty())
            put(key, val);
        return this;
    }

    public JsonObject add(String key, JsonObject val){
        if (val != null)
            put(key, val);
        return this;
    }

}
