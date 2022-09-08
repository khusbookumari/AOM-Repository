package xxspwr.oracle.apps.pos.account.webui;

import java.io.Serializable;
import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.jbo.domain.BlobDomain;

public class xxspwrViewInvoiceReportCO extends OAControllerImpl {
  public static final String RCS_ID = "$Header$";
  
  public static final boolean RCS_ID_RECORDED = VersionInfo.recordClassVersion("$Header$", "%packagename%");
  
  public void processRequest(OAPageContext pageContext, OAWebBean webBean) {
    super.processRequest(pageContext, webBean);
    String ActionEvent = pageContext.getParameter("event");
    OAApplicationModule am = pageContext.getApplicationModule(webBean);
    pageContext.writeDiagnostics(this, "xxspwrInvoiceReportCO::processRequest ActionEvent::" + ActionEvent, 1);
    String InvoiceId = pageContext.getParameter("InvoiceID");
    String StrInvoiceId = String.valueOf(InvoiceId);
    pageContext.writeDiagnostics(this, "xxspwrInvoiceReportCO::processRequest  InvoiceId::" + InvoiceId, 1);
    OAApplicationModule oaAM = pageContext.getApplicationModule(webBean);
    pageContext.putParameter("p_DataSource", "BlobDomain");
    pageContext.putParameter("p_DataSourceCode", "XXSPWR_SUPPLIER_INOVICE_REPORT");
    pageContext.putParameter("p_DataSourceAppsShortName", "XXMAXN");
    pageContext.putParameter("p_TemplateCode", "XXSPWR_SUPPLIER_INOVICE_REPORT");
    pageContext.putParameter("p_TemplateAppsShortName", "XXMAXN");
    pageContext.putParameter("p_Locale", "Default");
    pageContext.putParameter("p_Locale", "English:United States");
    pageContext.putParameter("p_OutputType", "PDF");
    pageContext.putParameter("p_XDORegionHeight", "200%");
    Serializable[] oaParams = { StrInvoiceId };
    BlobDomain result = (BlobDomain)oaAM.invokeMethod("getGVSData", oaParams);
    pageContext.putSessionValueDirect("XML_DATA_BLOB", result);
    pageContext.writeDiagnostics(this, "xxspwrInvoiceReportCO::processRequest - End", 2);
  }
  
  public void processFormRequest(OAPageContext pageContext, OAWebBean webBean) {
    super.processFormRequest(pageContext, webBean);
  }
}
