package oracle.apps.sfc.lcissuance.multi.lov.server;

import oracle.apps.fnd.framework.server.OAViewRowImpl;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class VendorVORowImpl extends OAViewRowImpl {
    public static final int VENDORID = 0;
    public static final int VENDORNAME = 1;

    /**This is the default constructor (do not remove)
     */
    public VendorVORowImpl() {
    }

    /**Gets the attribute value for the calculated attribute VendorId
     */
    public Number getVendorId() {
        return (Number) getAttributeInternal(VENDORID);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute VendorId
     */
    public void setVendorId(Number value) {
        setAttributeInternal(VENDORID, value);
    }

    /**Gets the attribute value for the calculated attribute VendorName
     */
    public String getVendorName() {
        return (String) getAttributeInternal(VENDORNAME);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute VendorName
     */
    public void setVendorName(String value) {
        setAttributeInternal(VENDORNAME, value);
    }

    /**getAttrInvokeAccessor: generated method. Do not modify.
     */
    protected Object getAttrInvokeAccessor(int index, 
                                           AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case VENDORID:
            return getVendorId();
        case VENDORNAME:
            return getVendorName();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case VENDORID:
            setVendorId((Number)value);
            return;
        case VENDORNAME:
            setVendorName((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }
}