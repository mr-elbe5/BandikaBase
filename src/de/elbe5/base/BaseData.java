/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2018 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.base;

import de.elbe5.request.RequestData;
import de.elbe5.request.RequestType;
import de.elbe5.user.UserCache;
import de.elbe5.user.UserData;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;

public class BaseData implements IJsonData {

    public static final int ID_MIN = 100;

    private int id = 0;
    private boolean isNew = false;
    private LocalDateTime creationDate = null;
    private LocalDateTime changeDate = null;
    private int creatorId = 0;
    private int changerId = 0;

    public BaseData(){

    }

    public BaseData(BaseData data){
        setNew(data.isNew());
        setId(data.getId());
        setCreationDate(data.getCreationDate());
        setChangeDate(data.getChangeDate());
        setCreatorId(data.getCreatorId());
        setChangerId(data.getChangerId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
        if (creationDate == null)
            creationDate = changeDate;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getChangerId() {
        return changerId;
    }

    public void setChangerId(int changerId) {
        this.changerId = changerId;
    }

    public String getCreatorName(){
        UserData user= UserCache.getUser(getCreatorId());
        if (user!=null)
            return user.getName();
        return "";
    }

    public String getChangerName(){
        UserData user= UserCache.getUser(getChangerId());
        if (user!=null)
            return user.getName();
        return "";
    }

    public boolean hasValidData(){
        return getId() != 0;
    }

    public void setCreateValues(RequestData rdata, RequestType type) {
        setNew(true);
        switch (type) {
            case api -> {
            }
            case backend, frontend -> {
                setCreationDate(DateHelper.getCurrentTime());
                setChangeDate(getCreationDate());
                setCreatorId(rdata.getUserId());
                setChangerId(getCreatorId());
                setNewId();
            }
        }
    }

    public void setNewId(){
    }

    public void setUpdateValues(RequestData rdata){
        setChangeDate(DateHelper.getCurrentTime());
        setChangerId(rdata.getUserId());
    }

    public void readRequestData(RequestData rdata, RequestType type){
        switch (type){
            case api -> {
                setId(rdata.getAttributes().getInt("id"));
                setCreatorId(rdata.getAttributes().getInt("creatorId"));
                setCreationDate(rdata.getAttributes().getIsoDateTime("creationDate"));
                setChangerId(rdata.getAttributes().getInt("changerId"));
                setChangeDate(rdata.getAttributes().getIsoDateTime("changeDate"));
                if (changeDate == null){
                    setChangeDate(creationDate);
                }
            }
            case backend, frontend -> {
            }
        }
    }

    public JsonObject getJson() {
        return new JsonObject()
                .add("id", getId())
                .add("isOnServer", true)
                .add("creationDate", getCreationDate())
                .add("creatorId", getCreatorId())
                .add("changeDate", getChangeDate())
                .add("changerId", getChangerId())
                .add("creatorName", getCreatorName())
                .add("changerName", getChangerName());
    }

    public JsonObject getIdJson() {
        JsonObject json = new JsonObject();
        json.add("id",getId());
        return json;
    }

    @Override
    public void fromJson(JSONObject json) {
        int i = getInt(json, "id");
        if (i!=0)
            setId(i);
        LocalDateTime ldt = getLocalDateTime(json,"creationDate");
        if (ldt != null)
            setCreationDate(ldt);
        i = getInt(json, "creatorId");
        if (i!=0)
            setCreatorId(i);
        ldt = getLocalDateTime(json,"changeDate");
        if (ldt != null)
            setChangeDate(ldt);
        i = getInt(json, "changerId");
        if (i!=0)
            setChangerId(i);
    }

    public String getClassDisplayName(){
        return LocalizedSystemStrings.getInstance().string(getClass().getName());
    }
}
