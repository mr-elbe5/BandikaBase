/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2021 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.configuration;

import de.elbe5.request.RequestData;
import de.elbe5.request.RequestKeys;
import de.elbe5.response.CloseDialogResponse;
import de.elbe5.response.ForwardResponse;
import de.elbe5.response.IResponse;
import de.elbe5.response.StatusResponse;
import de.elbe5.rights.GlobalRight;
import de.elbe5.servlet.Controller;
import de.elbe5.servlet.ControllerCache;
import jakarta.servlet.http.HttpServletResponse;

public class ConfigurationController extends Controller {

    public static final String KEY = "configuration";

    private static ConfigurationController instance = null;

    public static void setInstance(ConfigurationController instance) {
        ConfigurationController.instance = instance;
    }

    public static ConfigurationController getInstance() {
        return instance;
    }

    public static void register(ConfigurationController controller){
        setInstance(controller);
        ControllerCache.addController(controller.getKey(),getInstance());
    }

    @Override
    public String getKey() {
        return KEY;
    }

    public IResponse openEditConfiguration(RequestData rdata) {
        assertLoggedInSessionCall(rdata);
        assertRights(GlobalRight.hasGlobalApplicationEditRight(rdata.getLoginUser()));
        Configuration config = Configuration.getInstance().getCopy();
        rdata.setSessionObject("configuration", config);
        return showEditConfiguration();
    }

    public IResponse saveConfiguration(RequestData rdata) {
        assertLoggedInSessionCall(rdata);
        assertRights(GlobalRight.hasGlobalApplicationEditRight(rdata.getLoginUser()));
        Configuration data = (Configuration) rdata.getSessionObject("configuration");
        if (data==null){
            return new StatusResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        data.readRequestData(rdata);
        if (!rdata.checkFormErrors()) {
            return showEditConfiguration();
        }
        ConfigurationBean bean = ConfigurationBean.getInstance();
        if (bean.updateConfiguration(data)) {
            Configuration.setInstance(data);
            rdata.setMessage($S("_configurationSaved"), RequestKeys.MESSAGE_TYPE_SUCCESS);
            return new CloseDialogResponse("/ctrl/admin/openSystemAdministration");
        }
        else{
            return showEditConfiguration();
        }
    }

    private IResponse showEditConfiguration() {
        return new ForwardResponse("/WEB-INF/_jsp/configuration/editConfiguration.ajax.jsp");
    }

}
