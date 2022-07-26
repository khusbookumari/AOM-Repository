/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package oracle.apps.sfc.clm.PaymentForm.webui;

import com.sun.java.util.collections.HashMap;
import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.sfc.clm.PaymentForm.server.CLMIdSearchVORowImpl;
import oracle.apps.sfc.clm.PaymentForm.server.CLMPaymentAMImpl;

/**
 * Controller for ...
 */
public class CLMPaymentPGCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");
    String clmid="";

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    CLMPaymentAMImpl    am  =   (CLMPaymentAMImpl)pageContext.getApplicationModule(webBean);
//  Serializable s[]={clmid,"create"};
//  am.invokeMethod("initInvoicePaymentEOVO",s);
//  am.invokeMethod("HandlePPR");
//  System.out.println("in pg co");  */
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
    CLMPaymentAMImpl    am  =   (CLMPaymentAMImpl)pageContext.getApplicationModule(webBean);
    OAViewObject        vo  =   am.getCLMIdSearchVO();
    
    if ("CreateInvoice".equals(pageContext.getParameter(EVENT_PARAM)))
    {
        String pclmid="";
        for (int i = 0; i < vo.getFetchedRowCount(); i++)
        {   
            CLMIdSearchVORowImpl rowi = ( CLMIdSearchVORowImpl)vo.getRowAtRangeIndex(i);        
            if (rowi.getSelectFlag()!= null && rowi.getSelectFlag().equals("Y"))
            {
                pclmid  =   rowi.getClmId().toString();
            }
        }
        HashMap hm  =   new HashMap();
        hm.put("clmId",pclmid);               
        pageContext.setForwardURL(  "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMPaymentCreatePG" , 
                                    null                                                                    , 
                                    OAWebBeanConstants.KEEP_MENU_CONTEXT                                    , 
                                    null                                                                    , 
                                    hm                                                                      , 
                                    true                                                                    , 
                                    OAWebBeanConstants.ADD_BREAD_CRUMB_YES                                  , 
                                    OAWebBeanConstants.IGNORE_MESSAGES
                                 ); 
    
    
    }
  }
}


 
      
    /*  if ("makePayment".equals(pageContext.getParameter(EVENT_PARAM))){
        clmid= pageContext.getParameter("pclmid");
          
          am.invokeMethod("showPaymentType");
          
       //  String rowref= pageContext.getParameter(OAWebBeanConstants.EVENT_SOURCE_ROW_REFERENCE);
          
       }
       
       
        if (pageContext.getParameter("cancelBtn") != null){
        
             pageContext.setForwardURL("OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMPaymentHomePG", 
                                                null, 
                                                OAWebBeanConstants.KEEP_MENU_CONTEXT, 
                                                null, 
                                                null , 
                                                true, 
                                                OAWebBeanConstants.ADD_BREAD_CRUMB_NO, 
                                                OAWebBeanConstants.IGNORE_MESSAGES);
        
        
        
         }
       
      if ("invoiceDeatils".equals(pageContext.getParameter(EVENT_PARAM))){
         
         //  am.invokeMethod("execInvoiceHistoryVO");
           am.invokeMethod("setInvoiceDefaultValues");
           
           am.invokeMethod("showInvoiceRN");
       }
       
  //    if(pageContext.isLovEvent())  
     if ("PaymentTypeEvent".equals(pageContext.getParameter(EVENT_PARAM)))
      { 
      
        //  String lovInputSourceId = pageContext.getLovInputSourceId();  
           //   if("paymentTypeItemId".equals(lovInputSourceId)) {     
                  if(pageContext.getParameter("paymentTypeId").equalsIgnoreCase("Recurring") || 
                     pageContext.getParameter("paymentTypeId").equalsIgnoreCase("One Time Payment")) {
                     
                     
                        
                        System.out.println("clmid"+clmid);
                        
                         am.invokeMethod("executeRecurringVO",new Serializable[]{clmid} );
                         am.invokeMethod("SetValues");
                         am.invokeMethod("showRecurringTable");
                     }
                else{ 
                
                         System.out.println("clmid1"+clmid);
                         am.invokeMethod("executeRecurringVO",new Serializable[]{clmid} );
                         am.invokeMethod("SetAdvValues");
                         am.invokeMethod("showAdvancePayTable");
                       
          
                     }      
     } 
              
    
       
      if (pageContext.getParameter("saveBtn") != null){
      
          OAViewObject vo=(OAViewObject)am.findViewObject("CLMPaymentInvoiceEOVO");
          OARow rw=(OARow)vo.first(); 
          rw.setAttribute("InvoiceWfStatus","Saved");
          rw.setAttribute("SaveFlag","Y");
          am.invokeMethod("commit");
          OAException confirmMessage = 
                        new OAException("Data Has Been Saved Successfully..");
                    OADialogPage dialogPage = 
                        new OADialogPage(OAException.CONFIRMATION, confirmMessage, 
                                         null, 
                                         "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMPaymentHomePG", 
                                         null);

                    pageContext.redirectToDialogPage(dialogPage);
      
      }
      
      if (pageContext.getParameter("submitBtn") != null){
        
         OAViewObject vo=(OAViewObject)am.findViewObject("CLMPaymentInvoiceEOVO");
         OARow rw=(OARow)vo.first(); 
         rw.setAttribute("InvoiceWfStatus","Submitted");
         rw.setAttribute("SaveFlag","N");
       
         //calling workflow      
          String stmt = "BEGIN xxsify_clm_pay_wf_valid_pkg.sify_clm_pay_approver1_wf_call(:1,:2); end;";
             CallableStatement cs = 
                 am.getOADBTransaction().createCallableStatement(stmt, 1);
             try {
               
                 cs.setString(1,rw.getAttribute("ClmId").toString() );
                 cs.setInt(2,pageContext.getUserId());
                 cs.execute(); 
               
                 cs.close();
             } catch (Exception e) {
                throw new OAException("Error while calling wf"+e.getMessage());
             }        
      
          am.getOADBTransaction().commit();
          
            OAException confirmMessage = 
                          new OAException("Payment has been submitted sucessfully");
                      OADialogPage dialogPage = 
                          new OADialogPage(OAException.CONFIRMATION, confirmMessage, 
                                           null, 
                                           "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMPaymentHomePG", 
                                           null);

                      pageContext.redirectToDialogPage(dialogPage);
        
        }   
      
  }    

}*/
