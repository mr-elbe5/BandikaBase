/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.user;

import de.elbe5.base.*;
import de.elbe5.application.Configuration;
import de.elbe5.base.BaseData;
import de.elbe5.group.GroupData;
import de.elbe5.request.RequestData;
import de.elbe5.rights.GlobalRight;

import java.util.*;

public class UserData extends BaseData implements IJsonData {

    public static final int ID_ROOT = 1;

    public static int MIN_PASSWORD_LENGTH = 8;

    protected String name = "";
    protected String email = "";
    protected String login = "";
    protected String passwordHash = "";
    protected String token = "";

    protected boolean locked = false;
    protected boolean deleted = false;

    protected Set<Integer> groupIds = new HashSet<>();

    protected List<GroupData> groups = new ArrayList<>();

    //from groups
    protected Set<GlobalRight> globalRights = new HashSet<>();

    public UserData(){
    }

    public String getType() {
        return getClass().getName();
    }

    public UserBean getBean() {
        return UserBean.getInstance();
    }

    // base data

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean hasPassword() {
        return !passwordHash.isEmpty();
    }

    public void setPassword(String password) {
        if (password.isEmpty()) {
            setPasswordHash("");
        } else {
            setPasswordHash(UserSecurity.encryptPassword(password, Configuration.getSalt()));
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    public Set<GlobalRight> getGlobalRights() {
        return globalRights;
    }

    public void clearGlobalRights(){
        globalRights.clear();
    }

    public void addGlobalRight(GlobalRight right) {
        globalRights.add(right);
    }

    public boolean isRoot(){
        return getId()== ID_ROOT;
    }

    public Set<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Set<Integer> groupIds) {
        this.groupIds = groupIds;
    }

    // multiple data

    public String getBackendEditJsp() {
        return "/WEB-INF/_jsp/user/editUser.ajax.jsp";
    }

    public String getProfileJsp() {
        return "/WEB-INF/_jsp/user/profile.jsp";
    }

    public String getProfileEditJsp() {
        return "/WEB-INF/_jsp/user/changeProfile.ajax.jsp";
    }

    public void readBackendRequestData(RequestData rdata) {
        setName(rdata.getAttributes().getString("name"));
        setEmail(rdata.getAttributes().getString("email"));
        setLogin(rdata.getAttributes().getString("login"));
        setPassword(rdata.getAttributes().getString("password"));
        setGroupIds(rdata.getAttributes().getIntegerSet("groupIds"));
        if (login.isEmpty())
            rdata.addIncompleteField("login");
        if (isNew() && !hasPassword())
            rdata.addIncompleteField("password");
        if (name.isEmpty())
            rdata.addIncompleteField("name");
    }

    public void readProfileRequestData(RequestData rdata) {
        setName(rdata.getAttributes().getString("name"));
        setEmail(rdata.getAttributes().getString("email"));
        if (name.isEmpty())
            rdata.addIncompleteField("name");
    }

    @Override
    public JsonObject getJson() {
        return super.getJson()
                .add("id", getId())
                .add("name", getName());
    }

    public JsonObject getLoginJson() {
        return new JsonObject()
                .add("id",getId())
                .add("login",getLogin())
                .add("name", getName())
                .add("token", getToken())
                .add("isEditor", GlobalRight.hasGlobalContentEditRight(this))
                .add("isAdministrator", GlobalRight.hasGlobalUserEditRight(this));
    }

}
