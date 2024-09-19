package oracle.apps.sfc.lcissuance.common.server;

import com.sun.java.util.collections.HashMap;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;

import java.sql.CallableStatement;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.OAViewObject;
import oracle.apps.fnd.framework.server.OAApplicationModuleImpl;
import oracle.apps.fnd.framework.server.OADBTransaction;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.OAWebBeanConstants;

import oracle.jbo.Row;

import oracle.jdbc.OracleConnection;
//Report pkgs
 import java.io.IOException;
 import java.io.OutputStream;
 import oracle.apps.fnd.framework.OAException;
 import oracle.apps.fnd.framework.server.OAApplicationModuleImpl;
 import oracle.apps.fnd.framework.server.OAViewObjectImpl;
 import oracle.jbo.Row;
 import oracle.apps.fnd.framework.OAViewObject;
 import java.sql.CallableStatement;
 import java.sql.SQLException;
 import java.sql.Types;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import oracle.apps.fnd.framework.OARow;
import oracle.apps.fnd.framework.server.OADBTransactionImpl;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.sfc.lcissuance.server.LcIssuanceAMImpl;

import oracle.apps.xdo.oa.schema.server.TemplateHelper;

import oracle.cabo.ui.data.DataObject;

import oracle.jbo.XMLInterface;
 import oracle.jbo.domain.BlobDomain;
 import oracle.xml.parser.v2.XMLNode;


public class CommonClass 
{
    public CommonClass()
    {
    }
    
    public void setForwardPage(OAPageContext pageContext , String pageName , HashMap hmp , boolean retainAM)
    {
        pageContext.setForwardURL(
                                    "OA.jsp?page=/oracle/apps/sfc/lcissuance/webui/" + pageName     ,
                                    null                                                            ,
                                    OAWebBeanConstants.KEEP_MENU_CONTEXT                            ,
                                    null                                                            ,
                                    hmp                                                             ,
                                    retainAM                                                        ,
                                    OAWebBeanConstants.ADD_BREAD_CRUMB_YES                          ,
                                    OAWebBeanConstants.IGNORE_MESSAGES
                                );
    }
    public void commit(OAApplicationModule am)
    {
        am.getOADBTransaction().commit(); 
    }
    public void rollback(OAApplicationModule am)
    {
        am.getOADBTransaction().rollback(); 
    }
    public void executeQuery(OAViewObject vo , String whereClause)
    {
//        vo.reset();
//        vo.clearCache();
//        vo.setWhereClause(null);
        vo.setWhereClause(whereClause);
        vo.executeQuery();
        vo.first();
        
        System.out.println("Query   --> " + vo.getQuery());
        System.out.println("Get FRC --> " + vo.getFetchedRowCount());
        System.out.println("Get RC  --> " + vo.getRowCount());
//        writeDiagnostics(this,"Query   --> " + vo.getQuery() ,4);
//        writeDiagnostics(this,"Get FRC --> " + vo.getFetchedRowCount() ,4);
//        pageContwriteDiagnostics(this,"Get RC  --> " + vo.getRowCount() ,4);
    }
    public void createRow( OAApplicationModule am , OAViewObject vo , String pkInsertYesNo , String seqName , String seqAttr)
    {
        System.out.println("Inside Create Row");        
        if(!vo.isPreparedForExecution())
        {
            vo.setMaxFetchSize(0);
            vo.executeQuery();
        }
        vo.last();
        vo.next();
        Row row = vo.createRow();
        vo.insertRow(row);
        row.setNewRowState(row.STATUS_INITIALIZED);
        
        if(pkInsertYesNo != null    &&  pkInsertYesNo   !=  "")
        {
            if(pkInsertYesNo.equals("Yes"))
            {
                row.setAttribute(seqAttr, am.getOADBTransaction().getSequenceValue(seqName));
            }
        }
    }
    public void startWorkflow(OAApplicationModule am , String lcRequestId)
    {
        System.out.println("Inside start_workflow");
        try
        {
            CallableStatement callableStatement = am.getOADBTransaction().createCallableStatement("begin  xxsify_lc_issuance_wf_pkg.start_workflow(:1); end;", -1);
      
            System.out.println("1");
      
            callableStatement.setString(1, lcRequestId);
            System.out.println("2");
      
            callableStatement.execute();
            callableStatement.close();
            System.out.println("3");
        }
        catch (Exception e)
        {
            System.out.println("Calling xxsify_lc_issuance_wf_pkg.start_workflow() Func Exception::: " + e);
            throw new OAException("Calling xxsify_lc_issuance_wf_pkg.start_workflow() Func Exception:::" + e.getMessage(), (byte)0);
        }
    } 
    public void startMultiWorkflow(OAApplicationModule am , String lcRequestId)
    {
        System.out.println("Inside start_multi_workflow");
        try
        {
            CallableStatement callableStatement = am.getOADBTransaction().createCallableStatement("begin  xxsify_lc_multi_po_wf_pkg.start_workflow(:1); end;", -1);
      
            System.out.println("1");
      
            callableStatement.setString(1, lcRequestId);
            System.out.println("2");
      
            callableStatement.execute();
            callableStatement.close();
            System.out.println("3");
        }
        catch (Exception e)
        {
            System.out.println("Calling xxsify_lc_req_multi_wf_pkg.start_workflow() Func Exception::: " + e);
            throw new OAException("Calling xxsify_lc_req_multi_wf_pkg.start_workflow() Func Exception:::" + e.getMessage(), (byte)0);
        }
    } 
    public void continueWorkflow(OAApplicationModule am , String pItemKey)
    {
        try
        {
            CallableStatement callableStatement = am.getOADBTransaction().createCallableStatement("begin  xxsify_lc_issuance_wf_pkg.block_release(:1); end;", -1);
      
            callableStatement.setString(1, pItemKey);
            callableStatement.execute();
            callableStatement.close();
        }
        catch (Exception e)
        {
            System.out.println("Calling xxsify_lc_issuance_wf_pkg.block_release() Func Exception::: " + e);
            throw new OAException("Calling xxsify_lc_issuance_wf_pkg.block_release() Func Exception:::" + e.getMessage(), (byte)0);
        }
    }
    public void continueMultiWorkflow(OAApplicationModule am , String pItemKey)
    {
        try
        {
            CallableStatement callableStatement = am.getOADBTransaction().createCallableStatement("begin  xxsify_lc_multi_po_wf_pkg.block_release(:1); end;", -1);
      
            callableStatement.setString(1, pItemKey);
            callableStatement.execute();
            callableStatement.close();
        }
        catch (Exception e)
        {
            System.out.println("Calling xxsify_lc_req_multi_wf_pkg.block_release() Func Exception::: " + e);
            throw new OAException("Calling xxsify_lc_req_multi_wf_pkg.block_release() Func Exception:::" + e.getMessage(), (byte)0);
        }
    }
    public String executeSQLquery(OAApplicationModuleImpl am,String sqlquery)
    {
        OADBTransaction     oadbtransactionimpl     =   am.getOADBTransaction();
        PreparedStatement   prepareStatement        =   null;
        ResultSet           resultSet               =   null;
        OracleConnection    conn                    =   (OracleConnection)oadbtransactionimpl.getJdbcConnection();
        String              result                  =   null;
        try
        {
            String          query                   =   sqlquery;
                            prepareStatement        =   conn.prepareStatement(query);
                            resultSet               =   prepareStatement.executeQuery();
            if(resultSet.next())
            {
                result  =   resultSet.getString(1);
                resultSet.close();
                prepareStatement.close();
            }
        }
        catch(Exception e)
        {
            System.out.println("Execute SQL Query Exc --> " + e);
        }
        return result;
    }
     
