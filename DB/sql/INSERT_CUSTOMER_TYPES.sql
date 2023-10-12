/*******************************************************************************
Description: Insert customer types for classification into CUSTOMER_TYPE table
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

INSERT INTO APP_ASIG_NUM_TEL.DOCUMENT_TYPE (CUSTOMER_TYPE_ID, CUSTOMER_TYPE_NAME) VALUES (SEQ_CUSTOMER_TYPE.NEXTVAL, 'RESIDENCIAL');
INSERT INTO APP_ASIG_NUM_TEL.DOCUMENT_TYPE (CUSTOMER_TYPE_ID, CUSTOMER_TYPE_NAME) VALUES (SEQ_CUSTOMER_TYPE.NEXTVAL, 'COMERCIAL');
