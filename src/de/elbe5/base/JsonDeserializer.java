/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2018 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package de.elbe5.base;

import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

    public class JsonDeserializer {

        public Object deserialize(InputStream in) throws IOException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[0x4000];
            int len;
            try {
                while ((len = in.read(buffer, 0, 0x4000)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
            return deserialize(outputStream.toByteArray());
        }

        public Object deserialize(byte[] bytes) throws IOException {

            if (bytes == null) {
                throw new IOException("JSON byte array cannot be null");
            }
            if (bytes.length == 0) {
                throw new IOException("Invalid JSON: zero length byte array.");
            }
            try {
                String s = new String(bytes, StandardCharsets.UTF_8);
                return new JSONParser().parse(s);
            } catch (Exception e) {
                String msg = "Invalid JSON: " + e.getMessage();
                throw new IOException(msg, e);
            }
        }

    }
