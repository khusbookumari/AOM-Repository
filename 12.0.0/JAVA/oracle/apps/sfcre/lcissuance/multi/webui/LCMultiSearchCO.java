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
import oracle.apps.fnd.framework.webui.beans.form.OASubmitButtonBean;
import oracle.apps.fnd.framework.webui.beans.table.OAAdvancedTableBean;
import oracle.apps.fnd.framework.webui.beans.table.OAColumnBean;
import oracle.apps.sfc.lcissuance.common.server.CommonClass;
import oracle.apps.sfc.lcissuance.multi.server.LCMultiAMImpl;

/**
 * Controller for ...
 */
public class LCMultiSearchCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED = 
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
   CommonClass      cc              =   new CommonClass();
   String           PageMode        =   null;
   String           msgMode         =   null,   lcRequestId =   null            , commHeadName      =   null,   
                    treasuryName    =   null,   reqID       =   null            , suppName          =   null;
   String           finName         =   null,   stlbuyerName   =      null      , ApCommHeadName    =   null,
   sisbuyerName=null;
   
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
      super.processRequest(pageContext, webBean);
      writeLog(pageContext , "Inside PR ", 4);
      LCMultiAMImpl       am            =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
      OAViewObject        hdrVO         =   am.getLCRequestHdrEVO();
      
      OASubmitButtonBean    createReqBean   =   (OASubmitButtonBean)webBean.findChildRecursive("CreateReqBtn");
     //Starts here ... added by Jenish on 19-june-24 to make the PendingWithC column rendered false to supplier
      OAAdvancedTableBean   tableBean       =(OAAdvancedTableBean)webBean.findIndexedChildRecursive("AdvancedTable");
      OAColumnBean          PendingWithC    = (OAColumnBean)tableBean.findIndexedChildRecursive("PendingWithC");
      createReqBean.setRendered(false);
      String respname                          =   String.valueOf(pageContext.getResponsibilityName());
      writeLog(pageContext,"Resp Name   --> "+respname,4);
      if("Sify LC Supplier Responsibility".equalsIgnoreCase(respname) || "LC Requestor Responsibility".equalsIgnoreCase(respname)) 
      {
          PendingWithC.setRendered(false);
      }  
      else  
      { 
          PendingWithC.setRendered(true);
      }
      //Ends here ... added by Jenish on 19-june-24 to make the PendingWithC column rendered false to supplier
      try 
      {
          msgMode         =   pageContext.getParameter("MsgMode");
      }
      catch (Exception e) 
      {
          System.out.println("Msg Mode Exc --> " + e);  
      }
      //      Added By Kanaga //
       try 
       {    
           PageMode         =   pageContext.getParameter("PageMode");
//            PageMode        =   "INLAND";
       }
       catch (Exception e) 
       {
           System.out.println("PageMode Exc --> " + e);  
           System.out.println("Page Mode from SV --> " + PageMode);
           pageContext.writeDiagnostics(this,"PageMode   --> "   +   PageMode ,4);
        //   writeLog(pageContext,"Page Mode from SV --> "+PageMode,4);
       }
      
      if(PageMode   !=  null    &&  PageMode    !=  "")
      {
        pageContext.writeDiagnostics(this," Setting Session Value for Page Mode "   +   lcRequestId ,4);
        pageContext.putSessionValue("PageModeSV",PageMode);
        System.out.println("PageMode SV 1   --> "   +   pageContext.getSessionValue("PageModeSV").toString());
        pageContext.writeDiagnostics(this,"PageMode SV 1   --> "   +   pageContext.getSessionValue("PageModeSV").toString() ,4);  
      }
      else
      {
          System.out.println("Setting Session Value for Page Mode");
          pageContext.writeDiagnostics(this," Setting Session Value for Page Mode" ,4);
          PageMode =   pageContext.getSessionValue("PageModeSV").toString();
      }
      
      System.out.println("Page Mode --> " + PageMode);
      
      try 
      {
          lcRequestId     =   pageContext.getParameter("lcRequestId");
      }
      catch (Exception e) 
      {
          System.out.println("LC Request Id Exc --> " + e);  
      }
      System.out.println("Msg Mode        --> "   +   msgMode);
      System.out.println("LC Request Id   --> "   +   lcRequestId);
      pageContext.writeDiagnostics(this,"LC Request Id   --> "   +   lcRequestId ,4);
      pageContext.writeDiagnostics(this,"msgMode   --> "   +   msgMode ,4);   
      //added by Jenish_ITS on 04_07_2024
