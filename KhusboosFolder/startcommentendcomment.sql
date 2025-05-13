-- This view fetches dummy property data
/* This is a block comment for general documentation */

CREATE OR REPLACE VIEW dummyView (
    PROPERTY_ID,         -- Property unique identifier
    PROPERTY_KEY,        -- Key for the property
    PROPERTY_VALUE       -- Value associated with the key
)
AS
-- Start of the simplified SELECT query
SELECT 
    PROPERTY_ID,         -- Column from source table
    PROPERTY_KEY,        -- Column from source table
    PROPERTY_VALUE       -- Column from source table
FROM 
    t_tri2_properties cgu -- Source table alias
WHERE 
    1 = 1       

/*
Detailed original logic (replaced with simplified query above):

SELECT   batch_number,
         ROW_NUMBER () OVER (PARTITION BY hca.account_number ORDER BY hca.account_number,
          hps.party_site_number) batch_line_number,
         hca.account_number customer_number, hp.party_name customer_name,
         hca.cust_account_id, hps.party_site_number,
         (SELECT NAME
            FROM hr_operating_units
           WHERE organization_id = hcas.org_id) operating_unit,
         cgu.new_gst_number, hcas.attribute1 site_old_gst_number,
         (SELECT jpl.registration_number
            FROM jai_party_regs jpr,
                 jai_party_reg_lines jpl
           WHERE jpr.party_type_code = 'THIRD_PARTY_SITE'
             AND jpr.party_site_id = hcas.cust_acct_site_id
             AND jpr.party_reg_id = jpl.party_reg_id
             AND jpl.registration_type_code = 'GSTIN'
             AND jpl.effective_to IS NULL) third_party_old_gst_number,
         hcas.cust_acct_site_id, hl.address1, hl.address2, hl.address3,
         hl.address4, hl.city, hl.state, hl.country,
         hl.postal_code pin_code, cgu.status_message, cgu.status_flag,
         cgu.process_flag, cgu.creation_date, cgu.created_by,
         cgu.last_update_date, cgu.last_updated_by, cgu.last_update_login,
         cgu.reporting_code, cgu.fusion_update, cgu.reporting_start_date,
         cgu.reporting_end_date
    FROM hz_cust_accounts_all hca,
         xxsify_cust_gst_update_apex_t cgu,
         hz_parties hp,
         hz_party_sites hps,
         hz_locations hl,
         hz_cust_acct_sites_all hcas,
         hr_operating_units hou
   WHERE hca.account_number = cgu.customer_number
     AND hca.status = 'A'
     AND hca.party_id = hp.party_id
     AND hp.party_id = hps.party_id
     AND hps.location_id = hl.location_id
     AND hps.party_site_id = hcas.party_site_id
     AND hcas.cust_account_id = hca.cust_account_id
     AND UPPER (hl.state) = NVL (UPPER (cgu.state), UPPER (hl.state))
     AND hps.party_site_number =
                         NVL (cgu.party_site_number, hps.party_site_number)
     AND hps.status = 'A'
     AND hcas.status = 'A'
     AND hou.organization_id = NVL (cgu.org_id, hcas.org_id)
     AND NOT EXISTS (
            SELECT 1
              FROM xxsify_site_third_party_gst_upd
             WHERE batch_number = cgu.batch_number
               AND cust_acct_site_id = hcas.cust_acct_site_id
               AND status_flag = 'S')
     AND NOT EXISTS (
            SELECT 1
              FROM xxsify_site_third_party_hold_at
             WHERE party_site_number = cgu.party_site_number
               AND cust_acct_site_id = hcas.cust_acct_site_id
               AND batch_number = cgu.batch_number
               AND process_flag = 'H')
ORDER BY hca.account_number, hps.party_site_number;
*/
