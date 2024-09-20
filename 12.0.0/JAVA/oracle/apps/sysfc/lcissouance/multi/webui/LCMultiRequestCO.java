/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package oracle.apps.sfc.lcissuance.multi.webui;

import com.sun.java.util.collections.HashMap;
import java.sql.CallableStatement;
import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OARow;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OAApplicationModuleImpl;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADialogPage;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.form.OASubmitButtonBean;
import oracle.apps.fnd.framework.webui.beans.layout.OARowLayoutBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageChoiceBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageStyledTextBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.fnd.framework.webui.beans.table.OAAdvancedTableBean;
import oracle.apps.po.common.webui.ClientUtil;
import oracle.apps.sfc.lcissuance.multi.server.LCMultiAMImpl;
import oracle.apps.sfc.lcissuance.common.server.CommonClass;
import oracle.apps.sfc.lcissuance.multi.server.LCPoDetailsEVORowImpl;
import oracle.jbo.Row;

/**
 * Controller for ...
 */
public class LCMultiRequestCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");
/**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  CommonClass   cc            =   new   CommonClass();  
  Double           enteredValue  =   0.0   ,   totalPOValue    =   0.0;
//  float           enteredValue  =   0.00f   ,   totalPOValue    =   0.00f;
  ClientUtil cu = new ClientUtil();
  String AmendCancelAction = null , revisionNo = null , pageActionForPOLOV = null;
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    writeLog(pageContext ,"Inside PR" ,4);
    System.out.println("Inside LCMultiRequestPG PR");  
    LCMultiAMImpl       am            =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
    OAViewObject        hdrVO         =   am.getLCRequestHdrEVO();
    OAViewObject        poVO          =   am.getLCPoDetailsEVO();
    OAViewObject        lcAppHistVO   =   am.getLCApprHistoryEVO();
    OAViewObject        lcInlandVO    =   am.getLCInlandReqEVO();
    OAViewObject        lcDocumentVO  =   am.getLCDocDetailsEVO(); 
    OAViewObject        lcImportVO    =   am.getLCImportReqEVO();
    OAViewObject        lcValVO       =   am.getLCValueVO();
    OAViewObject        transVO       =   am.getTransientVO();
//    OAViewObject        repVO         =   am.getLCReportEVO();
//    OAViewObject        poLovVO       =   am.getPoDetailsVO();
    String              event         =   null      ,       whereClause =  "1=1"   ,   lcRequestId     =   null     ;
    String              reqName       =   null      ,       reqID       =   null   ,   lcRequestIdSV   =   null    ,   vendorId    =   null  ,
                        orgId         =   null      ,       orgName     =   null   ,   vendorName      =   null    ,   currCode    =   null  ,
                        inlandID      =   null      ,       importID    =   null   ;
    int     i   =   0   ,   j=0 ;
    HashMap hmp = new HashMap();
    OAAdvancedTableBean     attachAdvBean    = (OAAdvancedTableBean)webBean.findChildRecursive("AttachAdvTable") ; 
    String                  attachmentFlag   = (String)pageContext.getSessionValue("AttachmentFlag");
    OASubmitButtonBean      DraftBtnBean     = (OASubmitButtonBean)webBean.findChildRecursive("DraftBtn");
    OASubmitButtonBean      ImportBtnBean    = (OASubmitButtonBean)webBean.findChildRecursive("ImportBtn");
    OASubmitButtonBean      InlandBtnBean    = (OASubmitButtonBean)webBean.findChildRecursive("InlandBtn");
    OASubmitButtonBean      submitBean       = (OASubmitButtonBean)webBean.findChildRecursive("SubmitBtn");
    OASubmitButtonBean      ApprBtnBean      = (OASubmitButtonBean)webBean.findChildRecursive("ApprBtn");
    OASubmitButtonBean      RtnBtnBean       = (OASubmitButtonBean)webBean.findChildRecursive("RtnBtn");
    OASubmitButtonBean      ReportBtnBean    = (OASubmitButtonBean)webBean.findChildRecursive("ReportBtn");
    OAMessageTextInputBean  SupplierActionNE = (OAMessageTextInputBean)webBean.findChildRecursive("SupplierActionNE");
    OAMessageTextInputBean  TreasuryNE       = (OAMessageTextInputBean)webBean.findChildRecursive("TreasuryNE");
    OAMessageTextInputBean  CommHeadNE       = (OAMessageTextInputBean)webBean.findChildRecursive("CommHeadNE");
    OAMessageTextInputBean  FinanceNE        = (OAMessageTextInputBean)webBean.findChildRecursive("FinanceNE");
    OAMessageTextInputBean  APCommHeadNE     = (OAMessageTextInputBean)webBean.findChildRecursive("APCommHeadNE");
    OAMessageTextInputBean  BuyerNE          = (OAMessageTextInputBean)webBean.findChildRecursive("BuyerNE");
//    OAMessageTextInputBean  PIVoucherNoNE    = (OAMessageTextInputBean)webBean.findChildRecursive("PIVoucherNoNE");
//    OAAdvancedTableBean   poAdvtable         = (OAAdvancedTableBean)webBean.findIndexedChildRecursive("PoAdvTable");
//    OAColumnBean          piVoucherNoCER     = (OAColumnBean) poAdvtable.findIndexedChildRecursive("PIVoucherNoC");
    OAMessageTextInputBean  TreasuryE        = (OAMessageTextInputBean)webBean.findChildRecursive("Treasury");
    OARowLayoutBean         content          = (OARowLayoutBean)webBean.findChildRecursive("ApprActionRRN");
    OARowLayoutBean         actionHist       = (OARowLayoutBean)webBean.findChildRecursive("ActionHistRRN");
//    OARowLayoutBean         piDetails        = (OARowLayoutBean)webBean.findChildRecursive("PIRRN");
    String                  respname         =  String.valueOf(pageContext.getResponsibilityName());
    // Added by Jenish 
//    OAMessageChoiceBean    requiredCifDocs     =   (OAMessageChoiceBean)webBean.findChildRecursive("DocsRequiredCIf");
//    OAMessageChoiceBean    requiredFobDocs     =   (OAMessageChoiceBean)webBean.findChildRecursive("DocsRequiredFob");
//    
//    requiredFobDocs.setRendered(false);
//    requiredCifDocs.setRendered(false);
     //added by jenish
     transVO.setWhereClause("1=1");
     transVO.executeQuery();
     transVO.first();
//     transVO.getCurrentRow().setAttribute("RenLcCond", false);
    try
    {
        event   =   pageContext.getParameter(EVENT_PARAM);
    }
    catch (Exception e) 
    {
        writeLog(pageContext, "Event Exc -->" + e ,4); 
    }
      writeLog(pageContext, "Event  -->" + event    ,4);
      
      writeLog(pageContext, "Page Action-->"+pageContext.getParameter("PageAction")    ,4);
//    OAMessageTextInputBean periodOfTime = (OAMessageTextInputBean) webBean.findChildRecursive ("PeriodOfDocNE");
//    periodOfTime.setReadOnly (true);
// ab jenis 
    if ("U".equals(pageContext.getParameter("PageAction")) )
    {
        transVO.getCurrentRow().setAttribute("RenLcCond", true);
    }
    else  if ("C".equals(pageContext.getParameter("PageAction"))||("Z".equals(pageContext.getParameter("PageAction"))))
    {
        transVO.getCurrentRow().setAttribute("RenLcCond", false);
    } 
    else 
    {
        transVO.getCurrentRow().setAttribute("RenLcCond", true);
    }
      try 
      {
        lcRequestId =   pageContext.getParameter("p_hdr_id");
        
      }
      catch (Exception e) 
      {
        System.out.println("LC Request Id Exc --> " + e);    
      }
      writeLog(pageContext ,"LC Request Id --> lcRequestId ", 4);
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
          writeLog(pageContext ,"vendorId  exe  --> "   +   e ,4);
      }
      try 
      {
          vendorName  =   cc.executeSQLquery(am , "select distinct VENDOR_NAME from xxsify_lc_multi_supp_po_info_v where  VENDOR_ID= "+vendorId);
      }
      catch (Exception e) 
      {
     
          writeLog(pageContext ,"vendorName  exe  --> "   +   e ,4);
      }
      try 
      {
          orgName  =  cc.executeSQLquery(am , "select distinct ORGANIZATION_NAME from xxsify_lc_multi_supp_po_info_v where  VENDOR_ID= "+vendorId);
      }
      catch (Exception e) 
      {
          writeLog(pageContext ,"orgName  exe  --> "   +   e ,4);
      }
//      try 
//      {
//            orgId  = "82";
//          //orgId  =   cc.executeSQLquery(am , "select distinct ORG_ID from xxsify_lc_multi_supp_po_info_v where  VENDOR_ID= "+vendorId);
//      }
//      catch (Exception e) 
//      {
//          System.out.println("orgId  exe  --> "   +   e);
//          pageContext.writeDiagnostics(this,"orgId  exe  --> "   +   e ,4);
//      }
      // Added by Kanaga //
       try 
       {
           currCode  =   cc.executeSQLquery(am , "select distinct currency_code from xxsify_lc_multi_supp_po_info_v where  VENDOR_ID= "+vendorId);
       }
       catch (Exception e) 
       {
           writeLog(pageContext ,"vendorName  exe  --> "   +   e ,4);
       }
      // Added by Kanaga //
      writeLog(pageContext ,"vendorId  is  --> "   +   vendorId ,4);
      writeLog(pageContext ,"LC Request Id  is  --> "   +   lcRequestIdSV ,4);
    if((attachmentFlag!= null && attachmentFlag.equals("Y")) )
    { 
      writeLog(pageContext ,"attachmentFlag in PR not null & equals Y"    ,4);
    }
    else if (pageContext.getParameter("SaveYes") != null)
    {
        writeLog(pageContext ,"inside SaveYes   --> " ,4);
        String lcStatus     =   hdrVO.getCurrentRow().getAttribute("LcStatus").toString();
        
        writeLog(pageContext ,"Lc Status   --> "  +lcStatus,4);
        lcRequestIdSV   =    hdrVO.getCurrentRow().getAttribute("LcRequestId").toString();     
       // hdrVO.getCurrentRow().setAttribute("LcStatus" , "PWCH");
        hdrVO.getCurrentRow().setAttribute("LcStatus" , "PWB");
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
        String revNo = null;
        try
        {
            revNo = cc.executeSQLquery(am , "select xxsify_lc_multi_po_wf_pkg.get_version_no("+lcRequestIdSV+") from dual");
        }
        catch (Exception e) 
        {
            System.out.println("revNo Exc --> " + e);
        }
        writeLog(pageContext , "Rev No --> " + revNo , 4);
        lcAppHistVO.getCurrentRow().setAttribute("Attribute9"   , revNo);
        cc.commit(am);
        if(lcStatus.equals("D"))
        {
            writeLog(pageContext , "inside start workflow for request id  --> "  +lcRequestIdSV,4);
            cc.startMultiWorkflow(am , lcRequestIdSV);
        }
        else
        {
            writeLog(pageContext , "inside start workflow for request id  --> "  +lcRequestIdSV,4);
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
        try 
        {
            pageActionForPOLOV =   pageContext.getParameter("PageAction");
        }
        catch (Exception e) 
        {
            System.out.println("pageActionForPOLOV Exc --> " + e);
        }
        System.out.println("pageActionForPOLOV --> " + pageActionForPOLOV);
        if ("C".equals(pageContext.getParameter("PageAction")) )
        {
            System.out.println("Inside Create Event C");
            cc.createRow(am , hdrVO , "No" , null , null);
            cc.createRow(am , lcValVO , "No" , null , null);
            hdrVO.getCurrentRow().setAttribute("LcPeriodOfDocument" , "21 Days");
            hdrVO.getCurrentRow().setAttribute("FlowStatus" , "New");
            hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "EditableRN");
            hdrVO.getCurrentRow().setAttribute("VendorId" , vendorId);
            hdrVO.getCurrentRow().setAttribute("VendorName" , vendorName);
           // hdrVO.getCurrentRow().setAttribute("LcOrgId" , orgId);
            //hdrVO.getCurrentRow().setAttribute("LcOrgName" , orgName);
            // Added by kanaga //
            cc.createRow(am , lcInlandVO , "No" , null , null);
            inlandID =   am.getOADBTransaction().getSequenceValue("xxsify_lc_inland_req_id_s").toString();
            writeLog(pageContext , "inlandID-->"+inlandID, 4);
            pageContext.putSessionValue("InlID",inlandID);
            //pageContext.putSessionValue("PageAct",pageContext.getParameter("PageAction"));
            lcInlandVO.getCurrentRow().setAttribute("LcRequestId",lcRequestId);
            lcInlandVO.getCurrentRow().setAttribute("LcInlandId" , inlandID);
            lcInlandVO.getCurrentRow().setAttribute("TypeOfLc" , "IRREVOCABLE");
            hdrVO.getCurrentRow().setAttribute("Attribute13" , "21 Days");
            hdrVO.getCurrentRow().setAttribute("LcCurrencyCode" , "INR");
            //poVO.getCurrentRow().setAttribute("LcCurrencyCode" , "INR");
            hdrVO.getCurrentRow().setAttribute("LcClassification","Inland"); 
            hdrVO.getCurrentRow().setAttribute("TypeOfLc" , "IRREVOCABLE");
            hdrVO.getCurrentRow().setAttribute("Attribute9" , 0);
            hdrVO.getCurrentRow().setAttribute("RenCif" ,false);
            hdrVO.getCurrentRow().setAttribute("RenFob" ,false);
            hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
            hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false);
            hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,false); 
            hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,false); 
            // Added by Kanaga //
            RtnBtnBean.setRendered(Boolean.FALSE);
            ApprBtnBean.setRendered(Boolean.FALSE);
            submitBean.setRendered(Boolean.FALSE);
            InlandBtnBean.setRendered(Boolean.FALSE);
            ImportBtnBean.setRendered(Boolean.FALSE);
            ReportBtnBean.setRendered(Boolean.FALSE);
            attachAdvBean.setRendered(Boolean.FALSE);
            content.setRendered(Boolean.FALSE);
            actionHist.setRendered(Boolean.FALSE);
        } 
        // Added by Kanaga
         if ("Z".equals(pageContext.getParameter("PageAction")) )
         {
             System.out.println("Inside Create Event Z");
             cc.createRow(am , hdrVO , "No" , null , null);
             cc.createRow(am , lcValVO , "No" , null , null);
             hdrVO.getCurrentRow().setAttribute("LcPeriodOfDocument" , "21 Days");
             hdrVO.getCurrentRow().setAttribute("FlowStatus" , "New");
             hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "EditableRN");
             hdrVO.getCurrentRow().setAttribute("VendorId" , vendorId);
             hdrVO.getCurrentRow().setAttribute("VendorName" , vendorName);
             hdrVO.getCurrentRow().setAttribute("Attribute9" , 0);
            // hdrVO.getCurrentRow().setAttribute("LcOrgId" , orgId);
             //hdrVO.getCurrentRow().setAttribute("LcOrgName" , orgName);
             // Added by kanaga //
             cc.createRow(am , lcImportVO , "No" , null , null);
             importID =   am.getOADBTransaction().getSequenceValue("xxsify_lc_import_req_s").toString();
             writeLog(pageContext , "importID-->"+importID, 4);
             pageContext.putSessionValue("ImpID",importID);
             lcImportVO.getCurrentRow().setAttribute("LcRequestId",lcRequestId);
             lcImportVO.getCurrentRow().setAttribute("LcImportId" , importID);
             lcImportVO.getCurrentRow().setAttribute("LcType" , "IRREVOCABLE");
             hdrVO.getCurrentRow().setAttribute("Attribute13" , "21 Days");
             //hdrVO.getCurrentRow().setAttribute("LcCurrencyCode" , "INR");
             //poVO.getCurrentRow().setAttribute("LcCurrencyCode" , "INR");
             hdrVO.getCurrentRow().setAttribute("LcClassification","Import"); //IMPORT
             hdrVO.getCurrentRow().setAttribute("TypeOfLc" , "IRREVOCABLE");
             hdrVO.getCurrentRow().setAttribute("RenCif" ,false);
             hdrVO.getCurrentRow().setAttribute("RenFob" ,false);
             hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
             hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false);
             hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,false); 
             hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,false); 
             // Added by Kanaga //
             RtnBtnBean.setRendered(Boolean.FALSE);
             ApprBtnBean.setRendered(Boolean.FALSE);
             submitBean.setRendered(Boolean.FALSE);
             InlandBtnBean.setRendered(Boolean.FALSE);
             ImportBtnBean.setRendered(Boolean.FALSE);
             ReportBtnBean.setRendered(Boolean.FALSE);
             attachAdvBean.setRendered(Boolean.FALSE);
             content.setRendered(Boolean.FALSE);
             actionHist.setRendered(Boolean.FALSE);
         } 
        // Added by Kanaga
        if ("U".equals(pageContext.getParameter("PageAction")) || "A".equals(pageContext.getParameter("PageAction")))
        {
            System.out.println("Inside Update and Amend Event --> " + pageContext.getParameter("PageAction"));
            writeLog(pageContext , "lcRequestId    --> "   +lcRequestIdSV         , 4);
            try 
            {
                revisionNo  =   pageContext.getParameter("p_rev_no");
            }
            catch (Exception e) 
            {
                System.out.println("Rev No Exc --> " + e);
            }
            
            System.out.println("Rev No --> " + revisionNo);
            
            if("A".equals(pageContext.getParameter("PageAction")))
            {
                writeLog(pageContext , "Inside Amendment Activity"         , 4);
                AmendCancelAction = "A";
                callAmendmentActivities(am , lcRequestIdSV);
                revisionNo = String.valueOf((Integer.parseInt(revisionNo) + 1));
            }
            whereClause   =   whereClause +" and lc_request_id = " +lcRequestIdSV + " and attribute9='" + revisionNo +"'";
            hdrVO.setWhereClause(whereClause);
            hdrVO.executeQuery();
            hdrVO.first();
            hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,false); 
            hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,false); 
            lcInlandVO.setWhereClause(whereClause);
            lcInlandVO.executeQuery();
            hdrVO.first();
            lcDocumentVO.setWhereClause(whereClause);
            lcDocumentVO.executeQuery();
            lcDocumentVO.first();
            writeLog(pageContext , "hdrVO Query   --> "  +hdrVO.getQuery()             , 4);
            writeLog(pageContext , "hdrVO RC      --> "  +hdrVO.getRowCount()          , 4);
            writeLog(pageContext , "hdrVO FRC     --> "  +hdrVO.getFetchedRowCount()   , 4);
            writeLog(pageContext , "lcDocumentVO Query   --> "  +lcDocumentVO.getQuery()             , 4);
            writeLog(pageContext , "lcDocumentVO RC      --> "  +lcDocumentVO.getRowCount()          , 4);
            writeLog(pageContext , "lcDocumentVO FRC     --> "  +lcDocumentVO.getFetchedRowCount()   , 4);
            String lcClassify   =   hdrVO.getCurrentRow().getAttribute("LcClassification").toString();
            String lcStatus     =   hdrVO.getCurrentRow().getAttribute("LcStatus").toString();
            writeLog(pageContext ,"Lc Classification   --> "  +lcClassify,4);
            writeLog(pageContext ,"Lc Status           --> "  +lcStatus,4);
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
            if(lcStatus.equalsIgnoreCase("D"))
            {
                System.out.println("Inside Status --> " + lcStatus);
                String lcincoterm   =   null    ,   piincoterm  =   null    ,   lcVal   =   null    ,   totPoVal    =   null    ,   lcValWords  =   null;
                RtnBtnBean.setRendered(Boolean.FALSE);
                ApprBtnBean.setRendered(Boolean.FALSE);
                TreasuryE.setDisabled(Boolean.TRUE);
                hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,false); 
                hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,false); 
                if(lcStatus.equalsIgnoreCase("D") )
                {
                    content.setRendered(Boolean.FALSE);
                    actionHist.setRendered(Boolean.FALSE);
                    // Added by Kanaga
                    InlandBtnBean.setRendered(Boolean.FALSE);
                    ImportBtnBean.setRendered(Boolean.FALSE);
                    OAMessageTextInputBean  LcValueE            = (OAMessageTextInputBean)webBean.findChildRecursive("LcValue");
                    OAMessageTextInputBean  TotalPoValueE       = (OAMessageTextInputBean)webBean.findChildRecursive("TotalPoValue");
                    OAMessageStyledTextBean  LcValueWordsE      = (OAMessageStyledTextBean)webBean.findChildRecursive("LcValueWords");
                    try 
                    {
                        lcVal   =   hdrVO.getCurrentRow().getAttribute("LcValue").toString();
                        System.out.println("Lc LcValue     --> "   +lcVal);
                        pageContext.writeDiagnostics(this , "Lc lcVal   --> "  +lcVal,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc lcVal     --> "   +e);
                    }
                    try 
                    {
                        totPoVal   =   hdrVO.getCurrentRow().getAttribute("TotalPoValue").toString();
                        System.out.println("Lc totPoVal     --> "   +totPoVal);
                        pageContext.writeDiagnostics(this , "Lc totPoVal   --> "  +totPoVal,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc totPoVal     --> "   +e);
                    }
                    try 
                    {
                        lcValWords   =   hdrVO.getCurrentRow().getAttribute("LcValueWords").toString();
                        System.out.println("Lc lcValWords     --> "   +lcValWords);
                        pageContext.writeDiagnostics(this , "Lc lcValWords   --> "  +lcValWords,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc lcValWords     --> "   +e);
                    }
                    LcValueE.setText(pageContext, lcVal);
                    TotalPoValueE.setText(pageContext, totPoVal);
                    LcValueWordsE.setText(pageContext, lcValWords);
                    try 
                    {
                        lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
                        System.out.println("Lc lcincoterm     --> "   +lcincoterm);
                        pageContext.writeDiagnostics(this , "Lc lcincoterm   --> "  +lcincoterm,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc lcincoterm     --> "   +e);
                    }
                    if(lcincoterm != null && lcincoterm !="") 
                    {
                        if(lcincoterm.equals("FOB")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("RenCif" ,false);
                            hdrVO.getCurrentRow().setAttribute("RenFob" ,true);
                        }
                        if(lcincoterm.equals("CIF")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("RenFob" ,false);
                            hdrVO.getCurrentRow().setAttribute("RenCif" ,true);
                        }
                    }
                    try 
                    {
                        piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
                        System.out.println("Lc piincoterm     --> "   +piincoterm);
                        pageContext.writeDiagnostics(this , "Lc piincoterm   --> "  +piincoterm,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc piincoterm     --> "   +e);
                    }
                    if(piincoterm != null && piincoterm !="") 
                    {
                        if(piincoterm.equals("FOB")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                            hdrVO.getCurrentRow().setAttribute("PIRenFob" ,true);
                        }
                        if(piincoterm.equals("CIF")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false); 
                            hdrVO.getCurrentRow().setAttribute("PIRenCif" ,true);
                        }
                    }
                     // Added by kanaga
                }
                hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "EditableRN");
            }
            else if(lcStatus.equalsIgnoreCase("RBCH"))
            {
                System.out.println("Inside Status --> " + lcStatus);
                String lcincoterm   =   null    ,   piincoterm  =   null;
                RtnBtnBean.setRendered(Boolean.FALSE);
                ApprBtnBean.setRendered(Boolean.FALSE);
                TreasuryE.setDisabled(Boolean.TRUE);
                ReportBtnBean.setRendered(Boolean.FALSE);
                content.setRendered(Boolean.TRUE);
                actionHist.setRendered(Boolean.TRUE);
                    // Added by Kanaga
                InlandBtnBean.setRendered(Boolean.FALSE);
                ImportBtnBean.setRendered(Boolean.FALSE);
                hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,false); 
                hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,false); 
                    try 
                    {
                        lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
                        System.out.println("Lc lcincoterm     --> "   +lcincoterm);
                        pageContext.writeDiagnostics(this , "Lc lcincoterm   --> "  +lcincoterm,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc lcincoterm     --> "   +e);
                    }
                    if(lcincoterm != null && lcincoterm !="") 
                    {
                        if(lcincoterm.equals("FOB")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("RenCif" ,false); 
                        }
                        if(lcincoterm.equals("CIF")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("RenFob" ,false); 
                        }
                    }
                    try 
                    {
                        piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
                        System.out.println("Lc piincoterm     --> "   +piincoterm);
                        pageContext.writeDiagnostics(this , "Lc piincoterm   --> "  +piincoterm,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc piincoterm     --> "   +e);
                    }
                    
                    if(piincoterm != null && piincoterm !="") 
                    {
                        if(piincoterm.equals("FOB")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                        }
                        if(piincoterm.equals("CIF")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false); 
                        }
                    }
                     // Added by kanaga
                hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "EditableRN");
            }
            else if(lcStatus.equalsIgnoreCase("RBF")||lcStatus.equalsIgnoreCase("RBTR") || lcStatus.equalsIgnoreCase("RBSP")||lcStatus.equalsIgnoreCase("RBB") ||lcStatus.equalsIgnoreCase("RBAPCH"))
            {
                System.out.println("Inside Status --> " + lcStatus);
                String lcincoterm   =   null    ,   piincoterm  =   null;
                RtnBtnBean.setRendered(Boolean.FALSE);
                ApprBtnBean.setRendered(Boolean.FALSE);
                TreasuryE.setDisabled(Boolean.TRUE);
                ReportBtnBean.setRendered(Boolean.FALSE);
                content.setRendered(Boolean.TRUE);
                actionHist.setRendered(Boolean.TRUE);
            // Added by Kanaga
                InlandBtnBean.setRendered(Boolean.FALSE);
                ImportBtnBean.setRendered(Boolean.FALSE);
                hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,false); 
                hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,false); 
                    try 
                    {
                        lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
                        System.out.println("Lc lcincoterm     --> "   +lcincoterm);
                        pageContext.writeDiagnostics(this , "Lc lcincoterm   --> "  +lcincoterm,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc lcincoterm     --> "   +e);
                    }
                    if(lcincoterm != null && lcincoterm !="") 
                    {
                        if(lcincoterm.equals("FOB")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("RenCif" ,false); 
                        }
                        if(lcincoterm.equals("CIF")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("RenFob" ,false); 
                        }
                    }
                    try 
                    {
                        piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
                        System.out.println("Lc piincoterm     --> "   +piincoterm);
                        pageContext.writeDiagnostics(this , "Lc piincoterm   --> "  +piincoterm,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc piincoterm     --> "   +e);
                    }
                    
                    if(piincoterm != null && piincoterm !="") 
                    {
                        if(piincoterm.equals("FOB")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                            
                        }
                        
                        if(piincoterm.equals("CIF")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false); 
                        }
                    }
                     // Added by kanaga
                hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "EditableRN");
            }
            else if(reqName.equalsIgnoreCase(pageContext.getUserName().toString()) && respname.equals("LC Requestor Responsibility") && 
                    (   lcStatus.equalsIgnoreCase("PWAPCH") || 
                        lcStatus.equalsIgnoreCase("PWB")    || 
                        lcStatus.equalsIgnoreCase("PWTR")   || 
                        lcStatus.equalsIgnoreCase("PWF")    || 
             /*           lcStatus.equalsIgnoreCase("PWCH")   || */
                        lcStatus.equalsIgnoreCase("PWSP")   || 
                        lcStatus.equalsIgnoreCase("AC"))
                    )
            {
                System.out.println("Inside Status --> " + lcStatus);   
                String lcincoterm   =   null    ,   piincoterm  =   null;
                submitBean.setRendered(Boolean.FALSE);
                DraftBtnBean.setRendered(Boolean.FALSE);
                SupplierActionNE.setDisabled(Boolean.TRUE);
                APCommHeadNE.setDisabled(Boolean.TRUE);                    // AB Jenish
                BuyerNE.setDisabled(Boolean.TRUE);                         // AB Jenish
//                PIVoucherNoNE.setRendered(Boolean.TRUE);                   // AB Jenish
                CommHeadNE.setDisabled(Boolean.TRUE);
                TreasuryNE.setDisabled(Boolean.TRUE);
                RtnBtnBean.setRendered(Boolean.FALSE);
                FinanceNE.setDisabled(Boolean.TRUE);
                ApprBtnBean.setRendered(Boolean.FALSE);
                hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "NonEditableRN");
                try 
                {
                    lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
                    System.out.println("Lc lcincoterm     --> "   +lcincoterm);
                    pageContext.writeDiagnostics(this , "Lc lcincoterm   --> "  +lcincoterm,4);
                }
                catch (Exception e) 
                {
                    System.out.println("Lc lcincoterm     --> "   +e);
                }
                if(lcincoterm != null && lcincoterm !="") 
                {
                    if(lcincoterm.equals("FOB")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("RenCif" ,false); 
                    }
                    if(lcincoterm.equals("CIF")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("RenFob" ,false); 
                    }
                }
                try 
                {
                    piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
                    System.out.println("Lc piincoterm     --> "   +piincoterm);
                    pageContext.writeDiagnostics(this , "Lc piincoterm   --> "  +piincoterm,4);
                }
                catch (Exception e) 
                {
                    System.out.println("Lc piincoterm     --> "   +e);
                }
                if(piincoterm != null && piincoterm !="") 
                {
                    if(piincoterm.equals("FOB")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                        
                    }
                    if(piincoterm.equals("CIF")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false); 
                    }
                }
                cu.setViewOnlyRecursive(pageContext, webBean);
            }
