/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.user;

import de.elbe5.base.*;
import de.elbe5.base.BaseData;
import de.elbe5.request.*;
import de.elbe5.rights.GlobalRight;
import de.elbe5.servlet.Controller;
import de.elbe5.servlet.ControllerCache;
import de.elbe5.response.*;

import de.elbe5.servlet.ResponseException;
import jakarta.servlet.http.HttpServletResponse;

public class UserController extends Controller {

    public static final String KEY = "user";

    private static UserController instance = null;

    public static void setInstance(UserController instance) {
        UserController.instance = instance;
    }

    public static UserController getInstance() {
        return instance;
    }

    public static void register(UserController controller){
        setInstance(controller);
        ControllerCache.addController(controller.getKey(),getInstance());
    }

    @Override
    public String getKey() {
        return KEY;
    }

    public IResponse openLogin(RequestData rdata) {
        return showLogin();
    }

    public IResponse login(RequestData rdata) {
        if (rdata.getContext()==RequestContext.api){
            return apiLogin(rdata);
        }
        return webLogin(rdata);
    }

    protected IResponse webLogin(RequestData rdata) {
        assertSessionCall(rdata);
        assertRights(rdata.isPostback());
        String login = rdata.getAttributes().getString("login");
        String pwd = rdata.getAttributes().getString("password");
        if (login.length() == 0 || pwd.length() == 0) {
            rdata.setMessage($S("_notComplete"), RequestKeys.MESSAGE_TYPE_ERROR);
            return openLogin(rdata);
        }
        UserData data = UserBean.getInstance().loginUser(login, pwd);
        if (data == null) {
            Log.info("bad login of "+login);
            rdata.setMessage($S("_badLogin"), RequestKeys.MESSAGE_TYPE_ERROR);
            return openLogin(rdata);
        }
        rdata.setSessionUser(data);
        initWebUser(data, rdata);
        String next = rdata.getAttributes().getString("next");
        if (!next.isEmpty())
                return new ForwardResponse(next);
        return showLoginHome(rdata);
    }

    protected IResponse showLoginHome(RequestData rdata) {
        return showHome();
    }