//       String orgId =   null;
//      try 
//      {
//           orgId = hdrVO.getCurrentRow().getAttribute("LcOrgId").toString();
//      }
//      catch (Exception e) 
//      {
//          System.out.println("orgId Exe    --> "   +   e);
//          pageContext.writeDiagnostics(this,"orgId Exe   --> "   +   e ,4);
//      }
//      System.out.println("orgId     --> "   +   orgId);
//      pageContext.writeDiagnostics(this,"orgId    --> "   +   orgId ,4);
//      try 
//      {
//        if(orgId.equals(82))
//        {
//            buyerName  =   cc.executeSQLquery(am , "select meaning from XXSIFY_LC_APPROVER_V where tag=1");
//        }
//        else if (orgId.equals(424))
//        {
//            buyerName  =   cc.executeSQLquery(am , "select meaning from XXSIFY_LC_APPROVER_V where tag=2");
//        }
//      }
//      catch (Exception e) 
//      {
//          System.out.println("buyerName  exe  --> "   +   e);
//          pageContext.writeDiagnostics(this,"buyerName  exe  --> "   +   e ,4);
//      }
        try 
        {
         stlbuyerName  =   cc.executeSQLquery(am , "select meaning from XXSIFY_LC_APPROVER_V where tag=1");
        }
        catch (Exception e) 
        {
         System.out.println("ApCommHeadName  exe  --> "   +   e);
         pageContext.writeDiagnostics(this,"ApCommHeadName  exe  --> "   +   e ,4);
        }
        
        try 
        {
         sisbuyerName  =   cc.executeSQLquery(am , "select meaning from XXSIFY_LC_APPROVER_V where tag=2");
        }
        catch (Exception e) 
        {
         System.out.println("commHeadName  exe  --> "   +   e);
         pageContext.writeDiagnostics(this,"commHeadName  exe  --> "   +   e ,4);
        }
        try 
        {
          ApCommHeadName  =   cc.executeSQLquery(am , "select meaning from XXSIFY_LC_APPROVER_V where tag=3");
        }
        catch (Exception e) 
        {
          writeLog(pageContext, "ApCommHeadName  exe  --> "   +   e ,4);
        }
// Commented By Jenish ITS      
//      try 
//      {
//          commHeadName  =   cc.executeSQLquery(am , "select meaning from XXSIFY_LC_APPROVER_V where tag=3");
//      }
//      catch (Exception e) 
//      {
//          writeLog(pageContext, "commHeadName  exe  --> "   +   e ,4);
//      }

       // Added by Kanaga //
         try 
         {
            finName  =   cc.executeSQLquery(am , "select meaning from XXSIFY_LC_APPROVER_V where tag=4"); // Need to change
         }
         catch (Exception e) 
         {
            System.out.println("FinName  exe  --> "   +   e);
            pageContext.writeDiagnostics(this,"FinName  exe  --> "   +   e ,4);
         }
       // Added by Kanaga //
        try 
        {
          treasuryName  =   cc.executeSQLquery(am , "select meaning from XXSIFY_LC_APPROVER_V where tag=5");
        }
        catch (Exception e) 
        {
          writeLog(pageContext, "treasuryName  exe  --> "   +   e ,4);
        }
      writeLog( pageContext , "stlbuyerName     --> "   +   stlbuyerName    , 4 ) ;
      writeLog( pageContext , "sisbuyerName     --> "   +   sisbuyerName    , 4 ) ;
      writeLog( pageContext , "ApCommHeadName   --> "   +   ApCommHeadName  , 4 ) ;