// below code Added by Jenish to  ensure that the Comment section for Buyer & AP commercial
            else if(lcStatus.equalsIgnoreCase("PWB")) //  PWCH
                {
                    System.out.println("Inside Status --> " + lcStatus);
                    System.out.println("Inside PWB     --> ");
                    String lcincoterm   =   null    ,   piincoterm  =   null;
                    BuyerNE.setRequired("true");
//                    PIVoucherNoNE.setRendered(Boolean.TRUE);
//                    piVoucherNoCER.setRendered(Boolean.FALSE);
                    CommHeadNE.setDisabled(Boolean.TRUE);                       // AB Jenish
                    APCommHeadNE.setDisabled(Boolean.TRUE);                      // AB Jenish
                    submitBean.setRendered(Boolean.FALSE);
                    DraftBtnBean.setRendered(Boolean.FALSE);
                    SupplierActionNE.setDisabled(Boolean.TRUE);
                    TreasuryNE.setDisabled(Boolean.TRUE);
                    hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "NonEditableRN");
                    //Added by Kanaga// 
                    hdrVO.getCurrentRow().setAttribute("SetRen" , false);
                    FinanceNE.setDisabled(Boolean.TRUE);
                    InlandBtnBean.setRendered(Boolean.FALSE);
                    ImportBtnBean.setRendered(Boolean.FALSE);
                    ReportBtnBean.setRendered(Boolean.TRUE);
                    try 
                    {
                        lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
                        System.out.println("Lc lcincoterm     --> "   +lcincoterm);
                        pageContext.writeDiagnostics(this , "Lc lcincoterm   --> "  +lcincoterm,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc lcincoterm     --> "   +e);
                    }
                    if(lcincoterm != null && lcincoterm !="") 
                    {
                        if(lcincoterm.equals("FOB")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("RenCif" ,false); 
                        }
                        if(lcincoterm.equals("CIF")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("RenFob" ,false); 
                        }
                    }
                    try 
                    {
                        piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
                        System.out.println("Lc piincoterm     --> "   +piincoterm);
                        pageContext.writeDiagnostics(this , "Lc piincoterm   --> "  +piincoterm,4);
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Lc piincoterm     --> "   +e);
                    }
                    if(piincoterm != null && piincoterm !="") 
                    {
                        if(piincoterm.equals("FOB")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                            
                        }
                        if(piincoterm.equals("CIF")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false); 
                        }
                    }
                }   
            else if(lcStatus.equalsIgnoreCase("PWAPCH")) //  PWCH
                {
                    System.out.println("Inside PWAPCH     --> ");
                    writeLog(pageContext , "Inside Lc Status     -->"+lcStatus, 4   );
                    String lcincoterm   =   null    ,   piincoterm  =   null;
                    APCommHeadNE.setRequired("true");
//                    PIVoucherNoNE.setRequired("true");
//                    PIVoucherNoNE.setRendered(Boolean.TRUE);
//                    piVoucherNoCER.setRendered(Boolean.TRUE);
//                    piVoucherNoCER.setRequired("true");
                    CommHeadNE.setDisabled(Boolean.TRUE);                 // AB Jenish
                    BuyerNE.setDisabled(Boolean.TRUE);                      // AB Jenish
                    submitBean.setRendered(Boolean.FALSE);
                    DraftBtnBean.setRendered(Boolean.FALSE);
                    SupplierActionNE.setDisabled(Boolean.TRUE);
                    TreasuryNE.setDisabled(Boolean.TRUE);
                    hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "NonEditableRN");
                    //Added by Kanaga// 
                    hdrVO.getCurrentRow().setAttribute("SetRen" , false);
                    FinanceNE.setDisabled(Boolean.TRUE);
                    InlandBtnBean.setRendered(Boolean.FALSE);
                    ImportBtnBean.setRendered(Boolean.FALSE);
                    ReportBtnBean.setRendered(Boolean.TRUE);
                    try 
                    {
                        lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
                        writeLog(pageContext , "Lc lcincoterm   --> "  +lcincoterm,4);
                    }
                    catch (Exception e) 
                    {
                        writeLog(pageContext , "Lc lcincoterm   --> "  +e ,4);
                    }
                    if(lcincoterm != null && lcincoterm !="") 
                    {
                        if(lcincoterm.equals("FOB")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("RenCif" ,false); 
                        }
                        if(lcincoterm.equals("CIF")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("RenFob" ,false); 
                        }
                    }
                    try 
                    {
                        piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
                        writeLog(pageContext , "Lc piincoterm   --> "  +piincoterm,4);
                    }
                    catch (Exception e) 
                    {
                        writeLog(pageContext , "Lc piincoterm   --> "  +e ,4);
                    }
                    if(piincoterm != null && piincoterm !="") 
                    {
                        if(piincoterm.equals("FOB")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                            
                        }
                        if(piincoterm.equals("CIF")) 
                        {
                            hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false); 
                        }
                    }
                    // Added by kanaga//
                }
