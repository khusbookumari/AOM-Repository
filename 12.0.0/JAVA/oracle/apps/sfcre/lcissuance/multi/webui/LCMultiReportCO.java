/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package oracle.apps.sfc.lcissuance.multi.webui;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.sfc.lcissuance.common.server.CommonClass;
import oracle.apps.sfc.lcissuance.multi.server.LCMultiAMImpl;

/**
 * Controller for ...
 */
public class LCMultiReportCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");
    CommonClass   cc    =   new CommonClass();
    
    String lcReportId   =  null ,   lcReqId     =   null ;
    public void  writeLog(OAPageContext pageContext , String value , int i ) 
    {
        pageContext.writeDiagnostics(pageContext,value,i);
        System.out.println(value);
    }
    
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
        OAViewObject          repVO             =   am.getLCReportEVO();

        try 
        {
            lcReqId =   pageContext.getParameter("p_hdr_id");
        }
        catch (Exception e) 
        {
            writeLog(pageContext, "LC Request ID Exc -->" + e ,4);
        }
        writeLog(pageContext, "LC Request ID  in PR-->" +lcReqId  ,4);

        repVO.setWhereClause(" lc_request_id = "+lcReqId);
        repVO.executeQuery();
        repVO.first();
        
        if (repVO.getFetchedRowCount()>0)
        {
            writeLog(pageContext, "Inside IF LC Request ID PR -->" + lcReqId ,4);
        }
        else
        {
            writeLog(pageContext,   "Inside LC req Id Else In PR",  4);
            cc.createRow( am ,repVO,"Yes","xxsify_lc_multi_report_s" ,"LcRepId");
            
            writeLog(pageContext    ,   "Inside Else Set Attr --> "+    lcReqId,   4);
            repVO.first().setAttribute("LcRequestId", lcReqId);
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
        writeLog(pageContext, "Inside PFR",4);
        LCMultiAMImpl         am                =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
        OAViewObject          repVO             =   am.getLCReportEVO();
        String                event             =    null   ;
        try
        {
          event   =   pageContext.getParameter(EVENT_PARAM);
        }
        catch (Exception e) 
        {
          writeLog(pageContext, "Event Exc -->" + e ,4); 
        }
        
        writeLog(pageContext, "Event                --> " + event    ,4);
        writeLog(pageContext, "LC Request ID in PFR --> " + lcReqId  ,4);

        
        if(event    !=  null && event   !=  "")
        {
            if(event.equals("save"))
            {
                writeLog(pageContext, "Inside Event Save "                              , 4);
                writeLog(pageContext, "Before Commit    -->"+repVO.getFetchedRowCount() , 4);
                cc.commit(am);
                
                pageContext.putSessionValue("lc_Req_Id",lcReqId);
                pageContext.forwardImmediately( "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCReportSrchPG"    ,
                                                null                                                                    , 
                                                OAWebBeanConstants.KEEP_MENU_CONTEXT                                    , 
                                                null                                                                    ,
                                                null                                                                    , 
                                                false                                                                   , 
                                                OAWebBeanConstants.ADD_BREAD_CRUMB_NO
                                               );
                
                throw new OAException("Your LC report Details for lc Request Id : " + lcReqId + " has been saved successfully!", OAException.CONFIRMATION);
            }
            
            if(event.equals("cancel"))
            {
                writeLog(pageContext, "Inside Event Cancel "    ,4);
                pageContext.forwardImmediately( "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCReportSrchPG"    ,
                                                null                                                                    , 
                                                OAWebBeanConstants.KEEP_MENU_CONTEXT                                    , 
                                                null                                                                    ,
                                                null                                                                    , 
                                                false                                                                   , 
                                                OAWebBeanConstants.ADD_BREAD_CRUMB_NO
                                               );
            }
        }
    }
}
