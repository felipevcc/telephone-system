/*******************************************************************************
Description: Table that stores assigned telephone numbers to customers
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.TELEPHONE_NUMBER
(
    NUMBER_RECORD_ID NUMBER,
    CENTER_ID NUMBER NOT NULL,
    CUSTOMER_ID NUMBER NOT NULL,
    PHONE_NUMBER NUMBER(8) NOT NULL,
    ASSIGNMENT_DATE DATE DEFAULT SYSDATE NOT NULL
) TABLESPACE TS_APP_ASIG_NUM_TEL;


COMMENT ON TABLE APP_ASIG_NUM_TEL.TELEPHONE_NUMBER IS 'Table that stores assigned telephone numbers to customers';


COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER.NUMBER_RECORD_ID IS 'Unique identifier for the number record';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER.CENTER_ID  IS 'Unique identifier for the center';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER.CUSTOMER_ID  IS 'Unique identifier for the customer';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER.PHONE_NUMBER  IS 'Represents the telephone number';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.TELEPHONE_NUMBER.ASSIGNMENT_DATE  IS 'Represents the date of assignment of the telephone number';