// above code Added by Jenish to make sure that the Comment section for Buyer & AP commercial head           
            else if(lcStatus.equalsIgnoreCase("PWTR"))
            {
                System.out.println("Inside Status --> " + lcStatus);
                System.out.println("Inside PWTR--> ");
                String lcincoterm   =   null    ,   piincoterm  =   null;
                TreasuryNE.setRequired("true");
//                piVoucherNoCER.setRendered(Boolean.FALSE);
//                PIVoucherNoNE.setDisabled(Boolean.TRUE); 
                submitBean.setRendered(Boolean.FALSE);
                DraftBtnBean.setRendered(Boolean.FALSE);                        
                hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "EditableRN"); 
                FinanceNE.setDisabled(Boolean.TRUE);
                // Added by Kanaga //
                InlandBtnBean.setRendered(Boolean.FALSE);
                ImportBtnBean.setRendered(Boolean.FALSE);
                ReportBtnBean.setRendered(Boolean.TRUE);
                try 
                {
                    lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
                    System.out.println("Lc lcincoterm     --> "   +lcincoterm);
                    pageContext.writeDiagnostics(this , "Lc lcincoterm   --> "  +lcincoterm,4);
                }
                catch (Exception e) 
                {
                    System.out.println("Lc lcincoterm     --> "   +e);
                }
                hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,false); 
                hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,false);
                if(lcincoterm != null && lcincoterm !="") 
                {
                    if(lcincoterm.equals("FOB")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("RenCif" ,false);
                        hdrVO.getCurrentRow().setAttribute("RenFob" ,true);
                    }
                    if(lcincoterm.equals("CIF")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("RenFob" ,false); 
                        hdrVO.getCurrentRow().setAttribute("RenCif" ,true);
                    }
                }
                try 
                {
                    piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
                    System.out.println("Lc piincoterm     --> "   +piincoterm);
                    pageContext.writeDiagnostics(this , "Lc piincoterm   --> "  +piincoterm,4);
                }
                catch (Exception e) 
                {
                    System.out.println("Lc piincoterm     --> "   +e);
                }
                if(piincoterm != null && piincoterm !="") 
                {
                    if(piincoterm.equals("FOB")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                        hdrVO.getCurrentRow().setAttribute("PIRenFob" ,true); 
                    }
                    if(piincoterm.equals("CIF")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false);
                        hdrVO.getCurrentRow().setAttribute("PIRenCif" ,true);
                    }
                }
                // Added by Kanaga //
            }
            else if(lcStatus.equalsIgnoreCase("PWCH"))
            {
                System.out.println("Inside Status --> " + lcStatus);
                System.out.println("Inside PWCH     --> ");
                String lcincoterm   =   null    ,   piincoterm  =   null;
                CommHeadNE.setRequired("true");
                APCommHeadNE.setDisabled(Boolean.TRUE);                 // AB Jenish
                BuyerNE.setDisabled(Boolean.TRUE);                      // AB Jenish
//                PIVoucherNoNE.setRendered(Boolean.TRUE);               // AB Jenish
//                piVoucherNoCER.setRendered(Boolean.FALSE);
                submitBean.setRendered(Boolean.FALSE);
                DraftBtnBean.setRendered(Boolean.FALSE);
                SupplierActionNE.setDisabled(Boolean.TRUE);
                TreasuryNE.setDisabled(Boolean.TRUE);
                hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "NonEditableRN");
                //Added by Kanaga// 
                hdrVO.getCurrentRow().setAttribute("SetRen" , false);
                FinanceNE.setDisabled(Boolean.TRUE);
                InlandBtnBean.setRendered(Boolean.FALSE);
                ImportBtnBean.setRendered(Boolean.FALSE);
                ReportBtnBean.setRendered(Boolean.TRUE);
                try 
                {
                    lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
                    System.out.println("Lc lcincoterm     --> "   +lcincoterm);
                    pageContext.writeDiagnostics(this , "Lc lcincoterm   --> "  +lcincoterm,4);
                }
                catch (Exception e) 
                {
                    System.out.println("Lc lcincoterm     --> "   +e);
                }
                if(lcincoterm != null && lcincoterm !="") 
                {
                    if(lcincoterm.equals("FOB")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("RenCif" ,false); 
                    }
                    if(lcincoterm.equals("CIF")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("RenFob" ,false); 
                    }
                }
                try 
                {
                    piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
                    System.out.println("Lc piincoterm     --> "   +piincoterm);
                    pageContext.writeDiagnostics(this , "Lc piincoterm   --> "  +piincoterm,4);
                }
                catch (Exception e) 
                {
                    System.out.println("Lc piincoterm     --> "   +e);
                }
                if(piincoterm != null && piincoterm !="") 
                {
                    if(piincoterm.equals("FOB")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                        
                    }
                    if(piincoterm.equals("CIF")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false); 
                    }
                }
                // Added by kanaga//
            }
             else if(lcStatus.equalsIgnoreCase("PWF"))
             {
                 System.out.println("Inside Status --> " + lcStatus);
                 System.out.println("Inside PWF--> ");
                 String lcincoterm   =   null    ,   piincoterm  =   null;
                 FinanceNE.setRequired("true");
//                 piVoucherNoCER.setRendered(Boolean.FALSE);
                 submitBean.setRendered(Boolean.FALSE);
                 DraftBtnBean.setRendered(Boolean.FALSE);
                 SupplierActionNE.setDisabled(Boolean.TRUE);
                 TreasuryNE.setDisabled(Boolean.TRUE);
                 CommHeadNE.setDisabled(Boolean.TRUE);
                 APCommHeadNE.setDisabled(Boolean.TRUE);                 // AB Jenish
                 BuyerNE.setDisabled(Boolean.TRUE);                      // AB Jenish
//                 PIVoucherNoNE.setRendered(Boolean.TRUE);
                 hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "NonEditableRN");
                 //Added by Kanaga// 
                 hdrVO.getCurrentRow().setAttribute("SetRen" , true);
                 InlandBtnBean.setRendered(Boolean.FALSE);
                 ImportBtnBean.setRendered(Boolean.FALSE);
                 ReportBtnBean.setRendered(Boolean.TRUE);
                 try 
                 {
                     lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
                     System.out.println("Lc lcincoterm     --> "   +lcincoterm);
                     pageContext.writeDiagnostics(this , "Lc lcincoterm   --> "  +lcincoterm,4);
                 }
                 catch (Exception e) 
                 {
                     System.out.println("Lc lcincoterm     --> "   +e);
                 }
                 if(lcincoterm != null && lcincoterm !="") 
                 {
                     if(lcincoterm.equals("FOB")) 
                     {
                         hdrVO.getCurrentRow().setAttribute("RenCif" ,false); 
                         hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,false); 
                         hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,true); 
                     }
                     if(lcincoterm.equals("CIF")) 
                     {
                         hdrVO.getCurrentRow().setAttribute("RenFob" ,false); 
                         hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,true); 
                         hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,false); 
                     }
                 }
                 try 
                 {
                     piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
                     System.out.println("Lc piincoterm     --> "   +piincoterm);
                     pageContext.writeDiagnostics(this , "Lc piincoterm   --> "  +piincoterm,4);
                 }
                 catch (Exception e) 
                 {
                     System.out.println("Lc piincoterm     --> "   +e);
                 }
                 if(piincoterm != null && piincoterm !="") 
                 {
                     if(piincoterm.equals("FOB")) 
                     {
                         hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false); 
                         hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,false); 
                         hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,true); 
                     }
                     if(piincoterm.equals("CIF")) 
                     {
                         hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false); 
                         hdrVO.getCurrentRow().setAttribute("FinanceDocumentCIFRend" ,true); 
                         hdrVO.getCurrentRow().setAttribute("FinanceDocumentFOBRend" ,false); 
                     }
                 }
                 // Added by kanaga//
             }
            else 
            {
                System.out.println("Inside Status --> " + lcStatus);
                System.out.println("Inside PWSP--> ");
                String lcincoterm   =   null    ,   piincoterm  =   null;
//                piVoucherNoCER.setRendered(Boolean.FALSE);
                submitBean.setRendered(Boolean.FALSE);
                DraftBtnBean.setRendered(Boolean.FALSE);
                TreasuryNE.setDisabled(Boolean.TRUE);
                CommHeadNE.setDisabled(Boolean.TRUE);
                APCommHeadNE.setDisabled(Boolean.TRUE);                 // AB Jenish
                BuyerNE.setDisabled(Boolean.TRUE);                      // AB Jenish
//                PIVoucherNoNE.setRendered(Boolean.TRUE);                // Ab Jenish
                FinanceNE.setDisabled(Boolean.TRUE);
                SupplierActionNE.setRequired("true");
                ReportBtnBean.setRendered(Boolean.TRUE);
                hdrVO.getCurrentRow().setAttribute("RnSwitcher" , "NonEditableRN");
                hdrVO.getCurrentRow().setAttribute("SetRen" , true);
                InlandBtnBean.setRendered(Boolean.FALSE);
                ImportBtnBean.setRendered(Boolean.FALSE);
                try 
                {
                    lcincoterm   =   hdrVO.getCurrentRow().getAttribute("Lcincoterm").toString();
                    System.out.println("Lc lcincoterm     --> "   +lcincoterm);
                    pageContext.writeDiagnostics(this , "Lc lcincoterm   --> "  +lcincoterm,4);
                }
                catch (Exception e) 
                {
                    System.out.println("Lc lcincoterm     --> "   +e);
                }
                if(lcincoterm != null && lcincoterm !="") 
                {
                    if(lcincoterm.equals("FOB")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("RenCif" ,false); 
                    }
                    if(lcincoterm.equals("CIF")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("RenFob" ,false); 
                    }
                }
                try 
                {
                    piincoterm   =   hdrVO.getCurrentRow().getAttribute("Piincoterm").toString();
                    System.out.println("Lc piincoterm     --> "   +piincoterm);
                    pageContext.writeDiagnostics(this , "Lc piincoterm   --> "  +piincoterm,4);
                }
                catch (Exception e) 
                {
                    System.out.println("Lc piincoterm     --> "   +e);
                }
                if(piincoterm != null && piincoterm !="") 
                {
                    if(piincoterm.equals("FOB")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                        
                    }
                    if(piincoterm.equals("CIF")) 
                    {
                        hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false); 
                    }
                }
            }
            poVO.setWhereClause(whereClause);
            poVO.executeQuery();
            Row row1=poVO.first();
            System.out.println(" LC_REQUEST ID  before lcDocumentVO execution --> "   +lcRequestId);
            lcDocumentVO.setWhereClause("LC_REQUEST_ID ="+lcRequestId);
            lcDocumentVO.executeQuery();
            lcDocumentVO.first();
            writeLog(pageContext , "before row rend",4);
            for(Row row = poVO.first();row != null;row = poVO.next())
            { 
                i = i+1;
                row.setAttribute("Sno",i); 
                row.setAttribute("PiVoucherRO", true);
                row.setAttribute("TdsReadOnly", true);
                row.setAttribute("PiVouchNoER", true);
                writeLog(pageContext , "inside Row PiVoucherRO rend true",4);
//                if (
//                    "Sify LC AP Commercial Responsibility".equals(respname) ||
//                    "Sify LC Treasury Responsibility".equals(respname)      &&
//                    "U".equals(pageContext.getParameter("PageAction"))      &&
//                    "A".equals(pageContext.getParameter("PageAction"))
//                    ) 
            writeLog(pageContext , "pageAction Before If cond   -->"+pageContext.getParameter("PageAction"),4);
                if  (
                        ("Sify LC AP Commercial Responsibility".equals(respname) ||"Sify LC Treasury Responsibility".equals(respname) || "Sify LC Finance Responsibility".equals(respname) )
                                    &&
                        ("U".equals(pageContext.getParameter("PageAction")))
                    )
                {
                    
                    writeLog(pageContext , "inside Row PiVoucherRO inside loop rend false",4);
                    writeLog(pageContext,"Resp Name In PI Vocher    --> "+respname,4);
                    if("Sify LC Treasury Responsibility".equals(respname))
                    {
                        writeLog(pageContext,"Inside Treasury   --> "+respname,4);
                        row.setAttribute("PiVouchNoER", false);
                       // row.setAttribute("TdsReadOnly", false);
                    }
                    //to make tds editable for Finance Responsibility
                    else if("Sify LC Finance Responsibility".equals(respname)) 
                    {
                        writeLog(pageContext,"Inside Finance   --> "+respname,4);
                        row.setAttribute("TdsReadOnly", false);
                    }
                    else
                    {
                        writeLog(pageContext,"LC AP Commercial  --> "+respname,4);
                        row.setAttribute("PiVoucherRO", false);
                        row.setAttribute("TdsReadOnly", true);
                    }
                } 
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
      writeLog(pageContext, "Inside PFR",4);
      LCMultiAMImpl       am                =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
      OAViewObject        hdrVO             =   am.getLCRequestHdrEVO();
      OAViewObject        poVO              =   am.getLCPoDetailsEVO();
//      OAViewObject        periodOfPresentVO =   am.getPeriodOfPresentVO();
      OAViewObject        lcInlandVO        =   am.getLCInlandReqEVO();
      OAViewObject        lcImportVO        =   am.getLCImportReqEVO();
      OAViewObject        lcAppHistVO       =   am.getLCApprHistoryEVO();
      OAViewObject        lcDocumentVO      =   am.getLCDocDetailsEVO();
//      OAViewObject        poDetVO           =   am.getPoDetailsVO();
//      OAViewObject        lcValVO           =   am.getLCValueVO();
      OAViewObject        cifVO             =   am.getCifCatVO();
      OAViewObject        fobVO             =   am.getFobCatVO();
      OAViewObject        transVO           =   am.getTransientVO();
//      OAViewObject        orgVO             =   am.getOrganizationVO();
//      OAViewObject        vendorSiteVO      =   am.getVendorSiteVO();
//      OAViewObject        vendorVO          =   am.getVendorVO();   
      OAViewObject        poLovVO           =   am.getPoDetailsVO(); 
      String              respname          =   String.valueOf(pageContext.getResponsibilityName());
      transVO.setWhereClause("1=1");
      transVO.executeQuery();
      transVO.first();
//      transVO.getCurrentRow().setAttribute("RenLcCond", false);
      String              event             =   null    , commHeadName  =   null    , treasuryName      =   null    ,   lcStatus    =   null    ,financeName  =   null,
                          CommHeadComm      =   null    , treasuryComm  =   null    , suppComm          =   null    ,   finComm     =   null    , stlBuyerName    =   null,
                          sisBuyerName      =   null    , apCommHead    =   null    , apCommHeadComm    =   null    ,   buyerComm   =   null    ;
      HashMap hm = new HashMap();
      event = pageContext.getParameter(EVENT_PARAM);
      String lovevent = pageContext.getLovInputSourceId();
      pageContext.writeDiagnostics(this, "event in PFR --> "+ event ,4);
      System.out.println("event in PFR --> "+ event);
      pageContext.writeDiagnostics(this, "lovevent in PFR --> "+ lovevent ,4);
      System.out.println("lovevent in PFR --> "+ lovevent);
      String lcRequestId    =   null    ,   poCurr          =   null    ,   lcClassification    =   null    ,   attachVal       =   null    , lcFobattachVal =   null;
      String lcCifattachVal =   null    ,   piFobattachVal  =   null    ,   piCifattachVal      =   null    ,   lcRepRequestId  =   null    ;
      int i=0;
      //Added by Jenish to make the docs required based on the Incoterm  selection
      OAMessageChoiceBean    requiredCifDocs     =   (OAMessageChoiceBean)webBean.findChildRecursive("DocsRequiredCIf");
      OAMessageChoiceBean    requiredFobDocs     =   (OAMessageChoiceBean)webBean.findChildRecursive("DocsRequiredFob");
//      if( "LC Requestor Responsibility".equalsIgnoreCase(respname)) 
//      {
//          poVO.getCurrentRow().setAttribute("PiVouchNoER", true);
//      }  
//      else  
//      { 
//          poVO.getCurrentRow().setAttribute("PiVouchNoER", true);
//      }
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
      writeLog(pageContext , "lcStatus --> "+ lcStatus ,4);
      try 
      {
          lcRepRequestId     =   hdrVO.getCurrentRow().getAttribute("LcRequestId").toString();
      }
      catch (Exception e) 
      {
          System.out.println("LC Report Request Id Exc --> " + e);  
      }
      writeLog(pageContext , "lcRepRequestId --> "+ lcRepRequestId ,4);
      try 
      {
          stlBuyerName  =   cc.executeSQLquery(am , "select lookup_code from XXSIFY_LC_APPROVER_V where tag=1");
      }
      catch (Exception e) 
      {
          writeLog(pageContext , "stlBuyerName  exe  --> "   +   e ,4);
      }
      writeLog(pageContext , "stlBuyerName   --> "   +   stlBuyerName ,4);
      try 
      {
          sisBuyerName  =   cc.executeSQLquery(am , "select lookup_code from XXSIFY_LC_APPROVER_V where tag=2");
      }
      catch (Exception e) 
      {
          writeLog(pageContext , "sisBuyerName  exe  --> "   +   e ,4);
      }
      writeLog(pageContext , "sisBuyerName   --> "   +   sisBuyerName ,4);
      try 
      {
          apCommHead  =   cc.executeSQLquery(am , "select lookup_code from XXSIFY_LC_APPROVER_V where tag=3");
      }
      catch (Exception e) 
      {
          writeLog(pageContext , "apCommHead  exe  --> "   +   e ,4);
      }
      writeLog(pageContext , "apCommHead   --> "   +   apCommHead ,4);
//      try 
//      {
//          commHeadName  =   cc.executeSQLquery(am , "select lookup_code from XXSIFY_LC_APPROVER_V where tag=3");
//      }
//      catch (Exception e) 
//      {
//          writeLog(pageContext , "commHeadName  exe  --> "   +   e ,4);
//      }
//      writeLog(pageContext , "commHeadName   --> "   +   commHeadName ,4);
      try 
      {
          treasuryName  =   cc.executeSQLquery(am , "select lookup_code from XXSIFY_LC_APPROVER_V where tag=5");
      }
      catch (Exception e) 
      {
          writeLog(pageContext , "treasuryName  exe  --> "   +   e ,4);
      }
      writeLog(pageContext , "treasuryName   --> "   +   treasuryName ,4);
      // added by Kanaga for finance //
       try 
       {
           financeName  =   cc.executeSQLquery(am , "select lookup_code from XXSIFY_LC_APPROVER_V where tag=4");
       }
       catch (Exception e) 
       {
           writeLog(pageContext , "financeName  exe  --> "   +   e ,4);
       }
      writeLog(pageContext , "financeName   --> "   +   financeName ,4);
      // added by Kanaga for finance //
      if(event  !=  null  &&  event !=  "")
      {
          writeLog(pageContext ,"FV Po Header ID     --> "+pageContext.getParameter("PoHeaderIdFV"),4);
          writeLog(pageContext ,"event Not Null in PFR --> "+ event , 4);
          if(event.equals("draft"))
          {
              writeLog(pageContext , "Inside event draft in PFR ",4 );
              System.out.println("LC Expiry Date in Save Event"+hdrVO.getCurrentRow().getAttribute("LcExpiryDate"));
              writeLog(pageContext , "LC Expiry Date in Save Event"+hdrVO.getCurrentRow().getAttribute("LcExpiryDate"), 4);
              String lcClass =   null;
              if(lcRequestId !=   null    &&  lcRequestId !=  "")
              {
                 System.out.println("LC Request Id available , so no need to generate new id"); 
              }
              else
              {
                  System.out.println("LC Request Id not available , so we need to generate new id"); 
                  lcRequestId =   am.getOADBTransaction().getSequenceValue("xxsify_lc_request_hdr_s").toString(); 
                  writeLog(pageContext , "before lcRequestId---->"+lcRequestId, 4);
                  hdrVO.getCurrentRow().setAttribute("LcRequestId" , lcRequestId);  
                  writeLog(pageContext , "lcRequestId---->"+lcRequestId, 4);
                  //lcDocumentVO.getCurrentRow().setAttribute("LcRequestId",lcRequestId);
                  // Added by Kanaga    //
                  try 
                  {
                      lcClass = hdrVO.getCurrentRow().getAttribute("LcClassification").toString();
                      System.out.println("lcClass--->"+ lcClass);
                  }
                  catch (Exception e) 
                  {
                      System.out.println("lcClass--->"+ e);
                  }
                  try
                  {
                      if(lcClass.equals("Inland"))
                      {
                      lcInlandVO.getCurrentRow().setAttribute("LcRequestId",lcRequestId);
                      lcInlandVO.getCurrentRow().setAttribute("Attribute11",pageContext.getParameter("AppliBankName1"));
                      lcInlandVO.getCurrentRow().setAttribute("Attribute12",pageContext.getParameter("BankChrg"));
                      lcInlandVO.getCurrentRow().setAttribute("Attribute13",pageContext.getParameter("PeriodOfPres"));
                      lcInlandVO.getCurrentRow().setAttribute("Attribute14",pageContext.getParameter("ConfirmInstr"));
                      }
                      else 
                      {
                          lcImportVO.getCurrentRow().setAttribute("LcRequestId",lcRequestId);  
                          lcImportVO.getCurrentRow().setAttribute("Attribute11",pageContext.getParameter("AppliBankName1"));
                          lcImportVO.getCurrentRow().setAttribute("Attribute12",pageContext.getParameter("BankChrg"));
                          lcImportVO.getCurrentRow().setAttribute("Attribute13",pageContext.getParameter("PeriodOfPres"));
                          lcImportVO.getCurrentRow().setAttribute("Attribute14",pageContext.getParameter("ConfirmInstr"));
                      }
                  }
                  catch (Exception e)
                  {
                      System.out.println("Set Bank Details Exc --> " + e);
                  }
                  // Added by Kanaga   //
              }
              writeLog(pageContext , "Inside draft fetched row count of Po details-->"+poVO.getFetchedRowCount(),4);
              if (poVO.getFetchedRowCount()>0)
              {
                  writeLog(pageContext , "Inside Po details Insertion--> " +poVO.getFetchedRowCount(),4);
                  for(Row row = poVO.first();row != null;row = poVO.next())
                  {
                       row.setAttribute("LcRequestId" , lcRequestId);
                  }
              }
              if(lcDocumentVO.getFetchedRowCount()>0) 
              {
                  pageContext.writeDiagnostics(this, "Inside Doc details Insertion-->",4);
                  System.out.println("Inside Doc details Insertion--> " +poVO.getFetchedRowCount());
                  for(Row row1 = lcDocumentVO.first();row1 != null;row1 = lcDocumentVO.next())
                  {
                       row1.setAttribute("LcRequestId" , lcRequestId);
                  }
              }
              hdrVO.getCurrentRow().setAttribute("LcStatus" , "D");
              System.out.println("Before Commit");
              preValidation(pageContext ,  webBean); 
              System.out.println("LC Expiry Date in Save Event before commit"+hdrVO.getCurrentRow().getAttribute("LcExpiryDate"));
              cc.commit(am);
              System.out.println("LC Expiry Date in Save Event after commit"+hdrVO.getCurrentRow().getAttribute("LcExpiryDate"));
              System.out.println("After Commit");
              hm.put("MsgMode" , "Drafted");
              hm.put("lcRequestId" , lcRequestId);
//              hm.put("PageMode",lcClass.toUpperCase());
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
                writeLog(pageContext,"Inside Submit" , 4 );
                String lcClassifyValue  =   null  ,   lcIncoterms =   null    ,   piIncoterms =   null;
                try 
                {
                  lcClassifyValue         =   hdrVO.getCurrentRow().getAttribute("LcClassification").toString();
                }
                catch (Exception e) 
                {
                  writeLog(pageContext,"lcClassifyValue Exc --> " + e, 4 );
                }
                writeLog(pageContext,"lcClassifyValue --> " + lcClassifyValue, 4 );
                int fetchedRowCount     =   0;
                String getAttachValQuery ="select decode(count(lc_request_id),0,'N','Y')  from XXSIFY_LC_MULTI_PO_ATTACH_V where lc_request_id = '"+lcRequestId+"'";
                writeLog(pageContext,"getAttachValQuery   --> "   +   getAttachValQuery , 4   );
                attachVal =cc.executeSQLquery(am,getAttachValQuery);
              // Added by Kanaga for LC,PI Attachment //
               try 
               {
               lcIncoterms =   pageContext.getParameter("LcIncoterms").toString();
               writeLog(pageContext ,"lcIncoterms-->"+lcIncoterms, 4);
               }
               catch (Exception e) 
               {
               System.out.println("lcIncoterms Exc --> " + e);    
               }
               if(lcIncoterms.equals("FOB")) 
               {
                   String getLcFobQuery ="select decode(count(lc_request_id),6,'Y','N')  from xxsify_lc_fob_attach_v where lc_request_id = '"+lcRequestId+"'";
                   System.out.println("getLcFobQuery   --> "   +   getLcFobQuery);
                   lcFobattachVal =cc.executeSQLquery(am,getLcFobQuery);
//      Commented by Jenish on 19-06-24 to make attachment optional                     
//                   if( "N".equals(lcFobattachVal) ) 
//                   {
//                        System.out.println("Inside LcFobattachVal ");
//                       pageContext.writeDiagnostics(this,  "Inside LcFobattachVal "  ,4);
//                       throw new OAException("Attachment is Missing, Kindly add the attachment in LC Conditions FOB Inco Terms!",OAException.ERROR);
//                   }
               }
               else if(lcIncoterms.equals("CIF")) 
               {
                   String getLcCifQuery ="select decode(count(lc_request_id),7,'Y','N')  from xxsify_lc_cif_attach_v where lc_request_id = '"+lcRequestId+"'";
                   System.out.println("getLcCifQuery   --> "   +   getLcCifQuery);
                   lcCifattachVal =cc.executeSQLquery(am,getLcCifQuery);
//      Commented by Jenish on 19-06-24 to make attachment optional             
//                   if( "N".equals(lcCifattachVal) ) 
//                   {
//                       System.out.println("Inside LcCifattachVal ");
//                       pageContext.writeDiagnostics(this,  "Inside LcCifattachVal "  ,4);
//                       throw new OAException("Attachment is Missing, Kindly add the attachment in LC Conditions CIF Inco Terms!",OAException.ERROR);
//                   }
               }
              try 
              {
                  piIncoterms =   pageContext.getParameter("Piincoterm").toString();
                  writeLog(pageContext,"piIncoterms --> " + piIncoterms     ,4);
              }
              catch (Exception e) 
              {
                 System.out.println("lcIncoterms Exc --> " + e);    
              }
              if(piIncoterms.equals("FOB")) 
              {
                  String getPiFobQuery ="select xxsify_lc_multi_po_wf_pkg.validateFOBAttach('" + lcRequestId + "') from dual";
                  System.out.println("getPiFobQuery   --> "   +   getPiFobQuery);
                  piFobattachVal =cc.executeSQLquery(am,getPiFobQuery);
                  writeLog(pageContext,"piFobattachVal --> " + piFobattachVal     ,4);
//  Commented by Jenish on 19-06-24 to make attachment optional
//                  if( "N".equals(piFobattachVal) ) 
//                  {
//                      System.out.println("Inside piFobattachVal ");
//                      pageContext.writeDiagnostics(this,  "Inside piFobattachVal "  ,4);
//                      throw new OAException("Attachment is Missing, Kindly add the attachment in PI Details FOB Inco Terms!",OAException.ERROR);
//                  }
              }
              else if(piIncoterms.equals("CIF")) 
              {
                  String getPiCifQuery ="select xxsify_lc_multi_po_wf_pkg.validateCIFAttach('" + lcRequestId + "') from dual";
                  System.out.println("getPiCifQuery   --> "   +   getPiCifQuery);
                  piCifattachVal =cc.executeSQLquery(am,getPiCifQuery);
                  writeLog(pageContext,"piCifattachVal --> " + piCifattachVal     ,4);
//  Commented by Jenish on 19-06-24 to make attachment optional                  
//                  if( "N".equals(piCifattachVal) ) 
//                  {
//                      System.out.println("Inside piCifattachVal ");
//                      pageContext.writeDiagnostics(this,  "Inside piCifattachVal "  ,4);
//                      throw new OAException("Attachment is Missing, Kindly add the attachment in PI Details CIF Inco Terms!",OAException.ERROR);
//                  }
              }
               // Added by Kanaga for LC,PI Attachment //
                  
                  preValidation(pageContext ,  webBean);
                  if(lcClassifyValue.equals("Inland"))
                  {
                      writeLog(pageContext, "Inside lcClassifyValue  = Inland ", 4);
                      cc.executeQuery(lcInlandVO , "lc_request_id = " + lcRequestId);
                      fetchedRowCount     =  lcInlandVO.getFetchedRowCount();  
                  }
                  else
                  {
                      writeLog(pageContext, "Inside lcClassifyValue  != Inland ", 4);
                      cc.executeQuery(lcImportVO , "lc_request_id = " + lcRequestId);
                      fetchedRowCount     =  lcImportVO.getFetchedRowCount(); 
                  }
                 if( "N".equals(attachVal) )
                  {
                      writeLog(pageContext,"Inside attachVal --> "      ,4);
                      throw new OAException("Attachment is mandatory, Kindly add the attachment!",OAException.ERROR);   
                  }
                  else if(fetchedRowCount> 0)
                  {     
                      writeLog(pageContext,"Inside (fetchedRowCount> 0) --> "      ,4);
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
                      writeLog(pageContext,"Inside (fetchedRowCount> 0) else cond--> "      ,4);
                      throw new OAException(lcClassifyValue.toUpperCase()+ " doesn't have data for this request no: "+ lcRequestId + ".Kindly fill the data to proceed", OAException.ERROR);
                  }
          }
         if(event.equals("accept"))
         {
             writeLog(pageContext   , "Inside event accept in PFR" ,4 );  
             if ("Sify LC Treasury Responsibility".equals(pageContext.getResponsibilityName()))
             {
                writeLog(pageContext,"inside treasury accept "+pageContext.getResponsibilityName(),4);   
                 String lcClassifyValue  =   null  ,   lcIncoterms =   null    ,   piIncoterms =   null;
                 try 
                 {
                   lcClassifyValue         =   hdrVO.getCurrentRow().getAttribute("LcClassification").toString();
                 }
                 catch (Exception e) 
                 {
                   writeLog(pageContext,"lcClassifyValue Exc --> " + e, 4 );
                 }
                 writeLog(pageContext,"lcClassifyValue --> " + lcClassifyValue, 4 );
                 int fetchedRowCount     =   0;
                 String getAttachValQuery ="select decode(count(lc_request_id),0,'N','Y')  from XXSIFY_LC_MULTI_PO_ATTACH_V where lc_request_id = '"+lcRequestId+"'";
                 writeLog(pageContext,"getAttachValQuery   --> "   +   getAttachValQuery , 4   );
                 attachVal =cc.executeSQLquery(am,getAttachValQuery);
                 // Added by Kanaga for LC,PI Attachment //
                 try
                 {
                 lcIncoterms =   pageContext.getParameter("LcIncoterms").toString();
                 writeLog(pageContext ,"lcIncoterms-->"+lcIncoterms, 4);
                 }
                 catch (Exception e)
                 {
                 System.out.println("lcIncoterms Exc --> " + e);
                 }
                 if(lcIncoterms.equals("FOB"))
                 {
                    String getLcFobQuery ="select decode(count(lc_request_id),6,'Y','N')  from xxsify_lc_fob_attach_v where lc_request_id = '"+lcRequestId+"'";
                    System.out.println("getLcFobQuery   --> "   +   getLcFobQuery);
                    lcFobattachVal =cc.executeSQLquery(am,getLcFobQuery);
                 //      Commented by Jenish on 19-06-24 to make attachment optional
                 //                   if( "N".equals(lcFobattachVal) )
                 //                   {
                 //                        System.out.println("Inside LcFobattachVal ");
                 //                       pageContext.writeDiagnostics(this,  "Inside LcFobattachVal "  ,4);
                 //                       throw new OAException("Attachment is Missing, Kindly add the attachment in LC Conditions FOB Inco Terms!",OAException.ERROR);
                 //                   }
                 }
                 else if(lcIncoterms.equals("CIF"))
                 {
                    String getLcCifQuery ="select decode(count(lc_request_id),7,'Y','N')  from xxsify_lc_cif_attach_v where lc_request_id = '"+lcRequestId+"'";
                    System.out.println("getLcCifQuery   --> "   +   getLcCifQuery);
                    lcCifattachVal =cc.executeSQLquery(am,getLcCifQuery);
                 //      Commented by Jenish on 19-06-24 to make attachment optional
                 //                   if( "N".equals(lcCifattachVal) )
                 //                   {
                 //                       System.out.println("Inside LcCifattachVal ");
                 //                       pageContext.writeDiagnostics(this,  "Inside LcCifattachVal "  ,4);
                 //                       throw new OAException("Attachment is Missing, Kindly add the attachment in LC Conditions CIF Inco Terms!",OAException.ERROR);
                 //                   }
                 }
                 try
                 {
                   piIncoterms =   pageContext.getParameter("Piincoterm").toString();
                   writeLog(pageContext,"piIncoterms --> " + piIncoterms     ,4);
                 }
                 catch (Exception e)
                 {
                  System.out.println("lcIncoterms Exc --> " + e);    
                 }
                 if(piIncoterms.equals("FOB"))
                 {
                   String getPiFobQuery ="select xxsify_lc_multi_po_wf_pkg.validateFOBAttach('" + lcRequestId + "') from dual";
                   System.out.println("getPiFobQuery   --> "   +   getPiFobQuery);
                   piFobattachVal =cc.executeSQLquery(am,getPiFobQuery);
                   writeLog(pageContext,"piFobattachVal --> " + piFobattachVal     ,4);
                 //  Commented by Jenish on 19-06-24 to make attachment optional
                 //                  if( "N".equals(piFobattachVal) )
                 //                  {
                 //                      System.out.println("Inside piFobattachVal ");
                 //                      pageContext.writeDiagnostics(this,  "Inside piFobattachVal "  ,4);
                 //                      throw new OAException("Attachment is Missing, Kindly add the attachment in PI Details FOB Inco Terms!",OAException.ERROR);
                 //                  }
                 }
                 else if(piIncoterms.equals("CIF"))
                 {
                   String getPiCifQuery ="select xxsify_lc_multi_po_wf_pkg.validateCIFAttach('" + lcRequestId + "') from dual";
                   System.out.println("getPiCifQuery   --> "   +   getPiCifQuery);
                   piCifattachVal =cc.executeSQLquery(am,getPiCifQuery);
                   writeLog(pageContext,"piCifattachVal --> " + piCifattachVal     ,4);
                 //  Commented by Jenish on 19-06-24 to make attachment optional
                 //                  if( "N".equals(piCifattachVal) )
                 //                  {
                 //                      System.out.println("Inside piCifattachVal ");
                 //                      pageContext.writeDiagnostics(this,  "Inside piCifattachVal "  ,4);
                 //                      throw new OAException("Attachment is Missing, Kindly add the attachment in PI Details CIF Inco Terms!",OAException.ERROR);
                 //                  }
                 }
                 // Added by Kanaga for LC,PI Attachment //
                   
                   preValidation(pageContext ,  webBean);
                   if(lcClassifyValue.equals("Inland"))
                   {
                       writeLog(pageContext, "Inside lcClassifyValue  = Inland ", 4);
                       cc.executeQuery(lcInlandVO , "lc_request_id = " + lcRequestId);
                       fetchedRowCount     =  lcInlandVO.getFetchedRowCount();  
                   }
                   else
                   {
                       writeLog(pageContext, "Inside lcClassifyValue  != Inland ", 4);
                       cc.executeQuery(lcImportVO , "lc_request_id = " + lcRequestId);
                       fetchedRowCount     =  lcImportVO.getFetchedRowCount(); 
                   }
                  if( "N".equals(attachVal) )
                   {
                       writeLog(pageContext,"Inside attachVal --> "      ,4);
                       throw new OAException("Attachment is mandatory, Kindly add the attachment!",OAException.ERROR);   
                   }
//                   else
//                   {
//                       writeLog(pageContext,"Inside (fetchedRowCount> 0) else cond--> "      ,4);
//                       throw new OAException(lcClassifyValue.toUpperCase()+ " doesn't have data for this request no: "+ lcRequestId + ".Kindly fill the data to proceed", OAException.ERROR);
//                   }
             }
             try 
             {
                 buyerComm     =  hdrVO.getCurrentRow().getAttribute("Attribute16").toString();  
                 
             }
             catch (Exception e) 
             {
                 System.out.println("LC Request Id Exc --> " + e);  
             }
             try 
             {
                 apCommHeadComm     =  hdrVO.getCurrentRow().getAttribute("Attribute17").toString();  
                 
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
             
             try 
             {
                 finComm     =   hdrVO.getCurrentRow().getAttribute("FinanceComments").toString();
             }
             catch (Exception e) 
             {
                 System.out.println("finComm Exc --> " + e);  
             }
             writeLog(pageContext,"BuyerComments --> "   +buyerComm        ,4);
             writeLog(pageContext,"apCommHeadComm --> "  +apCommHeadComm   ,4);
//             writeLog(pageContext,"CommHeadComm --> "  +CommHeadComm        ,4);
             writeLog(pageContext,"treasuryComm --> "  +treasuryComm        ,4);
             writeLog(pageContext,"suppComm     --> "  +suppComm            ,4);
             writeLog(pageContext,"finComm      --> "  +finComm             ,4);
             
             cc.createRow(am , lcAppHistVO , "Yes" , "xxsify_lc_wf_appr_s" , "LcWfHistId");
             lcAppHistVO.getCurrentRow().setAttribute("LcMode" , "Multi");
             lcAppHistVO.getCurrentRow().setAttribute("LcRequestId" , lcRequestId);
             lcAppHistVO.getCurrentRow().setAttribute("Response" , "Accepted");
             lcAppHistVO.getCurrentRow().setAttribute("ApproverId" , pageContext.getUserId());
             lcAppHistVO.getCurrentRow().setAttribute("ApproverName" ,pageContext.getUserName() );
             lcAppHistVO.getCurrentRow().setAttribute("WfItemKey" , lcRequestId);
             lcAppHistVO.getCurrentRow().setAttribute("ActionDate" , pageContext.getCurrentDBDate());
             lcAppHistVO.getCurrentRow().setAttribute("SubmissionDate" , hdrVO.getCurrentRow().getAttribute("CreationDate"));
             String revNo = null;
             try
             {
                 revNo = cc.executeSQLquery(am , "select xxsify_lc_multi_po_wf_pkg.get_version_no("+lcRequestId+") from dual");
             }
             catch (Exception e) 
             {
                 System.out.println("revNo Exc --> " + e);
             }
             System.out.println("Rev No --> " + revNo);
             lcAppHistVO.getCurrentRow().setAttribute("Attribute9"   , revNo);
             if(lcStatus.equals("PWB")) 
             {
                 hdrVO.getCurrentRow().setAttribute("LcStatus" , "PWAPCH");
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,buyerComm );
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Buyer");
             }
             else if(lcStatus.equals("PWAPCH")) 
             {
                 hdrVO.getCurrentRow().setAttribute("LcStatus" , "PWF");                        // Approval from AP commercial to Finance Heaad 
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,apCommHeadComm );
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "AP Commercial Head");
             }
//             else if(lcStatus.equals("PWCH")) 
//             {
//                 hdrVO.getCurrentRow().setAttribute("LcStatus" , "PWF");
//                 lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,CommHeadComm );
//                 lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Commercial Head");
//             }
             else if(lcStatus.equals("PWTR")) 
             {
                 hdrVO.getCurrentRow().setAttribute("LcStatus" , "PWSP");
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,treasuryComm );
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Treasury");
             }
             else if(lcStatus.equals("PWF")) 
             {
                 hdrVO.getCurrentRow().setAttribute("LcStatus" , "PWTR");
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,finComm );
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Finance Head");
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
             writeLog(pageContext   , "Inside event return in PFR" ,4 );
                        
             try 
             {
                 buyerComm      =  hdrVO.getCurrentRow().getAttribute("Attribute16").toString();
             }
             catch (Exception e) 
             {
                 System.out.println("LC Request Id Exc --> " + e);  
             }
             try 
             {
                 apCommHeadComm   =  hdrVO.getCurrentRow().getAttribute("Attribute17").toString();
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
             try 
             {
                 finComm     =   hdrVO.getCurrentRow().getAttribute("FinanceComments").toString();
             }
             catch (Exception e) 
             {
                 System.out.println("FinanceComments Exc --> " + e);  
             }
             writeLog(pageContext , "BuyerComments    --> "   +buyerComm,  4);
             writeLog(pageContext , "apCommHeadComm   --> "   +apCommHeadComm,  4);
//             writeLog(pageContext , "CommHeadComm   --> "   +CommHeadComm,  4);
             writeLog(pageContext , "treasuryComm     --> "   +treasuryComm,  4);
             writeLog(pageContext , "suppComm         --> "   +suppComm,      4);
             writeLog(pageContext , "finComm          --> "   +finComm,       4);
             cc.createRow(am , lcAppHistVO , "Yes" , "xxsify_lc_wf_appr_s" , "LcWfHistId");
             lcAppHistVO.getCurrentRow().setAttribute("LcMode" , "Multi");
             lcAppHistVO.getCurrentRow().setAttribute("LcRequestId" , lcRequestId);
             lcAppHistVO.getCurrentRow().setAttribute("Response" , "Returned");
             lcAppHistVO.getCurrentRow().setAttribute("ApproverId" , pageContext.getUserId());
             lcAppHistVO.getCurrentRow().setAttribute("ApproverName" ,pageContext.getUserName() );
             lcAppHistVO.getCurrentRow().setAttribute("WfItemKey" , lcRequestId);
             lcAppHistVO.getCurrentRow().setAttribute("ActionDate" , pageContext.getCurrentDBDate());
             lcAppHistVO.getCurrentRow().setAttribute("SubmissionDate" , hdrVO.getCurrentRow().getAttribute("CreationDate"));       
             String revNo = null;
            try
            {
                revNo = cc.executeSQLquery(am , "select xxsify_lc_multi_po_wf_pkg.get_version_no("+lcRequestId+") from dual");
            }
            catch (Exception e) 
            {
                writeLog(pageContext ,"revNo Exc --> " + e ,4);
            }
            writeLog(pageContext , "Rev No --> " + revNo , 4);
            lcAppHistVO.getCurrentRow().setAttribute("Attribute9"   , revNo);
             // added by  jenish  to add buyer & Ap Commercial WF
             if(lcStatus.equals("PWB")) 
             {
                 hdrVO.getCurrentRow().setAttribute("LcStatus" , "RBB");
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,buyerComm );
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , " Buyer");
             }
             if(lcStatus.equals("PWAPCH")) 
             {
                 hdrVO.getCurrentRow().setAttribute("LcStatus" , "RBAPCH");
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,apCommHeadComm );
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "AP Commercial Head");
             }
