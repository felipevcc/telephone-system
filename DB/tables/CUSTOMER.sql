/*******************************************************************************
Description: Table that stores customer data
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.CUSTOMER
(
    CUSTOMER_ID NUMBER,
    CUSTOMER_TYPE_ID NUMBER NOT NULL,
    NAME VARCHAR2(40) NOT NULL,
    LAST_NAME VARCHAR2(40) NOT NULL,
    BIRTHDATE DATE NOT NULL,
    DOCUMENT_TYPE_ID NUMBER NOT NULL,
    DOCUMENT VARCHAR2(20) NOT NULL,
    ADDRESS VARCHAR2(100) NOT NULL,
    AREA_ID NUMBER NOT NULL,
    EMAIL VARCHAR2(50) NOT NULL,
    PHONE_NUMBER VARCHAR2(20) NOT NULL,
    CREATED_AT DATE DEFAULT SYSDATE NOT NULL
) TABLESPACE TS_APP_ASIG_NUM_TEL;


COMMENT ON TABLE APP_ASIG_NUM_TEL.CUSTOMER IS 'Table that stores customer data';


COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.CUSTOMER_ID IS 'Unique identifier for the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.CUSTOMER_TYPE_ID  IS 'Unique identifier for the customer type';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.NAME  IS 'Represents the name of the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.LAST_NAME  IS 'Represents the last name of the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.BIRTHDATE  IS 'Represents the birthdate of the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.DOCUMENT_TYPE_ID  IS 'Represents the document type of the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.DOCUMENT  IS 'Represents the document of the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.ADDRESS  IS 'Represents the address of the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.AREA_ID  IS 'Unique indentifier for the geographic area of the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.EMAIL  IS 'Represents the email of the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.PHONE_NUMBER  IS 'Represents the phone number of the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CUSTOMER.CREATED_AT  IS 'Customer creation date';
