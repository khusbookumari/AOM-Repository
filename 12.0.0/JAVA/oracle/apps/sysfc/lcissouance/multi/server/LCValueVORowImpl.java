package oracle.apps.sfc.lcissuance.multi.server;

import oracle.apps.fnd.framework.server.OAViewRowImpl;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LCValueVORowImpl extends OAViewRowImpl {
    public static final int LCREQUESTID = 0;
    public static final int LCVALUE = 1;
    public static final int TOTALPOVALUE = 2;
    public static final int LCVALUEWORDS = 3;

    /**This is the default constructor (do not remove)
     */
    public LCValueVORowImpl() {
    }

    /**Gets the attribute value for the calculated attribute LcValue
     */
    public Number getLcValue() {
        return (Number) getAttributeInternal(LCVALUE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute LcValue
     */
    public void setLcValue(Number value) {
        setAttributeInternal(LCVALUE, value);
    }

    /**Gets the attribute value for the calculated attribute TotalPoValue
     */
    public Number getTotalPoValue() {
        return (Number) getAttributeInternal(TOTALPOVALUE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute TotalPoValue
     */
    public void setTotalPoValue(Number value) {
        setAttributeInternal(TOTALPOVALUE, value);
    }

    /**Gets the attribute value for the calculated attribute LcValueWords
     */
    public String getLcValueWords() {
        return (String) getAttributeInternal(LCVALUEWORDS);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute LcValueWords
     */
    public void setLcValueWords(String value) {
        setAttributeInternal(LCVALUEWORDS, value);
    }

    /**getAttrInvokeAccessor: generated method. Do not modify.
     */
    protected Object getAttrInvokeAccessor(int index, 
                                           AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case LCREQUESTID:
            return getLcRequestId();
        case LCVALUE:
            return getLcValue();
        case TOTALPOVALUE:
            return getTotalPoValue();
        case LCVALUEWORDS:
            return getLcValueWords();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case LCREQUESTID:
            setLcRequestId((Number)value);
            return;
        case LCVALUE:
            setLcValue((Number)value);
            return;
        case TOTALPOVALUE:
            setTotalPoValue((Number)value);
            return;
        case LCVALUEWORDS:
            setLcValueWords((String)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the attribute value for the calculated attribute LcRequestId
     */
    public Number getLcRequestId() {
        return (Number) getAttributeInternal(LCREQUESTID);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute LcRequestId
     */
    public void setLcRequestId(Number value) {
        setAttributeInternal(LCREQUESTID, value);
    }
}
