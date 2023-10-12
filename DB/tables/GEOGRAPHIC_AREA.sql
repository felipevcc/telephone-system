/*******************************************************************************
Description: Table that stores the geographical areas (neighborhoods) in the city of Cali
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.GEOGRAPHIC_AREA
(
    AREA_ID NUMBER(10),
    NAME VARCHAR2(20) NOT NULL,
    CODE NUMBER(10) NOT NULL,
    COMMUNE NUMBER(2) NOT NULL
) TABLESPACE TS_APP_ASIG_NUM_TEL;