//      writeLog( pageContext , "commHeadName     --> "   +   commHeadName    , 4 ) ;
      writeLog( pageContext , "suppName         --> "   +   suppName        , 4 ) ;
      writeLog( pageContext , "finName          --> "   +   finName         , 4 ) ;
      writeLog( pageContext , "treasuryName     --> "   +   treasuryName    , 4 ) ;
      writeLog( pageContext , "UserName         --> "   +   pageContext.getUserName()    , 4 ) ;

        if(pageContext.getUserName().equals(stlbuyerName)) 
        {   
            writeLog( pageContext , "going into 1"    , 4 ) ;
             cc.executeQuery(hdrVO , "Lc_Status='PWB' and lc_org_id = 82 and upper(Lc_Classification)='" + PageMode + "'");  
        }
        if(pageContext.getUserName().equals(sisbuyerName)) 
        {   
            writeLog( pageContext , "going into 2"    , 4 ) ;
            cc.executeQuery(hdrVO , "Lc_Status='PWB' and lc_org_id = 424 and upper(Lc_Classification)='" + PageMode + "'");  
        }
        if(pageContext.getUserName().equals(ApCommHeadName)&&(pageContext.getResponsibilityName().equals("Sify LC AP Commercial Responsibility"))) 
        {   
            writeLog( pageContext , "going into 3"    , 4 ) ;
            cc.executeQuery(hdrVO , "Lc_Status='PWAPCH' and upper(Lc_Classification)='" + PageMode + "'");  
        }
        //      else if(pageContext.getUserName().equals(commHeadName)&&(pageContext.getResponsibilityName().equals("Sify LC Commercial Responsibility")))  // by jenish
        //      {
        //          cc.executeQuery(hdrVO , "Lc_Status='PWCH' and upper(Lc_Classification)='" + PageMode + "'");
        //      }
        if(pageContext.getUserName().equals(treasuryName)) 
        {   
            writeLog( pageContext , "going into 4"    , 4 ) ;
            cc.executeQuery(hdrVO , "Lc_Status='PWTR' and upper(Lc_Classification)='" + PageMode + "'");  
        }
        if(pageContext.getUserName().equals(finName))  // Need to change
        {   
            writeLog( pageContext , "going into 5"    , 4 ) ;
            cc.executeQuery(hdrVO , "Lc_Status='PWF' and upper(Lc_Classification)='" + PageMode + "'");
        }
        else
        {   
            writeLog( pageContext , "going into 6"    , 4 ) ;
            if(respname.equals("LC Requestor Responsibility"))
            {   
            writeLog( pageContext , "going into 7"    , 4 ) ;
                cc.executeQuery(hdrVO , "created_by = " + pageContext.getUserId() +  " and upper(Lc_Classification) ='" + PageMode + "'");
                createReqBean.setRendered(true);
            }
            else if (respname.equals("Sify LC Supplier Responsibility"))
            {   
            writeLog( pageContext , "going into 8"    , 4 ) ;
                cc.executeQuery(hdrVO , "created_by = " + pageContext.getUserId() + " and Lc_Status='PWSP' and upper(Lc_Classification)='" + PageMode + "'");
            }
        }
      if(msgMode  !=  null    &&  msgMode !=  "")
      {
        //  cc.executeQuery(hdrVO , "lc_request_id = " + lcRequestId);
          throw new OAException("Your Request No: " + lcRequestId + " has been " + msgMode + " successfully!" , OAException.CONFIRMATION);
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
        System.out.println("Inside Multi Search page PFR");
        pageContext.writeDiagnostics(this,"Inside Multi Search page PFR" ,4);
        LCMultiAMImpl     am      =  (LCMultiAMImpl) pageContext.getApplicationModule(webBean);
        OAViewObject      hdrVO   =   am.getLCRequestHdrEVO();
        HashMap           hm      =   new HashMap();
        String            event   =   null ;
        String respname                          =   String.valueOf(pageContext.getResponsibilityName());
        writeLog(pageContext , "stlbuyerName    --> "   +   stlbuyerName ,4);
        writeLog(pageContext , "sisbuyerName    --> "   +   sisbuyerName ,4);
        writeLog(pageContext , "ApCommHeadName  --> "   +   ApCommHeadName ,4);
        writeLog(pageContext , "treasuryName    --> "   +   treasuryName ,4);
        writeLog(pageContext , "commHeadName    --> "   +   commHeadName ,4);
        writeLog(pageContext , "suppName        --> "   +   suppName ,4);
        writeLog(pageContext , "finName         --> "   +   finName ,4);
        // Added by Kanaga //
           
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
        
        if(event    !=  null && event   !=  "")
        {
            if(event.equals("amend"))
            {
                System.out.println("Inside Amend");
                pageContext.writeDiagnostics(this,  "Inside Amend"   ,4);
                hm.put("PageAction", "A");
                pageContext.setForwardURL(
                                            "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiRequestPG"      ,
                                            null                                                                ,
                                            OAWebBeanConstants.KEEP_MENU_CONTEXT                                ,
                                            null                                                                ,
                                            hm                                                                  ,
                                            false                                                               ,
                                            OAWebBeanConstants.ADD_BREAD_CRUMB_YES                              ,
                                            OAWebBeanConstants.IGNORE_MESSAGES
                                         );
            }
            if(event.equals("create")) 
            {   
                System.out.println("Inside Create");
                pageContext.writeDiagnostics(this,  "Inside create"   ,4);
                if(PageMode.equals("INLAND"))
                {
                hm.put("PageAction", "C");
                pageContext.setForwardURL(
                                            "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiRequestPG"      ,
                                            null                                                                ,
                                            OAWebBeanConstants.KEEP_MENU_CONTEXT                                ,
                                            null                                                                ,
                                            hm                                                                  ,
                                            false                                                               ,
                                            OAWebBeanConstants.ADD_BREAD_CRUMB_YES                              ,
                                            OAWebBeanConstants.IGNORE_MESSAGES
                                         );
                }
                if(PageMode.equals("IMPORT"))
                {
                    hm.put("PageAction", "Z");
                    pageContext.setForwardURL(
                                                "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiRequestPG"      ,
                                                null                                                                ,
                                                OAWebBeanConstants.KEEP_MENU_CONTEXT                                ,
                                                null                                                                ,
                                                hm                                                                  ,
                                                false                                                               ,
                                                OAWebBeanConstants.ADD_BREAD_CRUMB_YES                              ,
                                                OAWebBeanConstants.IGNORE_MESSAGES
                                             );   
                }
            }
            if(event.equals("update")) 
            {   
                System.out.println("Inside update");
                pageContext.writeDiagnostics(this,  "Inside update"   ,4);
                hm.put("PageAction", "U");
                pageContext.setForwardURL(
                                            "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiRequestPG"      ,
                                            null                                                                ,
                                            OAWebBeanConstants.KEEP_MENU_CONTEXT                                ,
                                            null                                                                ,
                                            hm                                                                  ,
                                            false                                                               ,
                                            OAWebBeanConstants.ADD_BREAD_CRUMB_YES                              ,
                                            OAWebBeanConstants.IGNORE_MESSAGES
                                         );
            }
            if(event.equals("clear")) 
            {
                writeLog(pageContext , "Inside Clear" ,4);
                pageContext.forwardImmediatelyToCurrentPage(null , false , null);
            }
            if(event.equals("search")) 
            {
                writeLog(pageContext , "Inside Event Search" ,4);
                String  supplierName    =   null    ,   lcStatus        =   null    ,
                        requestNo       =   null    ,   whereClause     =   "1=1 and upper(Lc_Classification) ='" + PageMode + "'";
                try 
                {
                   // supplierName    =   pageContext.getParameter("SupplierName");
                   suppName           =   pageContext.getUserName();
                }
                catch (Exception e) 
                {
                    System.out.println("Supplier Name Exc --> " + e);
                }
                try 
                {
                    lcStatus        =   pageContext.getParameter("Status");
                }
                catch (Exception e) 
                {
                    System.out.println("Status Exc --> " + e);
                }
                try 
                {
                    requestNo       =   pageContext.getParameter("LCRequestNo");
                }
                catch (Exception e) 
                {
                    System.out.println("Request No Exc --> " + e);
                }  
                writeLog(pageContext , "Supplier Name  --> " +   suppName   , 4 );
                writeLog(pageContext, "LC Status       --> " +  lcStatus    , 4);
                writeLog(pageContext, "Request No      --> " +  requestNo   , 4);
                
                if(supplierName !=  null    &&  supplierName    !=  "")
                {
                    System.out.println("Supplier Name in search  --> ");
                    whereClause =   whereClause +   " and vendor_name = '" + supplierName + "'";
                }            
                if(lcStatus     !=  null    &&  lcStatus        !=  "")
                {
                    System.out.println("Lc status in search  --> ");
                    whereClause =   whereClause +   " and flow_status = '" + lcStatus + "'";
                }            
                if(requestNo    !=  null    &&  requestNo       !=  "")
                {
                    System.out.println("RequestNo in search  --> ");
                    whereClause =   whereClause +   " and lc_request_id = '" + requestNo + "'";
                } 
                if(pageContext.getUserName().equals(sisbuyerName)) 
                {
                //                    cc.executeQuery(hdrVO , "Lc_Status='PWB' ");
                    System.out.println("SIS Buyer in search  --> ");
                    whereClause =   whereClause +   " and Lc_Status='PWB'";
                }
                if(pageContext.getUserName().equals(stlbuyerName)) 
                {
                //                    cc.executeQuery(hdrVO , "Lc_Status='PWB' ");
                    System.out.println("STL Buyer in search  --> ");
                    whereClause =   whereClause +   " and Lc_Status='PWB'";
                }
                if(pageContext.getUserName().equals(ApCommHeadName)&&(pageContext.getResponsibilityName().equals("Sify LC AP Commercial Responsibility"))) 
                {
                //                    cc.executeQuery(hdrVO , "Lc_Status='PWAPCH' ");
                    System.out.println("APComm head in search  --> ");
                    whereClause =   whereClause +   " and Lc_Status='PWAPCH'";
                }
                
//                if(pageContext.getUserName().equals(commHeadName)&&(pageContext.getResponsibilityName().equals("Sify LC Commercial Responsibility")))  
//                {
////                    cc.executeQuery(hdrVO , "Lc_Status='PWCH' ");
//                    whereClause =   whereClause +   " and Lc_Status='PWCH'";
//                }
                if(pageContext.getUserName().equals(treasuryName)) 
                {
//                    cc.executeQuery(hdrVO , "Lc_Status='PWTR' ");
                    System.out.println("treasury in search  --> ");
                    whereClause =   whereClause +   " and Lc_Status='PWTR'";
                }
                if("Sify LC Supplier Responsibility".equals(respname)) 
                {
//                    cc.executeQuery(hdrVO , "Lc_Status='PWSP' ");
                    System.out.println("Supplier in search & resp name is --> "+respname);
                    whereClause =   whereClause +   " and Lc_Status='PWSP'";
                }
                // Added by Kanaga for finance //
                 if(pageContext.getUserName().equals(finName)) 
                 {
                     System.out.println("Finance in search  --> ");
//                     cc.executeQuery(hdrVO , "Lc_Status='PWF' ");
                     whereClause =   whereClause +   " and Lc_Status='PWF'";
                 }
                // Added by Kanaga for finance //   
                if(pageContext.getUserName().equals(suppName)&& "LC Requestor Responsibility".equals(respname)) 
                {
                    System.out.println("**********Inside Requestor statement********* " );
                    System.out.println("created_by --> " + pageContext.getUserId());
                    whereClause =   whereClause + " and created_by = "   + pageContext.getUserId();
              
                }
//                else 
//                {
//                    System.out.println("**********Inside else********* " );
//                    System.out.println("created_by --> " + pageContext.getUserId());
//                    whereClause =   whereClause + " and created_by = "   + pageContext.getUserId();
//                }
                cc.executeQuery(hdrVO , whereClause);  
            }
        }
    }
    //Added By Jenish On 19-Jun-24 Start...
    public void  writeLog(OAPageContext pageContext , String value , int i ) 
    {
        pageContext.writeDiagnostics(pageContext,value,i);
        System.out.println(value);
    }
    //Added By Jenish On 19-Jun-24 End...
}
