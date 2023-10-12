/*******************************************************************************
Description: Foreign key constraint for relationship between TELEPHONE_NUMBER and CUSTOMER
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.TELEPHONE_NUMBER
ADD CONSTRAINT FK_TELEPHONE_NUMBER_CUSTOMER
FOREIGN KEY (CUSTOMER_ID) REFERENCES APP_ASIG_NUM_TEL.CUSTOMER (CUSTOMER_ID);
