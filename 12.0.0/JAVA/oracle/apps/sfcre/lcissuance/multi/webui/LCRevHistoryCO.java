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
import oracle.apps.fnd.framework.webui.beans.form.OASubmitButtonBean;
import oracle.apps.fnd.framework.webui.beans.layout.OARowLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.fnd.framework.webui.beans.table.OAAdvancedTableBean;
import oracle.apps.po.common.webui.ClientUtil;
import oracle.apps.sfc.lcissuance.common.server.CommonClass;
import oracle.apps.sfc.lcissuance.multi.server.LCMultiAMImpl;

import oracle.jbo.Row;

/**
 * Controller for ...
 */
public class LCRevHistoryCO extends OAControllerImpl
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
    ClientUtil cu = new ClientUtil();
    String revisionNo = null;
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    pageContext.writeDiagnostics(this, "Inside PR" ,4);
    System.out.println("Inside LCMultiRequestPG PR");  
    LCMultiAMImpl       am            =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
    OAViewObject        hdrVO         =   am.getLCRequestHdrRevVO();
    OAViewObject        poVO          =   am.getLCPoDetailsRevVO();
    OAViewObject        lcAppHistVO       =   am.getLCApprHistoryRevVO();
    OAViewObject        lcDocumentVO        =   am.getLCDocDetailsRevVO();
    String              event         =   null      ,       lcRequestId     =       null , whereClause = "1=1";
   
    try
    {
        event   =   pageContext.getParameter(EVENT_PARAM);
    }
    catch (Exception e) 
    {
        pageContext.writeDiagnostics(this,  "Event Exc -->" + e    ,4);
        System.out.println( "Event Exc -->" + e ); 
    }
    
      pageContext.writeDiagnostics(this,  "Event  -->" + event    ,4);
      System.out.println( "Event Exc -->" + event );
    
    pageContext.writeDiagnostics(this,  "Page Action-->"+pageContext.getParameter("PageAction")    ,4);
    System.out.println( "Page Action-->"+pageContext.getParameter("PageAction") ); 
    
      try 
      {
        lcRequestId =   pageContext.getParameter("p_hdr_id");
      }
      catch (Exception e) 
      {
        System.out.println("LC Request Id Exc --> " + e);    
      }
      System.out.println("LC Request Id   --> " + lcRequestId);
      pageContext.writeDiagnostics(this, "LC Request Id " + lcRequestId,4);
      
      try 
      {
          revisionNo  =   pageContext.getParameter("p_rev_no");
      }
      catch (Exception e) 
      {
          System.out.println("Rev No Exc --> " + e);
      }
      
      System.out.println("Rev No --> " + revisionNo);
      
      whereClause   =   whereClause +" and lc_request_id = " +lcRequestId + " and attribute9='" + revisionNo +"'";
      hdrVO.setWhereClause(whereClause);
      hdrVO.executeQuery();
      hdrVO.first();
      
      lcDocumentVO.setWhereClause(whereClause);
      lcDocumentVO.executeQuery();
      lcDocumentVO.first();
      
      System.out.println("hdrVO Query   --> "   +hdrVO.getQuery()          );
      System.out.println("hdrVO RC      --> "   +hdrVO.getRowCount()       );
      System.out.println("hdrVO FRC     --> "   +hdrVO.getFetchedRowCount());
      pageContext.writeDiagnostics(this , "hdrVO Query   --> "  +hdrVO.getQuery()             , 4);
      pageContext.writeDiagnostics(this , "hdrVO RC      --> "  +hdrVO.getRowCount()          , 4);
      pageContext.writeDiagnostics(this , "hdrVO FRC     --> "  +hdrVO.getFetchedRowCount()   , 4);
      
      System.out.println("lcDocumentVO Query   --> "   +lcDocumentVO.getQuery()          );
      System.out.println("lcDocumentVO RC      --> "   +lcDocumentVO.getRowCount()       );
      System.out.println("lcDocumentVO FRC     --> "   +lcDocumentVO.getFetchedRowCount());
      pageContext.writeDiagnostics(this , "lcDocumentVO Query   --> "  +lcDocumentVO.getQuery()             , 4);
      pageContext.writeDiagnostics(this , "lcDocumentVO RC      --> "  +lcDocumentVO.getRowCount()          , 4);
      pageContext.writeDiagnostics(this , "lcDocumentVO FRC     --> "  +lcDocumentVO.getFetchedRowCount()   , 4);
      
          String lcincoterm   =   null    ,   piincoterm  =   null;
          try 
          {
              lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
          }
          catch (Exception e) 
          {
              System.out.println("Lc lcincoterm     --> "   +e);
          }
          
          System.out.println("Lc lcincoterm     --> "   +lcincoterm);
          pageContext.writeDiagnostics(this , "Lc lcincoterm   --> "  +lcincoterm,4);
        
        hdrVO.getCurrentRow().setAttribute("RenCif" ,false); 
        hdrVO.getCurrentRow().setAttribute("RenFob" ,false); 
        
          if(lcincoterm != null && lcincoterm !="") 
          {
              if(lcincoterm.equals("FOB")) 
              {
                  hdrVO.getCurrentRow().setAttribute("RenFob" ,true); 
              }
              if(lcincoterm.equals("CIF")) 
              {
                  hdrVO.getCurrentRow().setAttribute("RenCif" ,true); 
              }
          }
          
          try 
          {
              piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
          }
          catch (Exception e) 
          {
              System.out.println("Lc piincoterm     --> "   +e);
          }

            System.out.println("Lc piincoterm     --> "   +piincoterm);
            pageContext.writeDiagnostics(this , "Lc piincoterm   --> "  +piincoterm,4);
            
      hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false); 
      hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false); 
          if(piincoterm != null && piincoterm !="") 
          {
              if(piincoterm.equals("FOB")) 
              {
                  hdrVO.getCurrentRow().setAttribute("PIRenFob" ,true);
              }
              if(piincoterm.equals("CIF")) 
              {
                  hdrVO.getCurrentRow().setAttribute("PIRenCif" ,true); 
              }
          }
      
      poVO.setWhereClause(whereClause);
      poVO.executeQuery();
      poVO.first();
      
      int i = 0;
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
      whereClause   =   whereClause +" and Lc_Mode = 'Multi'" ;
      lcAppHistVO.setWhereClause(whereClause);
      lcAppHistVO.executeQuery();
      lcAppHistVO.first();
      
      int j = 0;
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
      cu.setViewOnlyRecursive(pageContext, webBean);
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
    System.out.println("Inside Multi Search page PFR");
    pageContext.writeDiagnostics(this,"Inside Multi Search page PFR" ,4);
    String event = null;
    
    try 
    {
        event   =   pageContext.getParameter(EVENT_PARAM);
    }
    catch (Exception e) 
    {
        System.out.println("Exception in Event-->"+e);
        pageContext.writeDiagnostics(this,"Exception in Event-->"+e  ,4);
    }
    
    System.out.println("Event-->"+event);
    pageContext.writeDiagnostics(this, "Event-->"+event  ,4);
    
    if(event != null && event != "")
    {
        System.out.println("Inside Event");
        if(event.equals("back"))
        {
            pageContext.setForwardURL(
                                        "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiSearchPG"      ,
                                        null                                                                ,
                                        OAWebBeanConstants.KEEP_MENU_CONTEXT                                ,
                                        null                                                                ,
                                        null                                                                ,
                                        false                                                               ,
                                        OAWebBeanConstants.ADD_BREAD_CRUMB_YES                              ,
                                        OAWebBeanConstants.IGNORE_MESSAGES
                                     );
        }
    }
  }

}
