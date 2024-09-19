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
import oracle.apps.sfc.lcissuance.multi.server.LCMultiAMImpl;

import oracle.jbo.Row;

/**
 * Controller for ...
 */
public class LCRevDetailsCO extends OAControllerImpl
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
      pageContext.writeDiagnostics(this, "Inside PR" ,4);
      pageContext.writeDiagnostics(this,"Inside PR   --> "    ,4); 
      System.out.println("Inside PR");  
      LCMultiAMImpl       am            =   (LCMultiAMImpl)pageContext.getApplicationModule(webBean);
      OAViewObject        revHistVO     =   am.getLCRevHistVO();
      
      String lcRequestID = null;
      
      try 
      {
          lcRequestID = pageContext.getParameter("p_lc_req_id");
      }
      catch (Exception e) 
      {
          System.out.println("lcRequestID Exc --> " + e);
      }
      
      System.out.println("lcRequestID --> " + lcRequestID);
      
      revHistVO.setWhereClause("lc_request_id = '" + lcRequestID +"'");
      revHistVO.executeQuery();
      revHistVO.first();
      
      int i = 1;
      for(Row row = revHistVO.first(); row != null ; row = revHistVO.next())
      {
        row.setAttribute("SNo" , i);
        i = i + 1;
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
  }

}
