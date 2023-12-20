/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.configuration;

import de.elbe5.base.Mailer;

import java.util.*;

public class Configuration {
    public static String smtpHost = null;
    public static int smtpPort = 25;
    public static Mailer.SmtpConnectionType smtpConnectionType = Mailer.SmtpConnectionType.plain;
    public static String smtpUser = "";
    public static String smtpPassword = "";
    public static String mailSender = null;
    public static String mailReceiver = null;

    public static String getSmtpHost() {
        return smtpHost;
    }

    public static void setSmtpHost(String smtpHost) {
        Configuration.smtpHost = smtpHost;
    }

    public static int getSmtpPort() {
        return smtpPort;
    }

    public static void setSmtpPort(int smtpPort) {
        Configuration.smtpPort = smtpPort;
    }

    public static Mailer.SmtpConnectionType getSmtpConnectionType() {
        return smtpConnectionType;
    }

    public static void setSmtpConnectionType(Mailer.SmtpConnectionType smtpConnectionType) {
        Configuration.smtpConnectionType = smtpConnectionType;
    }

    public static String getSmtpUser() {
        return smtpUser;
    }

    public static void setSmtpUser(String smtpUser) {
        Configuration.smtpUser = smtpUser;
    }

    public static String getSmtpPassword() {
        return smtpPassword;
    }

    public static void setSmtpPassword(String smtpPassword) {
        Configuration.smtpPassword = smtpPassword;
    }

    public static String getMailSender() {
        return mailSender;
    }

    public static void setMailSender(String mailSender) {
        Configuration.mailSender = mailSender;
    }

    public static String getMailReceiver() {
        return mailReceiver;
    }

    public static void setMailReceiver(String mailReceiver) {
        Configuration.mailReceiver = mailReceiver;
    }


    // base data

}
