/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package oracle.apps.sfc.clm.PaymentForm.webui;

import java.sql.CallableStatement;
import java.sql.Types;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.sfc.clm.PaymentForm.server.CLMPaymentAMImpl;

/**
 * Controller for ...
 */
public class CLMPaymentHistoryCO extends OAControllerImpl
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
    CLMPaymentAMImpl    am  =   (CLMPaymentAMImpl)pageContext.getApplicationModule(webBean);
    OAViewObject        vo  =   am.getCLMInvoiceHistoryVO1();
    
    String clmid    =   (String)pageContext.getParameter("param1");   
    String pono     =   (String)pageContext.getParameter("param2");
    String mode     =   (String)pageContext.getParameter("param3");
    
    System.out.println("param1"+clmid);
    System.out.println("param2"+pono);
    System.out.println("param3"+mode);
    
    vo.setWhereClauseParams(null);
    vo.setWhereClauseParam(0,clmid);
    vo.setWhereClauseParam(1,pono);
    vo.executeQuery();

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
    OAViewObject        vo  =   am.getCLMInvCheckDetailsVO();
    
    String  mode    =   (String)pageContext.getParameter("param3");
    System.out.println("mode+"+mode);
    
    if (pageContext.getParameter("backBtn") != null)
    {
        if ("AdvanceHome".equals(mode))
        {
            pageContext.setForwardURL(  "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMAdvancePayHomePG" , 
                                        null                                                                    , 
                                        OAWebBeanConstants.KEEP_MENU_CONTEXT                                    , 
                                        null                                                                    , 
                                        null                                                                    , 
                                        false                                                                   , 
                                        OAWebBeanConstants.ADD_BREAD_CRUMB_NO                                   , 
                                        OAWebBeanConstants.IGNORE_MESSAGES
                                      );
        
        }
        else
        {
            pageContext.setForwardURL(  "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMPaymentHomePG"   , 
                                        null                                                                    , 
                                        OAWebBeanConstants.KEEP_MENU_CONTEXT                                    , 
                                        null                                                                    , 
                                        null                                                                    , 
                                        false                                                                   , 
                                        OAWebBeanConstants.ADD_BREAD_CRUMB_NO                                   , 
                                        OAWebBeanConstants.IGNORE_MESSAGES
                                     );
        }
    }
    if ("viewCpOutput".equals(pageContext.getParameter(EVENT_PARAM))) 
    {
        String cp_url   =   "";
        String cpid     =   (String)pageContext.getParameter("param3");   
        
        System.out.println("cp id"+cpid);
        
        String              stmt    = "BEGIN xxsify_clm_pay_wf_valid_pkg.clm_cp_output_get_url(:1,:2); end;";
        CallableStatement   cs      =  am.getOADBTransaction().createCallableStatement(stmt, 1);
        try 
        {
            cs.setInt(1, Integer.parseInt(cpid));  
            cs.registerOutParameter(2,Types.VARCHAR);
            cs.execute(); 
            cp_url = cs.getString(2);
            cs.close();
        } 
        catch (Exception e) 
        {
            throw new OAException("Error while calling sify_clm_dates_valid procedre"+e.getMessage());
        }
        System.out.println("Url="+cp_url);
        if (cp_url != null)
        {
            try 
            {
                // redirects to URL
                pageContext.sendRedirect((String)cp_url);
                return;
            } 
            catch (Exception localException1) 
            {
                throw new OAException(" Error in report generation ",OAException.ERROR);
            }   
        }
    }
    if ("checkDetails".equals(pageContext.getParameter(EVENT_PARAM))) 
    {
        String invno    =   (String)pageContext.getParameter("InvNumP");   
        String supName  =   (String)pageContext.getParameter("SupNameP"); 
        System.out.println("invno"+invno+"-"+supName);
        
        vo.clearCache();
        vo.setWhereClauseParams(null);
        vo.setWhereClauseParam(0,invno);
        vo.setWhereClauseParam(1,supName);
        vo.executeQuery();
    }
  }
}
