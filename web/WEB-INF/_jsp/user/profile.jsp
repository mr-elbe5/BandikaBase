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
<%@ page import="de.elbe5.user.UserBean" %>
<%@ page import="de.elbe5.user.UserData" %>
<%@ taglib uri="/WEB-INF/formtags.tld" prefix="form" %>
<%
    RequestData rdata = RequestData.getRequestData(request);
    UserData user = UserBean.getInstance().getUser(rdata.getLoginUser().getId());
%>
<form:message/>
<section class="contentTop">
    <h1>
        <%=$SH("_profile")%>
    </h1>
</section>
<div class="row">
    <section class="col-md-8 contentSection">
        <div class="paragraph form">
            <form:line label="_id"><%=$I(user.getId())%>
            </form:line>
            <form:line label="_login"><%=$H(user.getLogin())%>
            </form:line>
            <form:line label="_name"><%=$H(user.getName())%>
            </form:line>
            <form:line label="_email"><%=$H(user.getEmail())%>
            </form:line>
        </div>
    </section>
    <aside class="col-md-4 asideSection">
        <div class="section">
            <div class="paragraph form">
                <div>
                    <a class="link" href="#" onclick="return openModalDialog('/ctrl/user/openChangePassword');"><%=$SH("_changePassword")%>
                    </a>
                </div>
                <div>
                    <a class="link" href="#" onclick="return openModalDialog('/ctrl/user/openChangeProfile');"><%=$SH("_changeProfile")%>
                    </a>
                </div>
            </div>
        </div>
    </aside>
</div>
