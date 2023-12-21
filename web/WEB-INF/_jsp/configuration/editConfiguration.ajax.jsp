<%--
  Bandika CMS - A Java based modular Content Management System
  Copyright (C) 2009-2021 Michael Roennau

  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
  You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
--%>
<%response.setContentType("text/html;charset=UTF-8");%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/_jsp/_include/_functions.inc.jsp" %>
<%@ page import="de.elbe5.request.RequestData" %>
<%@ page import="de.elbe5.configuration.Configuration" %>
<%@ page import="de.elbe5.base.Mailer" %>
<%@ taglib uri="/WEB-INF/formtags.tld" prefix="form" %>
<%
    RequestData rdata = RequestData.getRequestData(request);
    Configuration data = (Configuration) rdata.getSessionObject("configuration");
    if ((data == null))
        throw new AssertionError();
%>
<div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title"><%=$SH("_configuration")%>
            </h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <form:form url="/ctrl/configuration/saveConfiguration" name="configform" ajax="true">
            <div class="modal-body">
                <form:formerror/>
                <form:text name="smtpHost" label="_smtpHost" required="true" value="<%=$H(data.getSmtpHost())%>"/>
                <form:text name="smtpPort" label="_smtpPort" required="true" value="<%=$I(data.getSmtpPort())%>"/>
                <form:line label="_smtpConnectionType" padded="true" required="true">
                    <form:radio name="smtpConnectionType" value="<%=Mailer.SmtpConnectionType.plain.name()%>" checked="<%=data.getSmtpConnectionType() == Mailer.SmtpConnectionType.plain%>"><%=$SH("_plain")%>
                    </form:radio><br/>
                    <form:radio name="smtpConnectionType" value="<%=Mailer.SmtpConnectionType.ssl.name()%>" checked="<%=data.getSmtpConnectionType() == Mailer.SmtpConnectionType.ssl%>"><%=$SH("_ssl")%>
                    </form:radio><br/>
                    <form:radio name="smtpConnectionType" value="<%=Mailer.SmtpConnectionType.tls.name()%>" checked="<%=data.getSmtpConnectionType() == Mailer.SmtpConnectionType.tls%>"><%=$SH("_tls")%>
                    </form:radio><br/>
                </form:line>
                <form:text name="smtpUser" label="_smtpUser" required="true" value="<%=$H(data.getSmtpUser())%>"/>
                <form:text name="smtpPassword" label="_smtpPassword" required="true" value="<%=$H(data.getSmtpPassword())%>"/>
                <form:text name="mailSender" label="_mailSender" required="true" value="<%=$H(data.getMailSender())%>"/>
                <form:text name="mailReceiver" label="_mailReceiver" required="true" value="<%=$H(data.getMailReceiver())%>"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal"><%=$SH("_close")%>
                </button>
                <button type="submit" class="btn btn-outline-primary"><%=$SH("_save")%>
                </button>
            </div>
        </form:form>
    </div>
</div>

