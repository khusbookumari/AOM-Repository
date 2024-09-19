package oracle.apps.sfc.lcissuance.multi.schema.server;

import oracle.apps.fnd.framework.server.OAEntityDefImpl;
import oracle.apps.fnd.framework.server.OAEntityImpl;

import oracle.jbo.AttributeList;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.RowID;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LCApprHistoryEOImpl extends OAEntityImpl {
    public static final int LCWFHISTID = 0;
    public static final int LCREQUESTID = 1;
    public static final int RESPONSE = 2;
    public static final int APPROVERCOMMENTS = 3;
    public static final int APPROVERID = 4;
    public static final int APPROVERNAME = 5;
    public static final int WFITEMKEY = 6;
    public static final int SUBMISSIONDATE = 7;
    public static final int ACTIONDATE = 8;
    public static final int ORGANIZATION = 9;
    public static final int APPROVERROLE = 10;
    public static final int ATTRIBUTECATEGORY = 11;
    public static final int ATTRIBUTE1 = 12;
    public static final int ATTRIBUTE2 = 13;
    public static final int ATTRIBUTE3 = 14;
    public static final int ATTRIBUTE4 = 15;
    public static final int ATTRIBUTE5 = 16;
    public static final int ATTRIBUTE6 = 17;
    public static final int ATTRIBUTE7 = 18;
    public static final int ATTRIBUTE8 = 19;
    public static final int ATTRIBUTE9 = 20;
    public static final int ATTRIBUTE10 = 21;
    public static final int ATTRIBUTE11 = 22;
    public static final int ATTRIBUTE12 = 23;
    public static final int ATTRIBUTE13 = 24;
    public static final int ATTRIBUTE14 = 25;
    public static final int ATTRIBUTE15 = 26;
    public static final int CREATEDBY = 27;
    public static final int CREATIONDATE = 28;
    public static final int LASTUPDATEDBY = 29;
    public static final int LASTUPDATEDATE = 30;
    public static final int LASTUPDATELOGIN = 31;
    public static final int ROWID = 32;
    public static final int LCMODE = 33;


    private static OAEntityDefImpl mDefinitionObject;

    /**This is the default constructor (do not remove)
     */
    public LCApprHistoryEOImpl() {
    }


    /**Retrieves the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = 
                    (OAEntityDefImpl)EntityDefImpl.findDefObject("oracle.apps.sfc.lcissuance.multi.schema.server.LCApprHistoryEO");
        }
        return mDefinitionObject;
    }

    /**Add attribute defaulting logic in this method.
     */
    public void create(AttributeList attributeList) {
        super.create(attributeList);
    }

    /**Add entity remove logic in this method.
     */
    public void remove() {
        super.remove();
    }

    /**Add Entity validation code in this method.
     */
    protected void validateEntity() {
        super.validateEntity();
    }

    /**Add locking logic here.
     */
    public void lock() {
        super.lock();
    }

    /**Custom DML update/insert/delete logic here.
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
    }

    /**Gets the attribute value for LcWfHistId, using the alias name LcWfHistId
     */
    public Number getLcWfHistId() {
        return (Number)getAttributeInternal(LCWFHISTID);
    }

    /**Sets <code>value</code> as the attribute value for LcWfHistId
     */
    public void setLcWfHistId(Number value) {
        setAttributeInternal(LCWFHISTID, value);
    }

    /**Gets the attribute value for LcRequestId, using the alias name LcRequestId
     */
    public Number getLcRequestId() {
        return (Number)getAttributeInternal(LCREQUESTID);
    }

    /**Sets <code>value</code> as the attribute value for LcRequestId
     */
    public void setLcRequestId(Number value) {
        setAttributeInternal(LCREQUESTID, value);
    }

    /**Gets the attribute value for Response, using the alias name Response
     */
    public String getResponse() {
        return (String)getAttributeInternal(RESPONSE);
    }

    /**Sets <code>value</code> as the attribute value for Response
     */
    public void setResponse(String value) {
        setAttributeInternal(RESPONSE, value);
    }

    /**Gets the attribute value for ApproverComments, using the alias name ApproverComments
     */
    public String getApproverComments() {
        return (String)getAttributeInternal(APPROVERCOMMENTS);
    }

    /**Sets <code>value</code> as the attribute value for ApproverComments
     */
    public void setApproverComments(String value) {
        setAttributeInternal(APPROVERCOMMENTS, value);
    }

    /**Gets the attribute value for ApproverId, using the alias name ApproverId
     */
    public Number getApproverId() {
        return (Number)getAttributeInternal(APPROVERID);
    }

    /**Sets <code>value</code> as the attribute value for ApproverId
     */
    public void setApproverId(Number value) {
        setAttributeInternal(APPROVERID, value);
    }

    /**Gets the attribute value for ApproverName, using the alias name ApproverName
     */
    public String getApproverName() {
        return (String)getAttributeInternal(APPROVERNAME);
    }

    /**Sets <code>value</code> as the attribute value for ApproverName
     */
    public void setApproverName(String value) {
        setAttributeInternal(APPROVERNAME, value);
    }

    /**Gets the attribute value for WfItemKey, using the alias name WfItemKey
     */
    public String getWfItemKey() {
        return (String)getAttributeInternal(WFITEMKEY);
    }

    /**Sets <code>value</code> as the attribute value for WfItemKey
     */
    public void setWfItemKey(String value) {
        setAttributeInternal(WFITEMKEY, value);
    }

    /**Gets the attribute value for SubmissionDate, using the alias name SubmissionDate
     */
    public Date getSubmissionDate() {
        return (Date)getAttributeInternal(SUBMISSIONDATE);
    }

    /**Sets <code>value</code> as the attribute value for SubmissionDate
     */
    public void setSubmissionDate(Date value) {
        setAttributeInternal(SUBMISSIONDATE, value);
    }

    /**Gets the attribute value for ActionDate, using the alias name ActionDate
     */
    public Date getActionDate() {
        return (Date)getAttributeInternal(ACTIONDATE);
    }

    /**Sets <code>value</code> as the attribute value for ActionDate
     */
    public void setActionDate(Date value) {
        setAttributeInternal(ACTIONDATE, value);
    }

    /**Gets the attribute value for Organization, using the alias name Organization
     */
    public String getOrganization() {
        return (String)getAttributeInternal(ORGANIZATION);
    }

    /**Sets <code>value</code> as the attribute value for Organization
     */
    public void setOrganization(String value) {
        setAttributeInternal(ORGANIZATION, value);
    }

    /**Gets the attribute value for ApproverRole, using the alias name ApproverRole
     */
    public String getApproverRole() {
        return (String)getAttributeInternal(APPROVERROLE);
    }

    /**Sets <code>value</code> as the attribute value for ApproverRole
     */
    public void setApproverRole(String value) {
        setAttributeInternal(APPROVERROLE, value);
    }

    /**Gets the attribute value for AttributeCategory, using the alias name AttributeCategory
     */
    public String getAttributeCategory() {
        return (String)getAttributeInternal(ATTRIBUTECATEGORY);
    }

    /**Sets <code>value</code> as the attribute value for AttributeCategory
     */
    public void setAttributeCategory(String value) {
        setAttributeInternal(ATTRIBUTECATEGORY, value);
    }

    /**Gets the attribute value for Attribute1, using the alias name Attribute1
     */
    public String getAttribute1() {
        return (String)getAttributeInternal(ATTRIBUTE1);
    }

    /**Sets <code>value</code> as the attribute value for Attribute1
     */
    public void setAttribute1(String value) {
        setAttributeInternal(ATTRIBUTE1, value);
    }

    /**Gets the attribute value for Attribute2, using the alias name Attribute2
     */
    public String getAttribute2() {
        return (String)getAttributeInternal(ATTRIBUTE2);
    }

    /**Sets <code>value</code> as the attribute value for Attribute2
     */
    public void setAttribute2(String value) {
        setAttributeInternal(ATTRIBUTE2, value);
    }

    /**Gets the attribute value for Attribute3, using the alias name Attribute3
     */
    public String getAttribute3() {
        return (String)getAttributeInternal(ATTRIBUTE3);
    }

    /**Sets <code>value</code> as the attribute value for Attribute3
     */
    public void setAttribute3(String value) {
        setAttributeInternal(ATTRIBUTE3, value);
    }

    /**Gets the attribute value for Attribute4, using the alias name Attribute4
     */
    public String getAttribute4() {
        return (String)getAttributeInternal(ATTRIBUTE4);
    }

    /**Sets <code>value</code> as the attribute value for Attribute4
     */
    public void setAttribute4(String value) {
        setAttributeInternal(ATTRIBUTE4, value);
    }

    /**Gets the attribute value for Attribute5, using the alias name Attribute5
     */
    public String getAttribute5() {
        return (String)getAttributeInternal(ATTRIBUTE5);
    }

    /**Sets <code>value</code> as the attribute value for Attribute5
     */
    public void setAttribute5(String value) {
        setAttributeInternal(ATTRIBUTE5, value);
    }

    /**Gets the attribute value for Attribute6, using the alias name Attribute6
     */
    public String getAttribute6() {
        return (String)getAttributeInternal(ATTRIBUTE6);
    }

    /**Sets <code>value</code> as the attribute value for Attribute6
     */
    public void setAttribute6(String value) {
        setAttributeInternal(ATTRIBUTE6, value);
    }

    /**Gets the attribute value for Attribute7, using the alias name Attribute7
     */
    public String getAttribute7() {
        return (String)getAttributeInternal(ATTRIBUTE7);
    }

    /**Sets <code>value</code> as the attribute value for Attribute7
     */
    public void setAttribute7(String value) {
        setAttributeInternal(ATTRIBUTE7, value);
    }

    /**Gets the attribute value for Attribute8, using the alias name Attribute8
     */
    public String getAttribute8() {
        return (String)getAttributeInternal(ATTRIBUTE8);
    }

    /**Sets <code>value</code> as the attribute value for Attribute8
     */
    public void setAttribute8(String value) {
        setAttributeInternal(ATTRIBUTE8, value);
    }

    /**Gets the attribute value for Attribute9, using the alias name Attribute9
     */
    public String getAttribute9() {
        return (String)getAttributeInternal(ATTRIBUTE9);
    }

    /**Sets <code>value</code> as the attribute value for Attribute9
     */
    public void setAttribute9(String value) {
        setAttributeInternal(ATTRIBUTE9, value);
    }

    /**Gets the attribute value for Attribute10, using the alias name Attribute10
     */
    public String getAttribute10() {
        return (String)getAttributeInternal(ATTRIBUTE10);
    }

    /**Sets <code>value</code> as the attribute value for Attribute10
     */
    public void setAttribute10(String value) {
        setAttributeInternal(ATTRIBUTE10, value);
    }

    /**Gets the attribute value for Attribute11, using the alias name Attribute11
     */
    public String getAttribute11() {
        return (String)getAttributeInternal(ATTRIBUTE11);
    }

    /**Sets <code>value</code> as the attribute value for Attribute11
     */
    public void setAttribute11(String value) {
        setAttributeInternal(ATTRIBUTE11, value);
    }

    /**Gets the attribute value for Attribute12, using the alias name Attribute12
     */
    public String getAttribute12() {
        return (String)getAttributeInternal(ATTRIBUTE12);
    }

    /**Sets <code>value</code> as the attribute value for Attribute12
     */
    public void setAttribute12(String value) {
        setAttributeInternal(ATTRIBUTE12, value);
    }

    /**Gets the attribute value for Attribute13, using the alias name Attribute13
     */
    public String getAttribute13() {
        return (String)getAttributeInternal(ATTRIBUTE13);
    }

    /**Sets <code>value</code> as the attribute value for Attribute13
     */
    public void setAttribute13(String value) {
        setAttributeInternal(ATTRIBUTE13, value);
    }

    /**Gets the attribute value for Attribute14, using the alias name Attribute14
     */
    public String getAttribute14() {
        return (String)getAttributeInternal(ATTRIBUTE14);
    }

    /**Sets <code>value</code> as the attribute value for Attribute14
     */
    public void setAttribute14(String value) {
        setAttributeInternal(ATTRIBUTE14, value);
    }

    /**Gets the attribute value for Attribute15, using the alias name Attribute15
     */
    public String getAttribute15() {
        return (String)getAttributeInternal(ATTRIBUTE15);
    }

    /**Sets <code>value</code> as the attribute value for Attribute15
     */
    public void setAttribute15(String value) {
        setAttributeInternal(ATTRIBUTE15, value);
    }

    /**Gets the attribute value for CreatedBy, using the alias name CreatedBy
     */
    public Number getCreatedBy() {
        return (Number)getAttributeInternal(CREATEDBY);
    }

    /**Sets <code>value</code> as the attribute value for CreatedBy
     */
    public void setCreatedBy(Number value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**Gets the attribute value for CreationDate, using the alias name CreationDate
     */
    public Date getCreationDate() {
        return (Date)getAttributeInternal(CREATIONDATE);
    }

    /**Sets <code>value</code> as the attribute value for CreationDate
     */
    public void setCreationDate(Date value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**Gets the attribute value for LastUpdatedBy, using the alias name LastUpdatedBy
     */
    public Number getLastUpdatedBy() {
        return (Number)getAttributeInternal(LASTUPDATEDBY);
    }

    /**Sets <code>value</code> as the attribute value for LastUpdatedBy
     */
    public void setLastUpdatedBy(Number value) {
        setAttributeInternal(LASTUPDATEDBY, value);
    }

    /**Gets the attribute value for LastUpdateDate, using the alias name LastUpdateDate
     */
    public Date getLastUpdateDate() {
        return (Date)getAttributeInternal(LASTUPDATEDATE);
    }

    /**Sets <code>value</code> as the attribute value for LastUpdateDate
     */
    public void setLastUpdateDate(Date value) {
        setAttributeInternal(LASTUPDATEDATE, value);
    }

    /**Gets the attribute value for LastUpdateLogin, using the alias name LastUpdateLogin
     */
    public Number getLastUpdateLogin() {
        return (Number)getAttributeInternal(LASTUPDATELOGIN);
    }

    /**Sets <code>value</code> as the attribute value for LastUpdateLogin
     */
    public void setLastUpdateLogin(Number value) {
        setAttributeInternal(LASTUPDATELOGIN, value);
    }

    /**Gets the attribute value for RowID, using the alias name RowID
     */
    public RowID getRowID() {
        return (RowID)getAttributeInternal(ROWID);
    }

    /**getAttrInvokeAccessor: generated method. Do not modify.
     */
    protected Object getAttrInvokeAccessor(int index, 
                                           AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case LCWFHISTID:
            return getLcWfHistId();
        case LCREQUESTID:
            return getLcRequestId();
        case RESPONSE:
            return getResponse();
        case APPROVERCOMMENTS:
            return getApproverComments();
        case APPROVERID:
            return getApproverId();
        case APPROVERNAME:
            return getApproverName();
        case WFITEMKEY:
            return getWfItemKey();
        case SUBMISSIONDATE:
            return getSubmissionDate();
        case ACTIONDATE:
            return getActionDate();
        case ORGANIZATION:
            return getOrganization();
        case APPROVERROLE:
            return getApproverRole();
        case ATTRIBUTECATEGORY:
            return getAttributeCategory();
        case ATTRIBUTE1:
            return getAttribute1();
        case ATTRIBUTE2:
            return getAttribute2();
        case ATTRIBUTE3:
            return getAttribute3();
        case ATTRIBUTE4:
            return getAttribute4();
        case ATTRIBUTE5:
            return getAttribute5();
        case ATTRIBUTE6:
            return getAttribute6();
        case ATTRIBUTE7:
            return getAttribute7();
        case ATTRIBUTE8:
            return getAttribute8();
        case ATTRIBUTE9:
            return getAttribute9();
        case ATTRIBUTE10:
            return getAttribute10();
        case ATTRIBUTE11:
            return getAttribute11();
        case ATTRIBUTE12:
            return getAttribute12();
        case ATTRIBUTE13:
            return getAttribute13();
        case ATTRIBUTE14:
            return getAttribute14();
        case ATTRIBUTE15:
            return getAttribute15();
        case CREATEDBY:
            return getCreatedBy();
        case CREATIONDATE:
            return getCreationDate();
        case LASTUPDATEDBY:
            return getLastUpdatedBy();
        case LASTUPDATEDATE:
            return getLastUpdateDate();
        case LASTUPDATELOGIN:
            return getLastUpdateLogin();
        case ROWID:
            return getRowID();
        case LCMODE:
            return getLcMode();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case LCWFHISTID:
            setLcWfHistId((Number)value);
            return;
        case LCREQUESTID:
            setLcRequestId((Number)value);
            return;
        case RESPONSE:
            setResponse((String)value);
            return;
        case APPROVERCOMMENTS:
            setApproverComments((String)value);
            return;
        case APPROVERID:
            setApproverId((Number)value);
            return;
        case APPROVERNAME:
            setApproverName((String)value);
            return;
        case WFITEMKEY:
            setWfItemKey((String)value);
            return;
        case SUBMISSIONDATE:
            setSubmissionDate((Date)value);
            return;
        case ACTIONDATE:
            setActionDate((Date)value);
            return;
        case ORGANIZATION:
            setOrganization((String)value);
            return;
        case APPROVERROLE:
            setApproverRole((String)value);
            return;
        case ATTRIBUTECATEGORY:
            setAttributeCategory((String)value);
            return;
        case ATTRIBUTE1:
            setAttribute1((String)value);
            return;
        case ATTRIBUTE2:
            setAttribute2((String)value);
            return;
        case ATTRIBUTE3:
            setAttribute3((String)value);
            return;
        case ATTRIBUTE4:
            setAttribute4((String)value);
            return;
        case ATTRIBUTE5:
            setAttribute5((String)value);
            return;
        case ATTRIBUTE6:
            setAttribute6((String)value);
            return;
        case ATTRIBUTE7:
            setAttribute7((String)value);
            return;
        case ATTRIBUTE8:
            setAttribute8((String)value);
            return;
        case ATTRIBUTE9:
            setAttribute9((String)value);
            return;
        case ATTRIBUTE10:
            setAttribute10((String)value);
            return;
        case ATTRIBUTE11:
            setAttribute11((String)value);
            return;
        case ATTRIBUTE12:
            setAttribute12((String)value);
            return;
        case ATTRIBUTE13:
            setAttribute13((String)value);
            return;
        case ATTRIBUTE14:
            setAttribute14((String)value);
            return;
        case ATTRIBUTE15:
            setAttribute15((String)value);
            return;
        case CREATEDBY:
            setCreatedBy((Number)value);
            return;
        case CREATIONDATE:
            setCreationDate((Date)value);
            return;
        case LASTUPDATEDBY:
            setLastUpdatedBy((Number)value);
            return;
        case LASTUPDATEDATE:
            setLastUpdateDate((Date)value);
            return;
        case LASTUPDATELOGIN:
            setLastUpdateLogin((Number)value);
            return;
        case LCMODE:
            setLcMode((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the attribute value for LcMode, using the alias name LcMode
     */
    public String getLcMode() {
        return (String)getAttributeInternal(LCMODE);
    }

    /**Sets <code>value</code> as the attribute value for LcMode
     */
    public void setLcMode(String value) {
        setAttributeInternal(LCMODE, value);
    }
}