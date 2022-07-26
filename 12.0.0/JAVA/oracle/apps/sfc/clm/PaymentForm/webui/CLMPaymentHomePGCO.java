/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package oracle.apps.sfc.clm.PaymentForm.webui;

import com.sun.java.util.collections.HashMap;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAException;
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
public class CLMPaymentHomePGCO extends OAControllerImpl
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
    OAViewObject        vo  =   am.getCLMIdSearchVO();
    vo.setWhereClause(null);
    //  vo.setWhereClauseParams(null);
    //  vo.setWhereClause("CLM_ID="+clmid);
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
    OAViewObject        vo  =   am.getCLMIdSearchVO();
    if(pageContext.getParameter("cancelBtn") != null)
    {
        pageContext.setForwardURL(  "OA.jsp?page=/oracle/apps/fnd/framework/navigate/webui/HomePG"  , 
                                    null                                                            , 
                                    OAWebBeanConstants.KEEP_MENU_CONTEXT                            , 
                                    null                                                            , 
                                    null                                                            , 
                                    true                                                            , 
                                    OAWebBeanConstants.ADD_BREAD_CRUMB_NO                           , 
                                    OAWebBeanConstants.IGNORE_MESSAGES
                                 );
    }
    if (pageContext.getParameter("advancePayBtn") != null)
    {
        pageContext.setForwardURL(  "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/AdvancePayHomePG"   , 
                                    null                                                                    , 
                                    OAWebBeanConstants.KEEP_MENU_CONTEXT                                    , 
                                    null                                                                    , 
                                    null                                                                    , 
                                    true                                                                    , 
                                    OAWebBeanConstants.ADD_BREAD_CRUMB_YES                                  , 
                                    OAWebBeanConstants.IGNORE_MESSAGES
                                 );   
    }
    if (pageContext.getParameter(EVENT_PARAM).equals("NewInvoice"))
    {
        System.out.println("Inside New Invoice");
        String    pclmid      =   "";
        int       pbwReqid    =   0;
        String    pono        =   "";
        int       cnt         =   0;
        System.out.println("vo fetch row count    "   +   vo.getFetchedRowCount());
        System.out.println("vo row count          "   +   vo.getRowCount());
        for (int i = 0; i < vo.getRowCount(); i++)
        {   
            CLMIdSearchVORowImpl rowi = ( CLMIdSearchVORowImpl)vo.getRowAtRangeIndex(i);        
            System.out.println("rowi"+rowi.getClmId());
            if (rowi.getSelectFlag()!= null) 
            {
                System.out.println("inside select");
                if( rowi.getSelectFlag().equals("Y"))
                {
                    cnt         =   1;    
                    pclmid      =   rowi.getClmId().toString();
//                  pbwReqid    =   rowi.getBandWidthReqId().intValue();
                    pono        =   rowi.getEbsPoNum();
                    break;
                }     
            }
        }
        if (cnt == 0)
        {
            throw new OAException("Please Select a record to proceed..",OAException.ERROR);
        }
        System.out.println("pclmid  --> "   +   pclmid);
        System.out.println("pono    --> "   +   pono);
        HashMap hm  =   new HashMap();
        hm.put("clmId"      ,   pclmid); 
//        hm.put("clmId"      ,   "21662");  
//      hm.put("bwReqId"    ,   pbwReqid);    
        hm.put("poNo"       ,   pono); 
//        hm.put("poNo"       ,   "202001203931"); 
        pageContext.setForwardURL(  "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMPaymentCreatePG"   , 
                                    null                                                                            , 
                                    OAWebBeanConstants.KEEP_MENU_CONTEXT                                            , 
                                    null                                                                            , 
                                    hm                                                                              , 
                                    true                                                                            , 
                                    OAWebBeanConstants.ADD_BREAD_CRUMB_NO                                           , 
                                    OAWebBeanConstants.IGNORE_MESSAGES
                                 ); 
    }
    /* 
    if (pageContext.getParameter(EVENT_PARAM).equals("AdvancePayment"))
    {
        String          pclmid      =   "";
//      String          pbwReqid    =   "";
        String          pono        =   "";
        int             cnt         =   0;
        OAViewObject    vo          =   (OAViewObject)am.findViewObject("CLMIdSearchVO");
        
        for (int i = 0; i < vo.getFetchedRowCount(); i++)
        {   
            CLMIdSearchVORowImpl rowi = ( CLMIdSearchVORowImpl)vo.getRowAtRangeIndex(i);        
            if (rowi.getSelectFlag()!= null && rowi.getSelectFlag().equals("Y"))
            {
                cnt         =   1;
                pclmid      =   rowi.getClmId();
//              pbwReqid    =   rowi.getBandWidthReqId()
                pono        =   rowi.getEbsPoNum();
            }
        }
        if (cnt == 0)
        {
            throw new OAException("Please Select a record to proceed..",OAException.ERROR);
        }
        HashMap hm  =   new HashMap();
        hm.put("clmId"      ,   pclmid);      
//      hm.put("bwReqId"    ,   pbwReqid);    
        hm.put("poNo"       ,   pono); 
        pageContext.setForwardURL(  "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMAdvPaymentPG"  , 
                                    null                                                                        , 
                                    OAWebBeanConstants.KEEP_MENU_CONTEXT                                        , 
                                    null                                                                        , 
                                    hm                                                                          , 
                                    true                                                                        , 
                                    OAWebBeanConstants.ADD_BREAD_CRUMB_YES                                      , 
                                    OAWebBeanConstants.IGNORE_MESSAGES
                                 );   
      } */
      if (pageContext.getParameter(EVENT_PARAM).equals("CreditMemoPayment"))
      {
            String          pclmid      =   "";
//          String          pbwReqid    =   "";
            String          pono        =   "";
            int             cnt         =   0;
            for (int i = 0; i < vo.getFetchedRowCount(); i++)
            {   
                CLMIdSearchVORowImpl rowi = ( CLMIdSearchVORowImpl)vo.getRowAtRangeIndex(i);        
                if (rowi.getSelectFlag()!= null && rowi.getSelectFlag().equals("Y"))
                {
                    cnt         =   1;
                    pclmid      =   rowi.getClmId().toString();
//                  pbwReqid    =   rowi.getBandWidthReqId()
                    pono        =   rowi.getEbsPoNum();
                }
            }
            if (cnt == 0)
            {
                throw new OAException("Please Select a record to proceed..",OAException.ERROR);
            }
            HashMap hm  =   new HashMap();
            hm.put("clmId"  ,   pclmid);      
//          hm.put("bwReqId",pbwReqid);    
            hm.put("poNo"   ,   pono); 
            pageContext.setForwardURL(  "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMCreditMemoPG"  , 
                                        null                                                                        , 
                                        OAWebBeanConstants.KEEP_MENU_CONTEXT                                        , 
                                        null                                                                        , 
                                        hm                                                                          , 
                                        true                                                                        , 
                                        OAWebBeanConstants.ADD_BREAD_CRUMB_YES                                      , 
                                        OAWebBeanConstants.IGNORE_MESSAGES
                                     ); 
      }
      if (pageContext.getParameter(EVENT_PARAM).equals("DebitMemoPayment"))
      {
            String          pclmid      =   "";
//          String          pbwReqid    =   "";
            String          pono        =   "";
            int             cnt         =   0;
          
            for (int i = 0; i < vo.getFetchedRowCount(); i++)
            {   
                CLMIdSearchVORowImpl rowi = ( CLMIdSearchVORowImpl)vo.getRowAtRangeIndex(i);        
                if (rowi.getSelectFlag()!= null && rowi.getSelectFlag().equals("Y"))
                {
                    cnt         =   1;
                    pclmid      =   rowi.getClmId().toString();
//                  pbwReqid    =   rowi.getBandWidthReqId()
                    pono        =   rowi.getEbsPoNum();
                }
            }
            if (cnt == 0)
            {
                throw new OAException("Please Select a record to proceed..",OAException.ERROR);
            }
            HashMap hm  =   new HashMap();
            hm.put("clmId"  ,   pclmid);      
//          hm.put("bwReqId",pbwReqid);    
            hm.put("poNo"   ,   pono); 
            pageContext.setForwardURL(  "OA.jsp?page=/oracle/apps/sfc/clm/PaymentForm/webui/CLMDebitMemoPG"   , 
                                        null                                                                        , 
                                        OAWebBeanConstants.KEEP_MENU_CONTEXT                                        , 
                                        null                                                                        , 
                                        hm                                                                          , 
                                        true                                                                        , 
                                        OAWebBeanConstants.ADD_BREAD_CRUMB_YES                                      , 
                                        OAWebBeanConstants.IGNORE_MESSAGES
                                     ); 
      }
  }
}
