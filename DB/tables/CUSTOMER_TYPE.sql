/*******************************************************************************
Description: Table that stores customer types for classification
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.CUSTOMER_TYPE
(
    CUSTOMER_TYPE_ID NUMBER,
    CUSTOMER_TYPE_NAME VARCHAR2(30) NOT NULL
) TABLESPACE TS_APP_ASIG_NUM_TEL;


COMMENT ON TABLE APP_ASIG_NUM_TEL.CUSTOMER_TYPE IS 'Table that stores customer types for classification';


COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER_TYPE.CUSTOMER_TYPE_ID IS 'Unique identifier for the customer type';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER_TYPE.CUSTOMER_TYPE_NAME  IS 'Represents the name of the customer type';
