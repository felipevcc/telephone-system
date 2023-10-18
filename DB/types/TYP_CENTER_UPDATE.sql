/*******************************************************************************
Description: Object type to update a center
Author: Andres Felipe Villamizar Collazos
Date 17-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE TYPE APP_ASIG_NUM_TEL.TYP_CENTER_UPDATE AS OBJECT
(
    CENTER_ID NUMBER(10),
    ADDRESS VARCHAR2(100),
    EMAIL VARCHAR2(50),
    PHONE_NUMBER VARCHAR2(20)
);
