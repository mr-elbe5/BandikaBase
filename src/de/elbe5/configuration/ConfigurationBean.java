/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.configuration;

import de.elbe5.base.Log;
import de.elbe5.base.Mailer;
import de.elbe5.database.DbBean;

import java.sql.*;

public class ConfigurationBean extends DbBean {

    private static ConfigurationBean instance = null;

    public static ConfigurationBean getInstance() {
        if (instance == null) {
            instance = new ConfigurationBean();
        }
        return instance;
    }

    private static final String GET_CONFIGURATION_SQL =
            "SELECT smtp_host,smtp_port,smtp_connection_type,smtp_user,smtp_password,mail_sender,mail_receiver from t_configuration";

    public void readConfiguration() {
        Connection con = getConnection();
        PreparedStatement pst = null;
        Configuration config = Configuration.getInstance();
        try {
            pst = con.prepareStatement(GET_CONFIGURATION_SQL);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int i = 1;
                    config.setSmtpHost(rs.getString(i++));
                    config.setSmtpPort(rs.getInt(i++));
                    config.setSmtpConnectionType(Mailer.SmtpConnectionType.valueOf(rs.getString(i++)));
                    config.setSmtpUser(rs.getString(i++));
                    config.setSmtpPassword(rs.getString(i++));
                    config.setMailSender(rs.getString(i++));
                    config.setMailReceiver(rs.getString(i));
                }
            }
        } catch (SQLException se) {
            Log.error("sql error", se);
        } finally {
            closeStatement(pst);
            closeConnection(con);
        }
    }

    private static final String UPDATE_CONFIGURATION_SQL =
            "UPDATE t_configuration set smtp_host=?,smtp_port=?,smtp_connection_type=?,smtp_user=?,smtp_password=?,mail_sender=?,mail_receiver=?";

    public boolean updateConfiguration(Configuration config){
        Connection con = getConnection();
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(UPDATE_CONFIGURATION_SQL);
            int i = 1;
            pst.setString(i++, config.getSmtpHost());
            pst.setInt(i++, config.getSmtpPort());
            pst.setString(i++, config.getSmtpConnectionType().name());
            pst.setString(i++, config.getSmtpUser());
            pst.setString(i++, config.getSmtpPassword());
            pst.setString(i++, config.getMailSender());
            pst.setString(i, config.getMailReceiver());
            pst.executeUpdate();
        } catch (SQLException se) {
            Log.error("sql error", se);
            return false;
        } finally {
            closeStatement(pst);
            closeConnection(con);
        }
        return true;
    }

}
