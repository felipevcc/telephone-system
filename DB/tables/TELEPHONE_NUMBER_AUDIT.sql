/*******************************************************************************
Description: Table that stores the history of telephone number assignments to customers
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT
(
    NUMBER_RECORD_ID NUMBER(10),
    CENTER_ID NUMBER(10) NOT NULL,
    CUSTOMER_ID NUMBER(10) NOT NULL,
    PHONE_NUMBER VARCHAR2(8) NOT NULL,
    ASSIGNMENT_DATE DATE NOT NULL,
    RELEASE_DATE DATE NOT NULL,
) TABLESPACE TS_APP_ASIG_NUM_TEL;
