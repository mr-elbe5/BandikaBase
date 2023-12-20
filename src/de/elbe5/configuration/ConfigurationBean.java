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
        try {
            pst = con.prepareStatement(GET_CONFIGURATION_SQL);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int i = 1;
                    Configuration.setSmtpHost(rs.getString(i++));
                    Configuration.setSmtpPort(rs.getInt(i++));
                    Configuration.setSmtpConnectionType(Mailer.SmtpConnectionType.valueOf(rs.getString(i++)));
                    Configuration.setSmtpUser(rs.getString(i++));
                    Configuration.setSmtpPassword(rs.getString(i++));
                    Configuration.setMailSender(rs.getString(i++));
                    Configuration.setMailReceiver(rs.getString(i));
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
