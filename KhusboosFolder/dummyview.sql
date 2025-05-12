CREATE OR REPLACE VIEW dummyView (PROPERTY_ID,
                                       PROPERTY_KEY,
                                         PROPERTY_VALUE,
                                       DESCRIPTION
                                  )
                       AS
                  SELECT PROPERTY_ID,
                                       PROPERTY_KEY,
                                       PROPERTY_VALUE,
                                      DESCRIPTION
      FROM t_tri2_properties cgu
               WHERE 1 = 1