//             if(lcStatus.equals("PWCH")) 
//             {
//                 hdrVO.getCurrentRow().setAttribute("LcStatus" , "RBCH");
//                 lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,CommHeadComm );
//                 lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Commercial Head");
//             }
             if(lcStatus.equals("PWF")) 
             {
                 hdrVO.getCurrentRow().setAttribute("LcStatus" , "RBF");
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,finComm );
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "finance");
             }
             if(lcStatus.equals("PWTR")) 
             {
                 hdrVO.getCurrentRow().setAttribute("LcStatus" , "RBTR");
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverComments" ,treasuryComm );
                 lcAppHistVO.getCurrentRow().setAttribute("ApproverRole" , "Treasury");
             }
             if(lcStatus.equals("PWSP")) 
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
        if((event.equals("lovPrepare") || event.equals("lovValidate")) && lovevent.equals("PoNumLov"))
        {
            System.out.println("Inside lovPrepare || lovValidate");
            String vendorIdFv   = null;
            String vendorId     = null;
            try 
            {
                vendorIdFv = hdrVO.getCurrentRow().getAttribute("VendorId").toString();
            }
            catch (Exception e) 
            {
                writeLog(pageContext   , "Inside vendorIdFv Exe"+e ,4 );
            }                
            writeLog(pageContext   , "vendorIdFv is "+vendorIdFv ,4 );
            try 
            {
                vendorId  =   cc.executeSQLquery(am , "select distinct VENDOR_ID from xxsify_pos_supplier_users_v where  user_id= "+pageContext.getUserId());
            }
            catch (Exception e) 
            {
                writeLog(pageContext ,"vendorId  exe  --> "   +   e ,4);
            }
            writeLog(pageContext,"vendorId      -->"+   vendorId        ,   4);
            poLovVO.setWhereClause("vendor_id ='" + vendorId + "'");
            poLovVO.executeQuery();
            poLovVO.first();
            writeLog(pageContext , "poLovVO Query   --> "  +poLovVO.getQuery()    , 4);
            writeLog(pageContext , "poLovVO RC      --> "  +poLovVO.getRowCount()          , 4);
            writeLog(pageContext , "poLovVO FRC     --> "  +poLovVO.getFetchedRowCount()   , 4);
            if ("U".equals(pageContext.getParameter("PageAction")) )
            {
                System.out.println("Inside lovPrepare & lovValidate if U");  
                transVO.getCurrentRow().setAttribute("RenLcCond", true);
            }
            else  if (("C".equals(pageContext.getParameter("PageAction"))) || ("Z".equals(pageContext.getParameter("PageAction"))))
            {
                System.out.println("Inside lovPrepare & lovValidate if C || Z ");
                transVO.getCurrentRow().setAttribute("RenLcCond", false);
            } 
            else 
            {
                System.out.println("Inside lovPrepare & lovValidate Else");
                transVO.getCurrentRow().setAttribute("RenLcCond", true);
            }
        }
        if(event.equals("addRow")) 
        {     
            writeLog(pageContext   , "Inside event addRow in PFR" ,4 );
            if ("U".equals(pageContext.getParameter("PageAction")) )
            {
                System.out.println("Inside addRow if U");
                transVO.getCurrentRow().setAttribute("RenLcCond", true);
            }
            else  if (("C".equals(pageContext.getParameter("PageAction")))||("Z".equals(pageContext.getParameter("PageAction"))))
            {   
                System.out.println("rowcount in add row"+transVO.getRowCount());
                System.out.println("Inside addRow if C or Z");
//                if (1<transVO.getRowCount())
//                {
//                    transVO.getCurrentRow().setAttribute("RenLcCond", true);   
//                }
//                else
//                {
                    transVO.getCurrentRow().setAttribute("RenLcCond", false);   
//                }
            } 
            else 
            {
                System.out.println("Inside addRow else ");
                transVO.getCurrentRow().setAttribute("RenLcCond", true);
            }
            cc.createRow(am , poVO , "Yes" ,"xxsify_lc_po_details_s" , "LcPoDetId"); 
            writeLog(pageContext   , "After Inside event addRow in PFR" ,4 );
            poVO.getCurrentRow().setAttribute("Attribute9",hdrVO.getCurrentRow().getAttribute("Attribute9"));
            for(Row row = poVO.first();row != null;row = poVO.next())
            {
                i   =   i+1;
                row.setAttribute("Sno",i);
                row.setAttribute("PiVouchNoER", true);
                row.setAttribute("TdsReadOnly", true);
                writeLog(pageContext , "inside Row PiVouchNoER rend true",4);
                if ("Sify LC AP Commercial Responsibility".equals(respname)||"Sify LC Treasury Responsibility".equals(respname)) 
                {
                    writeLog(pageContext , "inside Row PiVouchNoER inside loop rend false",4);
                    row.setAttribute("PiVouchNoER", false);
                }
            }
        }
