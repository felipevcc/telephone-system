/*******************************************************************************
Description: Table that stores customer document types
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.DOCUMENT_TYPE
(
    DOCUMENT_TYPE_ID NUMBER,
    TYPE_CODE VARCHAR2(10) NOT NULL,
    DESCRIPTION VARCHAR2(50) NOT NULL
) TABLESPACE TS_APP_ASIG_NUM_TEL;


COMMENT ON TABLE APP_ASIG_NUM_TEL.DOCUMENT_TYPE IS 'Table that stores customer document types';


COMMENT ON COLUMN APP_ASIG_NUM_TEL.DOCUMENT_TYPE.DOCUMENT_TYPE_ID IS 'Unique identifier for the customer type';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.DOCUMENT_TYPE.TYPE_CODE  IS 'Represents the abbreviation of the document type';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.DOCUMENT_TYPE.DESCRIPTION  IS 'Represents the name of the document type';
