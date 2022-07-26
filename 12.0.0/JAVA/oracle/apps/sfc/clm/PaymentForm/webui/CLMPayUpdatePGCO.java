/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package oracle.apps.sfc.clm.PaymentForm.webui;

import java.io.Serializable;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OADialogPage;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.sfc.clm.PaymentForm.server.CLMPaymentAMImpl;

/**
 * Controller for ...
 */
public class CLMPayUpdatePGCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
    CLMPaymentAMImpl    am      =   (CLMPaymentAMImpl)pageContext.getApplicationModule(webBean);
    String              clmid   =   pageContext.getParameter("clmId");
    //  am.invokeMethod("executeHdrVO",new Serializable[]{clmid} );
    am.executeHdrVO(clmid);
    Serializable s[]={clmid,"update"};
    //  am.invokeMethod("initInvoicePaymentEOVO",s);
    am.initInvoicePaymentEOVO(clmid , "update");
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
    CLMPaymentAMImpl    am      =   (CLMPaymentAMImpl)pageContext.getApplicationModule(webBean);
    if (pageContext.getParameter("submitBtn") != null)
    {
        am.getOADBTransaction().commit();
        OAException     confirmMessage  =  new OAException("Payment has been submitted sucessfully");
        OADialogPage    dialogPage      =  new OADialogPage(OAException.CONFIRMATION, confirmMessage, null,  "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMPaymentHomePG",  null);
        pageContext.redirectToDialogPage(dialogPage);
    }
    if (pageContext.getParameter("cancelBtn") != null)
    {
        pageContext.setForwardURL(  "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMPaymentHomePG"   , 
                                    null                                                                    , 
                                    OAWebBeanConstants.KEEP_MENU_CONTEXT                                    , 
                                    null                                                                    , 
                                    null                                                                    ,   
                                    true                                                                    , 
                                    OAWebBeanConstants.ADD_BREAD_CRUMB_NO                                   , 
                                    OAWebBeanConstants.IGNORE_MESSAGES);
    }
  }

}