    protected IResponse apiLogin(RequestData rdata) {
        assertApiCall(rdata);
        if (!rdata.isPostback())
            throw new ResponseException(HttpServletResponse.SC_UNAUTHORIZED);
        String login = rdata.getAttributes().getString("login");
        String pwd = rdata.getAttributes().getString("password");
        if (login.length() == 0 || pwd.length() == 0) {
            return new StatusResponse(HttpServletResponse.SC_UNAUTHORIZED);
        }
        UserData data = UserBean.getInstance().loginApiUser(login, pwd);
        if (data == null) {
            Log.info("bad login of "+login);
            return new StatusResponse(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (data.getToken().isEmpty()){
            if (!UserBean.getInstance().setToken(data))
                return new StatusResponse(HttpServletResponse.SC_UNAUTHORIZED);
        }
        initApiUser(data, rdata);
        JsonObject json = data.getLoginJson();
        return new JsonResponse(json);
    }

    protected void initWebUser(UserData data, RequestData rdata){

    }

    protected void initApiUser(UserData data, RequestData rdata){

    }

    public IResponse checkTokenLogin(RequestData rdata) {
        assertApiCall(rdata);
        if (!rdata.isPostback())
            throw new ResponseException(HttpServletResponse.SC_UNAUTHORIZED);
        if (!rdata.isLoggedIn())
            return new StatusResponse(HttpServletResponse.SC_UNAUTHORIZED);
        return new StatusResponse(HttpServletResponse.SC_OK);
    }

    public IResponse showCaptcha(RequestData rdata) {
        assertSessionCall(rdata);
        String captcha = UserSecurity.generateCaptchaString();
        rdata.setSessionObject(RequestKeys.KEY_CAPTCHA, captcha);
        BinaryFile data = UserSecurity.getCaptcha(captcha);
        if (data==null){
            return new StatusResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return new MemoryFileResponse(data);
    }

    public IResponse logout(RequestData rdata) {
        assertSessionCall(rdata);
        rdata.setSessionUser(null);
        rdata.resetSession();
        rdata.setMessage($S("_loggedOut"), RequestKeys.MESSAGE_TYPE_SUCCESS);
        String next = rdata.getAttributes().getString("next");
        if (!next.isEmpty())
            return new ForwardResponse(next);
        return showLogoutHome();
    }

    protected IResponse showLogoutHome() {
        return showHome();
    }

    public IResponse openCreateUser(RequestData rdata) {
        assertLoggedInSessionCall(rdata);
        assertRights(GlobalRight.hasGlobalUserEditRight(rdata.getLoginUser()));
        UserData data = getNewUserData();
        data.setCreateValues(rdata, RequestType.backend);
        data.setId(UserBean.getInstance().getNextId());
        rdata.setSessionObject("userData", data);
        return showEditUser(data);
    }

    public IResponse openEditUser(RequestData rdata) {
        assertLoggedInSessionCall(rdata);
        assertRights(GlobalRight.hasGlobalUserEditRight(rdata.getLoginUser()));
        int userId = rdata.getId();
        UserData data = UserBean.getInstance().getUser(userId);
        data.setUpdateValues(rdata);
        rdata.setSessionObject("userData", data);
        return showEditUser(data);
    }

    protected UserData getNewUserData(){
        return new UserData();
    }

    public IResponse saveUser(RequestData rdata) {
        assertLoggedInSessionCall(rdata);
        assertRights(GlobalRight.hasGlobalUserEditRight(rdata.getLoginUser()));
        UserData data = (UserData) rdata.getSessionObject("userData");
        data.readBackendRequestData(rdata);
        if (!rdata.checkFormErrors()) {
            return showEditUser(data);
        }
        UserBean.getInstance().saveUser(data);
        UserCache.setDirty();
        if (rdata.getUserId() == data.getId()) {
            rdata.setSessionUser(data);
        }
        rdata.setMessage($S("_userSaved"), RequestKeys.MESSAGE_TYPE_SUCCESS);
        return new CloseDialogResponse("/ctrl/admin/openPersonAdministration?userId=" + data.getId());
    }

    public IResponse deleteUser(RequestData rdata) {
        assertLoggedInSessionCall(rdata);
        assertRights(GlobalRight.hasGlobalUserEditRight(rdata.getLoginUser()));
        int id = rdata.getId();
        if (id < BaseData.ID_MIN) {
            rdata.setMessage($S("_notDeletable"), RequestKeys.MESSAGE_TYPE_ERROR);
            return new ForwardResponse("/ctrl/admin/openPersonAdministration");
        }
        if (!UserBean.getInstance().deleteUser(id)){
            rdata.setMessage($S("_userNotDeleted"), RequestKeys.MESSAGE_TYPE_ERROR);
            return new ForwardResponse("/ctrl/admin/openPersonAdministration");
        }
        UserCache.setDirty();
        rdata.setMessage($S("_userDeleted"), RequestKeys.MESSAGE_TYPE_SUCCESS);
        return new ForwardResponse("/ctrl/admin/openPersonAdministration");
    }

    public IResponse openProfile(RequestData rdata) {
        assertSessionCall(rdata);
        assertRights(rdata.isLoggedIn());
        return showProfile(rdata.getLoginUser());
    }

    public IResponse openChangePassword(RequestData rdata) {
        assertSessionCall(rdata);
        assertRights(rdata.isLoggedIn());
        return showChangePassword();
    }

    public IResponse changePassword(RequestData rdata) {
        assertLoggedInSessionCall(rdata);
        assertRights(rdata.isLoggedIn() && rdata.getUserId() == rdata.getId());
        UserData user = UserBean.getInstance().getUser(rdata.getLoginUser().getId());
        if (user==null){
            return new StatusResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        String oldPassword = rdata.getAttributes().getString("oldPassword");
        String newPassword = rdata.getAttributes().getString("newPassword1");
        String newPassword2 = rdata.getAttributes().getString("newPassword2");
        if (newPassword.length() < UserData.MIN_PASSWORD_LENGTH) {
            rdata.addFormErrorField("newPassword1");
            rdata.addFormError($S("_passwordLengthError"));
            return showChangePassword();
        }
        if (!newPassword.equals(newPassword2)) {
            rdata.addFormErrorField("newPassword1");
            rdata.addFormErrorField("newPassword2");
            rdata.addFormError($S("_passwordsDontMatch"));
            return showChangePassword();
        }
        UserData data = UserBean.getInstance().loginUser(user.getLogin(), oldPassword);
        if (data == null) {
            rdata.addFormErrorField("newPassword1");
            rdata.addFormError($S("_badLogin"));
            return showChangePassword();
        }
        data.setPassword(newPassword);
        data.setUpdateValues(rdata);
        UserBean.getInstance().saveUserPassword(data);
        rdata.setMessage($S("_passwordChanged"), RequestKeys.MESSAGE_TYPE_SUCCESS);
        return new CloseDialogResponse("/ctrl/user/openProfile");
    }

    public IResponse openChangeProfile(RequestData rdata) {
        assertSessionCall(rdata);
        assertRights(rdata.isLoggedIn());
        return showChangeProfile(rdata.getLoginUser());
    }

    public IResponse changeProfile(RequestData rdata) {
        assertSessionCall(rdata);
        int userId = rdata.getId();
        assertRights(rdata.isLoggedIn() && rdata.getUserId() == userId);
        UserData data = UserBean.getInstance().getUser(userId);
        data.readProfileRequestData(rdata);
        if (!rdata.checkFormErrors()) {
            return showChangeProfile(data);
        }
        UserBean.getInstance().saveUserProfile(data);
        rdata.setSessionUser(data);
        UserCache.setDirty();
        rdata.setMessage($S("_userSaved"), RequestKeys.MESSAGE_TYPE_SUCCESS);
        return new CloseDialogResponse("/ctrl/user/openProfile");
    }

    protected IResponse showProfile(UserData data) {
        JspInclude jsp = new JspInclude(data.getProfileJsp());
        return new MasterResponse(MasterResponse.DEFAULT_MASTER, jsp);
    }

    protected IResponse showChangePassword() {
        return new ForwardResponse("/WEB-INF/_jsp/user/changePassword.ajax.jsp");
    }

    protected IResponse showChangeProfile(UserData data) {
        return new ForwardResponse(data.getProfileEditJsp());
    }

    protected IResponse showLogin() {
        return new ForwardResponse("/WEB-INF/_jsp/user/login.jsp");
    }

    protected IResponse showEditUser(UserData data) {
        return new ForwardResponse(data.getBackendEditJsp());
    }

}
