/*******************************************************************************
Description: Foreign key constraint for relationship between CUSTOMER and CUSTOMER_TYPE
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.CUSTOMER
ADD CONSTRAINT FK_CUSTOMER_CUSTOMER_TYPE
FOREIGN KEY (CUSTOMER_TYPE_ID) REFERENCES APP_ASIG_NUM_TEL.CUSTOMER_TYPE (CUSTOMER_TYPE_ID);
