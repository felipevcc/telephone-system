/*******************************************************************************
Description: Table that stores the history of telephone number assignments to customers
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT
(
    NUMBER_RECORD_ID NUMBER,
    CENTER_ID NUMBER NOT NULL,
    CUSTOMER_ID NUMBER NOT NULL,
    PHONE_NUMBER NUMBER(8) NOT NULL,
    ASSIGNMENT_DATE DATE NOT NULL,
    RELEASE_DATE DATE DEFAULT SYSDATE NOT NULL,
    IS_ACTIVE NUMBER(1) DEFAULT 1 NOT NULL
) TABLESPACE TS_APP_ASIG_NUM_TEL;


COMMENT ON TABLE APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT IS 'Table that stores the history of telephone number assignments to customers';


COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT.NUMBER_RECORD_ID IS 'Unique identifier for the number record';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT.CENTER_ID  IS 'Unique identifier for the center';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT.CUSTOMER_ID  IS 'Unique identifier for the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT.PHONE_NUMBER  IS 'Represents the telephone number';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT.ASSIGNMENT_DATE  IS 'Represents the date of assignment of the telephone number';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT.RELEASE_DATE  IS 'Represents the date of release of the telephone number';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT.IS_ACTIVE  IS 'Status of the number, 1 for partially released waiting for the required time and 0 for completely released';
