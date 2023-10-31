/*******************************************************************************
Description: Table that stores the geographical areas (neighborhoods) in the city of Cali
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.GEOGRAPHIC_AREA
(
    AREA_ID NUMBER,
    NAME VARCHAR2(50) NOT NULL,
    CODE NUMBER(10) NOT NULL,
    COMMUNE NUMBER(2) NOT NULL
) TABLESPACE TS_APP_ASIG_NUM_TEL;


COMMENT ON TABLE APP_ASIG_NUM_TEL.GEOGRAPHIC_AREA IS 'Table that stores the geographical areas (neighborhoods) in the city of Cali';


COMMENT ON COLUMN APP_ASIG_NUM_TEL.GEOGRAPHIC_AREA.AREA_ID IS 'Unique identifier for the neighborhood';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.GEOGRAPHIC_AREA.NAME  IS 'Represents the name of the neighborhood';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.GEOGRAPHIC_AREA.CODE  IS 'Represents the code of the neighborhood';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.GEOGRAPHIC_AREA.COMMUNE  IS 'Represents the commune of the neighborhood';
