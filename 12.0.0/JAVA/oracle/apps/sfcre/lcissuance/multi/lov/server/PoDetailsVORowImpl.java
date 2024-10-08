package oracle.apps.sfc.lcissuance.multi.lov.server;

import oracle.apps.fnd.framework.server.OAViewRowImpl;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PoDetailsVORowImpl extends OAViewRowImpl {
    public static final int VENDORID = 0;
    public static final int VENDORNAME = 1;
    public static final int VENDORSITEID = 2;
    public static final int LCCOUNTRYEXPIRE = 3;
    public static final int SITEADDRESS = 4;
    public static final int POHEADERID = 5;
    public static final int PONUMBER = 6;
    public static final int POCURRENCY = 7;
    public static final int PODATE = 8;
    public static final int LCTRADETERMS = 9;
    public static final int LCLASTDATEOFSHIPMENT = 10;
    public static final int LCPAYMENTTERMS = 11;
    public static final int POREMAININGVALUE = 12;
    public static final int POVALUE = 13;
    public static final int OUNAME = 14;
    public static final int VENDORSITECODE = 15;
    public static final int ORGANIZATIONID = 16;

    /**This is the default constructor (do not remove)
     */
    public PoDetailsVORowImpl() {
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

    /**Gets the attribute value for the calculated attribute VendorSiteId
     */
    public Number getVendorSiteId() {
        return (Number) getAttributeInternal(VENDORSITEID);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute VendorSiteId
     */
    public void setVendorSiteId(Number value) {
        setAttributeInternal(VENDORSITEID, value);
    }

    /**Gets the attribute value for the calculated attribute LcCountryExpire
     */
    public String getLcCountryExpire() {
        return (String) getAttributeInternal(LCCOUNTRYEXPIRE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute LcCountryExpire
     */
    public void setLcCountryExpire(String value) {
        setAttributeInternal(LCCOUNTRYEXPIRE, value);
    }

    /**Gets the attribute value for the calculated attribute SiteAddress
     */
    public String getSiteAddress() {
        return (String) getAttributeInternal(SITEADDRESS);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute SiteAddress
     */
    public void setSiteAddress(String value) {
        setAttributeInternal(SITEADDRESS, value);
    }

    /**Gets the attribute value for the calculated attribute PoHeaderId
     */
    public Number getPoHeaderId() {
        return (Number) getAttributeInternal(POHEADERID);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute PoHeaderId
     */
    public void setPoHeaderId(Number value) {
        setAttributeInternal(POHEADERID, value);
    }

    /**Gets the attribute value for the calculated attribute PoNumber
     */
    public String getPoNumber() {
        return (String) getAttributeInternal(PONUMBER);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute PoNumber
     */
    public void setPoNumber(String value) {
        setAttributeInternal(PONUMBER, value);
    }

    /**Gets the attribute value for the calculated attribute PoCurrency
     */
    public String getPoCurrency() {
        return (String) getAttributeInternal(POCURRENCY);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute PoCurrency
     */
    public void setPoCurrency(String value) {
        setAttributeInternal(POCURRENCY, value);
    }

    /**Gets the attribute value for the calculated attribute PoDate
     */
    public Date getPoDate() {
        return (Date) getAttributeInternal(PODATE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute PoDate
     */
    public void setPoDate(Date value) {
        setAttributeInternal(PODATE, value);
    }

    /**Gets the attribute value for the calculated attribute LcTradeTerms
     */
    public String getLcTradeTerms() {
        return (String) getAttributeInternal(LCTRADETERMS);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute LcTradeTerms
     */
    public void setLcTradeTerms(String value) {
        setAttributeInternal(LCTRADETERMS, value);
    }

    /**Gets the attribute value for the calculated attribute LcLastDateOfShipment
     */
    public Date getLcLastDateOfShipment() {
        return (Date) getAttributeInternal(LCLASTDATEOFSHIPMENT);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute LcLastDateOfShipment
     */
    public void setLcLastDateOfShipment(Date value) {
        setAttributeInternal(LCLASTDATEOFSHIPMENT, value);
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
        case VENDORID:
            return getVendorId();
        case VENDORNAME:
            return getVendorName();
        case VENDORSITEID:
            return getVendorSiteId();
        case LCCOUNTRYEXPIRE:
            return getLcCountryExpire();
        case SITEADDRESS:
            return getSiteAddress();
        case POHEADERID:
            return getPoHeaderId();
        case PONUMBER:
            return getPoNumber();
        case POCURRENCY:
            return getPoCurrency();
        case PODATE:
            return getPoDate();
        case LCTRADETERMS:
            return getLcTradeTerms();
        case LCLASTDATEOFSHIPMENT:
            return getLcLastDateOfShipment();
        case LCPAYMENTTERMS:
            return getLcPaymentTerms();
        case POREMAININGVALUE:
            return getPoRemainingValue();
        case POVALUE:
            return getPoValue();
        case OUNAME:
            return getOuName();
        case VENDORSITECODE:
            return getVendorSiteCode();
        case ORGANIZATIONID:
            return getOrganizationId();
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
        case VENDORSITEID:
            setVendorSiteId((Number)value);
            return;
        case LCCOUNTRYEXPIRE:
            setLcCountryExpire((String)value);
            return;
        case SITEADDRESS:
            setSiteAddress((String)value);
            return;
        case POHEADERID:
            setPoHeaderId((Number)value);
            return;
        case PONUMBER:
            setPoNumber((String)value);
            return;
        case POCURRENCY:
            setPoCurrency((String)value);
            return;
        case PODATE:
            setPoDate((Date)value);
            return;
        case LCTRADETERMS:
            setLcTradeTerms((String)value);
            return;
        case LCLASTDATEOFSHIPMENT:
            setLcLastDateOfShipment((Date)value);
            return;
        case LCPAYMENTTERMS:
            setLcPaymentTerms((String)value);
            return;
        case POREMAININGVALUE:
            setPoRemainingValue((Number)value);
            return;
        case POVALUE:
            setPoValue((Number)value);
            return;
        case OUNAME:
            setOuName((String)value);
            return;
        case VENDORSITECODE:
            setVendorSiteCode((String)value);
            return;
        case ORGANIZATIONID:
            setOrganizationId((Number)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Gets the attribute value for the calculated attribute PoRemainingValue
     */
    public Number getPoRemainingValue() {
        return (Number) getAttributeInternal(POREMAININGVALUE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute PoRemainingValue
     */
    public void setPoRemainingValue(Number value) {
        setAttributeInternal(POREMAININGVALUE, value);
    }

    /**Gets the attribute value for the calculated attribute PoValue
     */
    public Number getPoValue() {
        return (Number) getAttributeInternal(POVALUE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute PoValue
     */
    public void setPoValue(Number value) {
        setAttributeInternal(POVALUE, value);
    }

    /**Gets the attribute value for the calculated attribute OuName
     */
    public String getOuName() {
        return (String) getAttributeInternal(OUNAME);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute OuName
     */
    public void setOuName(String value) {
        setAttributeInternal(OUNAME, value);
    }

    /**Gets the attribute value for the calculated attribute VendorSiteCode
     */
    public String getVendorSiteCode() {
        return (String) getAttributeInternal(VENDORSITECODE);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute VendorSiteCode
     */
    public void setVendorSiteCode(String value) {
        setAttributeInternal(VENDORSITECODE, value);
    }

    /**Gets the attribute value for the calculated attribute OrganizationId
     */
    public Number getOrganizationId() {
        return (Number) getAttributeInternal(ORGANIZATIONID);
    }

    /**Sets <code>value</code> as the attribute value for the calculated attribute OrganizationId
     */
    public void setOrganizationId(Number value) {
        setAttributeInternal(ORGANIZATIONID, value);
    }
}
