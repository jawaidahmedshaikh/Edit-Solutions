/*
 * User: sdorman
 * Date: Jun 3, 2004
 * Time: 8:39:05 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package fission.ui.servlet;

import security.business.Security;
import security.component.SecurityComponent;
import security.utility.ProductSecurityConversion;

import edit.services.config.ServicesConfig;
import edit.services.db.DBTable;
import edit.common.exceptions.EDITSecurityException;
import edit.common.vo.EDITExport;
import edit.common.vo.EDITServicesConfig;
import edit.common.vo.JAASConfig;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import java.io.FileReader;
import java.io.IOException;

import fission.utility.*;



/**
 * Super class of all request managers.  Provides initialization of security and loading of the config file.
 */
public class BaseRequestManager extends HttpServlet
{

}