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
public class LCPoDetailsEOImpl extends OAEntityImpl {
    public static final int LCPODETID = 0;
    public static final int LCREQUESTID = 1;
    public static final int POHEADERID = 2;
    public static final int POVALUE = 3;
    public static final int POREMAININGVALUE = 4;
    public static final int POENTEREDVALUE = 5;
    public static final int LCPROFORMAINVNO = 6;
    public static final int LCPROFORMAINVDATE = 7;
    public static final int LCPOCOMMENTS = 8;
    public static final int ATTRIBUTECATEGORY = 9;
    public static final int ATTRIBUTE1 = 10;
    public static final int ATTRIBUTE2 = 11;
    public static final int ATTRIBUTE3 = 12;
    public static final int ATTRIBUTE4 = 13;
    public static final int ATTRIBUTE5 = 14;
    public static final int ATTRIBUTE6 = 15;
    public static final int ATTRIBUTE7 = 16;
    public static final int ATTRIBUTE8 = 17;
    public static final int ATTRIBUTE9 = 18;
    public static final int ATTRIBUTE10 = 19;
    public static final int ATTRIBUTE11 = 20;
    public static final int ATTRIBUTE12 = 21;
    public static final int ATTRIBUTE13 = 22;
    public static final int ATTRIBUTE14 = 23;
    public static final int ATTRIBUTE15 = 24;
    public static final int CREATIONDATE = 25;
    public static final int CREATEDBY = 26;
    public static final int LASTUPDATEDATE = 27;
    public static final int LASTUPDATEDBY = 28;
    public static final int LASTUPDATELOGIN = 29;
    public static final int ROWID = 30;


    private static OAEntityDefImpl mDefinitionObject;

    /**This is the default constructor (do not remove)
     */
    public LCPoDetailsEOImpl() {
    }


    /**Retrieves the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = 
                    (OAEntityDefImpl)EntityDefImpl.findDefObject("oracle.apps.sfc.lcissuance.multi.schema.server.LCPoDetailsEO");
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

    /**Gets the attribute value for LcPoDetId, using the alias name LcPoDetId
     */
    public Number getLcPoDetId() {
        return (Number)getAttributeInternal(LCPODETID);
    }

    /**Sets <code>value</code> as the attribute value for LcPoDetId
     */
    public void setLcPoDetId(Number value) {
        setAttributeInternal(LCPODETID, value);
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

    /**Gets the attribute value for PoHeaderId, using the alias name PoHeaderId
     */
    public Number getPoHeaderId() {
        return (Number)getAttributeInternal(POHEADERID);
    }

    /**Sets <code>value</code> as the attribute value for PoHeaderId
     */
    public void setPoHeaderId(Number value) {
        setAttributeInternal(POHEADERID, value);
    }

    /**Gets the attribute value for PoValue, using the alias name PoValue
     */
    public Number getPoValue() {
        return (Number)getAttributeInternal(POVALUE);
    }

    /**Sets <code>value</code> as the attribute value for PoValue
     */
    public void setPoValue(Number value) {
        setAttributeInternal(POVALUE, value);
    }

    /**Gets the attribute value for PoRemainingValue, using the alias name PoRemainingValue
     */
    public Number getPoRemainingValue() {
        return (Number)getAttributeInternal(POREMAININGVALUE);
    }

    /**Sets <code>value</code> as the attribute value for PoRemainingValue
     */
    public void setPoRemainingValue(Number value) {
        setAttributeInternal(POREMAININGVALUE, value);
    }

    /**Gets the attribute value for PoEnteredValue, using the alias name PoEnteredValue
     */
    public Number getPoEnteredValue() {
        return (Number)getAttributeInternal(POENTEREDVALUE);
    }

    /**Sets <code>value</code> as the attribute value for PoEnteredValue
     */
    public void setPoEnteredValue(Number value) {
        setAttributeInternal(POENTEREDVALUE, value);
    }

    /**Gets the attribute value for LcProformaInvNo, using the alias name LcProformaInvNo
     */
    public String getLcProformaInvNo() {
        return (String)getAttributeInternal(LCPROFORMAINVNO);
    }

    /**Sets <code>value</code> as the attribute value for LcProformaInvNo
     */
    public void setLcProformaInvNo(String value) {
        setAttributeInternal(LCPROFORMAINVNO, value);
    }

    /**Gets the attribute value for LcProformaInvDate, using the alias name LcProformaInvDate
     */
    public Date getLcProformaInvDate() {
        return (Date)getAttributeInternal(LCPROFORMAINVDATE);
    }

    /**Sets <code>value</code> as the attribute value for LcProformaInvDate
     */
    public void setLcProformaInvDate(Date value) {
        setAttributeInternal(LCPROFORMAINVDATE, value);
    }

    /**Gets the attribute value for LcPoComments, using the alias name LcPoComments
     */
    public String getLcPoComments() {
        return (String)getAttributeInternal(LCPOCOMMENTS);
    }

    /**Sets <code>value</code> as the attribute value for LcPoComments
     */
    public void setLcPoComments(String value) {
        setAttributeInternal(LCPOCOMMENTS, value);
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
        case LCPODETID:
            return getLcPoDetId();
        case LCREQUESTID:
            return getLcRequestId();
        case POHEADERID:
            return getPoHeaderId();
        case POVALUE:
            return getPoValue();
        case POREMAININGVALUE:
            return getPoRemainingValue();
        case POENTEREDVALUE:
            return getPoEnteredValue();
        case LCPROFORMAINVNO:
            return getLcProformaInvNo();
        case LCPROFORMAINVDATE:
            return getLcProformaInvDate();
        case LCPOCOMMENTS:
            return getLcPoComments();
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
        case CREATIONDATE:
            return getCreationDate();
        case CREATEDBY:
            return getCreatedBy();
        case LASTUPDATEDATE:
            return getLastUpdateDate();
        case LASTUPDATEDBY:
            return getLastUpdatedBy();
        case LASTUPDATELOGIN:
            return getLastUpdateLogin();
        case ROWID:
            return getRowID();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case LCPODETID:
            setLcPoDetId((Number)value);
            return;
        case LCREQUESTID:
            setLcRequestId((Number)value);
            return;
        case POHEADERID:
            setPoHeaderId((Number)value);
            return;
        case POVALUE:
            setPoValue((Number)value);
            return;
        case POREMAININGVALUE:
            setPoRemainingValue((Number)value);
            return;
        case POENTEREDVALUE:
            setPoEnteredValue((Number)value);
            return;
        case LCPROFORMAINVNO:
            setLcProformaInvNo((String)value);
            return;
        case LCPROFORMAINVDATE:
            setLcProformaInvDate((Date)value);
            return;
        case LCPOCOMMENTS:
            setLcPoComments((String)value);
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
        case CREATIONDATE:
            setCreationDate((Date)value);
            return;
        case CREATEDBY:
            setCreatedBy((Number)value);
            return;
        case LASTUPDATEDATE:
            setLastUpdateDate((Date)value);
            return;
        case LASTUPDATEDBY:
            setLastUpdatedBy((Number)value);
            return;
        case LASTUPDATELOGIN:
            setLastUpdateLogin((Number)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }
}
