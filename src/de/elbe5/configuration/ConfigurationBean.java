/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.configuration;

import de.elbe5.base.Log;
import de.elbe5.database.DbBean;
import de.elbe5.mail.MailConfiguration;

import java.sql.*;
import java.util.Locale;

public class ConfigurationBean extends DbBean {

    private static ConfigurationBean instance = null;

    public static ConfigurationBean getInstance() {
        if (instance == null) {
            instance = new ConfigurationBean();
        }
        return instance;
    }

    private static final String GET_CONFIGURATION_SQL = "SELECT title,salt,locale,show_date_time,use_read_rights,use_read_group,use_editor_group FROM t_configuration";

    public void readConfiguration() {
        Connection con = getConnection();
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(GET_CONFIGURATION_SQL);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int i = 1;
                    Configuration.setAppTitle(rs.getString(i++));
                    Configuration.setSalt(rs.getString(i++));
                    Configuration.setLocale(new Locale(rs.getString(i++)));
                    Configuration.setShowDateTime(rs.getBoolean(i++));
                    Configuration.setUseReadRights(rs.getBoolean(i++));
                    Configuration.setUseReadGroup(rs.getBoolean(i++));
                    Configuration.setUseEditorGroup(rs.getBoolean(i));
                }
            }
        } catch (SQLException se) {
            Log.error("sql error", se);
        } finally {
            closeStatement(pst);
            closeConnection(con);
        }
    }

    private static final String GET_MAIL_CONFIGURATION_SQL =
            "SELECT smtp_host,smtp_port,smtp_connection_type,smtp_user,smtp_assword,mail_sender,mail_receiver from t_configuration";

    public void readMailConfiguration() {
        Connection con = getConnection();
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement(GET_MAIL_CONFIGURATION_SQL);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int i = 1;
                    MailConfiguration.setSmtpHost(rs.getString(i++));
                    MailConfiguration.setSmtpPort(rs.getInt(i++));
                    MailConfiguration.setSmtpUser(rs.getString(i++));
                    MailConfiguration.setSmtpPassword(rs.getString(i++));
                    MailConfiguration.setMailSender(rs.getString(i++));
                    MailConfiguration.setMailReceiver(rs.getString(i));
                }
            }
        } catch (SQLException se) {
            Log.error("sql error", se);
        } finally {
            closeStatement(pst);
            closeConnection(con);
        }
    }

}
