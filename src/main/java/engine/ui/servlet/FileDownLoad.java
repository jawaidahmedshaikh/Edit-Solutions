/*
 * FileDownLoad.java      Version 2.00  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package engine.ui.servlet;

import fission.global.AppReqBlock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/** 
 * This class is used to down load files from the ENGINE system
 */

public class FileDownLoad extends HttpServlet {

     // Constants
    public static final String ERROR_PAGE = "error.jsp";
    public static final String JSP_DIR  = "/jsp/";
    
    
    public void init(ServletConfig sc) throws ServletException {
	
        super.init(sc);
        
    }
	
    public void destroy() {
 
    }


    public void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
		
        // Process Incomming request
        processRequest(req,res);
			
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            
        doGet(req,res);
    }
    private void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        try  {
            // Get Session information
            AppReqBlock aAppReqBlock = 
                (AppReqBlock) req.getAttribute("aAppReqBlock");
            if (aAppReqBlock == null)  {
                throw new Exception("AppReqBlock missing");
            }
            String fileName =
                (String) aAppReqBlock.getHttpSession().getAttribute("FileName");
            String filePath =
                (String) aAppReqBlock.getHttpSession().getAttribute("FilePath");
            String newFileName =
                (String) aAppReqBlock.getHttpSession().getAttribute("NewFileName");
            
            // Edit for fileName and filePath
            if ((fileName == null) || (filePath == null))  {
                throw new Exception("File Name/Path are required for " 
                    + "this option");
            }
            // newFileName is optional
            if (newFileName == null) {
                newFileName = fileName;
            }
        
            // Make sure user is logged in
//            if (!fission.ui.servlet.LoginTran.loggedIn(aAppReqBlock)) {
//                throw new Exception("User Login is required for this option");
//            }
        
            // Download File
            res.setContentType("application/x-filler");
            res.setHeader("Content-Disposition","attachment;filename=" 
                + newFileName);
            ServletOutputStream outputStream = res.getOutputStream();
            BufferedInputStream inputStream = new BufferedInputStream(
                new FileInputStream(filePath + fileName));
            
            byte[] inBuf = new byte[(1024 * 7 + 512)];
            int readCount = 0;
            while ((readCount = inputStream.read(inBuf,0,inBuf.length)) != -1){
                outputStream.write(inBuf,0,readCount);
            }
            
            //Close Files
            outputStream.close();
            inputStream.close();
           
        } catch (Exception e)  { 
            displayError(req, res, e); 
        
        }
        
         
    }

    // Displays error on Error web page
    private void displayError(HttpServletRequest req,HttpServletResponse res,
            Exception e)  {
    
        try  {
            //displayError(e.getMessage(), e); 
            req.setAttribute("javax.servlet.jsp.jspException",e);
            RequestDispatcher rd =
                getServletContext().getRequestDispatcher(JSP_DIR + ERROR_PAGE);
            rd.forward(req, res);
        
        } catch (Exception e2)  {
            displayError("RequestDispatcher problem displaying error page"
                , e2); 
        }
    }
    
    private void displayError(HttpServletRequest req, HttpServletResponse res, 
            String s)  {
    
        displayError(req, res, new Exception(s));
    }   
    
    private void displayError(String message, Exception e)  {
    
      System.out.println(e);
        
        e.printStackTrace();
    }
   
}
