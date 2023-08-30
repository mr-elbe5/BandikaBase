/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.rights;

import java.util.*;

public enum SystemZone {
    APPLICATION, USER, CONTENTREAD,
    CONTENTEDIT, CONTENTAPPROVE;

    private static final List<SystemZone> elevatedZones = List.of(APPLICATION, USER, CONTENTREAD, CONTENTEDIT, CONTENTAPPROVE);
    private static final List<SystemZone> contentReadZones = List.of(CONTENTREAD, CONTENTEDIT, CONTENTAPPROVE);
    private static final List<SystemZone> contentEditZones = List.of(CONTENTEDIT, CONTENTAPPROVE);
    private static final List<SystemZone> contentApproveZones = List.of(CONTENTAPPROVE);

    public static boolean includesElevatedZone(Set<SystemZone> zones){
        return includesAnyZoneOf(zones, elevatedZones);
    }

    public static boolean includesContentReadZone(Set<SystemZone> zones){
        return includesAnyZoneOf(zones, contentReadZones);
    }

    public static boolean includesContentEditZone(Set<SystemZone> zones){
        return includesAnyZoneOf(zones, contentEditZones);
    }

    public static boolean includesContentApproveZone(Set<SystemZone> zones){
        return includesAnyZoneOf(zones, contentApproveZones);
    }

    private static boolean includesAnyZoneOf(Set<SystemZone> zones, List<SystemZone> validZones){
        for (SystemZone zone : validZones){
            if (zones.contains(zone)){
                return true;
            }
        }
        return false;
    }
}