//         if(event.equals("lstdt"))
//         {
//             System.out.println("Inside event lstdt in PI Details");
//             pageContext.writeDiagnostics(this, "Inside event lstdt in PI Details ",4);  
//             String lstdt  =   pageContext.getParameter("LastDateOfShip");
//             if(lstdt !=  null    &&  lstdt    !=  "")
//             {
//                  String lstDtOfShip    =   cc.executeSQLquery(am , "select to_date('" + lstdt + "','DD-MM-RRRR') from dual");
//                 System.out.println("Last Date of Shipment --> " + lstDtOfShip);
//                 if(lstDtOfShip !=  null    &&  lstDtOfShip    !=  "")
//                 {
//                     hdrVO.getCurrentRow().setAttribute("Attribute15", lstDtOfShip);
//                 }
//             }
//         }
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
//         if(event.equals("calculateLCExpiryDate"))
//         {
//             System.out.println("Inside Calculate LC Expiry Date");
//             String  lastShipmentDate  = null    ,   periodOfPresentation    =   null , lcExpiryDate = null;
//             cc.executeQuery(periodOfPresentVO , "1=1");
//             try
//             {
//                 lastShipmentDate    =   hdrVO.getCurrentRow().getAttribute("LcLastDateOfShipment").toString();
//             }
//             catch (Exception e)
//             {
//                 System.out.println("Last Shipment Date Exc --> " + e);
//             }            
//             try
//             {
//                 periodOfPresentation    =   periodOfPresentVO.getCurrentRow().getAttribute("LookupCode").toString();
//             }
//             catch (Exception e)
//             {
//                 System.out.println("Period Of Presentation Exc --> " + e);
//             }
//             System.out.println("Last Shipment Date      --> " + lastShipmentDate);
//             System.out.println("Period Of Presentation  --> " + periodOfPresentation);
//             
//             if(lastShipmentDate !=  null    &&  lastShipmentDate    !=  "")
//             {
//                 lcExpiryDate    =   cc.executeSQLquery(am , "select to_date('" + lastShipmentDate + "','RRRR-MM-DD') + " + periodOfPresentation + " from dual");
//                 System.out.println("LC Expiry Date --> " + lcExpiryDate);
//                 if(lcExpiryDate !=  null    &&  lcExpiryDate    !=  "")
//                 {
//                     hdrVO.getCurrentRow().setAttribute("LcExpiryDate", lcExpiryDate);
//                 }
//             }
//         }  
         if ("delete".equals(event)) 
         {
             writeLog(pageContext   , "Inside event delete in PFR" ,4 );
             String rowRef = pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
             LCPoDetailsEVORowImpl curRow = (LCPoDetailsEVORowImpl)am.findRowByRef(rowRef);
             
             /**
             int pv   =   0   ,   totPOValue  =   0;
             int  ev=0,   entValue    =   0    ;
             String lcCurrencyValue    =   null    ,   lcValueWord   =   null;
             
//             Serializable[] params = { rowRef };
             
             System.out.println("After Commit");
             try 
             {
                 lcCurrencyValue =   curRow.getAttribute("PoCurrency").toString();
                 System.out.println("LC Currency Value  --> " + lcCurrencyValue); 
             }
             catch (Exception e) 
             {
                System.out.println("LC Currency Value Exc --> " + e);    
             }
             try 
             {
                 entValue   =  Integer.parseInt(curRow.getAttribute("PoEnteredValue").toString());                
             }
             catch (Exception e) 
             {
                 entValue = 0;
                 System.out.println("entValue Exc --> " + e); 
             }
             int enterValu  =   0;
             try 
             {
                 enterValu = Integer.parseInt(pageContext.getSessionValue("enteredValue1").toString());
             }
             catch (Exception e) 
             {
                 System.out.println("entervalu Exc --> " + e);
             }
             int d  =   enterValu    -   entValue;
             try 
             {
                 pv =  Integer.parseInt(curRow.getAttribute("PoValue").toString());          
             }
             catch (Exception e) 
             {
                 pv =   0;
                 System.out.println("pv Exc --> " + e); 
             }
             int totPoVal   = 0;
             try 
             {
                 totPOValue =  Integer.parseInt(pageContext.getSessionValue("totalPOVal").toString());
             }
             catch (Exception e)
             {
                 System.out.println("totPOValue Exc --> " + e);
             }
             totPOValue =   totPoVal    -  pv;
             System.out.println("Tot PO Value --> " + totPOValue);
            if(lcCurrencyValue!=null && lcCurrencyValue!="") 
            {
                lcValueWord    =   cc.executeSQLquery(am , "select xxsify_lc_curr_amt_words('"+d+"','"+lcCurrencyValue+"') from dual");
                System.out.println("LC value to word --> " + lcValueWord);                  
            }
//             lcValVO.getCurrentRow().setAttribute("TotalPoValue", totPOValue);
             hdrVO.getCurrentRow().setAttribute("TotalPoValue", totPOValue);
//             lcValVO.getCurrentRow().setAttribute("LcValue" ,d);
             hdrVO.getCurrentRow().setAttribute("LcValue" ,d);
//             lcValVO.getCurrentRow().setAttribute("LcValueWords" , lcValueWord.toUpperCase());
             try 
             {
                 hdrVO.getCurrentRow().setAttribute("LcValueWords" , lcValueWord.toUpperCase());
             }
             catch (Exception e) 
             {
                 System.out.println("Curr Set in Delete Exc --> " + e);
             }
//             am.invokeMethod("removeLines", params);
            **/
             curRow.remove();
             POEvent(am , pageContext , poVO , hdrVO);
             throw new OAException("Selected row deleted successfully", OAException.CONFIRMATION);
         }
         if ("cancel".equals(event)) 
         {
             writeLog(pageContext   , "Inside event cancel in PFR" ,4 );
             if(AmendCancelAction != null && AmendCancelAction!= "")
             {
               if(AmendCancelAction.equals("A"))
               {
                   AmendCancelAction = null;
                   callAmendmentRevokeActivities(am , lcRequestId);
               }
             }
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
             writeLog(pageContext   , "Inside event poValCal in PFR" ,4 );
             for(Row row = poVO.first();row != null;row = poVO.next())
             {
                 if(poVO.getFetchedRowCount()>0)
                 {
                     writeLog(pageContext   , "getFetchedRowCount----> "+poVO.getFetchedRowCount() ,4 );
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
                 else 
                 {
                  hdrVO.getCurrentRow().setAttribute("TotalPoValue", ""); 
                 }
             }
//             lcValVO.getCurrentRow().setAttribute("TotalPoValue", poValue);
             hdrVO.getCurrentRow().setAttribute("TotalPoValue" ,poValue);
             System.out.println("poValue-->"+poValue);
             pageContext.writeDiagnostics(this, "poValue-->"+poValue,4);
             writeLog(pageContext   , "poValue-->"+poValue,4);
         }
         // Added by Kanaga //
          if(event.equals("entvalue"))
          {
              writeLog(pageContext   , "Inside event entVal in PFR" ,4 );
              
              Double  // ev          =   0   ,   
                    enterVal    =   0.0   , 
                    remVal      =   0.0   ;
//              float  // ev          =   0   ,   
//                    enterVal    =   0.00f   , 
//                    remVal      =   0.00f   ;
              String lcCurrencyValue    =   null    ,   lcValWord   =   null  ,   po  =   null;
              String rowRef = pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
              LCPoDetailsEVORowImpl currentRow = (LCPoDetailsEVORowImpl)am.findRowByRef(rowRef);
              try 
              {
                  enterVal    =   Double.parseDouble(currentRow.getAttribute("PoEnteredValue").toString());
//                enterVal = Float.parseFloat(currentRow.getAttribute("PoEnteredValue").toString());
              }
              catch (Exception e) 
              {
                  System.out.println("enterVal --> " + e);  
              }
              try 
              {
                  remVal    =   Double.parseDouble(currentRow.getAttribute("PoRemainingValue").toString());
//                  remVal    =   Float.parseFloat(currentRow.getAttribute("PoRemainingValue").toString());

              }
              catch (Exception e) 
              {
                  System.out.println("enterVal --> " + e);  
              }
              System.out.println("enterVal --> " + enterVal);  
              System.out.println("remVal --> " + remVal);  
             if(enterVal <= remVal && enterVal > 0 && remVal >= 0)
             {
                writeLog(pageContext, "Inside If cond enterVal <= remVal && enterVal > 0 && remVal >= 0 ", 4);
                  try 
                  {
                      po    =   currentRow.getAttribute("PoNumber").toString();
                  }
                  catch (Exception e) 
                  {
                      System.out.println("po --> " + e);  
                  }
                  if(po!=null && po!="")
                  {
                        System.out.println("EV new --> " + enterVal);
                        enteredValue     =   enteredValue + enterVal ;
                        System.out.println("enteredValue new  --> " + enteredValue);
                        pageContext.putSessionValue("enteredValue1",enteredValue);
                        try 
                        {
                            lcCurrencyValue =   hdrVO.getCurrentRow().getAttribute("LcCurrencyCode").toString();
                        }
                        catch (Exception e) 
                        {
                            System.out.println("LC Currency Value Exc --> " + e);    
                        }
                        System.out.println("LC value to word --> " + lcValWord);
                  }
                  else 
                  {
                      throw new OAException("Select PO Number to Proceed", OAException.ERROR);  
                  }
             }
             else 
             {
                 throw new OAException("Entered Value Should be less than Remaining Value", OAException.ERROR); 
             }
             POEvent(am , pageContext , poVO , hdrVO);
          }
         if("invoice".equals(event))
         {
             System.out.println("Inside Invoice event --> " );
             String po        =     null;   
             String rowRef = pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
             LCPoDetailsEVORowImpl currRow = (LCPoDetailsEVORowImpl)am.findRowByRef(rowRef);
             try 
             {
                 po =   currRow.getAttribute("PoNumber").toString();
             }
             catch (Exception e) 
             {
                 System.out.println("po Exc --> " + e); 
             }
             
             if(po  !=  null && po  != "")   
             {
                 System.out.println("po Exc --> " + po);
             }
             else 
             {
                 throw new OAException("Select PO Number to Proceed", OAException.ERROR);
             }
         }
         if(event.equals("invdate"))
         {
             System.out.println("Inside invdate event --> " );
             String po        =     null;   
             String rowRef = pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
             LCPoDetailsEVORowImpl curntRow = (LCPoDetailsEVORowImpl)am.findRowByRef(rowRef);
             try 
             {
                 po =   curntRow.getAttribute("PoNumber").toString();
             }
             catch (Exception e) 
             {
                 System.out.println("po Exc --> " + e);
             }
             if(po  !=  null && po  != "")   
             {
                 System.out.println("po Exc --> " + po);
             }
             else 
             {
                 throw new OAException("Select PO Number to Proceed", OAException.ERROR);
             }
         }
         if(event.equals("comments"))
         {
             System.out.println("Inside invdate event --> " );
             String po        =     null;   
             String rowRef = pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
             LCPoDetailsEVORowImpl currentRow = (LCPoDetailsEVORowImpl)am.findRowByRef(rowRef);
             try 
             {
                 po =   currentRow.getAttribute("PoNumber").toString();
             }
             catch (Exception e) 
             {
                 System.out.println("po Exc --> " + e);
             }
             if(po  !=  null && po  != "")   
             {
                 System.out.println("po Exc --> " + po);
             }
             else 
             {
                 throw new OAException("Select PO Number to Proceed", OAException.ERROR);
             }
         }
         if(event.equals("lcincoterms")) 
         {
             System.out.println("Inside LC Condition incoterms"); 
             String lcIncoterms =   null;
             if ("Z".equals(pageContext.getParameter("PageAction")))
             {
                System.out.println("Inside LC PageAction Z"); 
                try 
                {
                    lcIncoterms =   pageContext.getParameter("LcIncoterms").toString();
                    System.out.println("lcIncoterms-->"+lcIncoterms);
                    pageContext.writeDiagnostics(this, "lcIncoterms-->"+lcIncoterms,4);
                }
                catch (Exception e) 
                {
                    System.out.println("lcIncoterms Exc --> " + e);    
                }
                
                if(lcIncoterms.equals("FOB")) 
                {
                     hdrVO.getCurrentRow().setAttribute("RenFob" ,false);
                     hdrVO.getCurrentRow().setAttribute("RenCif" ,false);
                }
                else if(lcIncoterms.equals("CIF")) 
                {
                     hdrVO.getCurrentRow().setAttribute("RenCif" ,false);
                     hdrVO.getCurrentRow().setAttribute("RenFob" ,false);
                }
             } 
             else if ("C".equals(pageContext.getParameter("PageAction")))
             {
                 System.out.println("Inside LC PageAction C"); 
                 try 
                 {
                     lcIncoterms =   pageContext.getParameter("LcIncoterms").toString();
                     System.out.println("lcIncoterms-->"+lcIncoterms);
                     pageContext.writeDiagnostics(this, "lcIncoterms-->"+lcIncoterms,4);
                 }
                 catch (Exception e) 
                 {
                    System.out.println("lcIncoterms Exc --> " + e);    
                 }
                 
                 if(lcIncoterms.equals("FOB")) 
                 {
                     hdrVO.getCurrentRow().setAttribute("RenFob" ,false);
                     hdrVO.getCurrentRow().setAttribute("RenCif" ,false);
                 }
                 else if(lcIncoterms.equals("CIF")) 
                 {
                     hdrVO.getCurrentRow().setAttribute("RenCif" ,false);
                     hdrVO.getCurrentRow().setAttribute("RenFob" ,false);
                 }
             } 
             else 
             {
                 System.out.println("Inside LC PageAction otherthan C and Z");
                 try 
                 {
                     lcIncoterms =   pageContext.getParameter("LcIncoterms").toString();
                     System.out.println("lcIncoterms-->"+lcIncoterms);
                     pageContext.writeDiagnostics(this, "lcIncoterms-->"+lcIncoterms,4);
                 }
                 catch (Exception e) 
                 {
                    System.out.println("lcIncoterms Exc --> " + e);    
                 }
                 if(lcIncoterms.equals("FOB")) 
                 {
                     hdrVO.getCurrentRow().setAttribute("RenFob" ,true);
                     hdrVO.getCurrentRow().setAttribute("RenCif" ,false);
                 }
                 else if(lcIncoterms.equals("CIF")) 
                 {
                     hdrVO.getCurrentRow().setAttribute("RenCif" ,true);
                     hdrVO.getCurrentRow().setAttribute("RenFob" ,false);
                 }   
             }
         }
         if(event.equals("piincoterm")) 
         {
             System.out.println("Inside PI Condition incoterms"); 
             String piIncoterms =   null;
             if ("Z".equals(pageContext.getParameter("PageAction")))
             {
             System.out.println("Inside LC PageAction Z"); 
             try 
             {
             piIncoterms =   pageContext.getParameter("Piincoterm").toString();
             System.out.println("piIncoterms-->"+piIncoterms);
             pageContext.writeDiagnostics(this, "piIncoterms-->"+piIncoterms,4);
             }
             catch (Exception e) 
             {
             System.out.println("lcIncoterms Exc --> " + e);    
             }
             if(piIncoterms.equals("FOB")) 
             {
                 hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false);
                 hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
             }
             else if(piIncoterms.equals("CIF")) 
             {
                 hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                 hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false);
             }
             }
                else if ("C".equals(pageContext.getParameter("PageAction")))
                 {
                 System.out.println("Inside LC PageAction C"); 
                 try 
                 {
                 piIncoterms =   pageContext.getParameter("Piincoterm").toString();
                 System.out.println("piIncoterms-->"+piIncoterms);
                 pageContext.writeDiagnostics(this, "piIncoterms-->"+piIncoterms,4);
                 }
                 catch (Exception e) 
                 {
                 System.out.println("lcIncoterms Exc --> " + e);    
                 }
                 if(piIncoterms.equals("FOB")) 
                 {
                     hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false);
                     hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                 }
                 else if(piIncoterms.equals("CIF")) 
                 {
                     hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
                     hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false);
                 }
             }
             else
              {
              System.out.println("Inside LC PageAction other than C and Z"); 
              try 
              {
              piIncoterms =   pageContext.getParameter("Piincoterm").toString();
              writeLog(pageContext,"piIncoterms      -->  "  +piIncoterms   ,4);
              }
              catch (Exception e) 
              {
              System.out.println("lcIncoterms Exc --> " + e);    
              }
              if(piIncoterms.equals("FOB")) 
              {
                  hdrVO.getCurrentRow().setAttribute("PIRenFob" ,true);
                  hdrVO.getCurrentRow().setAttribute("PIRenCif" ,false);
              }
              else if(piIncoterms.equals("CIF")) 
              {
                  hdrVO.getCurrentRow().setAttribute("PIRenCif" ,true);
                  hdrVO.getCurrentRow().setAttribute("PIRenFob" ,false);
              }
             }
         }
         if(event.equals("addDocument"))
         {
           System.out.println("Inside Add Document");
           pageContext.writeDiagnostics(this, "Inside Add Document", 4);
//             if(lcRequestId !=   null    &&  lcRequestId !=  "")
//             {
//                System.out.println("LC Request Id available , so no need to generate new id"); 
//             }
//             else
//             {
//                 System.out.println("LC Request Id not available , so we need to generate new id"); 
//                 lcRequestId =   am.getOADBTransaction().getSequenceValue("xxsify_lc_request_hdr_s").toString(); 
//                 lcDocumentVO.getCurrentRow().setAttribute("LcRequestId" ,hdrVO.getCurrentRow().getAttribute(lcRequestId) );
//                                 
//             }
        System.out.println("LC Request Id --> " + lcRequestId);
        cc.createRow(am , lcDocumentVO , "Yes" , "xxsify_lc_doc_details_s" , "LcDocId");
        try 
        {
            writeLog(pageContext ,"inside LC req ID Set in Doc Add", 4);
            lcDocumentVO.getCurrentRow().setAttribute("LcRequestId",lcRequestId);
            lcDocumentVO.getCurrentRow().setAttribute("Attribute9",hdrVO.getCurrentRow().getAttribute("Attribute9"));
        }
        catch (Exception e) 
        {
            writeLog(pageContext ,"inside LC req ID Set in Doc Add Exc --> " + e, 4);
            lcDocumentVO.getCurrentRow().setAttribute("LcRequestId",hdrVO.getCurrentRow().getAttribute("LcRequestId"));
        }
        }
