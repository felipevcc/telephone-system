/*******************************************************************************
Description: Table that stores customer document types
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.DOCUMENT_TYPE
(
    DOCUMENT_TYPE_ID NUMBER(10),
    TYPE_CODE VARCHAR2(10) NOT NULL,
    DESCRIPTION VARCHAR2(50) NOT NULL
) TABLESPACE TS_APP_ASIG_NUM_TEL;