        // Report code
        
         
    public XMLNode getOrderReportDetailsXML(LcIssuanceAMImpl am,String lcRequestID) 
    {
          //XMLNode lo_xmlNode;
        System.out.println("Inside the AM Method");
        OAViewObject vo =  am.getLCInlandFormatVO();
        if (!vo.isPreparedForExecution()){vo.executeQuery();}
          vo.clearCache();
          vo.setWhereClause("1=2");
          vo.setWhereClauseParams(null);
          vo.setWhereClause("lc_request_id="+lcRequestID);
          vo.executeQuery();
          XMLNode  lo_xmlNode = (XMLNode)vo.writeXML(-1, 0L);//OL Stands for XMLInterface.XML_OPT_ALL_ROWS which means to includes all rows in the view object's row set in the XML.
          return lo_xmlNode;
       }
    public XMLNode getPrintDataXML(OAViewObject vo)//XMLNode
    {
    System.out.println("Inside Get Print Data XML");
    XMLNode xmlNode = (XMLNode) vo.writeXML(4, XMLInterface.XML_OPT_ALL_ROWS);
    return xmlNode;
    }
    public void xmlreport(OAPageContext pageContext , OAWebBean webBean , OAViewObject vo , String fileName , String shortCode)
    {
    System.out.println("Inside XML Report");
    DataObject sessionDictionary = (DataObject)pageContext.getNamedDataObject("_SessionParameters");

    HttpServletResponse response = (HttpServletResponse)sessionDictionary.selectValue(null,"HttpServletResponse");
    try
    {
    System.out.println("Inside Try");
    ServletOutputStream os = response.getOutputStream();
    String contentDisposition = "attachment;filename=" + fileName + ".pdf";
    response.setHeader("Content-Disposition",contentDisposition);
    response.setContentType("application/pdf");
    // Get the Data XML File as the XMLNode
    System.out.println("1");
    XMLNode xmlNode = getPrintDataXML(vo);
    System.out.println("2");
    System.out.println("xmlNode -->"+xmlNode);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    xmlNode.print(outputStream);
    System.out.println("After calling XML generation method" + outputStream.toString());
    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    ByteArrayOutputStream pdfFile = new ByteArrayOutputStream();
    //Generate the PDF Report.
    //Process Template
    TemplateHelper.processTemplate
    (
    ((OADBTransactionImpl)pageContext.getApplicationModule(webBean).getOADBTransaction()).getAppsContext(),
    "SFC",//APPLICATION SHORT NAME
    shortCode, //TEMPLATE_SHORT_CODE
    ((OADBTransactionImpl)pageContext.getApplicationModule(webBean).getOADBTransaction()).getUserLocale().getLanguage(),
    ((OADBTransactionImpl)pageContext.getApplicationModule(webBean).getOADBTransaction()).getUserLocale().getCountry(),
    inputStream,
    TemplateHelper.OUTPUT_TYPE_PDF,
    null,
    pdfFile
    );
    byte[] b = pdfFile.toByteArray();
    response.setContentLength(b.length);
    os.write(b, 0, b.length);
    os.flush();
    os.close();
    pdfFile.flush();
    pdfFile.close();
    }
    catch(Exception e)
    {
    System.out.println("Exc --> " + e);
    response.setContentType("text/html");
    throw new OAException(e.getMessage(), OAException.ERROR);
    }
    System.out.println("3");
    pageContext.setDocumentRendered(true);
    }

}