// added by Jenish to ensure FOB and CIF lists must be added automatically in document section based on Incoterm Selection
    if(event.equals("incoDoc"))
    {
         String                 incoTerms           =   pageContext.getParameter("LcIncoterms");
         if(incoTerms  !=  null  &&  incoTerms !=  "")
         {
               if("FOB".equalsIgnoreCase(incoTerms) ) 
               {
                   for(Row row = fobVO.first();row != null;row = fobVO.next()) 
                     { 
                        System.out.println("Inside CIF Incoterms");
                            if(!lcDocumentVO.isPreparedForExecution())
                            {
                                lcDocumentVO.setMaxFetchSize(0);
                                lcDocumentVO.executeQuery();
                            }
                        lcDocumentVO.last();
                        lcDocumentVO.next();
                        OARow   row2    =   (OARow)lcDocumentVO.createRow();
                        lcDocumentVO.insertRow(row2);
                        row2.setNewRowState(row2.STATUS_INITIALIZED);
                        String id1 = am.getOADBTransaction().getSequenceValue("xxsify_lc_doc_details_s").toString();
                        System.out.println("id-->"+id1);
                        lcDocumentVO.getCurrentRow().setAttribute("LcDocId",id1);
                        System.out.println("1");
                        String docCategory  = row.getAttribute("DocCategory").toString();
                        System.out.println("2");
                        row2.setAttribute("DocumentsRequired",docCategory);
                         for(Row row1 = lcDocumentVO.first();row1 != null;row1 = lcDocumentVO.next()) 
                         { 
                             String dr=row1.getAttribute("DocumentsRequired").toString();
                             if(dr!=null) 
                             {
                                 System.out.println(" ino -->"+dr);
                                 if (dr.contains("CIF")) 
                                 {
                                         row1.remove();
                                 }
                             }
                         }       
                     } 
               }    
               else 
               { 
                   for(Row row = cifVO.first();row != null;row = cifVO.next()) 
                     { 
                        System.out.println("Inside CIF Incoterms");
                        if(!lcDocumentVO.isPreparedForExecution())
                        {
                            lcDocumentVO.setMaxFetchSize(0);
                            lcDocumentVO.executeQuery();
                        }
                        lcDocumentVO.last();
                        lcDocumentVO.next();
                        OARow   row2    =   (OARow)lcDocumentVO.createRow();
                        lcDocumentVO.insertRow(row2);
                        row2.setNewRowState(row2.STATUS_INITIALIZED);
                        String id1 = am.getOADBTransaction().getSequenceValue("xxsify_lc_doc_details_s").toString();
                        System.out.println("id-->"+id1);
                        lcDocumentVO.getCurrentRow().setAttribute("LcDocId",id1);
                        System.out.println("1");
                        String docCategory  = row.getAttribute("DocCategory").toString();
                        System.out.println("2");
                        row2.setAttribute("DocumentsRequired",docCategory);
                         for(Row row1 = lcDocumentVO.first();row1 != null;row1 = lcDocumentVO.next()) 
                         { 
                             String dr=row1.getAttribute("DocumentsRequired").toString();
                             if(dr!=null) 
                             {
                                 System.out.println(" ino -->"+dr);
                                 if (dr.contains("FOB")) 
                                 {
                                         row1.remove();
                                 }
                             }
                         }  
                     }
               }
         }
         else 
         {
            for(Row row1 = lcDocumentVO.first();row1 != null;row1 = lcDocumentVO.next()) 
                 { 
                     row1.remove();
                 }  
           throw new OAException("Please Select the Inco Terms in LC Conditions",OAException.ERROR);
        }
    } 
        // added by Jenish to ensure FOB & CIF doc ...... ends
    if(event.equals("report")) 
    {
         writeLog(pageContext,"Inside report",4);
         String applnName = "SFC" , cpName = "XXSIFY_LC_REQ_MULTI_REP";
         pageContext.forwardImmediately("OA.jsp?akRegionCode=FNDCPPROGRAMPAGE&akRegionApplicationId=0&programApplName="+applnName+"&programName="+cpName+"&programRegion=Hide&scheduleRegion=Hide&notifyRegion=Hide&printRegion=Hide", null, (byte)0, null, null, true, "Y");
    }
         // Added by kanaga//
         
         /*===================================================== Po currency validation starts======================================================== */     
