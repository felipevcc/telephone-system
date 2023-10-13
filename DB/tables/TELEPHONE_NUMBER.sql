/*******************************************************************************
Description: Table that stores assigned telephone numbers to customers
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.TELEPHONE_NUMBER
(
    NUMBER_RECORD_ID NUMBER(10),
    CENTER_ID NUMBER(10) NOT NULL,
    CUSTOMER_ID NUMBER(10) NOT NULL,
    PHONE_NUMBER VARCHAR2(8) NOT NULL,
    ASSIGNMENT_DATE DATE NOT NULL DEFAULT CURRENT_DATE,
    RELEASE_DATE DATE NULL,
) TABLESPACE TS_APP_ASIG_NUM_TEL;
