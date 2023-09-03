/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.mail;

import de.elbe5.application.Configuration;
import de.elbe5.base.Mailer;
import jakarta.servlet.ServletContext;

public class MailConfiguration {

    public static String ENCODING = "UTF-8";

    private static String smtpHost = null;
    private static int smtpPort = 25;
    private static Mailer.SmtpConnectionType smtpConnectionType = Mailer.SmtpConnectionType.plain;
    private static String smtpUser = "";
    private static String smtpPassword = "";
    private static String mailSender = null;
    private static String mailReceiver = null;

    public static String getSmtpHost() {
        return smtpHost;
    }

    public static void setSmtpHost(String smtpHost) {
        MailConfiguration.smtpHost = smtpHost;
    }

    public static int getSmtpPort() {
        return smtpPort;
    }

    public static void setSmtpPort(int smtpPort) {
        MailConfiguration.smtpPort = smtpPort;
    }

    public static Mailer.SmtpConnectionType getSmtpConnectionType() {
        return smtpConnectionType;
    }

    public static void setSmtpConnectionType(Mailer.SmtpConnectionType smtpConnectionType) {
        MailConfiguration.smtpConnectionType = smtpConnectionType;
    }

    public static String getSmtpUser() {
        return smtpUser;
    }

    public static void setSmtpUser(String smtpUser) {
        MailConfiguration.smtpUser = smtpUser;
    }

    public static String getSmtpPassword() {
        return smtpPassword;
    }

    public static void setSmtpPassword(String smtpPassword) {
        MailConfiguration.smtpPassword = smtpPassword;
    }

    public static String getMailSender() {
        return mailSender;
    }

    public static void setMailSender(String mailSender) {
        MailConfiguration.mailSender = mailSender;
    }

    public static String getMailReceiver() {
        return mailReceiver;
    }

    public static void setMailReceiver(String mailReceiver) {
        MailConfiguration.mailReceiver = mailReceiver;
    }

    // read from config file

    public static void setConfigs(ServletContext servletContext) {
        setSmtpHost(Configuration.getSafeInitParameter(servletContext,"mailHost"));
        setSmtpPort(Integer.parseInt(Configuration.getSafeInitParameter(servletContext,"mailPort")));
        setSmtpConnectionType(Mailer.SmtpConnectionType.valueOf(Configuration.getSafeInitParameter(servletContext,"mailConnectionType")));
        setSmtpUser(Configuration.getSafeInitParameter(servletContext,"mailUser"));
        setSmtpPassword(Configuration.getSafeInitParameter(servletContext,"mailPassword"));
        setMailSender(Configuration.getSafeInitParameter(servletContext,"mailSender"));
        setMailReceiver(Configuration.getSafeInitParameter(servletContext,"mailReceiver"));
    }

}