//        if
//            (
//                (
//                    event.equals("lovValidate") || event.equals("LovPrepare")
//                )   &&
//                lovevent.equals("PoNumLov")
//            )
//        {
//            writeLog(pageContext,"Inside LOV Update",4);
////            int     pv          =   0  ;
////            double  remVal      =   0    ,   b   =   0   ,   a   =   0;
////            String sumofLCVal   =   null;
//            writeLog(pageContext, "Inside LC PO Number",4);
            
//            String  lcCur        =   null    ,   tradeTerms   =   null,
//                    payTerms     =   null    ,   lcConExp     =   null,
//                    lastdtship   =   null    ,   vendorId      =   null ;
            
//            try 
//            {
//                 tradeTerms   =   pageContext.getParameter("TradeTerms");
//            }
//            catch (Exception e) 
//            {
//                System.out.println("TradeTerms Exc --> " + e); 
//            }
//             try 
//             {
//                 payTerms     =   pageContext.getParameter("PaymentTerms");
//             }
//             catch (Exception e) 
//             {
//                 System.out.println("PaymentTerms Exc --> " + e);
//             }
//             try 
//             {
//                 lcConExp     =   pageContext.getParameter("LcCountryExpiry");
//             }
//             catch (Exception e) 
//             {
//                 System.out.println("lcConExp Exc --> " + e);
//             }
//             try 
//             {
//                 lastdtship   =   pageContext.getParameter("LastDateOfShipment");
//             }
//             catch (Exception e) 
//             {
//                 System.out.println("lastdtship Exc --> " + e);
//             }
//            try 
//            {
//                vendorId  =   cc.executeSQLquery(am , "select distinct VENDOR_ID from xxsify_pos_supplier_users_v where  user_id= "+pageContext.getUserId());
//            }
//            catch (Exception e) 
//            {
//                writeLog(pageContext ,"vendorId  exe  --> "   +   e ,4);
//            }
//            
//            writeLog(pageContext,"vendorId      -->"+   vendorId        ,   4);
//            
//            writeLog(pageContext,"tradeTerms    -->"+   tradeTerms      ,   4);
//            writeLog(pageContext,"payTerms      -->"+   payTerms        ,   4);
//            writeLog(pageContext,"lcConExp      -->"+   lcConExp        ,   4);
//            writeLog(pageContext,"lastdtship    -->"+   lastdtship      ,   4);

//            poDetVO.setWhereClause("Lc_Trade_Terms = '"+tradeTerms+"' and Lc_Payment_Terms = '"+payTerms+"' and Lc_Country_Expire = '"+lcConExp+"' and Lc_Last_Date_Of_Shipment = '"+lastdtship+"'");
// added by jenish to change the po Logic            
//            poDetVO.setWhereClause("vendor_id ='" + vendorId + "'");
//            poDetVO.executeQuery();
//            poDetVO.first();
//            
//            writeLog(pageContext,"poDetVO Query     -->  "  +poDetVO.getQuery()             ,4);
//            writeLog(pageContext,"poDetVO RC        -->  "  +poDetVO.getRowCount()          ,4);
//            writeLog(pageContext,"poDetVO FRC       -->  "  +poDetVO.getFetchedRowCount()   ,4);

            
            /**
            try 
            {
                lcCur       =   hdrVO.getCurrentRow().getAttribute("LcCurrencyCode").toString();
            }
            catch (Exception e) 
            {
                lcCur  =   "";
            }
            
            System.out.println("lcCur   --> "   +   lcCur);
            pageContext.writeDiagnostics(this, "lcCur   --> "   +   lcCur   ,   4); 
            
            for(Row row = poVO.first();row != null;row = poVO.next())
            {
                if (lcCur  != null && lcCur  !="") 
                {
                    System.out.println("Inside lcCur not null");
                    pageContext.writeDiagnostics(this, "Inside lcCur not null",4); 
                } 
                else
                {
                    String newPONo    =   null;
                    
                    try
                    {
                        newPONo = row.getAttribute("PoNumber").toString();
                    }
                    catch (Exception e) 
                    {
                        System.out.println("newPONo Exc --> " + e);
                    }
                    System.out.println("newPONo --> " + newPONo);
                    pageContext.writeDiagnostics(this, "newPONo-->"+newPONo,4);
                    
                    try 
                    {
                        poCurr    =   cc.executeSQLquery(am , "select distinct currency_code from po_headers_all where segment1='"+ newPONo +"'");
                    }
                    catch (Exception e) 
                    {
                        System.out.println("poCurr Exc --> " + e);
                    }
                    
                    System.out.println("poCurr --> " + poCurr);
                    pageContext.writeDiagnostics(this, "poCurr-->"+poCurr,4); 
                    
                    if(poCurr != null && poCurr!= "")
                    {
                        System.out.println("PO Curr is not null");
                        if(poCurr.equals("INR"))
                        {
                            lcClassification    =   "Inland";
                        }
                        else
                        {
                            lcClassification    =   "Import";
                        }
                    }
                    
                    System.out.println("lcClassification --> " + lcClassification);
                    pageContext.writeDiagnostics(this, "lcClassification-->"+lcClassification,4); 
                    
                    hdrVO.getCurrentRow().setAttribute("LcCurrencyCode",poCurr);
                    hdrVO.getCurrentRow().setAttribute("LcClassification",lcClassification);
                    
                }
                
                // Added by Kanaga//
                
                // PO Value
                String newPOVal =  null;
                
                try 
                {
                    newPOVal = cc.executeSQLquery(am , "select sum(quantity*unit_price) po_value from po_headers_all pha ,po_lines_all   pll where pha.po_header_id  =   pll.po_header_id and pha.segment1='"+newPONo+"'").toString(); 
                }
                catch (Exception e) 
                {
                    System.out.println("newPOVal --> " + newPOVal);
                }
                
                System.out.println("newPOVal --> " + newPOVal);
                pageContext.writeDiagnostics(this, "newPOVal-->"+newPOVal,4);
                
                row.setAttribute("PoValue",newPOVal);
                
                // Total PO Value //
                
                if(newPOVal != null && newPOVal != "") 
                {
                    try 
                    {
                        totalPOValue           =   Integer.parseInt(row.getAttribute("PoValue").toString());
                    }
                    catch (Exception e) 
                    {
                        pv=0;
                    }
                    totalPOValue     =   totalPOValue + pv ;
                    pageContext.putSessionValue("totalPOVal",totalPOValue);
                    System.out.println("totalPOValue-->"+totalPOValue);
                    pageContext.writeDiagnostics(this, "totalPOValue-->"+totalPOValue,4);
                }
                // po Remaining Value
                String poHdrId =   row.getAttribute("PoHeaderId").toString();
                System.out.println("poHdrId-->"+poHdrId);
                
                String lcId   = null;
                
                try 
                {
                    lcId = cc.executeSQLquery(am ,"select Lc_Request_Id from xxsify_lc_po_details_t where po_header_id='"+poHdrId+"' ").toString();
                }
                catch (Exception e) 
                {
                    System.out.println("LC Id Exc --> " + e);
                }
                try
                {
                    sumofLCVal    =  cc.executeSQLquery(am , "select sum(lc_value) from xxsify_lc_request_hdr_t where lc_request_id='"+lcId+"'").toString();
                }
                catch (Exception e) 
                {
                    System.out.println("sumofLCVal Exception-->"+e);
                }
                
                System.out.println("lcId-->"+lcId);
                System.out.println("sumofLCVal-->"+sumofLCVal);
                
                a    =  Double.parseDouble(poVal);
                
                if(sumofLCVal !=null && sumofLCVal !="")
                {
                    b    =   Double.parseDouble(sumofLCVal);
                    remVal    =  a -  b;
                    row.setAttribute("PoRemainingValue", remVal);
                }
                else 
                {
                    row.setAttribute("PoRemainingValue", a);  
                }
            }
            hdrVO.getCurrentRow().setAttribute("TotalPoValue", totalPOValue);
        }
        **/
