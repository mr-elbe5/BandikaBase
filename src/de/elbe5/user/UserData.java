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
import de.elbe5.rights.GlobalRights;

import java.util.*;

public class UserData extends BaseData implements IJsonData {

    public static final int ID_ROOT = 1;

    public static int MIN_PASSWORD_LENGTH = 8;

    protected String firstName = "";
    protected String lastName = "";
    protected String email = "";
    protected String login = "";
    protected String passwordHash = "";
    protected String token = "";

    protected boolean locked = false;
    protected boolean deleted = false;

    protected String street = "";
    protected String zipCode = "";
    protected String city = "";
    protected String country = "";
    protected String phone = "";
    protected String mobile = "";
    protected String notes = "";
    protected Set<Integer> groupIds = new HashSet<>();

    protected List<GroupData> groups = new ArrayList<>();

    //from groups
    protected Set<GlobalRights> globalRights = new HashSet<>();

    // base data

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        if (firstName.length() == 0) {
            return lastName;
        }
        return firstName + ' ' + lastName;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<GlobalRights> getGlobalRights() {
        return globalRights;
    }

    public void clearGlobalRights(){
        globalRights.clear();
    }

    public void addGlobalRight(GlobalRights right) {
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

    private void readBasicData(RequestData rdata) {
        setFirstName(rdata.getAttributes().getString("firstName"));
        setLastName(rdata.getAttributes().getString("lastName"));
        setEmail(rdata.getAttributes().getString("email"));
        setStreet(rdata.getAttributes().getString("street"));
        setZipCode(rdata.getAttributes().getString("zipCode"));
        setCity(rdata.getAttributes().getString("city"));
        setCountry(rdata.getAttributes().getString("country"));
        setPhone(rdata.getAttributes().getString("phone"));
        setMobile(rdata.getAttributes().getString("mobile"));
        setNotes(rdata.getAttributes().getString("notes"));
    }

    private void checkBasics(RequestData rdata) {
        if (lastName.isEmpty())
            rdata.addIncompleteField("lastName");
        if (email.isEmpty() && Configuration.isEmailMandatory())
            rdata.addIncompleteField("email");
    }

    public void readSettingsRequestData(RequestData rdata) {
        readBasicData(rdata);
        setLogin(rdata.getAttributes().getString("login"));
        setPassword(rdata.getAttributes().getString("password"));
        setGroupIds(rdata.getAttributes().getIntegerSet("groupIds"));
        if (login.isEmpty())
            rdata.addIncompleteField("login");
        if (isNew() && !hasPassword())
            rdata.addIncompleteField("password");
        checkBasics(rdata);
    }

    public void readProfileRequestData(RequestData rdata) {
        readBasicData(rdata);
        checkBasics(rdata);
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
                .add("isEditor", GlobalRights.hasGlobalContentEditRight(this))
                .add("isAdministrator", GlobalRights.hasGlobalUserEditRight(this));
    }

}
