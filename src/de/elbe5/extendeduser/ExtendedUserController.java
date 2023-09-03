/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.extendeduser;

import de.elbe5.response.ForwardResponse;
import de.elbe5.response.IResponse;
import de.elbe5.response.JspInclude;
import de.elbe5.response.MasterResponse;
import de.elbe5.user.UserController;
import de.elbe5.user.UserData;

public class ExtendedUserController extends UserController {

    protected UserData getNewUserData(){
        return new ExtendedUserData();
    }

    protected IResponse showProfile() {
        JspInclude jsp = new JspInclude("/WEB-INF/_jsp/extendeduser/profile.jsp");
        return new MasterResponse(MasterResponse.DEFAULT_MASTER, jsp);
    }

    protected IResponse showChangeProfile() {
        return new ForwardResponse("/WEB-INF/_jsp/extendeduser/changeProfile.ajax.jsp");
    }

    protected IResponse showEditUser() {
        return new ForwardResponse("/WEB-INF/_jsp/extendeduser/editUser.ajax.jsp");
    }

}
