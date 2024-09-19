 /*===========================================================================+
  |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
  |                         All rights reserved.                              |
  +===========================================================================+
  |  HISTORY                                                                  |
  +===========================================================================*/
 package oracle.apps.sfc.lcissuance.multi.webui;

 import com.sun.java.util.collections.HashMap;

 import java.io.Serializable;

 import oracle.apps.fnd.common.VersionInfo;
 import oracle.apps.fnd.framework.OAApplicationModule;
 import oracle.apps.fnd.framework.OAException;
 import oracle.apps.fnd.framework.OAViewObject;
 import oracle.apps.fnd.framework.webui.OAControllerImpl;
 import oracle.apps.fnd.framework.webui.OADialogPage;
 import oracle.apps.fnd.framework.webui.OAPageContext;
 import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
 import oracle.apps.fnd.framework.webui.beans.OAWebBean;
 import oracle.apps.fnd.framework.webui.beans.form.OASubmitButtonBean;
 import oracle.apps.fnd.framework.webui.beans.layout.OARowLayoutBean;
 import oracle.apps.fnd.framework.webui.beans.message.OAMessageLovInputBean;
 import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
 import oracle.apps.fnd.framework.webui.beans.table.OAAdvancedTableBean;
 import oracle.apps.sfc.lcissuance.multi.server.LCMultiAMImpl;
 import oracle.apps.sfc.lcissuance.common.server.CommonClass;

 import oracle.jbo.Row;

 /**
  * Controller for ...
  */
 public class LCMultiAdminSrchInlandCO extends OAControllerImpl
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
   public void processRequest(OAPageContext pageContext, OAWebBean webBean)
   {
     super.processRequest(pageContext, webBean);
     pageContext.writeDiagnostics(this, "Inside PR" ,4);
     System.out.println("Inside LCMultiRequestPG PR");  
     LCMultiAMImpl       am            =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
     OAViewObject        hdrVO         =   am.getLCRequestHdrEVO();
     OAViewObject        poVO          =   am.getLCPoDetailsEVO();
     OAViewObject        lcAppHistVO       =   am.getLCApprHistoryEVO();
     String              event         =   null      ,       whereClause     =       "1=1"       ,       lcRequestId     =       null;
     String              reqName       =   null      ,       reqID    =   null   ,   lcRequestIdSV   =   null    ,   vendorId    =   null  ,
                         orgId         =   null      ,       orgName =   null    ,   vendorName      =   null    ;
     int     i   =   0   ,   j=0;
     HashMap hmp = new HashMap();
     OAAdvancedTableBean     attachAdvBean    = (OAAdvancedTableBean)webBean.findChildRecursive("AttachAdvTable") ; 
     String                  attachmentFlag   = (String)pageContext.getSessionValue("AttachmentFlag");  
     OASubmitButtonBean      DraftBtnBean     = (OASubmitButtonBean)webBean.findChildRecursive("DraftBtn");
     OASubmitButtonBean      ImportBtnBean    = (OASubmitButtonBean)webBean.findChildRecursive("ImportBtn");
     OASubmitButtonBean      InlandBtnBean    = (OASubmitButtonBean)webBean.findChildRecursive("InlandBtn");
     OASubmitButtonBean      submitBean       = (OASubmitButtonBean)webBean.findChildRecursive("SubmitBtn");
     OASubmitButtonBean      ApprBtnBean      = (OASubmitButtonBean)webBean.findChildRecursive("ApprBtn");
     OASubmitButtonBean      RtnBtnBean       = (OASubmitButtonBean)webBean.findChildRecursive("RtnBtn");
     OAMessageTextInputBean  SupplierActionNE = (OAMessageTextInputBean)webBean.findChildRecursive("SupplierActionNE");
     OAMessageTextInputBean  TreasuryNE       = (OAMessageTextInputBean)webBean.findChildRecursive("TreasuryNE");
     OAMessageTextInputBean  CommHeadNE       = (OAMessageTextInputBean)webBean.findChildRecursive("CommHeadNE");
     
     OAMessageTextInputBean  TreasuryE        = (OAMessageTextInputBean)webBean.findChildRecursive("Treasury");
     OARowLayoutBean  content                 = (OARowLayoutBean)webBean.findChildRecursive("ApprActionRRN");
     OARowLayoutBean  actionHist              = (OARowLayoutBean)webBean.findChildRecursive("ActionHistRRN");
     OARowLayoutBean  piDetails                 = (OARowLayoutBean)webBean.findChildRecursive("PIRRN");
     String respname                          =   String.valueOf(pageContext.getResponsibilityName());

              
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
       if(lcRequestId != null) 
       {
           lcRequestIdSV   =   lcRequestId;
           pageContext.putSessionValue("lcRequestIdSV",lcRequestId);
       }
       try 
       {
           vendorId  =   cc.executeSQLquery(am , "select distinct VENDOR_ID from xxsify_pos_supplier_users_v where  user_id= "+pageContext.getUserId());
       }
       catch (Exception e) 
       {
           System.out.println("vendorId  exe  --> "   +   e);
           pageContext.writeDiagnostics(this,"vendorId  exe  --> "   +   e ,4);
       }
       try 
       {
           vendorName  =   cc.executeSQLquery(am , "select distinct VENDOR_NAME from xxsify_lc_multi_supp_po_info_v where  VENDOR_ID= "+vendorId);
       }
       catch (Exception e) 
       {
           System.out.println("vendorName  exe  --> "   +   e);
           pageContext.writeDiagnostics(this,"vendorName  exe  --> "   +   e ,4);
       }
       try 
       {
           orgName  =  cc.executeSQLquery(am , "select distinct ORGANIZATION_NAME from xxsify_lc_multi_supp_po_info_v where  VENDOR_ID= "+vendorId);
       }
       catch (Exception e) 
       {
           System.out.println("orgName  exe  --> "   +   e);
           pageContext.writeDiagnostics(this,"orgName  exe  --> "   +   e ,4);
       }
       try 
       {
             orgId  = "82";
           //orgId  =   cc.executeSQLquery(am , "select distinct ORG_ID from xxsify_lc_multi_supp_po_info_v where  VENDOR_ID= "+vendorId);
       }
       catch (Exception e) 
       {
           System.out.println("orgId  exe  --> "   +   e);
           pageContext.writeDiagnostics(this,"orgId  exe  --> "   +   e ,4);
       }
       System.out.println("vendorId   --> "   +   vendorId);
       pageContext.writeDiagnostics(this,"vendorId  --> "   +   vendorId ,4);
       
       System.out.println("LC Request Id   --> " + lcRequestIdSV);
       pageContext.writeDiagnostics(this, "LC Request Id " + lcRequestIdSV,4);
       
     if((attachmentFlag!= null && attachmentFlag.equals("Y")) )
     {
       System.out.println("attachmentFlag in PR");
       pageContext.writeDiagnostics(this,  "attachmentFlag in PR"    ,4);
     }
     else if (pageContext.getParameter("SaveYes") != null)
     {
         System.out.println("inside yes");
         String lcStatus     =   hdrVO.getCurrentRow().getAttribute("LcStatus").toString();
         System.out.println("Lc Status     --> "   +lcStatus);
         pageContext.writeDiagnostics(this , "Lc Status   --> "  +lcStatus,4);
         lcRequestIdSV   =    hdrVO.getCurrentRow().getAttribute("LcRequestId").toString();      
         hdrVO.getCurrentRow().setAttribute("LcStatus" , "PWCH");
         cc.createRow(am , lcAppHistVO , "Yes" , "xxsify_lc_wf_appr_s" , "LcWfHistId");
         lcAppHistVO.getCurrentRow().setAttribute("LcRequestId" ,lcRequestIdSV);
         lcAppHistVO.getCurrentRow().setAttribute("LcMode" , "Multi");
         lcAppHistVO.getCurrentRow().setAttribute("Response" , "Submitted");
         lcAppHistVO.getCurrentRow().setAttribute("ApproverId" , pageContext.getUserId());
         lcAppHistVO.getCurrentRow().setAttribute("ApproverName" ,pageContext.getUserName() );
         lcAppHistVO.getCurrentRow().setAttribute("WfItemKey" , lcRequestId);
         lcAppHistVO.getCurrentRow().setAttribute("ActionDate" , pageContext.getCurrentDBDate());
         lcAppHistVO.getCurrentRow().setAttribute("SubmissionDate" , hdrVO.getCurrentRow().getAttribute("CreationDate"));
         lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Requester");
         cc.commit(am);
         if(lcStatus.equals("D"))
         {
             System.out.println("Inside start workflow for request id  --> "   +lcRequestIdSV);
             pageContext.writeDiagnostics(this , "nside start workflow for request id  --> "  +lcRequestIdSV,4);
             cc.startMultiWorkflow(am , lcRequestIdSV);
         }
         else
         {
             System.out.println("Inside start workflow for request id  --> "   +lcRequestIdSV);
             pageContext.writeDiagnostics(this , "nside start workflow for request id  --> "  +lcRequestIdSV,4);
             cc.continueMultiWorkflow(am , lcRequestIdSV);
         }
         
         hmp.put("MsgMode" , "Submitted");
         hmp.put("lcRequestId" , lcRequestIdSV);
         pageContext.setForwardURL(
                                     "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiSearchPG",
                                     null                                                                ,
                                     OAWebBeanConstants.KEEP_MENU_CONTEXT                                ,
                                     null                                                                ,
                                     hmp                                                                  ,
                                     false                                                               ,
                                     OAWebBeanConstants.ADD_BREAD_CRUMB_YES                              ,
                                     OAWebBeanConstants.IGNORE_MESSAGES
                                  ); 
     }
     else if (pageContext.getParameter("SaveNo") != null)
     {
         System.out.println("inside no");
        
         System.out.println("LC Request Id --> " + lcRequestIdSV);
     }
     else
     {
         if ("C".equals(pageContext.getParameter("PageAction")) )
         {
             System.out.println("Inside Create Event");
             cc.createRow(am , hdrVO , "No" , null , null);
             hdrVO.getCurrentRow().setAttribute("LcPeriodOfDocument" , "21 Days");
             hdrVO.getCurrentRow().setAttribute("FlowStatus" , "New");
             hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "EditableRN");
             hdrVO.getCurrentRow().setAttribute("VendorId" , vendorId);
             hdrVO.getCurrentRow().setAttribute("VendorName" , vendorName);
            // hdrVO.getCurrentRow().setAttribute("LcOrgId" , orgId);
             //hdrVO.getCurrentRow().setAttribute("LcOrgName" , orgName);
             
             RtnBtnBean.setRendered(Boolean.FALSE);
             ApprBtnBean.setRendered(Boolean.FALSE);
             submitBean.setRendered(Boolean.FALSE);
             InlandBtnBean.setRendered(Boolean.FALSE);
             ImportBtnBean.setRendered(Boolean.FALSE);
             attachAdvBean.setRendered(Boolean.FALSE);
             content.setRendered(Boolean.FALSE);
             actionHist.setRendered(Boolean.FALSE);
         } 
         if ("U".equals(pageContext.getParameter("PageAction")) )
         {
             System.out.println("Inside Update Event");
             
             System.out.println("lcRequestId    --> "   +lcRequestIdSV);
             pageContext.writeDiagnostics(this , "lcRequestId    --> "   +lcRequestIdSV         , 4);
             whereClause   =   whereClause +" and lc_request_id = " +lcRequestIdSV;
             hdrVO.setWhereClause(whereClause);
             hdrVO.executeQuery();
             hdrVO.first();
             System.out.println("hdrVO Query   --> "   +hdrVO.getQuery()          );
             System.out.println("hdrVO RC      --> "   +hdrVO.getRowCount()       );
             System.out.println("hdrVO FRC     --> "   +hdrVO.getFetchedRowCount());
             pageContext.writeDiagnostics(this , "hdrVO Query   --> "  +hdrVO.getQuery()             , 4);
             pageContext.writeDiagnostics(this , "hdrVO RC      --> "  +hdrVO.getRowCount()          , 4);
             pageContext.writeDiagnostics(this , "hdrVO FRC     --> "  +hdrVO.getFetchedRowCount()   , 4);
             
             String lcClassify   =   hdrVO.getCurrentRow().getAttribute("LcClassification").toString();
             System.out.println("Lc Classification     --> "   +lcClassify);
             pageContext.writeDiagnostics(this , "Lc Classification   --> "  +lcClassify,4);
             String lcStatus     =   hdrVO.getCurrentRow().getAttribute("LcStatus").toString();
             System.out.println("Lc Status     --> "   +lcStatus);
             pageContext.writeDiagnostics(this , "Lc Status   --> "  +lcStatus,4);
             
                      
              reqID   =   hdrVO.getCurrentRow().getAttribute("CreatedBy").toString();
              try 
              {
                  reqName  =   cc.executeSQLquery(am , "select user_name from fnd_user where user_id= "+reqID);
              }
              catch (Exception e) 
              {
                  System.out.println("reqName  exe  --> "   +   e);
                  pageContext.writeDiagnostics(this,"reqName  exe  --> "   +   e ,4);
              }
              System.out.println("reqName   --> "   +   reqName);
              pageContext.writeDiagnostics(this,"reqName  --> "   +   reqName ,4);
                          
             //END
                         
             if(lcClassify.equalsIgnoreCase("Inland"))
             {
                 ImportBtnBean.setRendered(Boolean.FALSE);
                 //hdrVO.getCurrentRow().setAttribute("ButtonSwitcher" , "ShowInlandButtons");
             }
             if(lcClassify.equalsIgnoreCase("Import")) 
             {
                  InlandBtnBean.setRendered(Boolean.FALSE);
                 //hdrVO.getCurrentRow().setAttribute("ButtonSwitcher" , "ShowImportButtons");
             }
             if(lcStatus.equalsIgnoreCase("D") || lcStatus.equalsIgnoreCase("RBCH") || lcStatus.equalsIgnoreCase("RBTR") || lcStatus.equalsIgnoreCase("RBSP"))
             {
                 RtnBtnBean.setRendered(Boolean.FALSE);
                 ApprBtnBean.setRendered(Boolean.FALSE);
                 TreasuryE.setDisabled(Boolean.TRUE);
                 if(lcStatus.equalsIgnoreCase("D") )
                 {
                     content.setRendered(Boolean.FALSE);
                     actionHist.setRendered(Boolean.FALSE);
                 }
                 hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "EditableRN");
                                     
             }
             else if(reqName.equalsIgnoreCase(pageContext.getUserName().toString()) && respname.equals("LC Requestor Responsibility") && (lcStatus.equalsIgnoreCase("PWTR") || lcStatus.equalsIgnoreCase("PWCH") || lcStatus.equalsIgnoreCase("PWSP")||lcStatus.equalsIgnoreCase("AC")))
             {
                 submitBean.setRendered(Boolean.FALSE);
                 DraftBtnBean.setRendered(Boolean.FALSE);
                 SupplierActionNE.setDisabled(Boolean.TRUE);
                 CommHeadNE.setDisabled(Boolean.TRUE);
                 TreasuryNE.setDisabled(Boolean.TRUE);
                 RtnBtnBean.setRendered(Boolean.FALSE);
                 ApprBtnBean.setRendered(Boolean.FALSE);
                 hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "NonEditableRN");
             }
             else if(lcStatus.equalsIgnoreCase("PWTR"))
             {
                 TreasuryNE.setRequired("true");
                 submitBean.setRendered(Boolean.FALSE);
                 DraftBtnBean.setRendered(Boolean.FALSE);                        
                 hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "EditableRN");  
             }
             else if(lcStatus.equalsIgnoreCase("PWCH"))
             {
                 CommHeadNE.setRequired("true");
                 submitBean.setRendered(Boolean.FALSE);
                 DraftBtnBean.setRendered(Boolean.FALSE);
                 SupplierActionNE.setDisabled(Boolean.TRUE);
                 TreasuryNE.setDisabled(Boolean.TRUE);
                 hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "NonEditableRN");  
             }
             else 
             {
                 submitBean.setRendered(Boolean.FALSE);
                 DraftBtnBean.setRendered(Boolean.FALSE);
                 TreasuryNE.setDisabled(Boolean.TRUE);
                 CommHeadNE.setDisabled(Boolean.TRUE);
                 SupplierActionNE.setRequired("true");
                 hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "NonEditableRN");
             }
             poVO.setWhereClause(whereClause);
             poVO.executeQuery();
             poVO.first();
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
       pageContext.writeDiagnostics(this, "Inside PFR" ,4);
       System.out.println("Inside PFR");  
       LCMultiAMImpl       am                =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
       OAViewObject        hdrVO             =   am.getLCRequestHdrEVO();
       OAViewObject        poVO              =   am.getLCPoDetailsEVO();
       OAViewObject        periodOfPresentVO =   am.getPeriodOfPresentVO();
       OAViewObject        lcInlandVO        =   am.getLCInlandReqEVO();
       OAViewObject        lcImportVO        =   am.getLCImportReqEVO();
       OAViewObject        lcAppHistVO       =   am.getLCApprHistoryEVO();
       String              event             =   null    , commHeadName  =   null    , treasuryName  =   null    ,   lcStatus  =   null    ;
       String              CommHeadComm      =   null    , treasuryComm  =   null    , suppComm      =   null;
       HashMap hm = new HashMap();
       event = pageContext.getParameter(EVENT_PARAM);
       String lovevent = pageContext.getLovInputSourceId();
       pageContext.writeDiagnostics(this, "event in PFR --> "+ event ,4);
       System.out.println("event in PFR --> "+ event);
       pageContext.writeDiagnostics(this, "lovevent in PFR --> "+ lovevent ,4);
       System.out.println("lovevent in PFR --> "+ lovevent);
       String lcRequestId    =   null    , poCurr  =   null, lcClassification  =   null, attachVal   =   null;
       int i=0;
       try 
       {
           lcRequestId     =   hdrVO.getCurrentRow().getAttribute("LcRequestId").toString();
       }
       catch (Exception e) 
       {
           System.out.println("LC Request Id Exc --> " + e);  
       }
       pageContext.writeDiagnostics(this, "LC Request Id --> "+ lcRequestId ,4);
       System.out.println("LC Request Id --> --> "+ lcRequestId);
       try 
       {
           lcStatus     =   hdrVO.getCurrentRow().getAttribute("LcStatus").toString();
       }
       catch (Exception e) 
       {
           System.out.println("LC Request Id Exc --> " + e);  
       }
       pageContext.writeDiagnostics(this, "lcStatus --> "+ lcStatus ,4);
       System.out.println("lcStatus --> --> "+ lcStatus);
       try 
       {
           commHeadName  =   cc.executeSQLquery(am , "select lookup_code from XXSIFY_LC_APPROVER_V where tag=1");
       }
       catch (Exception e) 
       {
           System.out.println("commHeadName  exe  --> "   +   e);
           pageContext.writeDiagnostics(this,"commHeadName  exe  --> "   +   e ,4);
       }
       try 
       {
           treasuryName  =   cc.executeSQLquery(am , "select lookup_code from XXSIFY_LC_APPROVER_V where tag=2");
           
       }
       catch (Exception e) 
       {
           System.out.println("treasuryName  exe  --> "   +   e);
           pageContext.writeDiagnostics(this,"treasuryName  exe  --> "   +   e ,4);
       }
       System.out.println("treasuryName   --> "   +   treasuryName);
       pageContext.writeDiagnostics(this,"treasuryName  --> "   +   treasuryName ,4);
       System.out.println("commHeadName    --> "   +   commHeadName);
       pageContext.writeDiagnostics(this,"commHeadName    --> "   +   commHeadName ,4);
       if(event  !=  null  &&  event !=  "")
       {
           pageContext.writeDiagnostics(this, "event Not Null in PFR --> "+ event ,4);
           System.out.println("event Not Null in PFR --> "+ event);
           
           if(event.equals("draft"))
           {
               pageContext.writeDiagnostics(this, "Inside event draft in PFR ",4);
               System.out.println("Inside event draft in PFR ");
               if(lcRequestId !=   null    &&  lcRequestId !=  "")
               {
                  System.out.println("LC Request Id available , so no need to generate new id"); 
               }
               else
               {
                   System.out.println("LC Request Id not available , so we need to generate new id"); 
                   lcRequestId =   am.getOADBTransaction().getSequenceValue("xxsify_lc_request_hdr_s").toString(); 
                   hdrVO.getCurrentRow().setAttribute("LcRequestId" , lcRequestId);
                                   
               }
               pageContext.writeDiagnostics(this, "Inside draft fetched row count of Po details-->"+poVO.getFetchedRowCount(),4);
               System.out.println("Inside draft fetched row count of Po details " +poVO.getFetchedRowCount());
               if (poVO.getFetchedRowCount()>0)
               {
                   pageContext.writeDiagnostics(this, "Inside Po details Insertion-->",4);
                   System.out.println("Inside Po details Insertion--> " +poVO.getFetchedRowCount());
                   for(Row row = poVO.first();row != null;row = poVO.next())
                   {
                        row.setAttribute("LcRequestId" , lcRequestId);
                   }
               }
               hdrVO.getCurrentRow().setAttribute("LcStatus" , "D");
               cc.commit(am);
               hm.put("MsgMode" , "Drafted");
               hm.put("lcRequestId" , lcRequestId);
               pageContext.setForwardURL(
                                           "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiSearchPG"      ,
                                           null                                                                ,
                                           OAWebBeanConstants.KEEP_MENU_CONTEXT                                ,
                                           null                                                                ,
                                           hm                                                                  ,
                                           false                                                               ,
                                           OAWebBeanConstants.ADD_BREAD_CRUMB_YES                              ,
                                           OAWebBeanConstants.IGNORE_MESSAGES
                                        );
               
           }
           if(event.equals("submit"))
           {
                   System.out.println("Inside Submit");
                   String lcClassifyValue  =   null;
                   lcClassifyValue         =   hdrVO.getCurrentRow().getAttribute("LcClassification").toString();
                   int fetchedRowCount     =   0;
                   String getAttachValQuery ="select decode(count(lc_request_id),0,'N','Y')  from XXSIFY_LC_MULTI_PO_ATTACH_V where lc_request_id = '"+lcRequestId+"'";
                   System.out.println("getAttachValQuery   --> "   +   getAttachValQuery);
                   attachVal =cc.executeSQLquery(am,getAttachValQuery);
                   preValidation(pageContext ,  webBean);                 
                   if(lcClassifyValue.equals("Inland"))
                   {
                       cc.executeQuery(lcInlandVO , "lc_request_id = " + lcRequestId);
                       fetchedRowCount     =  lcInlandVO.getFetchedRowCount();  
                   }
                   else
                   {
                       cc.executeQuery(lcImportVO , "lc_request_id = " + lcRequestId);
                       fetchedRowCount     =  lcImportVO.getFetchedRowCount(); 
                   }
                  if( "N".equals(attachVal) )
                   {
                       System.out.println("Inside attachVal ");
                       pageContext.writeDiagnostics(this,  "Inside attachVal "  ,4);
                       throw new OAException("Attachment is mandatory, Kindly add the attachemnt!",OAException.ERROR);
                   }
                   else if(fetchedRowCount> 0)
                   {  
                       System.out.println("Inside Submit");                    
                       OAException descMesg    =  new OAException("Are you sure to submit the page?");
                       OAException instrMesg   =  new OAException("Press Yes to submit the request");
                       OADialogPage dialogPage =  new OADialogPage(OAException.WARNING, descMesg, instrMesg, "",  "");
                       dialogPage.setOkButtonToPost(true);
                       dialogPage.setNoButtonToPost(true);
                       dialogPage.setPostToCallingPage(true);
                       dialogPage.setOkButtonItemName("SaveYes");
                       dialogPage.setNoButtonItemName("SaveNo");
                       pageContext.redirectToDialogPage(dialogPage);
                   }
                   else
                   {
                       throw new OAException(lcClassifyValue.toUpperCase()+ " doesn't have data for this request no: "+ lcRequestId + ".Kindly fill the data to proceed", OAException.ERROR);
                   }
           }
          if(event.equals("accept"))
          {
              pageContext.writeDiagnostics(this, "Inside event accept in PFR ",4);
              System.out.println("Inside event accept in PFR ");
              try 
              {
                  CommHeadComm     =  hdrVO.getCurrentRow().getAttribute("CommheadComments").toString();
                  
              }
              catch (Exception e) 
              {
                  System.out.println("LC Request Id Exc --> " + e);  
              }
              try 
              {
                  treasuryComm     =   hdrVO.getCurrentRow().getAttribute("TreasuryComments").toString();
              }
              catch (Exception e) 
              {
                  System.out.println("LC Request Id Exc --> " + e);  
              }
              try 
              {
                  suppComm     =   hdrVO.getCurrentRow().getAttribute("SupplierComments").toString();
              }
              catch (Exception e) 
              {
                  System.out.println("LC Request Id Exc --> " + e);  
              }
              pageContext.writeDiagnostics(this, "CommHeadComm--> "+CommHeadComm,4);
              System.out.println("CommHeadComm--> "+CommHeadComm);
              pageContext.writeDiagnostics(this, "treasuryComm--> "+treasuryComm,4);
              System.out.println("treasuryComm--> "+treasuryComm);
              pageContext.writeDiagnostics(this, "suppComm--> "+suppComm,4);
              System.out.println("suppComm--> "+suppComm);
              cc.createRow(am , lcAppHistVO , "Yes" , "xxsify_lc_wf_appr_s" , "LcWfHistId");
              lcAppHistVO.getCurrentRow().setAttribute("LcMode" , "Multi");
              lcAppHistVO.getCurrentRow().setAttribute("LcRequestId" , lcRequestId);
              lcAppHistVO.getCurrentRow().setAttribute("Response" , "Accepted");
              lcAppHistVO.getCurrentRow().setAttribute("ApproverId" , pageContext.getUserId());
              lcAppHistVO.getCurrentRow().setAttribute("ApproverName" ,pageContext.getUserName() );
              lcAppHistVO.getCurrentRow().setAttribute("WfItemKey" , lcRequestId);
              lcAppHistVO.getCurrentRow().setAttribute("ActionDate" , pageContext.getCurrentDBDate());
              lcAppHistVO.getCurrentRow().setAttribute("SubmissionDate" , hdrVO.getCurrentRow().getAttribute("CreationDate"));
              
              if(lcStatus.equals("PWCH")) 
              {
                  hdrVO.getCurrentRow().setAttribute("LcStatus" , "PWTR");
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,CommHeadComm );
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Commercial Head");
                  
              }
              else if(lcStatus.equals("PWTR")) 
              {
                  hdrVO.getCurrentRow().setAttribute("LcStatus" , "PWSP");
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,treasuryComm );
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Treasury");
                  
              }
             else 
              {
                  hdrVO.getCurrentRow().setAttribute("LcStatus" , "AC");
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,suppComm );
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Supplier");
                  
              }
              cc.commit(am);
              cc.continueMultiWorkflow(am , lcRequestId);
              hm.put("MsgMode" , "Accepted");
              hm.put("lcRequestId" , lcRequestId);
              pageContext.setForwardURL(
                                          "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiSearchPG",
                                          null                                                                ,
                                          OAWebBeanConstants.KEEP_MENU_CONTEXT                                ,
                                          null                                                                ,
                                          hm                                                                 ,
                                          false                                                               ,
                                          OAWebBeanConstants.ADD_BREAD_CRUMB_YES                              ,
                                          OAWebBeanConstants.IGNORE_MESSAGES
                                       ); 
              
          }
          if(event.equals("return"))
          {
              pageContext.writeDiagnostics(this, "Inside event return in PFR ",4);
              System.out.println("Inside event return in PFR ");
              try 
              {
                  CommHeadComm     =  hdrVO.getCurrentRow().getAttribute("CommheadComments").toString();
                  
              }
              catch (Exception e) 
              {
                  System.out.println("LC Request Id Exc --> " + e);  
              }
              try 
              {
                  treasuryComm     =   hdrVO.getCurrentRow().getAttribute("TreasuryComments").toString();
              }
              catch (Exception e) 
              {
                  System.out.println("LC Request Id Exc --> " + e);  
              }
              try 
              {
                  suppComm     =   hdrVO.getCurrentRow().getAttribute("SupplierComments").toString();
              }
              catch (Exception e) 
              {
                  System.out.println("LC Request Id Exc --> " + e);  
              }
              pageContext.writeDiagnostics(this, "CommHeadComm--> "+CommHeadComm,4);
              System.out.println("CommHeadComm--> "+CommHeadComm);
              pageContext.writeDiagnostics(this, "treasuryComm--> "+treasuryComm,4);
              System.out.println("treasuryComm--> "+treasuryComm);
              pageContext.writeDiagnostics(this, "suppComm--> "+suppComm,4);
              System.out.println("suppComm--> "+suppComm);
              cc.createRow(am , lcAppHistVO , "Yes" , "xxsify_lc_wf_appr_s" , "LcWfHistId");
              lcAppHistVO.getCurrentRow().setAttribute("LcMode" , "Multi");
              lcAppHistVO.getCurrentRow().setAttribute("LcRequestId" , lcRequestId);
              lcAppHistVO.getCurrentRow().setAttribute("Response" , "Returned");
              lcAppHistVO.getCurrentRow().setAttribute("ApproverId" , pageContext.getUserId());
              lcAppHistVO.getCurrentRow().setAttribute("ApproverName" ,pageContext.getUserName() );
              lcAppHistVO.getCurrentRow().setAttribute("WfItemKey" , lcRequestId);
              lcAppHistVO.getCurrentRow().setAttribute("ActionDate" , pageContext.getCurrentDBDate());
              lcAppHistVO.getCurrentRow().setAttribute("SubmissionDate" , hdrVO.getCurrentRow().getAttribute("CreationDate"));
              
              if(lcStatus.equals("PWCH")) 
              {
                  hdrVO.getCurrentRow().setAttribute("LcStatus" , "RBCH");
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,CommHeadComm );
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Commercial Head");
                  
              }
              else if(lcStatus.equals("PWTR")) 
              {
                  hdrVO.getCurrentRow().setAttribute("LcStatus" , "RBTR");
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,treasuryComm );
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Treasury");
                  
              }
              else 
              {
                  hdrVO.getCurrentRow().setAttribute("LcStatus" , "RBSP");
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,suppComm );
                  lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Supplier");
                  
              }
              cc.commit(am);
              cc.continueMultiWorkflow(am , lcRequestId);
              hm.put("MsgMode" , "Returned");
              hm.put("lcRequestId" , lcRequestId);
              pageContext.setForwardURL(
                                          "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiSearchPG",
                                          null                                                                ,
                                          OAWebBeanConstants.KEEP_MENU_CONTEXT                                ,
                                          null                                                                ,
                                          hm                                                                  ,
                                          false                                                               ,
                                          OAWebBeanConstants.ADD_BREAD_CRUMB_YES                              ,
                                          OAWebBeanConstants.IGNORE_MESSAGES
                                       ); 
          }
         
           if(event.equals("addRow"))
           {
                 System.out.println("Inside event addRow in PO Details");
                 pageContext.writeDiagnostics(this, "Inside event addRow in PFR ",4);
                 cc.createRow(am , poVO , "Yes" ,"xxsify_lc_po_details_s" , "LcPoDetId"); 
                 for(Row row = poVO.first();row != null;row = poVO.next())
                 {
                     i   =   i+1;
                     row.setAttribute("Sno",i);
                 }
           }
           if(event.equals("lcValueWordConv"))
           {
               System.out.println("Inside LC word conversion");
               String lcValue=null, lcValWord=null,lcCurrencyValue = null;
               try
               {
                   lcValue    =   hdrVO.getCurrentRow().getAttribute("LcValue").toString();
                   System.out.println("Lc Value    "+lcValue);
               }
               catch (Exception e)
               {
                   System.out.println("Lc value to word conversion Exc --> " + e);
               }    
               try 
               {
               lcCurrencyValue =   hdrVO.getCurrentRow().getAttribute("LcCurrencyCode").toString();
               }
               catch (Exception e) 
               {
               System.out.println("LC Currency Value Exc --> " + e);    
               }
               if(lcValue !=  null    &&  lcValue    !=  "")
               {                 
                    lcValWord    =   cc.executeSQLquery(am , "select xxsify_lc_curr_amt_words('"+lcValue+"','"+lcCurrencyValue+"') from dual");
                    System.out.println("LC value to word --> " + lcValWord);                  
                    hdrVO.getCurrentRow().setAttribute("LcValueWords" , lcValWord.toUpperCase());
               }
          }  
          
          if(event.equals("calculateLCExpiryDate"))
          {
              System.out.println("Inside Calculate LC Expiry Date");
              String  lastShipmentDate  = null    ,   periodOfPresentation    =   null , lcExpiryDate = null;
              cc.executeQuery(periodOfPresentVO , "1=1");
              try
              {
                  lastShipmentDate    =   hdrVO.getCurrentRow().getAttribute("LcLastDateOfShipment").toString();
              }
              catch (Exception e)
              {
                  System.out.println("Last Shipment Date Exc --> " + e);
              }            
              try
              {
                  periodOfPresentation    =   periodOfPresentVO.getCurrentRow().getAttribute("LookupCode").toString();
              }
              catch (Exception e)
              {
                  System.out.println("Period Of Presentation Exc --> " + e);
              }
              System.out.println("Last Shipment Date      --> " + lastShipmentDate);
              System.out.println("Period Of Presentation  --> " + periodOfPresentation);
              
              if(lastShipmentDate !=  null    &&  lastShipmentDate    !=  "")
              {
                  lcExpiryDate    =   cc.executeSQLquery(am , "select to_date('" + lastShipmentDate + "','RRRR-MM-DD') + " + periodOfPresentation + " from dual");
                  System.out.println("LC Expiry Date --> " + lcExpiryDate);
                  if(lcExpiryDate !=  null    &&  lcExpiryDate    !=  "")
                  {
                      hdrVO.getCurrentRow().setAttribute("LcExpiryDate", lcExpiryDate);
                  }
              }
          }         
          if ("delete".equals(event)) 
          {
              System.out.println("inside delete method");
              pageContext.writeDiagnostics(this , "inside delete method --> ", 4);
              String rowRef = pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
              Serializable[] params = { rowRef };
              am.invokeMethod("removeLines", params);
              throw new OAException("Selected row deleted successfully", OAException.CONFIRMATION);
          }
          if ("cancel".equals(event)) 
          {
              System.out.println("inside cancel method");
              pageContext.writeDiagnostics(this , "inside cancel method --> ", 4);
              pageContext.forwardImmediately("OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiSearchPG", 
                                             null, 
                                             OAWebBeanConstants.KEEP_MENU_CONTEXT, 
                                             null, null, false, 
                                             OAWebBeanConstants.ADD_BREAD_CRUMB_NO);          
          
          }
          if("oaAddAttachment".equals(pageContext.getParameter(EVENT_PARAM)) ||  "oaGotoAttachments".equals(pageContext.getParameter(EVENT_PARAM)))
          {
              pageContext.putSessionValue("AttachmentFlag","Y"); 
          } 
          if(event.equals("importreq"))
          {
              System.out.println("Inside Import");
              cc.commit(am);
              hm.put("p_lc_request_id" , hdrVO.getCurrentRow().getAttribute("LcRequestId").toString());
              
              pageContext.setForwardURL(
                                          "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiImportReqPG"   ,
                                          null                                                                       ,
                                          OAWebBeanConstants.KEEP_MENU_CONTEXT                                       ,
                                          null                                                                       ,
                                          hm                                                                         ,
                                          false                                                                      ,
                                          OAWebBeanConstants.ADD_BREAD_CRUMB_YES                                     ,
                                          OAWebBeanConstants.IGNORE_MESSAGES
                                       );
          }
          if(event.equals("inlandreq"))
          {
              System.out.println("Inside Inland");
              cc.commit(am);
              hm.put("p_lc_request_id" , hdrVO.getCurrentRow().getAttribute("LcRequestId").toString());
              
              pageContext.setForwardURL(
                                          "OA.jsp?page=/oracle/apps/sfc/lcissuance/multi/webui/LCMultiInlandReqPG"   ,
                                          null                                                                       ,
                                          OAWebBeanConstants.KEEP_MENU_CONTEXT                                       ,
                                          null                                                                       ,
                                          hm                                                                         ,
                                          false                                                                      ,
                                          OAWebBeanConstants.ADD_BREAD_CRUMB_YES                                     ,
                                          OAWebBeanConstants.IGNORE_MESSAGES
                                       );
          }
          if(event.equals("poValCal"))
          {
              int poValue    =   0, pv=0;
              System.out.println("Inside event poValCal");
              pageContext.writeDiagnostics(this, "Inside event poValCal ",4);
              for(Row row = poVO.first();row != null;row = poVO.next())
              {
                  try 
                  {
                      pv          =   Integer.parseInt(row.getAttribute("PoValue").toString());
                  }
                  catch (Exception e) 
                  {
                      pv=0;
                  }
                  poValue     =   poValue + pv ;
              }
              System.out.println("poValue-->"+poValue);
              pageContext.writeDiagnostics(this, "poValue-->"+poValue,4);
              hdrVO.getCurrentRow().setAttribute("TotalPoValue", poValue);
          }
          
          /*===================================================== Po currency validation starts======================================================== */     
                   if(event.equals("lovUpdate") || event.equals("lovValidate") && lovevent.equals("PoNumLov"))
                   {
                    System.out.println("Inside LOV Update");
                    pageContext.writeDiagnostics(this, "IInside LOV Update",4);
                       if(lovevent.equals("PoNumLov"))
                       {
                           System.out.println("Inside LC PO Number");
                           pageContext.writeDiagnostics(this, "Inside LC PO Number",4); 
                           String lcCur   =   null;
                           OAMessageLovInputBean poNum           =           (OAMessageLovInputBean)webBean.findChildRecursive("PoNumLov");
                           try 
                           {
                                lcCur    =   hdrVO.getCurrentRow().getAttribute("LcCurrencyCode").toString();
                           }
                           catch (Exception e) 
                           {
                               lcCur  =   "";
                           }
                           System.out.println("lcCur-->"+lcCur);
                           pageContext.writeDiagnostics(this, "lcCur-->"+lcCur,4); 
                           for(Row row = poVO.first();row != null;row = poVO.next())
                           {
                               if (lcCur  != null && lcCur  !="") 
                               {
                                   System.out.println("Inside lcCur not null");
                                   pageContext.writeDiagnostics(this, "Inside lcCur not null",4); 
                               } 
                               else
                               {
                                   poCurr    =   cc.executeSQLquery(am , "select distinct currency_code from po_headers_all where segment1='"+poNum.getValue(pageContext)+"'");
                                   System.out.println("poCurr --> " + poCurr);
                                   pageContext.writeDiagnostics(this, "poCurr-->"+poCurr,4); 
                                   lcClassification    =   cc.executeSQLquery(am , "select distinct  Decode(upper(currency_code), 'INR','Inland','Import') from po_headers_all where segment1='"+poNum.getValue(pageContext)+"'");
                                   System.out.println("lcClassification --> " + lcClassification);
                                   pageContext.writeDiagnostics(this, "lcClassification-->"+lcClassification,4); 
                                   hdrVO.getCurrentRow().setAttribute("LcCurrencyCode",poCurr);
                                   hdrVO.getCurrentRow().setAttribute("LcClassification",lcClassification);

                               }
                               break;
                           }
                          
                       }
                  }

          /*===================================================== Po currency validation ends======================================================== */
      }
   }
   
   public void preValidation(OAPageContext pageContext , OAWebBean webBean ) 
     {
         System.out.println("Inside preValidation");
         pageContext.writeDiagnostics(this,  "Inside preValidation"   ,4);
         LCMultiAMImpl       am                =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
         OAViewObject        hdrVO             =   am.getLCRequestHdrEVO();
         if(!hdrVO.getCurrentRow().getAttribute("LcPartialShipment").equals(hdrVO.getCurrentRow().getAttribute("Attribute1"))) 
         {
             throw new OAException("LC condition values and PI details values should match, Kindly correct the  Partial Shipment to proceed", OAException.ERROR);  
         }
         if(!hdrVO.getCurrentRow().getAttribute("LcTranshipment").equals(hdrVO.getCurrentRow().getAttribute("Attribute2"))) 
         {
             throw new OAException("LC condition values and PI details values should match, Kindly correct the  Transhipment to proceed", OAException.ERROR);  
         }
         if(!hdrVO.getCurrentRow().getAttribute("LcModeOfTransport").equals(hdrVO.getCurrentRow().getAttribute("Attribute3"))) 
         {
             throw new OAException("LC condition values and PI details values should match, Kindly correct the  Mode Of Transport to proceed", OAException.ERROR);  
         }
         if(!hdrVO.getCurrentRow().getAttribute("LcCountryOfOrigin").equals(hdrVO.getCurrentRow().getAttribute("Attribute4"))) 
         {
             throw new OAException("LC condition values and PI details values should match, Kindly correct the  Country Of Origin to proceed", OAException.ERROR);  
         }
         if(!hdrVO.getCurrentRow().getAttribute("LcPaymentTerms").equals(hdrVO.getCurrentRow().getAttribute("Attribute5"))) 
         {
             throw new OAException("LC condition values and PI details values should match, Kindly correct the  Payment Terms to proceed", OAException.ERROR);  
         }
         if(!hdrVO.getCurrentRow().getAttribute("LcTradeTerms").equals(hdrVO.getCurrentRow().getAttribute("Attribute6"))) 
         {
             throw new OAException("LC condition values and PI details values should match, Kindly correct the  Trade Terms to proceed", OAException.ERROR);  
         }
         if(!hdrVO.getCurrentRow().getAttribute("LcPortOfLoading").equals(hdrVO.getCurrentRow().getAttribute("Attribute7"))) 
         {
             throw new OAException("LC condition values and PI details values should match, Kindly correct the  Port Of Loading to proceed", OAException.ERROR);  
         }
         if(!hdrVO.getCurrentRow().getAttribute("LcPortOfDischarge").equals(hdrVO.getCurrentRow().getAttribute("Attribute8"))) 
         {
             throw new OAException("LC condition values and PI details values should match, Kindly correct the  Port of Discharge to proceed", OAException.ERROR);  
         }
     }    
 }
