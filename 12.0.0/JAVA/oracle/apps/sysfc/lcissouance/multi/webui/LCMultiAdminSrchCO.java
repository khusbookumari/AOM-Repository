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
 import oracle.apps.fnd.framework.webui.beans.table.OAAdvancedTableBean;
 import oracle.apps.fnd.framework.webui.beans.table.OAColumnBean;
 import oracle.apps.sfc.lcissuance.common.server.CommonClass;
 import oracle.apps.sfc.lcissuance.multi.server.LCMultiAMImpl;
 import oracle.apps.sfc.lcissuance.multi.server.LCRequestHdrEVORowImpl;
 import oracle.apps.sfc.lcissuance.server.LCAdminSrchVORowImpl;
 import oracle.apps.sfc.lcissuance.server.LcIssuanceAMImpl;

 /**
  * Controller for ...
  */
  
 public class LCMultiAdminSrchCO extends OAControllerImpl
 {
   public static final String RCS_ID="$Header$";
   public static final boolean RCS_ID_RECORDED =
         VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

   /**
    * Layout and page setup logic for a region.
    * @param pageContext the current OA page context
    * @param webBean the web bean corresponding to the region
    */
    CommonClass  cc  = new CommonClass(); 
    HashMap      hmp = new HashMap();
   public void processRequest(OAPageContext pageContext, OAWebBean webBean)
   {
    
     super.processRequest(pageContext, webBean);
     LCMultiAMImpl        am              =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
     OAViewObject         searchVO        =   am.getLCRequestHdrEVO();
     String               msgMode         =   null    ,   lcRequestId =   null   , pageMode  =   null;
     
     try 
     {
         msgMode         =   pageContext.getParameter("MsgMode");
     }
     catch (Exception e) 
     {
         System.out.println("Msg Mode Exc --> " + e);  
     }
       try 
       {
           pageMode         =  pageContext.getParameter("PageMode");
       }
       catch (Exception e) 
       {
           System.out.println("Msg Mode Exc --> " + e);  
       }
     try 
     {
         lcRequestId     =   pageContext.getParameter("lcRequestId");
     }
     catch (Exception e) 
     {
         System.out.println("LC Request Id Exc --> " + e);  
         pageContext.writeDiagnostics(this, "LC Request Id Exc -->"+e,4);
     }
     System.out.println("Msg Mode        --> "   +   msgMode);
     pageContext.writeDiagnostics(this, "Msg Mode        --> "+msgMode,4);
     System.out.println("LC Request Id   --> "   +   lcRequestId);
     pageContext.writeDiagnostics(this, "LC Request Id -->"+lcRequestId,4);
     System.out.println("Page Mode   --> "   +   pageMode);
     pageContext.writeDiagnostics(this, "Page Mode -->"+pageMode,4);
     
        
     if(msgMode  !=  null    &&  msgMode !=  "")
     {
         cc.executeQuery(searchVO , "lc_request_id = " + lcRequestId);
         System.out.println("lc Request HdrVO Query   --> "   +searchVO.getQuery()          );
         System.out.println("lc Request HdrVO RC      --> "   +searchVO.getRowCount()       );
         System.out.println("lc Request HdrVO FRC     --> "   +searchVO.getFetchedRowCount());
         throw new OAException("Your Request No: " + lcRequestId + " has been " + msgMode + " successfully!" , OAException.CONFIRMATION);
     }
     else if("TS".equals(pageMode))
     {
         cc.executeQuery(searchVO , "1=1");
         System.out.println("lc Request HdrVO Query   --> "   +searchVO.getQuery()          );
         System.out.println("lc Request HdrVO RC      --> "   +searchVO.getRowCount()       );
         System.out.println("lc Request HdrVO FRC     --> "   +searchVO.getFetchedRowCount());
         pageContext.putSessionValue("PageModeSV",pageMode);
         OAAdvancedTableBean     advBean    = (OAAdvancedTableBean)webBean.findChildRecursive("AdvancedTable") ; 
         OAColumnBean columnBean = (OAColumnBean)advBean.findIndexedChildRecursive("DetailC");
         columnBean.setRendered(false);
     }
     else
     {
         cc.executeQuery(searchVO , "1=2");
     }
       System.out.println("Page Mode SV      --> " + pageContext.getSessionValue("PageModeSV"));
       pageContext.writeDiagnostics(this, "Page Mode SV--> "+ pageContext.getSessionValue("PageModeSV"),4);  
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
         OAViewObject          searchVO        =   am.getLCRequestHdrEVO();
         String                event           =   null;
         
         
         try 
         {
             event           =   pageContext.getParameter(EVENT_PARAM);              
         }
         catch (Exception e) 
         {
             System.out.println("event --> " + e);
             pageContext.writeDiagnostics(this, "LC Request Id -->"+e,4);
         }
         System.out.println("event --> " + event);
         pageContext.writeDiagnostics(this, "event -->"+event,4);
         if(event  !=  null  &&  event !=  "")
         {
             if(event.equals("search"))
             {
                 System.out.println("Inside Search");
                 pageContext.writeDiagnostics(this, "Inside Search event -->",4);
                 String  supplierName    =   null    ,   lcStatus        =   null    , 
                         requestId       =   null    ,   whereClause     =   "1=1";
                         
                 try 
                 {
                     supplierName    =   pageContext.getParameter("SupplierName");
                 }
                 catch (Exception e) 
                 {
                     System.out.println("Supplier Name Exc --> " + e);
                     pageContext.writeDiagnostics(this, "Supplier Name Exc -->",4);
                 }
                 
                 try 
                 {
                     requestId       =  pageContext.getParameter("RequestId");
                 }
                 catch (Exception e) 
                 {
                     System.out.println("Request No Exc --> " + e);
                     pageContext.writeDiagnostics(this, "Request No Exc --> ",4);
                 }
                                
                 System.out.println("Supplier Name   --> " + supplierName);
                 pageContext.writeDiagnostics(this, "Supplier Name --> "+ supplierName,4);
                 System.out.println("Request No      --> " + requestId);
                 pageContext.writeDiagnostics(this, "Request No --> "+ requestId,4);
                 System.out.println("Page Mode  SV    --> " + pageContext.getSessionValue("PageModeSV"));
                 pageContext.writeDiagnostics(this, "Page Mode  SV--> "+ pageContext.getSessionValue("PageModeSV"),4);                
                              
                 if(supplierName !=  null    &&  supplierName    !=  "")
                 {
                     whereClause =   whereClause +   " and lc_supplier_name = '" + supplierName + "'";
                 }            
                 if(requestId    !=  null    &&  requestId       !=  "")
                 {
                     whereClause =   whereClause +   " and lc_request_id = '" + requestId + "'";
                 } 
                 
                 if(!"TS".equals(pageContext.getSessionValue("PageModeSV"))) 
                 {
                     whereClause =   whereClause +   " and flow_status = 'Accepted'";
                 }
                 else
                 {
                     OAAdvancedTableBean     advBean    = (OAAdvancedTableBean)webBean.findChildRecursive("AdvancedTable") ; 
                     OAColumnBean columnBean = (OAColumnBean)advBean.findIndexedChildRecursive("DetailC");
                     columnBean.setRendered(false);
                     
                 }
                 
                 cc.executeQuery(searchVO , whereClause);
                 System.out.println("lc Request HdrVO Query   --> "   +searchVO.getQuery()          );
                 System.out.println("lc Request HdrVO RC      --> "   +searchVO.getRowCount()       );
                 System.out.println("lc Request HdrVO FRC     --> "   +searchVO.getFetchedRowCount());
             }
             if(event.equals("clear"))
             {
                 System.out.println("Inside Clear");
                 pageContext.writeDiagnostics(this, "Request No Exc --> ",4);
                 pageContext.forwardImmediatelyToCurrentPage(null , false , null);
             }
             if(event.equals("view"))
             {
               System.out.println("Inside view");
               String                    rowRef      =   pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
               LCRequestHdrEVORowImpl    row         =   (LCRequestHdrEVORowImpl)am.findRowByRef(rowRef);
               String                    lcRequestId =   null;
               try 
               {
                   lcRequestId = row.getAttribute("LcRequestId").toString();
               }
               catch (Exception e) 
               {
                   System.out.println("LC Request Id Exc --> " + e); 
                   pageContext.writeDiagnostics(this, "Request No Exc --> ",4);
               }
               System.out.println("LC Request Id --> " + lcRequestId); 
               pageContext.writeDiagnostics(this, "Request No Exc --> ",4);
                 
               hmp.put("p_lc_request_id" , lcRequestId);
               
               pageContext.setForwardURL(
                                             "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiAdminPG"        ,
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
