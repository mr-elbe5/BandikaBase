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

public class JsonObject extends JSONObject {

    @SuppressWarnings("unchecked")
    public void add(String key, String val){
        put(key, val);
    }

    @SuppressWarnings("unchecked")
    public void addIfNotEmpty(String key, String val){
        if (!val.isEmpty())
            put(key, val);
    }

    @SuppressWarnings("unchecked")
    public void add(String key, int val){
        put(key, val);
    }

    @SuppressWarnings("unchecked")
    public void addIfNotZero(String key, int val){
        if (val != 0)
            put(key, val);
    }

    @SuppressWarnings("unchecked")
    public void add(String key, LocalDateTime val){
        put(key, DateHelper.asMillis(val));
    }

    @SuppressWarnings("unchecked")
    public void addIfNotNull(String key, LocalDateTime val){
        if (val != null)
            put(key, DateHelper.asMillis(val));
    }

    @SuppressWarnings("unchecked")
    public void add(String key, LocalDate val){
        put(key, DateHelper.asMillis(val));
    }

    @SuppressWarnings("unchecked")
    public void addIfNotNull(String key, LocalDate val){
        if (val != null)
            put(key, DateHelper.asMillis(val));
    }

    @SuppressWarnings("unchecked")
    public void add(String key, boolean val){
        put(key, Boolean.toString(val));
    }

    @SuppressWarnings("unchecked")
    public void add(String key, JsonArray val){
        put(key, val);
    }

    @SuppressWarnings("unchecked")
    public void addIfNotEmpty(String key, JSONArray val){
        if (val != null && !val.isEmpty())
            put(key, val);
    }

    @SuppressWarnings("unchecked")
    public void add(String key, JsonObject val){
        put(key, val);
    }

    @SuppressWarnings("unchecked")
    public void addIfNotNull(String key, JsonObject val){
        if (val != null)
            put(key, val);
    }

}
