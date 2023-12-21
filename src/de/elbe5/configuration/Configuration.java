/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.configuration;

import de.elbe5.base.Mailer;
import de.elbe5.request.RequestData;

public class Configuration {

    private static Configuration instance = new Configuration();

    public static void setInstance(Configuration instance) {
        Configuration.instance = instance;
    }

    public static Configuration getInstance() {
        return instance;
    }

    public String smtpHost = null;
    public int smtpPort = 25;
    public Mailer.SmtpConnectionType smtpConnectionType = Mailer.SmtpConnectionType.plain;
    public String smtpUser = "";
    public String smtpPassword = "";
    public String mailSender = null;
    public String mailReceiver = null;

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public Mailer.SmtpConnectionType getSmtpConnectionType() {
        return smtpConnectionType;
    }

    public void setSmtpConnectionType(Mailer.SmtpConnectionType smtpConnectionType) {
        this.smtpConnectionType = smtpConnectionType;
    }

    public String getSmtpUser() {
        return smtpUser;
    }

    public void setSmtpUser(String smtpUser) {
        this.smtpUser = smtpUser;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public String getMailSender() {
        return mailSender;
    }

    public void setMailSender(String mailSender) {
        this.mailSender = mailSender;
    }

    public String getMailReceiver() {
        return mailReceiver;
    }

    public void setMailReceiver(String mailReceiver) {
        this.mailReceiver = mailReceiver;
    }

    public Configuration getCopy(){
        Configuration config = new Configuration();
        config.smtpHost = this.smtpHost;
        config.smtpPort = this.smtpPort;
        config.smtpConnectionType = this.smtpConnectionType;
        config.smtpUser = this.smtpUser;
        config.smtpPassword = this.smtpPassword;
        config.mailSender = this.mailSender;
        config.mailReceiver = this.mailReceiver;
        return config;
    }

    public void readRequestData(RequestData rdata){
        smtpHost = rdata.getAttributes().getString("smtpHost");
        smtpPort = rdata.getAttributes().getInt("smtpPort");
        smtpConnectionType = Mailer.SmtpConnectionType.valueOf(rdata.getAttributes().getString("smtpConnectionType"));
        smtpUser = rdata.getAttributes().getString("smtpUser");
        smtpPassword = rdata.getAttributes().getString("smtpPassword");
        mailSender = rdata.getAttributes().getString("mailSender");
        mailReceiver = rdata.getAttributes().getString("mailReceiver");
    }

}
