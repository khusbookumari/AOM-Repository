package xxspwr.oracle.apps.pos.account.server;

import com.sun.java.util.collections.Hashtable;
import java.sql.SQLException;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.server.OAApplicationModuleImpl;
import oracle.apps.fnd.framework.server.OADBTransactionImpl;
import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.oa.util.DataTemplate;
import oracle.jbo.domain.BlobDomain;

public class xxspwrViewInvoiceReportAMImpl extends OAApplicationModuleImpl {
  public static void main(String[] args) {
    launchTester("xxspwr.oracle.apps.pos.account.server", "xxspwrViewInvoiceReportAMLocal");
  }
  
  public BlobDomain getGVSData(String InvoiceId) {
    getOADBTransaction().writeDiagnostics(this, "xxspwrViewInvoiceReportAM::getGVSData InvoiceId::" + InvoiceId, 1);
    BlobDomain blobDomain = new BlobDomain();
    int NumInvoiceId = Integer.parseInt(InvoiceId);
    try {
      getOADBTransaction().writeDiagnostics(this, "xxspwrViewInvoiceReportAM::getGVSData inside try blok InvoiceId::" + InvoiceId, 1);
      DataTemplate datatemplate = new DataTemplate(((OADBTransactionImpl)getOADBTransaction()).getAppsContext(), "XXMAXN", "XXSPWR_SUPPLIER_INOVICE_REPORT");
      Hashtable parameters = new Hashtable();
      parameters.put("P_INVOICE_ID", Integer.valueOf(NumInvoiceId));
      datatemplate.setParameters(parameters);
      datatemplate.setOutput(blobDomain.getBinaryOutputStream());
      datatemplate.processData();
    } catch (SQLException e) {
      getOADBTransaction().writeDiagnostics(this, "xxspwrViewInvoiceReportAM::getGVSData inside SQLException InvoiceId::" + InvoiceId, 1);
      throw new OAException("SQL Error=" + e.getMessage(), (byte)0);
    } catch (XDOException e) {
      getOADBTransaction().writeDiagnostics(this, "xxspwrViewInvoiceReportAM::getGVSData inside XDOException InvoiceId::" + InvoiceId, 1);
      throw new OAException("XDOException" + e.getMessage(), (byte)0);
    } catch (Exception e) {
      getOADBTransaction().writeDiagnostics(this, "xxspwrViewInvoiceReportAM::getGVSData inside Exception InvoiceId::" + InvoiceId, 1);
      throw new OAException("Exception :" + e.getMessage(), (byte)0);
    } 
    return blobDomain;
  }
}
