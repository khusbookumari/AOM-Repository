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

import oracle.jbo.Row;

/**
 * Controller for ...
 */
public class LCMultiImportReqCO extends OAControllerImpl
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
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
      pageContext.writeDiagnostics(this, "Inside PR" ,4);
      System.out.println("Inside PR");  
      LCMultiAMImpl       am                  =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
      OAViewObject        reqHdrVO            =   am.getLCRequestHdrEVO();
      OAViewObject        poVO                =   am.getLCPoDetailsEVO();
      OAViewObject        lcImportVO          =   am.getLCImportReqEVO();
      OAViewObject        lcDocumentVO        =   am.getLCDocDetailsEVO();
      String lcRequestIdSV    =   null;
      int   i   =   0;
      try 
      {
        lcRequestId =   pageContext.getParameter("p_lc_request_id");
        
      }
      catch (Exception e) 
      {
        System.out.println("LC Request Id Exc --> " + e);    
      }
      if(lcRequestId != null) 
      {
          lcRequestIdSV   =   lcRequestId;
          pageContext.putSessionValue("lcRequestIdSV",lcRequestId);
      }
      else
      {
          try 
          {
              lcRequestIdSV   =   pageContext.getSessionValue("lcRequestIdSV").toString();
          }
          catch (Exception e) 
          {
              System.out.println("LC Request Id Sessionvalue --> " + e); 
          }
          
      }
      
      System.out.println("LC Request Id   --> " + lcRequestId);
      pageContext.writeDiagnostics(this, "LC Request Id " + lcRequestId,4);
      
      cc.executeQuery(lcImportVO  ,   "lc_request_id = " + lcRequestIdSV);
      cc.executeQuery(lcDocumentVO,   "lc_request_id = " + lcRequestIdSV) ;
      cc.executeQuery(poVO        ,   "lc_request_id = " +lcRequestIdSV);
      cc.executeQuery(reqHdrVO    ,   "lc_request_id = " + lcRequestIdSV);
      
      for(Row row = poVO.first();row != null;row = poVO.next())
      {
        i   =   i+1;
        row.setAttribute("Sno",i);
      }
      System.out.println("hdrVO Query   --> "   +lcImportVO.getQuery()          );
      System.out.println("hdrVO RC      --> "   +lcImportVO.getRowCount()       );
      System.out.println("hdrVO FRC     --> "   +lcImportVO.getFetchedRowCount());
      pageContext.writeDiagnostics(this , "hdrVO Query   --> "  +lcImportVO.getQuery()             , 4);
      pageContext.writeDiagnostics(this , "hdrVO RC      --> "  +lcImportVO.getRowCount()          , 4);
      pageContext.writeDiagnostics(this , "hdrVO FRC     --> "  +lcImportVO.getFetchedRowCount()   , 4);
      if(lcImportVO.getFetchedRowCount()> 0)
      {
          System.out.println("Lc import contains records");
          pageContext.writeDiagnostics(this, "Lc import contains records ", 4);
      }
      else
      {
          System.out.println("Create Row in import");
          pageContext.writeDiagnostics(this, "Create Row in import ", 4);
          cc.createRow(am , lcImportVO , "No" , null , null);
          lcImportVO.getCurrentRow().setAttribute("LcType" , "IRREVOCABLE");
          lcImportVO.getCurrentRow().setAttribute("PeriodOfPresentation" , "21 Days");
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
      LCMultiAMImpl       am                  =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
      OAViewObject        lcImportVO          =   am.getLCImportReqEVO();
      OAViewObject        lcDocumentVO        =   am.getLCDocDetailsEVO();
      System.out.println("Inside PFR"); 
      pageContext.writeDiagnostics(this, "Inside PFR",4);
      String  event   =   null    ,   lcImportId =   null ,   importID    =   null;
      HashMap hmp = new HashMap();
      
      try 
      {
          event       =   pageContext.getParameter(EVENT_PARAM);  
      }
      catch (Exception e) 
      {
          System.out.println("Event Exc --> " + e); 
      }
      System.out.println("Event --> " + event);
      pageContext.writeDiagnostics(this   , "Event"   +event, 4);  
      System.out.println("LC Request Id   --> " + lcRequestId);
      pageContext.writeDiagnostics(this, "LC Request Id " + lcRequestId,4);
      try 
      {
          lcImportId     =   lcImportVO.getCurrentRow().getAttribute("LcImportId").toString();
      }
      catch (Exception e) 
      {
          System.out.println("LC Request Id Exc --> " + e);  
      }
      pageContext.writeDiagnostics(this, "LC Import Id --> "+ lcImportId ,4);
      System.out.println("LC Import Id --> --> "+ lcImportId);

      if(event  !=  null  &&  event !=  "")
      {
          if(event.equals("save"))
          {
              System.out.println("Inside Save");
              if(lcImportId !=   null    &&  lcImportId !=  "")
              {
                  System.out.println("Update and save contents");
                  pageContext.writeDiagnostics(this, "Update and save contents ", 4);
                  
              }
              else
              {
                System.out.println("LC Import Id not available , so we need to generate new id"); 
                pageContext.writeDiagnostics(this, "LC Import Id not available , so we need to generate new id", 4);
                importID =   am.getOADBTransaction().getSequenceValue("xxsify_lc_import_req_s").toString();
                System.out.println("ImportID-->"+importID);
                pageContext.writeDiagnostics(this, "ImportID-->"+importID, 4);
                lcImportVO.getCurrentRow().setAttribute("LcRequestId",lcRequestId);
                lcImportVO.getCurrentRow().setAttribute("LcImportId" , importID);
              }
              cc.commit(am);
              throw new OAException("Your Data has been Saved successfully!" , OAException.CONFIRMATION);
          }     
          if(event.equals("back"))
          {
            System.out.println("Inside Back");
            pageContext.writeDiagnostics(this, "Inside Back", 4);
            cc.rollback(am);
            hmp.put("p_hdr_id" , lcRequestId);
            hmp.put("PageAction", "U");
            pageContext.setForwardURL(
                                          "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiRequestPG",
                                          null                                                                 ,
                                          OAWebBeanConstants.KEEP_MENU_CONTEXT                                 ,
                                          null                                                                 ,
                                          hmp                                                                  ,
                                          true                                                                ,
                                          OAWebBeanConstants.ADD_BREAD_CRUMB_YES                               ,
                                          OAWebBeanConstants.IGNORE_MESSAGES
                                       );
           }       
          if(event.equals("addDocument"))
          {
            System.out.println("Inside Add Document");
            pageContext.writeDiagnostics(this, "Inside Add Document", 4);
            System.out.println("LC Request Id --> " + lcRequestId);
            cc.createRow(am , lcDocumentVO , "Yes" , "xxsify_lc_doc_details_s" , "LcDocId");
            lcDocumentVO.getCurrentRow().setAttribute("LcRequestId",lcRequestId);
          }
          if(event.equals("report")) 
          {
              System.out.println("inside report");
              String applnName = "SFC" , cpName = "XXSIFY_LC_REQ_MULTI_REP";
              pageContext.forwardImmediately("OA.jsp?akRegionCode=FNDCPPROGRAMPAGE&akRegionApplicationId=0&programApplName="+applnName+"&programName="+cpName+"&programRegion=Hide&scheduleRegion=Hide&notifyRegion=Hide&printRegion=Hide", null, (byte)0, null, null, true, "Y");
          }
      }

  }

}
