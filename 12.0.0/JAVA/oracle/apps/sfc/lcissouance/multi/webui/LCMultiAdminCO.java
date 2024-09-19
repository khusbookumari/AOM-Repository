/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package oracle.apps.sfc.lcissuance.multi.webui;

import com.sun.java.util.collections.HashMap;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.sfc.lcissuance.common.server.CommonClass;
import oracle.apps.sfc.lcissuance.multi.server.LCMultiAMImpl;

import oracle.jbo.Row;

/**
 * Controller for ...
 */
public class LCMultiAdminCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
   CommonClass   cc          =   new CommonClass(); 
   String        lcRequestId =   null;
   HashMap       hmp         =   new HashMap(); 
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    LCMultiAMImpl           am              =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
    OAViewObject            hdrVO           =   am.getLCRequestHdrEVO();
    OAViewObject            poVO            =   am.getLCPoDetailsEVO();
    OAViewObject            lcAppHistVO     =   am.getLCApprHistoryEVO();
    String                  event           =   null    , whereClause= "1=1";
        try 
        {
          event       =   pageContext.getParameter(EVENT_PARAM);  
        }
        catch (Exception e) 
        {
          System.out.println("Event Exc --> " + e); 
        }
        System.out.println("Event --> " + event);
        if(event    !=  null    &&  event   !=  "")
        {
            if(event.equals("view"))
            {
                System.out.println("Inside View Event");
                
                try 
                {
                  lcRequestId =   pageContext.getParameter("p_lc_request_id");
                }
                catch (Exception e) 
                {
                  System.out.println("LC Request Id Exc --> " + e);    
                }
                System.out.println("LC Request Id --> " + lcRequestId);
                
                cc.executeQuery(hdrVO , "lc_request_id = " + lcRequestId);
                System.out.println("hdrVO Query   --> "   +hdrVO.getQuery()          );
                System.out.println("hdrVO RC      --> "   +hdrVO.getRowCount()       );
                System.out.println("hdrVO FRC     --> "   +hdrVO.getFetchedRowCount());
                pageContext.writeDiagnostics(this , "hdrVO Query   --> "  +hdrVO.getQuery()             , 4);
                pageContext.writeDiagnostics(this , "hdrVO RC      --> "  +hdrVO.getRowCount()          , 4);
                pageContext.writeDiagnostics(this , "hdrVO FRC     --> "  +hdrVO.getFetchedRowCount()   , 4);
                  
                cc.executeQuery(poVO,"lc_request_id = " + lcRequestId);
                poVO.first();
                int i=0,j=0;
                for(Row row = poVO.first();row != null;row = poVO.next())
                { 
                    i = i+1;
                    row.setAttribute("Sno",i); 
                }
                       
                System.out.println("poVO Query   --> "   +poVO.getQuery()          );
                System.out.println("poVO RC      --> "   +poVO.getRowCount()       );
                System.out.println("poVO FRC     --> "   +poVO.getFetchedRowCount());
                pageContext.writeDiagnostics(this , "poVO Query   --> "  +poVO.getQuery()             , 4);
                pageContext.writeDiagnostics(this , "poVO RC      --> "  +poVO.getRowCount()          , 4);
                pageContext.writeDiagnostics(this , "poVO FRC     --> "  +poVO.getFetchedRowCount()   , 4);
                whereClause   =   whereClause +" and lc_request_id ="+lcRequestId+" and Lc_Mode = 'Multi'" ;
                lcAppHistVO.setWhereClause(whereClause);
                lcAppHistVO.executeQuery();
                lcAppHistVO.first();
                for(Row row = lcAppHistVO.first();row != null;row = lcAppHistVO.next())
                { 
                    j = j+1;
                    row.setAttribute("Sno",j); 
                }
                       
                System.out.println("lcAppHistVO Query   --> "   +lcAppHistVO.getQuery()          );
                System.out.println("lcAppHistVO RC      --> "   +lcAppHistVO.getRowCount()       );
                System.out.println("lcAppHistVO FRC     --> "   +lcAppHistVO.getFetchedRowCount());
                pageContext.writeDiagnostics(this , "lcAppHistVO Query   --> "  +lcAppHistVO.getQuery()             , 4);
                pageContext.writeDiagnostics(this , "lcAppHistVO RC      --> "  +lcAppHistVO.getRowCount()          , 4);
                pageContext.writeDiagnostics(this , "lcAppHistVO FRC     --> "  +lcAppHistVO.getFetchedRowCount()   , 4);
            }
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
    LCMultiAMImpl         am              =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
    //OAViewObject          hdrVO           =   am.getLCRequestHdrEVO();
    String                event           =   null;
      
    try 
    {
        event       =   pageContext.getParameter(EVENT_PARAM);  
    }
    catch (Exception e) 
    {
        System.out.println("Event Exc --> " + e); 
    }   
    try 
    {
      lcRequestId =   pageContext.getParameter("p_lc_request_id");
    }
    catch (Exception e) 
    {
      System.out.println("LC Request Id Exc --> " + e);    
    }
    System.out.println("LC Request Id --> " + lcRequestId);
    if(event  !=  null  &&  event !=  "")
    {
        if(event.equals("save"))
        {
            System.out.println("Inside Save");
            cc.commit(am);
            hmp.put("MsgMode" , "Saved");
            hmp.put("lcRequestId" , lcRequestId);
            pageContext.setForwardURL(
                                          "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiAdminSrchPG"    ,
                                          null                                                                        ,
                                          OAWebBeanConstants.KEEP_MENU_CONTEXT                                        ,
                                          null                                                                        ,
                                          hmp                                                                         ,
                                          false                                                                       ,
                                          OAWebBeanConstants.ADD_BREAD_CRUMB_YES                                      ,
                                          OAWebBeanConstants.IGNORE_MESSAGES
                                       );
        }
          if(event.equals("cancel"))
          {
              System.out.println("Inside Cancel");
              cc.rollback(am);
              pageContext.setForwardURL(
                                            "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiAdminSrchPG"    ,
                                            null                                                                        ,
                                            OAWebBeanConstants.KEEP_MENU_CONTEXT                                        ,
                                            null                                                                        ,
                                            null                                                                        ,
                                            false                                                                       ,
                                            OAWebBeanConstants.ADD_BREAD_CRUMB_YES                                      ,
                                            OAWebBeanConstants.IGNORE_MESSAGES
                                         );
          }
      }  
  }

}
