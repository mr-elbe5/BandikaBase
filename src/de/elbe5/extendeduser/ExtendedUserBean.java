/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.extendeduser;

import de.elbe5.base.Log;
import de.elbe5.user.UserBean;
import de.elbe5.user.UserData;

import java.sql.*;

public class ExtendedUserBean extends UserBean {

    private static ExtendedUserBean instance = null;

    public static ExtendedUserBean getInstance() {
        if (instance == null) {
            instance = new ExtendedUserBean();
        }
        return instance;
    }

    private static final String GET_USER_EXTRAS_SQL = "SELECT first_name,street,zipCode,city,country,phone,mobile,notes FROM t_extended_user WHERE id=?";

    public void readUserExtras(Connection con, UserData userData) throws SQLException{
        if (!(userData instanceof ExtendedUserData data))
            return;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(GET_USER_EXTRAS_SQL);
            pst.setInt(1, data.getId());
            boolean passed;
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int i = 1;
                    data.setFirstName(rs.getString(i++));
                    data.setStreet(rs.getString(i++));
                    data.setZipCode(rs.getString(i++));
                    data.setCity(rs.getString(i++));
                    data.setCountry(rs.getString(i++));
                    data.setPhone(rs.getString(i++));
                    data.setMobile(rs.getString(i++));
                    data.setNotes(rs.getString(i++));
                }
            }
        } catch (SQLException se) {
            Log.error("sql error", se);
        } finally {
            closeStatement(pst);
        }
    }

    private static final String INSERT_EXTENDED_SQL = "insert into t_extended_user (first_name,street,zipCode,city,country,phone,mobile,notes,id) values(?,?,?,?,?,?,?,?,?)";

    public void createUserExtras(Connection con, UserData userData) throws SQLException{
        if (!userData.isNew() || !(userData instanceof ExtendedUserData data))
            return;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(INSERT_EXTENDED_SQL);
            int i = 1;
            pst.setString(i++, data.getFirstName());
            pst.setString(i++, data.getStreet());
            pst.setString(i++, data.getZipCode());
            pst.setString(i++, data.getCity());
            pst.setString(i++, data.getCountry());
            pst.setString(i++, data.getPhone());
            pst.setString(i++, data.getMobile());
            pst.setString(i++, data.getNotes());
            pst.setInt(i, data.getId());
            pst.executeUpdate();
            pst.close();
            writeUserGroups(con, data);
        } finally {
            closeStatement(pst);
        }
    }

    private static final String UPDATE_EXTENDED_SQL = "update t_extended_user set first_name=?,street=?,zipCode=?,city=?,country=?,phone=?,mobile=?,notes=? where id=?";

    public void updateUserExtras(Connection con, UserData userData) throws SQLException{
        if (userData.isNew() || !(userData instanceof ExtendedUserData data))
            return;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(UPDATE_EXTENDED_SQL);
            int i = 1;
            pst.setString(i++, data.getFirstName());
            pst.setString(i++, data.getStreet());
            pst.setString(i++, data.getZipCode());
            pst.setString(i++, data.getCity());
            pst.setString(i++, data.getCountry());
            pst.setString(i++, data.getPhone());
            pst.setString(i++, data.getMobile());
            pst.setString(i++, data.getNotes());
            pst.setInt(i, data.getId());
            pst.executeUpdate();
            pst.close();
            writeUserGroups(con, data);
        } finally {
            closeStatement(pst);
        }
    }

}