//       }
        /*===================================================== Po currency validation ends======================================================== */
     
         if(event.equals("lovUpdate") && lovevent.equals("PoNumLov"))
         {
            String vendorId = null;
            writeLog(pageContext , "PoNumLov and Update Event", 4);
            try 
            {
                vendorId  =   cc.executeSQLquery(am , "select distinct VENDOR_ID from xxsify_pos_supplier_users_v where  user_id= "+pageContext.getUserId());
            }
            catch (Exception e) 
            {
                writeLog(pageContext ,"vendorId  exe  --> "   +   e ,4);
            }
            writeLog(pageContext , "vendorId  is  --> "+vendorId, 4);
            writeLog(pageContext , "poVO.getFetchedRowCount() -->" + poVO.getFetchedRowCount(), 4);
            POEvent(am , pageContext , poVO , hdrVO);
        //added by Jenish 
             if ("U".equals(pageContext.getParameter("PageAction")) )
             {
                 System.out.println("Inside lovUpdate poNumLov if cond U");
                 transVO.getCurrentRow().setAttribute("RenLcCond", true);
             }
             else  if (("C".equals(pageContext.getParameter("PageAction")))||("Z".equals(pageContext.getParameter("PageAction"))))
             {
                 System.out.println("Inside lovUpdate poNumLov  if C or Z");

                     transVO.getCurrentRow().setAttribute("RenLcCond", true);   
             } 
             else 
             {
                 System.out.println("Inside lovUpdate poNumLov  else ");
                 transVO.getCurrentRow().setAttribute("RenLcCond", true);
             }
         }
        //added by Jenish tds & taxable amount calculation
        if(event.equals("tds"))
        {
            writeLog(pageContext   , "Inside TDS" ,4 );
            double  tdsPercent      = 0.0 ,     tdsValue        = 0.0 , 
                    enteredValue    = 0.0 ,     taxableAmnt     = 0.0 ;
            
            String                  rowRef  =   pageContext.getParameter(EVENT_SOURCE_ROW_REFERENCE);
            LCPoDetailsEVORowImpl   curRow  =   (LCPoDetailsEVORowImpl)am.findRowByRef(rowRef);
            
            try 
            {
                enteredValue = Double.parseDouble(curRow.getAttribute("PoEnteredValue").toString());
            }
            catch (Exception e) 
            {
                writeLog(pageContext , "enteredValue  Exc  --> "+e, 4); 
            }
            writeLog(pageContext , "enteredValue  --> "+enteredValue , 4);
            try 
            {
                tdsPercent = Double.parseDouble(curRow.getAttribute("Attribute2").toString());
            }
            catch (Exception e) 
            {
                writeLog(pageContext , "tdsPercent  Exc  --> "+e, 4); 
            }
            writeLog(pageContext , "tdsPercent  --> "+tdsPercent , 4); 
            
            try 
            {
                tdsValue = (enteredValue * tdsPercent)/ 100.0;              
            }
            catch (Exception e) 
            {
                writeLog(pageContext , "tdsValue  Exc  --> "+e, 4); 
            }
            
            writeLog(pageContext, " tdsValue Amount --> " +tdsValue, 4);
            
            curRow.setAttribute("Attribute4"  ,tdsValue);
            try 
            {
                taxableAmnt = enteredValue - tdsValue ;
            }
            catch (Exception e) 
            {
                writeLog(pageContext, "taxable Amount Exc --> " +e, 4);
            }
            writeLog(pageContext, "taxable Amount --> " +taxableAmnt, 4);
    
            curRow.setAttribute("Attribute3"  ,taxableAmnt);
        }
    }
//     Addded to check the field rendering on Lov update  by Jenish ITS
  }
  
  public void preValidation(OAPageContext pageContext , OAWebBean webBean ) 
    {
        writeLog(pageContext ,"Inside preValidation"   ,4);
        LCMultiAMImpl       am                =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
        OAViewObject        hdrVO             =   am.getLCRequestHdrEVO();
        OAViewObject        poVO              =   am.getLCPoDetailsEVO();
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
        if(!hdrVO.getCurrentRow().getAttribute("LcLastDateOfShipment").equals(hdrVO.getCurrentRow().getAttribute("LastDateOfShipPi"))) 
        {
            throw new OAException("LC condition values and PI details values should match, Kindly correct the  LC Last Shipment Date to proceed", OAException.ERROR);
        }
        if(!pageContext.getParameter("LcIncoterms").equals(pageContext.getParameter("Piincoterm"))) 
        {
            throw new OAException("LC condition values and PI details values should match, Kindly correct the  Inco Terms to proceed", OAException.ERROR);
        }
        for(Row row = poVO.first();row != null;row = poVO.next())
        {
            String po   =   row.getAttribute("PoNumber").toString();
            
                if(po  !=  null && po  != "")   
                {
                    System.out.println("po Exc --> " + po);
                }
                else 
                {
                    throw new OAException("Select PO Number to Proceed", OAException.ERROR);
                }        
        }
    }   
    public void callAmendmentActivities(OAApplicationModule am , String lcRequestId)
    {
        System.out.println("Inside callAmendmentActivities");
        try
        {
            CallableStatement callableStatement = am.getOADBTransaction().createCallableStatement("begin  xxsify_lc_multi_po_wf_pkg.setrevtables(:1); end;", -1);
            System.out.println("1");
            callableStatement.setString(1, lcRequestId);
            System.out.println("2");
            callableStatement.execute();
            callableStatement.close();
            System.out.println("3");
        }
        catch (Exception e)
        {
            System.out.println("Calling xxsify_lc_req_multi_wf_pkg.setrevtables() Func Exception::: " + e);
            throw new OAException("Calling xxsify_lc_req_multi_wf_pkg.setrevtables() Func Exception:::" + e.getMessage(), (byte)0);
        }
    }     
    public void callAmendmentRevokeActivities(OAApplicationModule am , String lcRequestId)
    {
        System.out.println("Inside callAmendmentRevokeActivities");
        try
        {
            CallableStatement callableStatement = am.getOADBTransaction().createCallableStatement("begin  xxsify_lc_multi_po_wf_pkg.revokerevtables(:1); end;", -1);
            System.out.println("1");
            callableStatement.setString(1, lcRequestId);
            System.out.println("2");
            callableStatement.execute();
            callableStatement.close();
            System.out.println("3");
        }
        catch (Exception e)
        {
            System.out.println("Calling xxsify_lc_req_multi_wf_pkg.setrevtables() Func Exception::: " + e);
            throw new OAException("Calling xxsify_lc_req_multi_wf_pkg.setrevtables() Func Exception:::" + e.getMessage(), (byte)0);
        }
    }
    public void POEvent(OAApplicationModuleImpl am , OAPageContext pageContext , OAViewObject poDetVO , OAViewObject hdrVO  )
    {
        System.out.println("POEvent");
        String  lcValWord       =   null    ,   currency        =   null    ,
                lcCountryExp    =   null    ,   payTerms        =   null    ,
                tradeTerms      =   null    ,   ouName          =   null    ,
                supplierSite    =   null    ,   lcLasShipDate   =   null    ,
                orgId           =   null    ,   supplierSiteID  =   null    ;
        int     poLovFetchRowC  =   0       ;
        OAViewObject poDetVOLOV         = (OAViewObject)am.findViewObject("PoDetailsVO");
        OAViewObject periodOfPresentVO  = (OAViewObject)am.findViewObject("PeriodOfPresentVO");
        OAViewObject vendorSiteVO       =  (OAViewObject)am.findViewObject("VendorSiteVO");
        OAViewObject transVO            =  (OAViewObject)am.findViewObject("TransientVO");

        Double lcTotalValue = 0.0 ,  poTotalValue = 0.0;
//        float lcTotalValue = 0.00f ,  poTotalValue = 0.00f;

        String importCurrency   =   null;
        int importCount = 0;
        writeLog(pageContext,"Fetch row Count poDetVO "+poDetVO.getFetchedRowCount(),4 );
        try 
        {
            poLovFetchRowC = poDetVOLOV.getFetchedRowCount();
        }
        catch (Exception e) 
        {
            writeLog(pageContext, "poLovFetchRowC Exc  --> " +e ,4);
        }
        writeLog(pageContext, "Row Count in po Evevnt Lov --> " +poLovFetchRowC, 4);
        
        for(Row row = poDetVO.first(); row != null ; row = poDetVO.next())
        {
            String  newPoNo                 =   null    ,   newPoDate       =   null    ,   
                    newPoCurrency           =   null    ,   newPoValue      =   null    ,
                    newPoRemainingValue     =   null    ,   newEnteredValue =   null    ;
            try 
            {
                orgId    =   row.getAttribute("OrganizationId").toString();
            }
            catch (Exception e)
            {
                System.out.println("ORG ID Exc --> " + e);
            }      
            try 
            {
                newPoNo    =   row.getAttribute("PoNumber").toString();
            }
            catch (Exception e)
            {
                System.out.println("PO No Exc --> " + e);
            }      
            try 
            {
                newPoDate    =   row.getAttribute("PoDate").toString();
            }
            catch (Exception e)
            {
                System.out.println("poDate Exc --> " + e);
            }   
            try 
            {
                newPoCurrency    =   row.getAttribute("PoCurrency").toString();
            }
            catch (Exception e)
            {
                System.out.println("poCurrency Exc --> " + e);
            }
            try 
            {
                newPoValue    =   row.getAttribute("PoValue").toString();
            }
            catch (Exception e)
            {
                System.out.println("poValue Exc --> " + e);
            }
            try 
            {
                newPoRemainingValue    =   row.getAttribute("PoRemainingValue").toString();
            }
            catch (Exception e)
            {
                System.out.println("poRemainingValue Exc --> " + e);
            }
            try 
            {
                newEnteredValue    =   row.getAttribute("PoEnteredValue").toString();
            }
            catch (Exception e)
            {
                System.out.println("enteredValue Exc --> " + e);
            }
            //Start added by Jenish to make the PO based LC conditions Get
             poDetVOLOV.setWhereClause("po_number = '"+newPoNo+"'");
             poDetVOLOV.executeQuery();
             poDetVOLOV.first();
             writeLog(pageContext,"poDetVOLOV Query     --> "+poDetVOLOV.getQuery(),4);
             writeLog(pageContext , "poDetVOLOV RC      --> "  +poDetVOLOV.getRowCount()          , 4);
             writeLog(pageContext , "poDetVOLOV FRC     --> "  +poDetVOLOV.getFetchedRowCount()   , 4);
             
            
             try 
             {
                 lcCountryExp    =   poDetVOLOV.getCurrentRow().getAttribute("LcCountryExpire").toString();
             }
             catch (Exception e)
             {
                 System.out.println("LcCountryExpire Exc --> " + e);
             }      
            try 
            {
                payTerms    =   poDetVOLOV.getCurrentRow().getAttribute("LcPaymentTerms").toString();
            }
            catch (Exception e)
            {
                System.out.println("LcPaymentTerms Exc --> " + e);
            }      
            try 
            {
                tradeTerms    =   poDetVOLOV.getCurrentRow().getAttribute("LcTradeTerms").toString();
            }
            catch (Exception e)
            {
                System.out.println("LcTradeTerms Exc --> " + e);
            }      
            try 
            {
                ouName    =   poDetVOLOV.getCurrentRow().getAttribute("OuName").toString();
            }
            catch (Exception e)
            {
                System.out.println("OuName Exc --> " + e);
            }      
            try 
            {
                supplierSite    =   poDetVOLOV.getCurrentRow().getAttribute("VendorSiteCode").toString();
            }
            catch (Exception e)
            {
                System.out.println("supplierSite Exc --> " + e);
            }
            try 
            {
                supplierSiteID    =   poDetVOLOV.getCurrentRow().getAttribute("VendorSiteId").toString();
            }
            catch (Exception e)
            {
                System.out.println("supplierSiteID Exc --> " + e);
            }
            try
            {
                lcLasShipDate    =   poDetVOLOV.getCurrentRow().getAttribute("LcLastDateOfShipment").toString();
            }
            catch (Exception e)
            {
                System.out.println("lcLasShipDate Exc --> " + e);
            }     
            //End added by Jenish to make the PO based LC conditions (Get)
            System.out.println("PO No           --> " + newPoNo);
            System.out.println("poDate          --> " + newPoDate);
            System.out.println("poCurrency      --> " + newPoCurrency);
            System.out.println("poValue         --> " + newPoValue);
            System.out.println("poRemainingValue--> " + newPoRemainingValue);
            System.out.println("enteredValue    --> " + newEnteredValue);
            System.out.println("lcCountryExpire     --> " + lcCountryExp);
            System.out.println("payTerms            --> " + payTerms);
            System.out.println("tradeTerms          --> " + tradeTerms);
            System.out.println("OuName              --> " + ouName);
            System.out.println("supplierSite        --> " + supplierSite);
            System.out.println("supplierSiteID        --> " + supplierSiteID);
            System.out.println("lcLast Shipment Date        --> " + lcLasShipDate);
            System.out.println("ORG ID       --> " + orgId);
            if  (
                    (newPoNo               !=  null    && newPoNo               != ""   ) &&    (newPoDate         !=  null && newPoDate      != "")   &&
                    (newPoCurrency         !=  null    && newPoCurrency         != ""   ) &&    (newPoValue        !=  null && newPoValue     != "")   &&
                    (newPoRemainingValue   !=  null    && newPoRemainingValue   != ""   ) &&    (newEnteredValue   !=  null && newEnteredValue!= "")
                )
            {
                System.out.println("All the values are passed");
                lcTotalValue    =   lcTotalValue + Double.parseDouble(newEnteredValue);
                poTotalValue    =   poTotalValue + Double.parseDouble(newPoValue);
                
//                lcTotalValue    =   lcTotalValue + Float.parseFloat(newEnteredValue);
//                poTotalValue    =   poTotalValue + Float.parseFloat(newPoValue);
                currency        =   newPoCurrency;
                System.out.println("lcTotalValue    --> "   +   lcTotalValue);
                System.out.println("poTotalValue    --> "   +   poTotalValue);
            }
            else
            {
                System.out.println("All the values are not passed");
            }
            
            if(importCount == 0)
            {
                importCurrency = newPoCurrency;
            }
            
            importCount = 1;
        }
        try 
        {
            lcValWord    =   cc.executeSQLquery(am , "select xxsify_lc_curr_amt_words('"+lcTotalValue+"','"+currency+"') from dual");
        }
        catch (Exception e)
        {
            System.out.println("lcValWord Exc -->   " + e);
        }
//        to set vendor addres in LC po Info
        vendorSiteVO.setWhereClause("1=1");
        vendorSiteVO.executeQuery();
        vendorSiteVO.first();
        String   vendorSitAddrs = null;
        try 
        {
               vendorSitAddrs    =   vendorSiteVO.getCurrentRow().getAttribute("VendorSiteAddr").toString();
        }
        catch (Exception e)
        {
            System.out.println("vendorAddrs Exc -->   " + e);
        }
        writeLog(pageContext ,"lcValWord --> " + lcValWord , 4);
        hdrVO.getCurrentRow().setAttribute("LcValue"        , lcTotalValue);
        hdrVO.getCurrentRow().setAttribute("TotalPoValue"   , poTotalValue);
        hdrVO.getCurrentRow().setAttribute("LcValueWords"   , lcValWord.toUpperCase());
        // added by Jenish to make the PO based LC conditions Set        Starts
        writeLog(pageContext ,"lcCountryExp     --> " + lcCountryExp    , 4);
        writeLog(pageContext ,"payTerms         --> " + payTerms        , 4);
        writeLog(pageContext ,"tradeTerms       --> " + tradeTerms      , 4);
        writeLog(pageContext ,"ouName           --> " + ouName          , 4);
        writeLog(pageContext ,"supplierSite     --> " + supplierSite    , 4);
        writeLog(pageContext ,"lcLasShipDate    --> " + lcLasShipDate   , 4);
        writeLog(pageContext ,"orgId            --> " + orgId           , 4);
        writeLog(pageContext ,"vendorSitAddrs   --> " + vendorSitAddrs  , 4);
        
        if(poDetVO.getFetchedRowCount() ==1)
        {
       
            hdrVO.getCurrentRow().setAttribute("LcCountryExpire"                , lcCountryExp);
            hdrVO.getCurrentRow().setAttribute("LcPaymentTerms"                 , payTerms);
            hdrVO.getCurrentRow().setAttribute("LcTradeTerms"                   , tradeTerms);
            hdrVO.getCurrentRow().setAttribute("LcOrgName"                      , ouName);
            hdrVO.getCurrentRow().setAttribute("VendorSiteCode"                 , supplierSite);
            hdrVO.getCurrentRow().setAttribute("VendorSiteId"                   , supplierSiteID);
            hdrVO.getCurrentRow().setAttribute("Attribute5"                     , payTerms);
            hdrVO.getCurrentRow().setAttribute("Attribute6"                     , tradeTerms); 
            hdrVO.getCurrentRow().setAttribute("LcLastDateOfShipment"           , lcLasShipDate);
            hdrVO.getCurrentRow().setAttribute("LastDateOfShipPi"               , lcLasShipDate);
            hdrVO.getCurrentRow().setAttribute("VendorAddress"                  , vendorSitAddrs);
            hdrVO.getCurrentRow().setAttribute("LcOrgId"                        , orgId);
            writeLog(pageContext , "Inside Calculate LC Expiry Date",4);
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
            /** Written below logic to default LC currency as 1st PO Currency **/
            if(importCurrency != null && importCurrency!= "")
            {
                System.out.println("importCurrency is not null");
                if(importCurrency.equals("INR"))
                {
                    System.out.println("Not related to Import concept");
                }
                else
                {
                    hdrVO.getCurrentRow().setAttribute("LcClassification","Import");
                    hdrVO.getCurrentRow().setAttribute("LcCurrencyCode",importCurrency);
                }
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
     