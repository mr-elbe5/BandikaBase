/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2018 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package de.elbe5.base;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JsonWebToken {

    public static final String ALGORITHM = "HmacSHA256";
    public static final String jwtHeader = """
        {"alg":"HS256","typ":"JWT"}""";
    public static SecretKeySpec secretKey;

    static{
        createSecretKey("key");
    }

    public static void createSecretKey(String key){
        secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
    }

    public static byte[] calculateHMac(String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance(ALGORITHM);
        sha256_HMAC.init(secretKey);
        return sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String createData(String json){
        return Base64.getUrlEncoder().withoutPadding().encodeToString(jwtHeader.getBytes(StandardCharsets.UTF_8)) +
                "." +
                Base64.getUrlEncoder().withoutPadding().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    public static String createJwt(String json) throws Exception{
        String data = createData(json);
        return data + "." + Base64.getUrlEncoder().withoutPadding().encodeToString(calculateHMac(data));
    }

    public static boolean verifyJwt(String jwt){
        try{
            int pos = jwt.lastIndexOf('.');
            if (pos == -1)
                return false;
            String data = jwt.substring(0, pos);
            return jwt.substring(pos+1).equals(Base64.getUrlEncoder().withoutPadding().encodeToString(calculateHMac(data)));
        }
        catch (Exception e){
            return false;
        }
    }

    public static String getJson
            (String jwt){
        int pos1 = jwt.indexOf('.');
        int pos2 = jwt.lastIndexOf('.');
        if (pos1 == -1 || pos2 == -1 || pos1==pos2)
            return "";
        return new String(Base64.getUrlDecoder().decode(jwt.substring(pos1+1, pos2)), StandardCharsets.UTF_8);
    }

}
