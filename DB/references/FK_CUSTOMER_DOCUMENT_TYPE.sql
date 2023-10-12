/*******************************************************************************
Description: Foreign key constraint for relationship between CUSTOMER and DOCUMENT_TYPE
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.CUSTOMER
ADD CONSTRAINT FK_CUSTOMER_DOCUMENT_TYPE
FOREIGN KEY (DOCUMENT_TYPE_ID) REFERENCES APP_ASIG_NUM_TEL.DOCUMENT_TYPE (DOCUMENT_TYPE_ID);
