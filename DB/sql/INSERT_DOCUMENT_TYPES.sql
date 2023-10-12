/*******************************************************************************
Description: Insert ID document types for customers into DOCUMENT_TYPE table
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

INSERT INTO APP_ASIG_NUM_TEL.DOCUMENT_TYPE (DOCUMENT_TYPE_ID, TYPE_CODE, DESCRIPTION) VALUES (SEQ_DOCUMENT_TYPE.NEXTVAL, 'CC', 'Cédula de ciudadanía');
INSERT INTO APP_ASIG_NUM_TEL.DOCUMENT_TYPE (DOCUMENT_TYPE_ID, TYPE_CODE, DESCRIPTION) VALUES (SEQ_DOCUMENT_TYPE.NEXTVAL, 'CE', 'Cédula de extranjería');
INSERT INTO APP_ASIG_NUM_TEL.DOCUMENT_TYPE (DOCUMENT_TYPE_ID, TYPE_CODE, DESCRIPTION) VALUES (SEQ_DOCUMENT_TYPE.NEXTVAL, 'PA', 'Pasaporte');
