/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.extendeduser;

import de.elbe5.base.JsonObject;
import de.elbe5.request.RequestData;
import de.elbe5.rights.GlobalRight;
import de.elbe5.user.UserBean;
import de.elbe5.user.UserData;

public class ExtendedUserData extends UserData {

    protected String firstName = "";
    protected String email = "";
    protected String street = "";
    protected String zipCode = "";
    protected String city = "";
    protected String country = "";
    protected String phone = "";
    protected String mobile = "";
    protected String notes = "";

    public ExtendedUserData(){
    }

    public UserBean getBean() {
        return ExtendedUserBean.getInstance();
    }

    // base data

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        if (firstName.length() == 0) {
            return name;
        }
        return firstName + ' ' + name;
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

    public String getBackendEditJsp() {
        return "/WEB-INF/_jsp/extendeduser/editUser.ajax.jsp";
    }

    public String getProfileJsp() {
        return "/WEB-INF/_jsp/extendeduser/profile.jsp";
    }

    public String getProfileEditJsp() {
        return "/WEB-INF/_jsp/extendeduser/changeProfile.ajax.jsp";
    }

    public void readBasicData(RequestData rdata) {
        super.readBackendRequestData(rdata);
        setFirstName(rdata.getAttributes().getString("firstName"));
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
        if (getName().isEmpty())
            rdata.addIncompleteField("lastName");
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
                .add("isEditor", GlobalRight.hasGlobalContentEditRight(this))
                .add("isAdministrator", GlobalRight.hasGlobalUserEditRight(this));
    }

}
