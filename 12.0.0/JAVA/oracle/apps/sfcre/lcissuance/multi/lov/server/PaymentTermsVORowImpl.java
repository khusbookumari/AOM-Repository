package oracle.apps.sfc.lcissuance.multi.lov.server;

import oracle.apps.fnd.framework.server.OAViewRowImpl;

import oracle.jbo.server.AttributeDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PaymentTermsVORowImpl extends OAViewRowImpl {


    public static final int LCPAYMENTTERMS = 0;

    /**This is the default constructor (do not remove)
     */
    public PaymentTermsVORowImpl() {
    }

    /**Gets the attribute value for the calculated attribute LcPaymentTerms
     */
    public String getLcPaymentTerms() {
        return (String) getAttributeInternal(LCPAYMENTTERMS);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute LcPaymentTerms
     */
    public void setLcPaymentTerms(String value) {
        setAttributeInternal(LCPAYMENTTERMS, value);
    }

    /**getAttrInvokeAccessor: generated method. Do not modify.
     */
    protected Object getAttrInvokeAccessor(int index, 
                                           AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case LCPAYMENTTERMS:
            return getLcPaymentTerms();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case LCPAYMENTTERMS:
            setLcPaymentTerms((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }
}