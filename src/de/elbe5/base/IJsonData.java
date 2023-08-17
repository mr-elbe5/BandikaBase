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

public interface IJsonData {
    JsonObject getJson();

    default void fromJson(JSONObject json){
    }

    default JsonObject getJsonRecursive(){
        return getJson();
    }

    default void fromJsonRecursive(JSONObject json){
        fromJson(json);
    }

    default <T> T get(JSONObject obj, String key, Class<T> cls){
        try {
            return cls.cast(obj.get(key));
        }
        catch(NullPointerException | ClassCastException e){
            return null;
        }
    }

    default String getString(JSONObject obj, String key){
        String val = get(obj, key, String.class);
        return val == null ? "" : val;
    }

    default long getLong(JSONObject obj, String key){
        Number val = get(obj, key, Number.class);
        return val == null ? 0 : val.longValue();
    }

    default int getInt(JSONObject obj, String key){
        Number val = get(obj, key, Number.class);
        return val == null ? 0 : val.intValue();
    }

    default LocalDateTime getLocalDateTime(JSONObject obj, String key){
        return DateHelper.fromISODateTime(getString(obj, key));
    }

    default LocalDate getLocalDate(JSONObject obj, String key){
        return DateHelper.fromISODate(getString(obj, key));
    }

    default boolean getBoolean(JSONObject obj, String key){
        Boolean val = get(obj, key, Boolean.class);
        return val != null && val;
    }

    default JSONArray getJSONArray(JSONObject obj, String key){
        JSONArray val = get(obj, key, JSONArray.class);
        return val == null ? new JSONArray() : val;
    }

    default JSONObject getJSONObject(JSONObject obj, String key){
        return get(obj, key, JSONObject.class);
    }
}
