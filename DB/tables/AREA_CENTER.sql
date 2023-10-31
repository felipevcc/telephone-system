/*******************************************************************************
Description: Table that stores the relationships between geographic areas and centers
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.AREA_CENTER
(
    AREA_ID NUMBER,
    CENTER_ID NUMBER
) TABLESPACE TS_APP_ASIG_NUM_TEL;


COMMENT ON TABLE APP_ASIG_NUM_TEL.AREA_CENTER IS 'Table that stores the relationships between geographic areas and centers';


COMMENT ON COLUMN APP_ASIG_NUM_TEL.AREA_CENTER.AREA_ID IS 'Unique identifier for the geographic area';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.AREA_CENTER.CENTER_ID  IS 'Unique identifier for the center';
