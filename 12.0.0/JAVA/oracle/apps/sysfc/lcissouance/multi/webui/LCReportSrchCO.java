/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package oracle.apps.sfc.lcissuance.multi.webui;

import com.sun.java.util.collections.HashMap;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.sfc.lcissuance.common.server.CommonClass;
import oracle.apps.sfc.lcissuance.multi.server.LCMultiAMImpl;

import oracle.apps.sfc.lcissuance.multi.server.LCRequestHdrEVORowImpl;

import oracle.jbo.Row;

/**
 * Controller for ...
 */
public class LCReportSrchCO extends OAControllerImpl
{
    CommonClass      cc              =   new CommonClass();
    public static final String RCS_ID="$Header$";
    public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");
    public void  writeLog(OAPageContext pageContext , String value , int i ) 
    {
        pageContext.writeDiagnostics(pageContext,value,i);
        System.out.println(value);
    }
    int i=0;
    HashMap hmp = new HashMap();

    /**
    * Layout and page setup logic for a region.
    * @param pageContext the current OA page context
    * @param webBean the web bean corresponding to the region
    */
    public void processRequest(OAPageContext pageContext, OAWebBean webBean)
    {
        super.processRequest(pageContext, webBean);
        writeLog(pageContext , "Inside PR ", 4);
        LCMultiAMImpl         am                =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
//        OAViewObject          repVO             =   am.getLCReportEVO();
        OAViewObject          hdrVO             =   am.getLCRequestHdrEVO();
        String                whereClause       =   "1=1"   ,   event   =   null;  
         try 
         {
             event   =   pageContext.getParameter(EVENT_PARAM);
         }
         catch (Exception e) 
         {
             writeLog(pageContext , "Exception in Event-->"+e  ,4);
         }
       
        hdrVO.setWhereClause(whereClause);
        hdrVO.executeQuery();
        hdrVO.first();
        
//        for(Row row = repVO.first();row != null;row = repVO.next())
//        { 
//            i = i+1;
//            row.setAttribute("Sno",i); 
//            writeLog(pageContext , "inside Row ",4);
//        }   
        
        if("save".equals(event))
        {
            String lc_Req_Id    =   null;
            try 
            {
                lc_Req_Id   =   pageContext.getSessionValue("lc_Req_Id").toString();
            }
            catch (Exception e) 
            {
                writeLog(pageContext,"Lc Req ID Exc --> "+e,4);
            }
            throw new OAException("Your LC report Details for lc Request Id : " + lc_Req_Id + " has been saved successfully!", OAException.CONFIRMATION);
        }
    }
    
    /**
    * Procedure to handle form submissions for form elements in
    * a region.
    * @param pageContext the current OA page context
    * @param webBean the web bean corresponding to the region
    */
    public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
    {
        super.processFormRequest(pageContext, webBean);
        writeLog(pageContext , "Inside PFR ", 4);
        LCMultiAMImpl         am                =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
        OAViewObject          repVO             =   am.getLCReportEVO();        
        OAViewObject          hdrVO             =   am.getLCRequestHdrEVO();
        String                whereClause       =   "1=1" ;  
        String                event             =   null ;
        try 
        {
            event   =   pageContext.getParameter(EVENT_PARAM);
        }
        catch (Exception e) 
        {
            writeLog(pageContext , "Exception in Event-->"+e  ,4);
        }
        hdrVO.setWhereClause(whereClause);
        hdrVO.executeQuery();
        hdrVO.first();
//        for(Row row = repVO.first();row != null;row = repVO.next())
//        { 
//            i = 0;
//            i = i+1;
//            row.setAttribute("Sno",i); 
//        //            row.setAttribute("PiVoucherRO", true);
//            writeLog(pageContext , "inside Row Sno",4);
//        }   
        writeLog(pageContext , "Event-->"+event  ,4);        
        if(event    !=  null && event   !=  "")
        {
            if(event.equals("search"))
            {
                writeLog(pageContext, "Inside Event Search  --> " , 4);
                String requestNo       =   null  ,  whereClause1 = "1=1" ,  lcStatus =null ;
                try 
                {
                    requestNo       =   pageContext.getParameter("LCRequestNo");
                }
                catch (Exception e) 
                {
                    System.out.println("Request No Exc --> " + e);
                }  
                writeLog(pageContext, "Request No      --> " + requestNo , 4);
                try 
                {
                    lcStatus        =   pageContext.getParameter("Status");
                }
                catch (Exception e) 
                {
                    System.out.println("Status Exc --> " + e);
                }
                writeLog(pageContext, "lcStatus is      --> " + lcStatus , 4);
                if(requestNo    !=  null    &&  requestNo       !=  "")
                {
                    System.out.println("RequestNo in search  --> ");
                    whereClause1 =   whereClause +   " and lc_request_id = '" + requestNo + "'";
                } 
                if(lcStatus     !=  null    &&  lcStatus        !=  "")
                {
                    System.out.println("Lc status in search  --> ");
                    whereClause1=   whereClause +   " and flow_status = '" + lcStatus + "'";
                } 
                cc.executeQuery(hdrVO , whereClause1);
            }
            if(event.equals("clear"))
            {
                writeLog(pageContext, "Inside Event Clear  --> " , 4);
                pageContext.forwardImmediatelyToCurrentPage(null , false , null);
            }            
            if(event.equals("update"))
            { 
                System.out.println("Inside update");
                pageContext.writeDiagnostics(this,  "Inside update"   ,4);
                String rowRef = pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
                LCRequestHdrEVORowImpl curRow = (LCRequestHdrEVORowImpl)am.findRowByRef(rowRef);            
                String                    lcReqId =   null;
                try 
                {
                    lcReqId =   curRow.getAttribute("LcRequestId").toString();
                }
                catch (Exception e) 
                {
                    System.out.println("LC Request Id Exc --> " + e); 
                    pageContext.writeDiagnostics(this, "Request No Exc -->"+e,4);
                }
                System.out.println("LC Request Id --> " + lcReqId); 
                pageContext.writeDiagnostics(this, "Request No --> "+lcReqId,4);
                hmp.put("p_lc_request_id" , lcReqId);
                
                pageContext.setForwardURL(
                                            "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiReportPG"       ,
                                            null                                                                        ,
                                            OAWebBeanConstants.KEEP_MENU_CONTEXT                                        ,
                                            null                                                                        ,
                                            hmp                                                                         ,
                                            false                                                                       ,
                                            OAWebBeanConstants.ADD_BREAD_CRUMB_YES                                      ,
                                            OAWebBeanConstants.IGNORE_MESSAGES
                                         );
            }
        }
    }

}
