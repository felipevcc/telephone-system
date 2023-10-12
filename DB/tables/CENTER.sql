/*******************************************************************************
Description: Table that stores the data of the telephone centers
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.CENTER
(
    CENTER_ID NUMBER(10),
    NAME VARCHAR2(20) NOT NULL,
    ADDRESS VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR2(50) NOT NULL,
    PHONE_NUMBER VARCHAR2(20) NOT NULL,
    INITIAL_NUMBER NUMBER(8) NOT NULL,
    FINAL_NUMBER NUMBER(8) NOT NULL,
    CREATED_AT DATE NOT NULL
) TABLESPACE TS_APP_ASIG_NUM_TEL;
