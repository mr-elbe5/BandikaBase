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
<%@ page import="de.elbe5.content.ContentCache" %>
<%@ page import="de.elbe5.request.RequestData" %>
<%@ page import="de.elbe5.content.ContentData" %>
<%@ page import="de.elbe5.rights.GlobalRight" %>
<%@ page import="de.elbe5.rights.GlobalRight" %>
<%@ taglib uri="/WEB-INF/formtags.tld" prefix="form" %>
<%
    RequestData rdata = RequestData.getRequestData(request);
    ContentData rootContent = ContentCache.getContentRoot();
%>
            <section class="treeSection">
                <% if (GlobalRight.hasGlobalContentEditRight(rdata.getLoginUser())) { %>
                <div class = "">
                    <a class = "btn btn-sm btn-outline-light" href="/ctrl/content/clearClipboard"><%=$SH("_clearClipboard")%></a>
                </div>
                <div class = "">
                    <a class = "btn btn-sm btn-outline-light" href="/ctrl/content/reduceImages"><%=$SH("_reduceImages")%></a>
                </div>
                <ul class="tree pagetree">
                    <% rootContent.displayBackendTreeContent(pageContext,rdata);%>
                </ul>
                <%}%>
            </section>



