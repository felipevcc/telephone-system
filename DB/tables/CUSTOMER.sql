/*******************************************************************************
Description: Table that stores customer data
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.CUSTOMER
(
    CUSTOMER_ID NUMBER(10),
    CUSTOMER_TYPE_ID NUMBER(10) NOT NULL,
    NAME VARCHAR2(40) NOT NULL,
    LAST_NAME VARCHAR2(40) NOT NULL,
    BIRTHDATE DATE NOT NULL,
    DOCUMENT_TYPE_ID NUMBER(10) NOT NULL,
    DOCUMENT VARCHAR2(20) NOT NULL,
    ADDRESS VARCHAR2(100) NOT NULL,
    AREA_ID NUMBER(10) NOT NULL,
    EMAIL VARCHAR2(50) NOT NULL,
    PHONE_NUMBER VARCHAR2(20) NOT NULL,
    CREATED_AT DATE NOT NULL DEFAULT CURRENT_DATE
) TABLESPACE TS_APP_ASIG_NUM_TEL;
