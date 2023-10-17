/*******************************************************************************
Description: Object type to create a center
Author: Andres Felipe Villamizar Collazos
Date 17-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE OR REPLACE TYPE APP_ASIG_NUM_TEL.TYP_CENTER_CREATION AS OBJECT
(
    NAME VARCHAR2(20),
    ADDRESS VARCHAR2(100),
    EMAIL VARCHAR2(50),
    PHONE_NUMBER VARCHAR2(20),
    INITIAL_NUMBER NUMBER(8),
    FINAL_NUMBER NUMBER(8),
);

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
