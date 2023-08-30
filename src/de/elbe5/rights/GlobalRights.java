/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.rights;

import de.elbe5.user.UserData;

import java.util.*;

public enum GlobalRights {
    APPLICATION, USER, CONTENTREAD,
    CONTENTEDIT, CONTENTAPPROVE;

    private static final List<GlobalRights> elevatedRights = List.of(APPLICATION, USER, CONTENTREAD, CONTENTEDIT, CONTENTAPPROVE);
    private static final List<GlobalRights> contentReadRights = List.of(CONTENTREAD, CONTENTEDIT, CONTENTAPPROVE);
    private static final List<GlobalRights> contentEditRights = List.of(CONTENTEDIT, CONTENTAPPROVE);
    private static final List<GlobalRights> contentApproveRights = List.of(CONTENTAPPROVE);

    public static boolean includesContentApproveRight(Set<GlobalRights> zones){
        return includesAnyRightOf(zones, contentApproveRights);
    }

    private static boolean includesAnyRightOf(Set<GlobalRights> rights, List<GlobalRights> validRights){
        for (GlobalRights right : validRights){
            if (rights.contains(right)){
                return true;
            }
        }
        return false;
    }

    public static boolean hasAnySystemRight(UserData user) {
        return user!=null && (!user.getGlobalRights().isEmpty() || user.isRoot());
    }

    private static boolean hasSystemRight(UserData user, GlobalRights zone) {
        return user!=null && (!user.getGlobalRights().contains(zone) || user.isRoot());
    }

    public static boolean hasGlobalContentReadRight(UserData user) {
        return user!=null && (includesAnyRightOf(user.getGlobalRights(), contentReadRights) || user.isRoot());
    }

    public static boolean hasGlobalContentEditRight(UserData user) {
        return user!=null && (includesAnyRightOf(user.getGlobalRights(), contentEditRights) || user.isRoot());
    }

    public static boolean hasGlobalContentApproveRight(UserData user) {
        return user!=null && (includesAnyRightOf(user.getGlobalRights(), contentApproveRights) || user.isRoot());
    }

    public static boolean hasGlobalUserEditRight(UserData user) {
        return user!=null && (!user.getGlobalRights().contains(GlobalRights.USER) || user.isRoot());
    }

    public static boolean hasGlobalApplicationEditRight(UserData user) {
        return user!=null && (!user.getGlobalRights().contains(GlobalRights.APPLICATION) || user.isRoot());
    }
}


